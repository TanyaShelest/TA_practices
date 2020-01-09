import junitx.framework.FileAssert;

import org.junit.Before;
import org.junit.Test;

import org.xml.sax.SAXException;
import ua.edu.sumdu.ta.shelest.pr7.AbstractTaskList;
import ua.edu.sumdu.ta.shelest.pr7.ArrayTaskList;
import ua.edu.sumdu.ta.shelest.pr7.Task;
import ua.edu.sumdu.ta.shelest.pr7.TaskXMLSerializer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;


public class TaskXMLSerializerTest {

    TaskXMLSerializer t = new TaskXMLSerializer();
    ArrayTaskList tasks = new ArrayTaskList();

    @Before
    public void fillList() {
        tasks.add(new Task("a", 1));
        tasks.add(new Task("b", 2));
        tasks.add(new Task("c", 3));
        tasks.add(new Task("d", 4));
    }



    @Test
    public void saveTest() throws FileNotFoundException, TransformerException, ParserConfigurationException {


        File expected = new File("/home/shelest/taskserializer");

        TaskXMLSerializer act = new TaskXMLSerializer();
        File actual = act.save(tasks, "/home/shelest/actual");

        FileAssert.assertEquals(actual, expected);

    }

    @Test
    public void saveNotNull() throws FileNotFoundException, TransformerException, ParserConfigurationException {
        ArrayTaskList tasks = new ArrayTaskList();
        tasks.add(new Task("a", 1));
        tasks.add(new Task("b", 2));
        tasks.add(new Task("c", 3));
        tasks.add(new Task("d", 4));

        TaskXMLSerializer act = new TaskXMLSerializer();
        File actual = act.save(tasks, "/home/shelest/actual");

        assertNotNull(actual);

    }

    @Test
    public void loadTest() throws IOException, SAXException, ParserConfigurationException {
        File file = new File("/home/shelest/actual");
        AbstractTaskList actual = (ArrayTaskList) t.load(file);

        ArrayTaskList tasks = new ArrayTaskList();
        tasks.add(new Task("a", 1));
        tasks.add(new Task("b", 2));
        tasks.add(new Task("c", 3));
        tasks.add(new Task("d", 4));

        assertEquals(actual.toString(), tasks.toString());






    }
}
