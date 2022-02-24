package com.portafolio;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.portafolio.security.entity.Role;
import com.portafolio.security.entity.User;
import com.portafolio.security.enums.RoleName;
import com.portafolio.security.service.RoleService;
import com.portafolio.security.service.UserService;

@SpringBootApplication
public class PortafolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortafolioApplication.class, args);
	}
	
	/*@Bean
	CommandLineRunner run(UserService userService, RoleService roleService) {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(RoleName.ROLE_ADMIN));
		
		return args -> {
			roleService.save(new Role(RoleName.ROLE_ADMIN));
			roleService.save(new Role(RoleName.ROLE_USER));
			
			userService.save(new User(null, "Fran", "Fran", "fran@fran.com", roles));
			userService.saveUser(new User(null, "Will Smith", "will", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));
			
			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("john", "ROLE_MANAGER");
			userService.addRoleToUser("will", "ROLE_MANAGER");
			userService.addRoleToUser("jim", "ROLE_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_USER");
		};
	}*/

}
