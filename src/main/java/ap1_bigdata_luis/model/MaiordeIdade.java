package ap1_bigdata_luis.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatorMaiordeIdade.class)
public @interface MaiordeIdade {
    String message() default "O cliente deve ter mais de 18 anos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
