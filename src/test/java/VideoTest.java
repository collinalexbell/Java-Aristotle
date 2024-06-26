
import java.io.File;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

public class VideoTest extends TestCase{

    public void testFromFileTest() throws FileNotFoundException {
        File srt = new File("test_data/Video/oVvPfq0MoaQ_0_en.srt");
        Video vid = new Video(srt);
        assertEquals(vid.id, "oVvPfq0MoaQ");
        assertEquals(vid.blocks.get(0).words,"good morning Hank it's Tuesday your book");
        assertEquals(vid.blocks.get(111).words, "up and Hank I will see you on Friday");
        assertEquals(vid.blocks.get(111).id, "112");
    }


    public void testMarkAsHavingBeenIndexed(){
        Video initial = new Video("testID");
        initial.instanceUsername = "testInstance";

        Video secondary = new Video("testID");
        secondary.instanceUsername = "testInstance2";

        //Just in case this test failed and deletion never happened;
        initial.unmarkAsHavingBeenIndexed();
        secondary.unmarkAsHavingBeenIndexed();

        //Both should be unindexed
        assertFalse(initial.hasBeenIndexedP());
        assertFalse(secondary.hasBeenIndexedP());

        //One should be indexed
        initial.markAsHavingBeenIndexed();
        assertTrue(initial.hasBeenIndexedP());
        assertFalse(secondary.hasBeenIndexedP());

        //Both should be indexed
        secondary.markAsHavingBeenIndexed();

        //Back to being one indexed
        initial.unmarkAsHavingBeenIndexed();

        //Make sure unmarking initial didn't unmark secondary since they share a videoID
        assertTrue(secondary.hasBeenIndexedP());

        //Clean up secondary
        secondary.unmarkAsHavingBeenIndexed();
    }
}
