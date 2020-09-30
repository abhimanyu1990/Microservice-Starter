package poc.projectmgt.user.controllers;



import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import poc.projectmgt.config.PrivilegeConfig;


@RestController
@RequestMapping("/api/v1")
public class PermissionController {
	
	@Autowired
	private PrivilegeConfig privilegesDetailConfig;
	
	@GetMapping(value = "/privileges}")
	public JSONArray getPrevileges() {
		return privilegesDetailConfig.getPermissions();
	
	}

}
