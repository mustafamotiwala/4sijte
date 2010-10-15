package org.mm.contact.ui;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.inject.Inject;
import org.mm.ApplicationTab;
import org.mm.contact.ContactListModel;

import javax.swing.*;
import java.util.List;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 6:39:26 PM
 */
public class ContactTab implements ApplicationTab {
//    private JXPanel tabContent = new JXPanel();
//    private final JXList list = new JXList();

    public ContactTab() {
//        JXPanel contactDetails = new JXPanel();
//        tabContent.setLayout(new MigLayout("", "[grow]", "[grow]"));
//        contactDetails.setLayout(new MigLayout());
    }

    @Override
    public JPanel getTab() {
        return new JPanel();
//        return tabContent;
    }

    @Override
    public void userLoggedIn() {
//        tabContent.removeAll();
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.addHighlighter(HighlighterFactory.createAlternateStriping());
//        JScrollPane jspContacts = new JScrollPane(list);
//        jspContacts.setColumnHeaderView(new JXLabel("Contacts", JXLabel.CENTER));
//        tabContent.add(jspContacts, "grow");
//        tabContent.validate();
    }

    public void setContacts(List<ContactEntry> contacts) {
//        list.setModel(new ContactListModel(contacts));
    }

    @Override
    public String getName() {
        return "Contacts";
    }

    @Inject
    public void register(List<ApplicationTab> tabs) {
        tabs.add(this);
    }
}
