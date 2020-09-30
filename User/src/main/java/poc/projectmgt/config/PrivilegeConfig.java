package poc.projectmgt.config;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import poc.projectmgt.enums.DefaultRoles;

@Component
public class PrivilegeConfig {
	private JSONArray permissions = new JSONArray();
	private JSONArray organizationAdminPermissions = new JSONArray();
	
	public static final String VALUE = "value";
	public static final String DESCRIPTION = "description";
	public static final String NAME ="name";
	public static final String ACCESSLEVEL ="accessLevel";
	
	public PrivilegeConfig() {
		
		JSONObject permissionCreateAnyUser = new JSONObject();
		permissionCreateAnyUser.put(PrivilegeConfig.VALUE,"PERMISSION_CREATE_ANY_USER");
		permissionCreateAnyUser.put(PrivilegeConfig.NAME,"CREATE NEW USER");
		permissionCreateAnyUser.put(PrivilegeConfig.DESCRIPTION,"This permission is to create a new user");
		permissionCreateAnyUser.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionCreateAnyUser);
		
		JSONObject permissionReadAnyUser = new JSONObject();
		permissionReadAnyUser.put(PrivilegeConfig.VALUE,"PERMISSION_READ_ANY_USER");
		permissionReadAnyUser.put(PrivilegeConfig.NAME,"FIND A USER DETAIL");
		permissionReadAnyUser.put(PrivilegeConfig.DESCRIPTION,"This permission is to read detail about other user");
		permissionReadAnyUser.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionReadAnyUser);
		
		JSONObject permissionUpdateAnyUser = new JSONObject();
		permissionUpdateAnyUser.put(PrivilegeConfig.VALUE,"PERMISSION_UPDATE_ANY_USER");
		permissionUpdateAnyUser.put(PrivilegeConfig.NAME,"UPDATE ANY USER DETAIL");
		permissionUpdateAnyUser.put(PrivilegeConfig.DESCRIPTION,"This permission is to update any user detail");
		permissionUpdateAnyUser.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionUpdateAnyUser);
		
		JSONObject permissionDeleteAnyUser = new JSONObject();
		permissionDeleteAnyUser.put(PrivilegeConfig.VALUE,"PERMISSION_DELETE_ANY_USER");
		permissionDeleteAnyUser.put(PrivilegeConfig.NAME,"DELETE ANY USER DETAIL");
		permissionDeleteAnyUser.put(PrivilegeConfig.DESCRIPTION,"This permission is to delete any user detail");
		permissionDeleteAnyUser.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionDeleteAnyUser);
		
		JSONObject permissionListUser = new JSONObject();
		permissionListUser.put(PrivilegeConfig.VALUE,"PERMISSION_LIST_USER");
		permissionListUser.put(PrivilegeConfig.NAME,"LIST ALL USERS");
		permissionListUser.put(PrivilegeConfig.DESCRIPTION,"This permission is to list all the users");
		permissionListUser.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionListUser);
	
		JSONObject permissionListUserOfAnyORG = new JSONObject();
		permissionListUserOfAnyORG.put(PrivilegeConfig.VALUE,"PERMISSION_LIST_ANY_ORG_USER");
		permissionListUserOfAnyORG.put(PrivilegeConfig.NAME,"LIST ALL USER OF ANY ORGANIZATION");
		permissionListUserOfAnyORG.put(PrivilegeConfig.DESCRIPTION,"This permission is to list all the users of any organization");
		permissionListUserOfAnyORG.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_ADMIN);
		this.permissions.put(permissionListUserOfAnyORG);
		this.organizationAdminPermissions.put(permissionListUserOfAnyORG);
		
		JSONObject permissionReadProfile = new JSONObject();
		permissionReadProfile.put(PrivilegeConfig.VALUE,"PERMISSION_READ_PROFILE");
		permissionReadProfile.put(PrivilegeConfig.NAME,"READ OWN PROFILE");
		permissionReadProfile.put(PrivilegeConfig.DESCRIPTION,"This permission is get own profile");
		permissionReadProfile.put(PrivilegeConfig.ACCESSLEVEL, DefaultRoles.ROLE_USER);
		this.permissions.put(permissionReadProfile);
		this.organizationAdminPermissions.put(permissionReadProfile);
		
	}
	
	
	public JSONArray getPermissions() {
		return this.permissions;
	}
	
	
	
	public JSONArray getAdminPermission(String requestedRole) {
		if ( requestedRole != null && requestedRole.equals(DefaultRoles.ROLE_ADMIN.toString())){
			return this.permissions;
		}
		return null;
	}
}
