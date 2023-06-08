package it.uniroma3.siw.siwmoviesav.controller.validator;

import it.uniroma3.siw.siwmoviesav.model.User;
import it.uniroma3.siw.siwmoviesav.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if(userService.alreadyExists(user)){
            errors.reject("user.alreadyRegistered");
        }
    }
}
