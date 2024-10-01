package ap1_bigdata_luis.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class ValidatorMaiordeIdade implements ConstraintValidator<MaiordeIdade, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;  // Pode ajustar dependendo se nulo é permitido ou não
        }

        // Calcula a idade da pessoa
        return Period.between(value, LocalDate.now()).getYears() >= 18;
    }
}