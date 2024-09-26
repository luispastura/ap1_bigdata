package ap1_bigdata_luis.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AdultoValidator implements ConstraintValidator<Adulto, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; 
        }

        return Period.between(value, LocalDate.now()).getYears() >= 18;
    }
}