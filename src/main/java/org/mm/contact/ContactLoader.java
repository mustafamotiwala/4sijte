package org.mm.contact;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.mm.Application;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 4:39:40 PM
 */
public class ContactLoader implements Runnable{
    private static Log log = LogFactory.getLog(ContactLoader.class);
    @Inject
    @Named("svcContacts")
    private ContactsService service;

    @Override
    public void run() {
        Application app = Application.getInstance();
        try {
            Query contactQuery = new Query(app.getServiceUrl());
            contactQuery.setMaxResults(Integer.MAX_VALUE);
            ContactFeed resultFeed = service.getFeed(contactQuery, ContactFeed.class);
            List<ContactEntry> contacts = resultFeed.getEntries();
            JXList list = new JXList(new ContactListModel(contacts));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addHighlighter(HighlighterFactory.createAlternateStriping());
            JScrollPane jspContacts = new JScrollPane(list);
            jspContacts.setColumnHeaderView(new JXLabel("Contacts", JXLabel.CENTER));
            app.add(jspContacts, "grow");
            app.validate();
            log.info("Contacts loaded...");
            app.makeAvailable();
        } catch (ServiceException e) {
            log.error("Service Exception:",e);
        } catch (MalformedURLException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
