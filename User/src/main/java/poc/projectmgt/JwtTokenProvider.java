package poc.projectmgt;

import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import poc.projectmgt.user.services.UserDetailsServiceImpl;

@Component
public class JwtTokenProvider {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value("${jwt.header.name}")
	private String header;

	@Value("${jwt.token.expire.milliseconds}")
	private long validityInMilliseconds; // 1h

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	 
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String email) {

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("auth",userDetailsServiceImpl.getPrivilegesByEmail(email));
		JSONObject userPrivileges = userDetailsServiceImpl.getOrganizationAndPrivileges(email);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		String jToken = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
		redisTemplate.opsForValue().set(email, jToken);
		redisTemplate.expireAt(email, validity);
		redisTemplate.opsForValue().set(email+"_auth",userPrivileges.toString());
		redisTemplate.expireAt(email+"_auth", validity);
		return jToken;
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
		 userDetails.getAuthorities());
		
		
	}

	public String getUsername(String token) {
		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(header);
		if (bearerToken != null) {
			return bearerToken;
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return (!claims.getBody().getExpiration().before(new Date()));
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}

}
