package org.mm;

import java.awt.Color;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.BusyPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.util.PaintUtils;
import org.mm.contact.ContactModule;

/**
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 5:37:17 PM
 */
public class Application extends JFrame {
    private static final Log log = LogFactory.getLog(Application.class);

    private static Application app;

    @Inject
    @Named("svcContacts")
    private ContactsService service;
    @Inject
    @Named("loginAction")
    private Action loginAction;
    @Inject
    private List<ApplicationTab> applicationTabs;

    private String userName;
    private URL serviceUrl;
    private JXPanel busyPanel = new JXPanel(new MigLayout("", "[center, grow]", "[center, grow]"), true);

    public Application() {
        super("Google Contact Application");
    }

    public static void main(String args[]) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new ContactModule());
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

        /*
        Busy Panel:
        */
        JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(50, 50));
        busyLabel.setName("Please Wait");
        BusyPainter busyPainter = busyLabel.getBusyPainter();
        busyPainter.setAntialiasing(true);
        busyPainter.setHighlightColor(new Color(44, 61, 146).darker());
        busyPainter.setBaseColor(new Color(168, 204, 241).brighter());
        busyLabel.setBusy(true);
        busyPanel.setBackgroundPainter(new MattePainter(PaintUtils.NIGHT_GRAY, true));
        busyPanel.add(busyLabel);
        setGlassPane(busyPanel);
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
        for (ApplicationTab tab : applicationTabs) {
            tabPane.addTab(tab.getName(), tab.getTab());
        }
        add(tabPane, "grow");
    }

    public static Application getInstance() {
        return app;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        try {
            setServiceUrl(new URL(String.format("http://www.google.com/m8/feeds/contacts/%1$s/full", userName)));
        } catch (MalformedURLException ignored) {
        }
    }

    public URL getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void makeBusy() {
        busyPanel.setVisible(true);
        validate();
    }

    public void makeAvailable() {
        busyPanel.setVisible(false);
    }
}
