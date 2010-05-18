package org.mm.contact;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.google.inject.Inject;
import org.jdesktop.swingx.JXLoginPane;
import org.mm.Application;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends AbstractAction {
    @Inject
    private GoogleLoginServiceAdapter loginService;

    public LoginAction() {
        super("Login");
        putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JXLoginPane loginPane = new JXLoginPane(loginService);
        loginPane.setMessage("Please enter your account information.");
        if (JXLoginPane.Status.SUCCEEDED == JXLoginPane.showLoginDialog(Application.getInstance(), loginPane)){
            Application.getInstance().loggedIn(loginPane.getUserName());
        }
    }
}
