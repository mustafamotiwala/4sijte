package org.mm.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.*;
import org.mm.ApplicationTab;
import org.mm.PIMApplication;
import org.mm.contact.GoogleLoginServiceAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * TODO: Abbas: There is an error in LoginAction when hitting enter it does not attempt to login.
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends Action {
    private static final Log log = LogFactory.getLog(LoginAction.class);
    @Inject
    private GoogleLoginServiceAdapter loginService;
    @Inject @Named("contactLoader")
    private Callable loader;
    @Inject
    List<ApplicationTab> tabs= Collections.emptyList();

    private TextInput username, password;

    public LoginAction(){
    }

    @Override
    public void perform(Component c) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AuthenticationCallable loginCallable = new AuthenticationCallable(loginService, this);
        Future<Boolean> authenticationFuture=executor.submit(loginCallable);
        while(!authenticationFuture.isDone()){
        }
        try {
            log.debug("Login Status:"+authenticationFuture.get());
        } catch (InterruptedException e) {
            log.debug(e);
        } catch (ExecutionException e) {
            log.debug(e);
        }
    }

    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        return password.getText();
    }
}

class AuthenticationCallable implements Callable<Boolean>{
    private GoogleLoginServiceAdapter loginService;
    private LoginAction action;

    AuthenticationCallable(GoogleLoginServiceAdapter loginService, LoginAction action) {
        this.loginService = loginService;
        this.action = action;
    }

    @Override
    public Boolean call() throws Exception {
        return loginService.authenticate(action.getUsername(), action.getPassword());
    }
}
