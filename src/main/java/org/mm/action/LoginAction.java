package org.mm.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.pivot.wtk.Action;
import org.jdesktop.swingx.JXLoginPane;
import org.mm.contact.GoogleLoginServiceAdapter;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends Action {
    @Inject
    private GoogleLoginServiceAdapter loginService;
    @Inject
    @Named("contactLoader")
    private Runnable loader;

    @Override
    public void perform() {
        System.out.println("Login Action Invoked!");
//        JXLoginPane loginPane = new JXLoginPane(loginService);
//        loginPane.setMessage("Please enter your account information.");
    }
}
