package conference.validator;

import conference.model.Log;
import conference.model.entity.Article;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ArticleValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Article.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Log.message("validator", "validator", this);
        errors.rejectValue("conference", "addArticle.conference");
        Article article = (Article) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "conference", "addArticle.conference");
    }
}
