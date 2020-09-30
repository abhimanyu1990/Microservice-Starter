package poc.projectmgt.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import poc.projectmgt.enums.DefaultRoles;

@Component
public class RoleConfig {
	private JSONArray roles = new JSONArray();
	public static final String REPRESENTATION = "representationName";
	public static final String DESCRIPTION = "description";
	public static final String NAME ="name";
	public RoleConfig()  {
		
		JSONObject roleUser = new JSONObject();
		roleUser.put(RoleConfig.REPRESENTATION,"USER");
		roleUser.put(RoleConfig.NAME,DefaultRoles.ROLE_USER);
		roleUser.put(RoleConfig.DESCRIPTION,"This role have limited rights at organization level");
		this.roles.put(roleUser);
		
		JSONObject roleAdmin = new JSONObject();
		roleAdmin.put(RoleConfig.REPRESENTATION,"ADMIN");
		roleAdmin.put(RoleConfig.NAME,DefaultRoles.ROLE_ADMIN);
		roleAdmin.put(RoleConfig.DESCRIPTION,"The user with this role have all the rights");
		this.roles.put(roleAdmin);
		
		
		
	}
	
	
	public JSONArray getRoles() {
		return this.roles;
	}
}
