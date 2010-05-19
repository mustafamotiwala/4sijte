package org.mm.contact.ui;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.inject.Inject;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.mm.ApplicationTab;
import org.mm.contact.ContactListModel;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 6:39:26 PM
 */
public class ContactTab implements ApplicationTab {
    private JXPanel tabContent;
    private final JXList list = new JXList();

    public ContactTab() {
        tabContent = new JXPanel();
        tabContent.setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    @Override
    public JPanel getTab() {
        return tabContent;
    }

    @Override
    public void userLoggedIn() {
        tabContent.removeAll();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addHighlighter(HighlighterFactory.createAlternateStriping());
        JScrollPane jspContacts = new JScrollPane(list);
        jspContacts.setColumnHeaderView(new JXLabel("Contacts", JXLabel.CENTER));
        tabContent.add(jspContacts, "grow");
        tabContent.validate();
    }

    public void setContacts(List<ContactEntry> contacts) {
        list.setModel(new ContactListModel(contacts));
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
