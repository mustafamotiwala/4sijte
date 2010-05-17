package org.mm.contact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.data.ExtensionProfile;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.common.xml.XmlWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * User: Mustafa Motiwala
 * Date: May 6, 2010
 * Time: 6:44:30 PM
 */
public class ContactReaderTest {
    private ContactReader testTarget;
    private static Log log = LogFactory.getLog(ContactReader.class);

    @Test
    public void read(){
        try{
            testTarget = new ContactReader(new FileInputStream("/home/mustafa/addr.xml"));
            List<ContactEntry> contactList = testTarget.read();
            Assert.assertFalse(contactList.isEmpty());
            for(ContactEntry contact:contactList){
                StringWriter sw = new StringWriter();
                contact.generate(new XmlWriter(sw), new ExtensionProfile() );
                log.info(sw.toString());
            }
        }catch (FileNotFoundException e){
            Assert.fail(e.getMessage());
        }catch(IOException e){
            Assert.fail(e.getMessage());
        }
    }
}
