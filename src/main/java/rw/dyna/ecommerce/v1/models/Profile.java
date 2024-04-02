package rw.dyna.ecommerce.v1.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Profile {
    Object profile;

    public User asUser() {
        return (User) profile;
    }

    public Client asClient(){return (Client) profile;}

    public Administrator asAdministrator(){return (Administrator) profile;}

}
