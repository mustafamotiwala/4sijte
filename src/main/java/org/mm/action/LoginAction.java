package org.mm.action;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pivot.serialization.SerializationException;
import org.apache.pivot.wtk.*;
import org.apache.pivot.wtkx.WTKXSerializer;
import org.mm.ApplicationTab;
import org.mm.PIMApplication;
import org.mm.contact.GoogleLoginServiceAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 9:26:33 PM
 */
public class LoginAction extends Action {
    private static final Log log = LogFactory.getLog(LoginAction.class);
    @Inject
    private GoogleLoginServiceAdapter loginService;
    private LoginButtonPressListener loginButtonListener;
    @Inject @Named("contactLoader")
    private Callable loader;
    @Inject
    List<ApplicationTab> tabs= Collections.emptyList();

    private Dialog loginDialog;
    private TextInput username, password;
    private PushButton loginButton;
    private ActivityIndicator loginProgress;

    public LoginAction(){
        WTKXSerializer serializer = new WTKXSerializer();
        loginButtonListener = new LoginButtonPressListener(this);

        try {
            loginDialog=(Dialog)serializer.readObject(Dialog.class,"/LoginDialog.wtkx");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        username = (TextInput) serializer.get("username");
        password = (TextInput) serializer.get("password");
        loginButton = (PushButton)serializer.get("loginButton");
        loginProgress = (ActivityIndicator) serializer.get("loginActivity");
        loginButton.getButtonPressListeners().add(loginButtonListener);
        loginDialog.setModal(true);
    }

    @Override
    public void perform() {
        Window parent = PIMApplication.getInstance().getWindow();
        loginDialog.open(parent);
    }

    void login(){
        makeBusy();
        //TODO: execute a thread to authenticate.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AuthenticationCallable loginCallable = new AuthenticationCallable(loginService, this);
        executor.submit(loginCallable);
    }

    void loginCompleted(boolean status){
        if(status){
            loginDialog.close();
        }else{
            log.warn("Unable to authenticate!");
            Alert.alert(MessageType.ERROR, "Unable to authenticate.",loginDialog);
        }
        makeAvailable();
    }
    
    private void makeBusy(){
        loginButton.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);
        loginProgress.setActive(true);
    }

    private void makeAvailable(){
        loginButton.setEnabled(true);
        username.setEnabled(true);
        password.setEnabled(true);
        loginProgress.setActive(false);
    }


    public String getUsername(){
        return username.getText();
    }

    public String getPassword(){
        return password.getText();
    }
}

class LoginButtonPressListener implements ButtonPressListener{
    private LoginAction callbackAction;
    LoginButtonPressListener(LoginAction action){
        callbackAction = action;
    }
    @Override
    public void buttonPressed(Button button) {
        callbackAction.login();
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
        Boolean returnVal = Boolean.FALSE;
        if(loginService.authenticate(action.getUsername(), action.getPassword()))
            returnVal = Boolean.TRUE;
        action.loginCompleted(returnVal);
        return returnVal;
    }
}
