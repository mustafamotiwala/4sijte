package org.mm.contact;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jdesktop.swingx.auth.LoginService;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 8:21:23 PM
 */
public class GoogleLoginServiceAdapter extends LoginService {
    @Inject
    @Named("svcContacts")
    private ContactsService service;

    @Override
    public boolean authenticate(String username, char[] password, String s1) throws Exception {
        try {
            service.setUserCredentials(username, new String(password));
            return true;
        } catch (AuthenticationException ae) {
            return false;
        }
    }
}
