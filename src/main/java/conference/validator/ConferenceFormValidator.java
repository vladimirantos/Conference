package conference.validator;

import conference.model.Log;
import conference.model.entity.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class ConferenceFormValidator implements Validator{

    private static final String YEAR_PATTERN = "^(19[9-9]\\d|20[0-4]\\d|2020)$";

    @Override
    public boolean supports(Class<?> clazz) {
        return Conference.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Conference conference = (Conference)target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "addconf.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "theme", "addconf.theme");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "addconf.city");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "addconf.state");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "month", "addconf.month");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "addconf.year");

        if(!conference.getYear().matches(YEAR_PATTERN))
            errors.rejectValue("year", "addconf.yearNumber");

        if(conference.getMonth().equals("0"))
            errors.rejectValue("month", "addconf.month");
    }
}
