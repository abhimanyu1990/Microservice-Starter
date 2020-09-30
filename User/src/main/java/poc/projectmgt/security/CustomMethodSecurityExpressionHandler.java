package poc.projectmgt.security;

import org.aopalliance.intercept.MethodInvocation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;




@Component
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	 private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
	 
		 
		    @Override
		    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
		      Authentication authentication, MethodInvocation invocation) {
		    	String email = ((User)authentication.getPrincipal()).getUsername();
		    	JSONObject userPrivileges = new JSONObject(redisTemplate.opsForValue().get(email+"_auth"));
		        CustomMethodSecurityExpressionRoot root = 
		          new CustomMethodSecurityExpressionRoot(authentication,email,userPrivileges);
		        root.setPermissionEvaluator(getPermissionEvaluator());
		        root.setTrustResolver(this.trustResolver);
		        root.setRoleHierarchy(getRoleHierarchy());
		        return root;
		    }

}
