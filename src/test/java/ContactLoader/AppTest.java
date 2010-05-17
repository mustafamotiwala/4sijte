package ContactLoader;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName){
    }

    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/tmp/customRefs"));
        Map<String,Object> customRefs = (HashMap)ois.readObject();
        for(Map.Entry entry: customRefs.entrySet()){
            if(null == entry.getValue()) JOptionPane.showMessageDialog(null,entry.getKey() + " is null", "HashMap...", JOptionPane.OK_OPTION);
            System.out.println(entry.getKey() + ":" + entry.getValue() + String.format("[%s]", entry.getValue().getClass()));
        }
    }
}
