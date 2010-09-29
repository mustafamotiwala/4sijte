package org.mm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtkx.WTKXSerializer;
import org.mm.contact.ContactModule;

/**
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 5:37:17 PM
 */
@SuppressWarnings("unused")
public class PIMApplication implements Application {
    private static final Log log = LogFactory.getLog(PIMApplication.class);

    private static PIMApplication app;

    @Inject
    @Named("svcContacts")
    private ContactsService service;
    @Inject
    @Named("loginAction")
    private Action loginAction;
    @Inject
    private List<ApplicationTab> applicationTabs = new ArrayList<ApplicationTab>(0);

    private String userName;
    private URL serviceUrl;
    private Window window;

    @Override
    public void startup(Display display, Map<String, String> properties) throws Exception {
        WTKXSerializer wtkxSerializer = new WTKXSerializer();
        window = (Window)wtkxSerializer.readObject(app,"PIMApplication.wtkx.xml");
        window.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void suspend() throws Exception {
    }

    @Override
    public void resume() throws Exception {
    }

    public static void main(String args[]) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new ContactModule());
        app = injector.getInstance(PIMApplication.class);
        DesktopApplicationContext.main(PIMApplication.class,args);
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

    public void setServiceUrl(URL serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
