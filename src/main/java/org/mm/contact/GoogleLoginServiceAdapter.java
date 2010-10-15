package org.mm.contact;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 8:21:23 PM
 */
public class GoogleLoginServiceAdapter {
    @Inject
    @Named("svcContacts")
    private ContactsService service;

    public boolean authenticate(String username, char[] password) throws Exception {
        try {
            service.setUserCredentials(username, new String(password));
            return true;
        } catch (AuthenticationException ae) {
            return false;
        }
    }
}
