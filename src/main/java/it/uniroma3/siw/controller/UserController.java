package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validation.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.EditUser;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidator userValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SessionData sessionData;

    /**
     * This method is called when a GET request is sent by the user to URL "/users/user_id".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     */
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        model.addAttribute("user", loggedUser);
        return "homenew";
    }

    /**
     * This method is called when a GET request is sent by the user to URL "/users/user_id".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     */
    @RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
    public String me(Model model) {
        User loggedUser = sessionData.getLoggedUser();
        Credentials credentials = sessionData.getLoggedCredentials();
        model.addAttribute("user", loggedUser);
        model.addAttribute("credentials", credentials);

        return "userProfile";
    }

  
    
    @RequestMapping(value = {"/users/me/edit"}, method = RequestMethod.GET)
    public String userEdit(Model model){
    	User loggedUser = sessionData.getLoggedUser();
    	model.addAttribute("loggedUser", loggedUser);
    	model.addAttribute("editUserForm", new EditUser());
    	return "editUser";
    }
    @RequestMapping(value = {"/users/me/edit"}, method = RequestMethod.POST)
    public String userEditPost(@Valid @ModelAttribute("editUserForm") EditUser editUser, BindingResult bindingResult, Model model) {
    	userValidator.validateEditUser(editUser, bindingResult);
    	User loggedUser = sessionData.getLoggedUser();
		if(!bindingResult.hasErrors()) {
			boolean modified = false;
			if(!editUser.getFirstName().isBlank()) { 
				loggedUser.setFirstName(editUser.getFirstName());
				modified = true;
			}
			if(!editUser.getLastName().isBlank()) {
				loggedUser.setLastName(editUser.getLastName());
				modified = true;
			}
			if(modified) {
				this.userRepository.save(loggedUser);
			}
			Credentials credentials = sessionData.getLoggedCredentials();
			model.addAttribute("credentials", credentials);
			model.addAttribute("user", loggedUser);
			return "userProfile";
		}
		model.addAttribute("loggedUser", loggedUser);
    	model.addAttribute("editUserForm", editUser);
    	return "editUser";
		
    }

}
