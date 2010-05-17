package org.mm.contact;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.mm.contact.ui.ContactListModel;

/**
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 5:37:17 PM
 */
public class Application extends JFrame {
    private static final Log log = LogFactory.getLog(Application.class);

    public Application(String username, String password) {
        super("Google Contact Application");
        try {
            final ContactsService service = new ContactsService("Contact Organizer.");
            service.setUserCredentials(username, password);
            URL urlContactsService = new URL(String.format("http://www.google.com/m8/feeds/contacts/%1$s/full", username));
            Query contactQuery = new Query(urlContactsService);
            contactQuery.setMaxResults(Integer.MAX_VALUE);
            ContactFeed resultFeed = service.getFeed(contactQuery, ContactFeed.class);
            List<ContactEntry> contacts = resultFeed.getEntries();
            JXList list = new JXList(new ContactListModel(contacts));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addHighlighter(HighlighterFactory.createAlternateStriping());
            JScrollPane jspContacts = new JScrollPane(list);
            jspContacts.setColumnHeaderView(new JXLabel("Contacts", JXLabel.CENTER));
            setLayout(new MigLayout("wrap", "[grow]", "[grow][]"));
            add(jspContacts, "grow");
        } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(null, "Unable to authenticate.", "Contact Organizer", JOptionPane.ERROR_MESSAGE);
            log.info("Unable authenticate");
            System.exit(1);
        } catch (ServiceException e) {
            log.error(e);
        } catch (MalformedURLException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static void main(String args[]) {
        if (args.length < 2) {
            printUsage();
            System.exit(1);
        }
        Application app = new Application(args[1], args[2]);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setSize(400, 200);
    }

    private static void printUsage() {
        System.out.println("Usage: java org.mm.contact.Application <username> <password>");
    }
}
