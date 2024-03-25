package rw.dyna.ecommerce.v1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.dyna.ecommerce.v1.dtos.CreateLocationAddressDTO;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Table(name="address")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue
    private UUID id;
    private String country;

    private String province;

    private String district;

    private String sector;

    private String cell;

    private String village;

    private String streetName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    public Address(CreateLocationAddressDTO dto) {
        this.setCountry(dto.getCountry());
        this.setProvince(dto.getProvince());
        this.setDistrict(dto.getDistrict());
        this.setSector(dto.getSector());
        this.setCell(dto.getCell());
        this.setVillage(dto.getVillage());
        this.setStreetName(dto.getStreetName());
    }
}
