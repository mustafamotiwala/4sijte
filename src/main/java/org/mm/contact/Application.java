package org.mm.contact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.*;

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
    private List<ContactEntry> contacts;
    private static Application app;
    private ContactsService contactsService;
    private URL urlContactsService;

    public Application(String file, String username, String password){
        super("Google Contact Application");
        try {
            contactsService = new ContactsService("Contact Synchronizer.");
            contactsService.setUserCredentials(username,password);
            urlContactsService = new URL(String.format("http://www.google.com/m8/feeds/contacts/%1$s/full", username));
            Query contactQuery = new Query(urlContactsService);
            contactQuery.setMaxResults(Integer.MAX_VALUE);
            ContactFeed resultFeed = contactsService.getFeed(contactQuery, ContactFeed.class);
//            ContactReader cr = new ContactReader(new FileInputStream(file));
//            contacts = cr.read();
            contacts = resultFeed.getEntries();
            JXList list = new JXList(new ContactListModel(contacts));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addHighlighter(HighlighterFactory.createAlternateStriping());
            JScrollPane jspContacts = new JScrollPane(list);
            jspContacts.setColumnHeaderView(new JXLabel("Contacts",JXLabel.CENTER));
            setLayout(new MigLayout("wrap","[grow]", "[grow][]"));
            add(jspContacts, "grow");
            add(new JButton( new LoadContactsAction()));
        } catch (FileNotFoundException e) {
            log.info("Could not find file to load in given path:" + file);
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

    public static void main(String args[]){
        if(args.length < 3){
            printUsage();
            System.exit(1);
        }
        app = new Application(args[0], args[1], args[2]);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setSize(400,200);
    }

    private static void printUsage() {
        System.out.println("Usage: java org.mm.contact.Application <file to load> <username> <password>");
    }

    public void loadContacts() throws IOException, ServiceException {
        long startMilliseconds = System.currentTimeMillis();
        for(ContactEntry contact:contacts){
            contactsService.insert(urlContactsService, contact);
        }
        long totalMilliseconds = System.currentTimeMillis() - startMilliseconds;
        long totalTimeInSeconds = totalMilliseconds / 1000;
        log.info(String.format("Total time to load all contacts: %1$d", totalTimeInSeconds));
        log.info(String.format("Average time to load a contact: %1$d", totalTimeInSeconds /contacts.size()));
    }

    public static Application getInstance(){
        return app; 
    }
}
