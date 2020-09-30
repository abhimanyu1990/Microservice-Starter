package poc.projectmgt.security;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;




public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

	public static final Logger LOGGER = LoggerFactory.getLogger(CustomMethodSecurityExpressionRoot.class);
    private Object filterObject;
    private Object returnObject;

   
    private String email;
   
    private JSONObject userPrivileges;
    
    public CustomMethodSecurityExpressionRoot(Authentication authentication, String email,  JSONObject userPrivileges) {
    	super(authentication);
    	this.email = email;
    	this.userPrivileges = userPrivileges;
   
    }

    //
	public boolean isMemberAndHaveAuthority(Long organizationId, String authority) {
		String key = this.email+"_auth";
		JSONArray jsonArray = userPrivileges.getJSONArray(key);
		for(Object item: jsonArray) {
			JSONObject orgPrivilege = (JSONObject) item;
			JSONArray privileges =  (JSONArray) (orgPrivilege).get("privileges");
			Long orgId = orgPrivilege.getLong("id");
			if(orgId == organizationId.longValue() && privileges.toList().contains(authority)) {
				return true;
			}
		}	
		return false;
	}

    //

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }

}
