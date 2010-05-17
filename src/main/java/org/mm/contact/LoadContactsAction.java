package org.mm.contact;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

import com.google.gdata.util.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: Mustafa Motiwala
 * Date: May 16, 2010
 * Time: 8:24:10 PM
 */
public class LoadContactsAction extends AbstractAction {
    private static final Log log = LogFactory.getLog(LoadContactsAction.class);
    public LoadContactsAction() {
        super("Fire Away!");
        super.setEnabled(true);
        super.putValue(MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Application application = Application.getInstance();
        int response = JOptionPane.showConfirmDialog(application,"Are you sure you want me to fire away?", "Confirm load...",YES_NO_OPTION,QUESTION_MESSAGE);
        if(YES_OPTION == response){
            try {
                log.info("Begin Loading contacts.");
                application.loadContacts();
                log.info("Finished Loading contacts.");
            } catch (IOException e) {
                log.error(e);
            } catch (ServiceException e) {
                log.error(e);
            }
        }
    }
}
