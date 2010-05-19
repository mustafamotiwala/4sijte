package org.mm;

import java.util.List;
import javax.swing.JPanel;

/**
 * User: Mustafa Motiwala
 * Date: May 18, 2010
 * Time: 6:40:17 PM
 */
public interface ApplicationTab {
    /**
     * Get the Content for this tab.
     *
     * @return self managed JPanel to be displayed in the application.
     */
    JPanel getTab();

    /**
     * @return - The name for the tab.
     */
    String getName();

    /**
     * Notify the content panes that the user has successfully logged in.
     */
    void userLoggedIn();

    /**
     * Register this component with the application.
     *
     * @param tabs - The list of application wide content panes.
     */
    void register(List<ApplicationTab> tabs);
}
