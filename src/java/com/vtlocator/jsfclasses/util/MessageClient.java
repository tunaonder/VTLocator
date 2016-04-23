/*
 * Created by Sean Goodrich on 2016.04.22  * 
 * Copyright © 2016 Sean Goodrich. All rights reserved. * 
 */
package com.vtlocator.jsfclasses.util;

import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 *
 * @author Sean Goodrich
 */
public class MessageClient {
    
    
    
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "ACa2c822a7f1d8e2544e6c15f978e5f64f";
    public static final String AUTH_TOKEN = "9457fc58b612411e10fc85dd9d4f0639";
    
    public MessageClient() {
    }
    
    public static final String MESSAGE_FOOTER = " -- This message was sent by VTLocator.";
    public static boolean sendMessage(String number, String message) {
         try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
            Account account = client.getAccount();
            MessageFactory messageFactory = account.getMessageFactory();
            List<NameValuePair> params = new ArrayList();
            params.add(new BasicNameValuePair("To", "+1" + number));
            params.add(new BasicNameValuePair("From", "+14849993473 "));
            params.add(new BasicNameValuePair("Body", message + MESSAGE_FOOTER));
            Message sms = messageFactory.create(params);
            return true;
        } catch (TwilioRestException e) {
            return false;
        }
  
    }
}
