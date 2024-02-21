package rw.dyna.ecommerce.v1.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Date timestamp;
    private Object info = null;

    public ErrorResponse(int status,String message,Date timestamp){
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }
}
