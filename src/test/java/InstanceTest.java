import org.xml.sax.SAXException;

import junit.framework.TestCase;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class InstanceTest extends TestCase{

    public void testSaveAndLoadInstanceFromDB() throws ParserConfigurationException, SAXException, IOException {
        File jbpXML = new File("test_data/Instance/saveAndLoad.xml");
        Instance saveAndLoad = Instance.fromFile(jbpXML);
        try {
            List<Instance> activeInstances = Instance.fromDB();
            assertFalse(instanceExistsWithUsername(activeInstances, saveAndLoad.getUsername()));

            saveAndLoad.save();
            activeInstances = Instance.fromDB();
            assertTrue(instanceExistsWithUsername(activeInstances, saveAndLoad.getUsername()));



        } catch(Exception e) {
            fail(e.getMessage());
        } finally {
            DBConnection.makeUpdate(String.format("delete from \"instances\" where username='%s'",
                    saveAndLoad.getUsername()));
        }

        //TODO test that video sources are added to index and that the profile pic is created.

    }

    private boolean instanceExistsWithUsername(List<Instance> instances, String username){
        boolean exists = false;
        for(Instance instance: instances) {
            if (instance.getUsername().equals(username)){
                exists = true;
            }
        }

        return exists;
    }

    public void testFromFile(){

        File jbpXML = new File("test_data/InstanceFactory/jordanBPeterson.xml");

        try {
            Instance jordanBPeterson = Instance.fromFile(jbpXML);
            assertNotNull(jordanBPeterson);
            assertEquals("Jordan Peterson", jordanBPeterson.getName());
            assertEquals("jordan-peterson", jordanBPeterson.getUsername());
            assertEquals("http://solr.daemon.life:8983/solr/videos", jordanBPeterson.getSolrVideoURL().toString());
            assertEquals("http://solr.daemon.life:8983/solr/video_blocks", jordanBPeterson.getSolrBlockURL().toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
 }