package rw.dyna.ecommerce.v1.payloads;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ErrorResponse {
    private int status;
    private String message;
    private Date timestamp;
    private Object info = null;

    private List<String> details;

    public ErrorResponse(int status,String message,Date timestamp){
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }
}
