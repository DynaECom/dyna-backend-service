package rw.dyna.ecommerce.v1.dtos;

import lombok.Getter;
import lombok.Setter;
import rw.dyna.ecommerce.v1.models.LocationAddress;

@Getter
@Setter
public class GetAllLocationsDTO {

    private LocationAddress province;

    private LocationAddress district;

    private LocationAddress sector;

    private LocationAddress cell;

    private LocationAddress village;
}
