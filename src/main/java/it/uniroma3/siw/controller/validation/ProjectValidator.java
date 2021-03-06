package it.uniroma3.siw.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Project;
import it.uniroma3.siw.model.User;

@Component
public class ProjectValidator implements Validator{
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIPTION_LENGTH = 1000;
	
	@Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;
        String name = project.getName().trim();
        String description = project.getDescription().trim();

        if (name.isBlank())
            errors.rejectValue("name", "required");
        else if(name.length()<MIN_NAME_LENGTH||name.length()>MAX_NAME_LENGTH)
        	errors.rejectValue("name", "namesize");
        if(description.length()>MAX_DESCRIPTION_LENGTH)
        	errors.rejectValue("description", "descsize");
    }
	
	

	 @Override
	    public boolean supports(Class<?> clazz) {
	        return User.class.equals(clazz);
	    }



	public void validateName(String name, Errors errors) {
		
		 if(name.length()<MIN_NAME_LENGTH||name.length()>MAX_NAME_LENGTH)
	        	errors.rejectValue("name", "size");
	}
	public void validateDescription(String description, Errors errors) {
		 if(description.length()>MAX_DESCRIPTION_LENGTH)
	        	errors.rejectValue("description", "size");
}
}
