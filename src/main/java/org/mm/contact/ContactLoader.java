package org.mm.contact;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mm.contact.ui.ContactTab;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 4:39:40 PM
 */
public class ContactLoader implements Runnable {
    private static Log log = LogFactory.getLog(ContactLoader.class);
    @Inject
    @Named("svcContacts")
    private ContactsService service;
    @Inject
    private ContactTab contactTab;

    @Override
    public void run() {
//        final PIMApplication app = PIMApplication.getInstance();
//        try {
//            Query contactQuery = new Query(app.getServiceUrl());
//            contactQuery.setMaxResults(Integer.MAX_VALUE);
//            ContactFeed resultFeed = service.getFeed(contactQuery, ContactFeed.class);
//            contactTab.setContacts(resultFeed.getEntries());
//            contactTab.userLoggedIn();
//            log.info("Contacts loaded...");
//            app.makeAvailable();
//        } catch (ServiceForbiddenException forbidden) {
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    app.makeAvailable();
//                    JOptionPane.showMessageDialog(app, "Access was denied querying your account.\nRetrying with using your domain name may help solve the problem.", "Contact Organizer", JOptionPane.ERROR_MESSAGE);
//                }
//            });
//        } catch (ServiceException e) {
//            log.error("Service Exception:", e);
//        } catch (MalformedURLException e) {
//            log.error(e);
//        } catch (IOException e) {
//            log.error(e);
//        }
    }
}
