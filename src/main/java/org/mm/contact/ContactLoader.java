package org.mm.contact;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.pivot.wtk.Action;
import org.mm.action.LoginAction;
import org.mm.contact.ui.ContactTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 4:39:40 PM
 */
public class ContactLoader implements Callable<Boolean> {
    private static Logger log = LoggerFactory.getLogger(ContactLoader.class);
    @Inject
    @Named("svcContacts")
    private ContactsService service;
    @Inject
    private ContactTab contactTab;
    @Inject @Named("loginAction")
    private Action loginAction;

    @Override
    public Boolean call() throws Exception {
        String username = ((LoginAction)loginAction).getUsername();
        String serviceUrlLocation = String.format("http://www.google.com/m8/feeds/contacts/%1$s/full", username);
        try {
            Query contactQuery = new Query(new URL(serviceUrlLocation));
            contactQuery.setMaxResults(1);
            ContactFeed resultFeed = service.getFeed(contactQuery, ContactFeed.class);
              contactTab.setContacts(resultFeed.getEntries());
//            contactTab.userLoggedIn();
            log.info("Authenticated successfully.");
//            app.makeAvailable();
        } catch (ServiceForbiddenException forbidden) {
            throw forbidden;
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    app.makeAvailable();
//                    JOptionPane.showMessageDialog(app, "Access was denied querying your account.\nRetrying with using your domain name may help solve the problem.", "Contact Organizer", JOptionPane.ERROR_MESSAGE);
//                }
//            });
        } catch (ServiceException e) {
            log.error("Service Exception:", e);
            throw e;
        } catch (MalformedURLException e) {
            log.error("Incorrect URL specified:",e);
//            throw e;
        } catch (IOException e) {
            log.error("IOException: ",e);
            throw e;
        }
        return true;
    }
}
