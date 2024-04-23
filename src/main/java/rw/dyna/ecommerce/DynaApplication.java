package rw.dyna.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rw.dyna.ecommerce.v1.enums.Erole;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.repositories.IRoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class DynaApplication {

	private final IRoleRepository roleRepository;

	public DynaApplication(IRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public void registerRoles(){
		Set<Erole> roles = new HashSet<>();
		roles.add(Erole.ADMIN);
		roles.add(Erole.CLIENT);


		for (Erole role: roles){
			Optional<Role> roleByName = roleRepository.findByName(role);
			if(!roleByName.isPresent()){
				Role newRole = new Role(role,role.toString());
				roleRepository.save(newRole);
			}
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DynaApplication.class, args);
	}

}
