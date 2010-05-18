package org.mm.contact;

import java.net.URL;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jdesktop.swingx.auth.LoginService;
import org.mm.Application;

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
