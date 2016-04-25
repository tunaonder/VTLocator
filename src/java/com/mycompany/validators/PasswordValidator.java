/*
 * Created by Sait Tuna Onder on 2016.02.18  * 
 * Copyright © 2016 Sait Tuna Onder. All rights reserved. * 
 */
package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
/**
 * This validator validates a password using a regular expression
 * @author Onder
 */
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        // Typecast the password "value" entered by the user to String.
        String password = (String) value;

        if (password == null || password.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
       
        /*** REGular EXpression (regex) for validating password strength ***
         * (?=.{8,31})      ==> Validate the password to be minimum 8 and maximum 31 characters long.
         * (?=.*[!@#$%^&*]) ==> Validate the password to contain at least one special character.
         * (?=.*[A-Z])      ==> Validate the password to contain at least one uppercase letter.
         * (?=.*[a-z])      ==> Validate the password to contain at least one lowercase letter.
         * (?=.*[0-9])      ==> Validate the password to contain at least one number from 0 to 9.
         */
        String regex = "^(?=.{8,31})(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";
        
        if (!password.matches(regex)) {
            throw new ValidatorException(new FacesMessage("The password must be minimum 8 "
                    + "characters long, contain at least one special character, "
                    + "contain at least one uppercase letter, "
                    + "contain at least one lowercase letter, "
                    + "and contain at least one number 0 to 9."));
        }        
    } 
    
}