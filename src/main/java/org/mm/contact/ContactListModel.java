package org.mm.contact;

import java.util.Collections;
import java.util.List;
import javax.swing.AbstractListModel;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;

/**
 * User: Mustafa Motiwala
 * Date: May 16, 2010
 * Time: 5:19:25 PM
 */
public class ContactListModel extends AbstractListModel {
    private List<ContactEntry> data = Collections.emptyList();

    public ContactListModel(List<ContactEntry> data) {
        this.data = Collections.unmodifiableList(data);
    }

    @Override
    public Object getElementAt(int index) {
        ContactEntry object = data.get(index);
        StringBuilder returnVal = new StringBuilder();
        boolean hasFullName = false;
        try {
            Name name = object.getName();
            FullName fullName = name.getFullName();
            if (null != fullName) {
                returnVal.append(fullName.getValue()).append("( ");
                hasFullName = true;
            }
            FamilyName last = name.getFamilyName();
            if (null != last) returnVal.append(last.getValue()).append(", ");
            GivenName given = name.getGivenName();
            if (null != given) returnVal.append(given.getValue());
        } catch (NullPointerException ignored) { }
        if (hasFullName) returnVal.append(")");
        if(returnVal.length() < 1) return "N/A";
        else return returnVal.toString();
    }

    @Override
    public int getSize() {
        return data.size();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
