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

@FacesValidator("emailValidator")
/**
 * This class validates an email address using a regular expression (regex).
 * @author Onder
 */
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        // Typecast the password "value" entered by the user to String.
        String email = (String) value;

        if (email == null || email.isEmpty()) {
            // Do not take any action. 
            // The required="true" in the XHTML file will catch this and produce an error message.
            return;
        }
       /**
        * regex stands for REGular EXpression, which defines a search pattern for strings.
        * To learn about how to use regex, see https://docs.oracle.com/javase/tutorial/essential/regex/
        **/
        
        // Validate if the email address entered by the user is in the right format.
        String regex = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
        if (!email.matches(regex)) {
            throw new ValidatorException(new FacesMessage("Please Enter a Valid Email Address!"));
        }        
    } 
    
}
