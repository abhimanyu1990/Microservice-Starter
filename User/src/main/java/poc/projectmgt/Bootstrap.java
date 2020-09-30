package poc.projectmgt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import poc.projectmgt.config.PrivilegeConfig;
import poc.projectmgt.config.RoleConfig;
import poc.projectmgt.enums.DefaultRoles;
import poc.projectmgt.enums.UserStatus;
import poc.projectmgt.user.entities.Organization;
import poc.projectmgt.user.entities.Privilege;
import poc.projectmgt.user.entities.Role;
import poc.projectmgt.user.entities.User;
import poc.projectmgt.user.repositories.OrganizationRepository;
import poc.projectmgt.user.repositories.PrivilegeRepository;
import poc.projectmgt.user.repositories.RoleRepository;
import poc.projectmgt.user.repositories.UserRepository;


@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	private PrivilegeConfig privilegesConfig;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleConfig roleConfig;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createOrganizationIfNotExist();
		
		
		
	}
	
	
	
	private  void createOrganizationIfNotExist() {
		Organization org  = organizationRepository.findByOrganizationName("DemoComp");
		if(org == null) {
			Organization organization = new Organization();
			organization.setOrganizationName("DemoComp");
			organization = organizationRepository.save(organization);
			createPrivilegeIfNotExist(organization);
			createRolesIfNotExist(organization);
			createAdminIfNotExist(organization);
		}
		
	}
	
	
	private  void createAdminIfNotExist(Organization organization) {
		List<Role> roles = roleRepository.findAll();
		User user = new User();
		HashSet<Organization> organizationSet = new HashSet<>();
		organizationSet.add(organization);
		User adminUser = userRepository.findByEmail("example@admin.com");
		if(adminUser == null) {
		HashSet<Role> roleSet = new HashSet<>(roles);
		user.setEmail("example@admin.com");
		user.setFirstName("admin");
		user.setLastName("example");
		user.setAccountLocked(false);
		user.setStatus(UserStatus.ACTIVE);
		user.setPassword(passwordEncoder.encode("example"));
		user.setRoles(roleSet);
		user.setOrganizations(organizationSet);
		userRepository.saveAndFlush(user);
		}
	}
	
	
	private void createRolesIfNotExist(Organization organization) {
		
		JSONArray configuredRoles = roleConfig.getRoles();
		List<Role> roleList = new ArrayList<>();
		Pageable limit = PageRequest.of(0, 1);
		Page<Role> alreadySavedRoles = roleRepository.findAll(limit);
		if(alreadySavedRoles.getTotalElements()== 0) {
		configuredRoles.forEach(item -> {
			JSONObject obj = (JSONObject)item;
			List<Privilege> availablePrivileges =	privilegeRepository.findAllByOrganizationAndAccessLevel(organization, DefaultRoles.valueOf(obj.get("name").toString()));
			Set<Privilege> privilegeSet = new HashSet<>(availablePrivileges);
			Role role = new Role();
			role.setName(obj.get("name").toString());
			role.setDescription(obj.get("description").toString());
			role.setRepresentationName(obj.get("representationName").toString());
			role.setPrivileges(privilegeSet);
			role.setOrganization(organization);
			roleList.add(role);
		});
		roleRepository.saveAll(roleList);
		}
	}
	
	
	private void createPrivilegeIfNotExist(Organization organization) {
		JSONArray permissions = privilegesConfig.getPermissions();
		Pageable limit = PageRequest.of(0,1);
		Page<Privilege> page = privilegeRepository.findAll(limit);
		if(page.getNumberOfElements() == 0) {
			List<Privilege> privileges = new ArrayList<>();
			permissions.forEach(i -> {
				JSONObject obj = (JSONObject) i;
				Privilege privilege = new Privilege();
				privilege.setName(obj.get("name").toString());
				privilege.setValue(obj.get("value").toString());
				privilege.setDescription(obj.get("description").toString());
				privilege.setAccessLevel(DefaultRoles.valueOf(obj.get("accessLevel").toString()));
				privilege.setOrganization(organization);
				privileges.add(privilege);
			});
			privilegeRepository.saveAll(privileges);
			
		}
	}

}
