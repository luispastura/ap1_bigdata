package ap1_bigdata_luis.exception;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
}
