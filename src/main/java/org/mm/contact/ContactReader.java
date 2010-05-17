package org.mm.contact;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.common.xml.XmlWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Mu. Ali Asgher Hakim:
 * Sh. Yusuf Sabir: (248) 252 2592
 * User: Mustafa Motiwala
 * Date: Apr 17, 2010
 * Time: 4:15:40 PM
 */
public class ContactReader {
    private static final Log log = LogFactory.getLog(ContactReader.class);
    private XMLStreamReader reader = null;

    public ContactReader(FileInputStream fis) {
        try {
            reader = XMLInputFactory.newInstance().createXMLStreamReader(fis);
        } catch (XMLStreamException e) {
            log.error(e);
        }
    }

    public List<ContactEntry> read() {
        List<ContactEntry> returnVal = new ArrayList<ContactEntry>();
        if (null == reader) {
            log.warn("XML Source is not setup correctly. Can not read. Will return empty list.");
            return returnVal;
        }
        try {
            ContactEntry entry = null;
            while (reader.hasNext()) {
                int code = reader.next();
//                log.info("Begin Reading:" + reader.getLocalName());
                if (StringUtils.equalsIgnoreCase("Contact", reader.getLocalName())) {
                    if (reader.isStartElement()) entry = new ContactEntry();
                    else returnVal.add(entry);
                    //Get Details for contact.
                }else if (StringUtils.equalsIgnoreCase("Detail", reader.getLocalName())) {
                    if (reader.isStartElement()){
                        extractContactDetail(entry);
                    }
                }
            }
            reader.close();
        } catch (XMLStreamException e) {
            log.error(e);
        }
        return returnVal;
    }

    private void extractContactDetail(final ContactEntry entry) {
        String label = reader.getAttributeValue(null, "label");
        String type = reader.getAttributeValue(null, "type");
        String value = reader.getAttributeValue(null, "value");
        Name name = entry.getName();
        log.info(String.format("Label :%2$s; Type: %1$s; Value: %3$s", label, type, value));
        if(null == name){
            name = new Name();
            entry.setName(name);
        }
        if(StringUtils.equalsIgnoreCase("first_name", type)){
            name.setGivenName(new GivenName(value,null));
        }else if(StringUtils.equalsIgnoreCase("last_name", type)){
            if(name.getGivenName() == null) name.setFullName(new FullName(value, null));
            else name.setFamilyName( new FamilyName(value,null));
        }else if(StringUtils.equalsIgnoreCase("phone_number",type)){
            PhoneNumber number = new PhoneNumber();
            number.setLabel("Home");
            number.setPhoneNumber(value);
            entry.addPhoneNumber(number);
        }else if(StringUtils.equalsIgnoreCase("mobile_number",type)){
            PhoneNumber number = new PhoneNumber();
            number.setLabel("Mobile");
            number.setPhoneNumber(value);
            entry.addPhoneNumber(number);
        }
    }
}
