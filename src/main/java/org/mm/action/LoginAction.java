package org.mm.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jdesktop.swingx.JXLoginPane;
import org.mm.Application;
import org.mm.contact.GoogleLoginServiceAdapter;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends AbstractAction {
    @Inject
    private GoogleLoginServiceAdapter loginService;
    @Inject
    @Named("contactLoader")
    private Runnable loader;

    public LoginAction() {
        super("Login");
        putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JXLoginPane loginPane = new JXLoginPane(loginService);
        loginPane.setMessage("Please enter your account information.");
        Application application = Application.getInstance();
        if (JXLoginPane.Status.SUCCEEDED == JXLoginPane.showLoginDialog(application, loginPane)) {
            application.setUserName(loginPane.getUserName());
            application.makeBusy();
            Executors.newSingleThreadExecutor().execute(loader);
        }
    }
}
