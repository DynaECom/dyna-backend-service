package rw.dyna.ecommerce.v1.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.models.Role;
import rw.dyna.ecommerce.v1.models.User;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private User user;
    private Set<Role> roles;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String rolesToString() {
        String rolesString = "";
        for (Role role : roles) {
            rolesString += role.getName() + ", ";
        }
        return rolesString;
    }
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", user=" + user +
                ", roles=" + roles +
                '}';
    }
}
