package rw.dyna.ecommerce.v1.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rw.dyna.ecommerce.v1.models.Administrator;
import rw.dyna.ecommerce.v1.models.Client;
import rw.dyna.ecommerce.v1.models.User;


public class Mapper {

    public static ModelMapper modelMapper = new ModelMapper();
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User getUserFromDTO(Object object, String password) {
        User user = getUserFromDTO(object);
        user.setPassword(passwordEncoder.encode(password));
        user.setId(null);
        return user;
    }

    public static Client getClientFromDTO(Object object, String password){
        Client client = modelMapper.map(object, Client.class);
        client.setPassword(passwordEncoder.encode(password));
        client.setId(null);
        return client;
    }

    public static Administrator getAdministratorFromDTO(Object object, String password){
        Administrator administrator = modelMapper.map(object, Administrator.class);
        administrator.setPassword(passwordEncoder.encode(password));
        administrator.setId(null);
        return administrator;
    }

    public static String encode(String raw){
        return passwordEncoder.encode(raw);
    }

    public static boolean compare(String encoded, String raw){
        return passwordEncoder.matches(raw, encoded);
    }

    public static User getUserFromDTO(Object object) {
        return modelMapper.map(object, User.class);
    }


}
