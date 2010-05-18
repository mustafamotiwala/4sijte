package org.mm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.painter.BusyPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.util.PaintUtils;
import org.mm.contact.ContactListModel;
import org.mm.contact.ContactModule;
import org.mm.contact.GoogleLoginServiceAdapter;
import org.mm.contact.LoginAction;

/**
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 5:37:17 PM
 */
public class Application extends JFrame {
    private static final Log log = LogFactory.getLog(Application.class);
    @Inject @Named("svcContacts")
    private ContactsService service;
    @Inject
    private LoginAction loginAction;
    private static Application app;

    public Application() {
        super("Google Contact Application");
    }

    public static void main(String args[]) {
        Injector injector = Guice.createInjector(new ContactModule());
        app = injector.getInstance(Application.class);
        app.initialize();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setSize(800, 600);
    }

    private void initialize() {
        setLayout(new MigLayout("wrap", "[grow]", "[grow][]"));
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic('F');
        menuFile.add(loginAction);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuFile);
        add(menuBar, "north");
    }

    public static Application getInstance() {
        return app;
    }

    public void loggedIn(String user){
        JXPanel panel = new JXPanel(new MigLayout("","[center, grow]","[center, grow]"), true);
        JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(50,50));
        busyLabel.setName("Please Wait");
        BusyPainter busyPainter = busyLabel.getBusyPainter();
        busyPainter.setAntialiasing(true);
        busyPainter.setHighlightColor(new Color(44, 61, 146).darker());
        busyPainter.setBaseColor(new Color(168, 204, 241).brighter());
        busyLabel.setBusy(true);
        panel.setBackgroundPainter(new MattePainter(PaintUtils.NIGHT_GRAY, true));
        panel.add(busyLabel);
        setGlassPane(panel);
        panel.setVisible(true);
        validate();
        try {
            URL urlContactsService = new URL(String.format("http://www.google.com/m8/feeds/contacts/%1$s/full", user));
            Query contactQuery = new Query(urlContactsService);
            contactQuery.setMaxResults(Integer.MAX_VALUE);
            ContactFeed resultFeed = service.getFeed(contactQuery, ContactFeed.class);
            List<ContactEntry> contacts = resultFeed.getEntries();
            JXList list = new JXList(new ContactListModel(contacts));
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.addHighlighter(HighlighterFactory.createAlternateStriping());
            JScrollPane jspContacts = new JScrollPane(list);
            jspContacts.setColumnHeaderView(new JXLabel("Contacts", JXLabel.CENTER));
            add(jspContacts, "grow");
            validate();
            log.info("Contacts loaded...");
        } catch (ServiceException e) {
            log.error("Service Exception:",e);
        } catch (MalformedURLException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
        panel.setVisible(false);
    }
}
