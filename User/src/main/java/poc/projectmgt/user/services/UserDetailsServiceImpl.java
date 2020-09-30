package poc.projectmgt.user.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import poc.projectmgt.customexceptions.GenericAuthenticationException;
import poc.projectmgt.customexceptions.GenericNotFoundException;
import poc.projectmgt.user.entities.Organization;
import poc.projectmgt.user.entities.Privilege;
import poc.projectmgt.user.entities.Role;
import poc.projectmgt.user.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;
	
	poc.projectmgt.user.entities.User user;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		
		user = userRepository.findByEmail(email);
		if(user != null) {
			return new User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
		}
		throw new GenericAuthenticationException("Bad Credentials");
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}
	
	private List<String> getPrivileges(Collection<Role> roles) {
		List<String> privilegeList = new ArrayList<>();
        roles.forEach(role ->{
        	Set<Privilege> privileges = role.getPrivileges();
        	privileges.forEach(privilege -> {
        		LOGGER.info(privilege.getValue());
        		privilegeList.add(privilege.getValue());
        	});
        });
        return privilegeList;
    }
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
	
	
	public List<String> getPrivilegesByEmail(String email) {
		user = userRepository.findByEmail(email);
		if(user != null) {
			return getPrivileges(user.getRoles());
		}
		throw new GenericNotFoundException("User not found");
	}
	
	public JSONObject getOrganizationAndPrivileges(String email) {
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		user = userRepository.findByEmail(email);
		if(user != null) {
			Set<Role> roles = user.getRoles();
			roles.forEach(role ->{
				Organization organization = role.getOrganization();
				Set<Privilege> privileges = role.getPrivileges();
				ArrayList<String> privilegeList = new ArrayList<>();
				privileges.forEach(priv -> privilegeList.add(priv.getValue()));
				JSONObject orgPrivJson = new JSONObject();
				orgPrivJson.put("id", organization.getId());
				orgPrivJson.put("orgName",organization.getOrganizationName());
				orgPrivJson.put("privileges",privilegeList);
				jsonArray.put(orgPrivJson);
			});
			String key = email+"_auth";
			json.put(key,jsonArray);
			LOGGER.info("Orgasnization Privileges"+json.toString());
			return json;
		}
		return null;
	}

}
