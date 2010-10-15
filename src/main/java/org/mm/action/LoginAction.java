package org.mm.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtkx.WTKXSerializer;
import org.mm.PIMApplication;
import org.mm.contact.GoogleLoginServiceAdapter;

import java.io.IOException;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends Action {
    @Inject
    private GoogleLoginServiceAdapter loginService;
//    @Inject
//    @Named("contactLoader")
//    private Runnable loader;

    @Override
    public void perform() {
        System.out.println("Login Action Invoked!");
        if(loginService == null){
            System.out.println("Login Service is null!");
        }
        WTKXSerializer serializer = new WTKXSerializer();
        try {
            Dialog dlg = (Dialog)serializer.readObject(Dialog.class,"/LoginDialog.wtkx");
            Window parent = PIMApplication.getInstance().getWindow();
            dlg.open(parent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
//        JXLoginPane loginPane = new JXLoginPane(loginService);
//        loginPane.setMessage("Please enter your account information.");
    }
}
