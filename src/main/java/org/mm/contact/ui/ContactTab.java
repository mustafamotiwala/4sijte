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

    public ContactTab() {
    }

    @Override
    public JPanel getTab() {
        return null;
    }

    @Override
    public void userLoggedIn() {
    }

    public void setContacts(List<ContactEntry> contacts) {
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
