package org.mm.contact;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.concurrent.Callable;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 8:21:23 PM
 */
public class GoogleLoginServiceAdapter implements Callable<Boolean> {
    @Inject
    @Named("svcContacts")
    private ContactsService service;

    private String username, password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Boolean call() throws Exception {
        return this.authenticate(username, password);
    }

    private boolean authenticate(String username, String password) throws Exception {
        try {
            service.setUserCredentials(username, password);
            return true;
        } catch (AuthenticationException ae) {
            return false;
        }
    }
}
