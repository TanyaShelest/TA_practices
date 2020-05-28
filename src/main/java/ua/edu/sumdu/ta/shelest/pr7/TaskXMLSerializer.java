package ua.edu.sumdu.ta.shelest.pr7;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaskXMLSerializer {

    public TaskXMLSerializer() {
        super();
    }

    public File save(AbstractTaskList object, String filename) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element tasks = document.createElement("tasks");
        document.appendChild(tasks);

        for (Task t : object) {
            Element task = document.createElement("task");
            task.setTextContent(t.getTitle());
            task.setAttribute("active", "true");
            task.setAttribute("repeated", Boolean.toString(t.isRepeated()));
            task.setAttribute("time", Integer.toString(t.getTime()));
            task.setAttribute("start", Integer.toString(t.getStartTime()));
            task.setAttribute("end", Integer.toString(t.getEndTime()));
            task.setAttribute("repeat", Integer.toString(t.getRepeatInterval()));
            tasks.appendChild(task);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        File file = new File(filename);
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(filename)));
        StreamResult result = new StreamResult(System.out);
        return file;

    }

    public AbstractTaskList load(File filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(filename);

        document.getDocumentElement();

        NodeList list = document.getElementsByTagName("task");

        AbstractTaskList tasks = new ArrayTaskList();

        for (int i = 0; i < list.getLength(); i++) {
            String title = list.item(i).getTextContent();
            NamedNodeMap attributes = list.item(i).getAttributes();
            int time = Integer.parseInt(attributes.getNamedItem("time").getNodeValue());
            int start = Integer.parseInt(attributes.getNamedItem("start").getNodeValue());
            int end = Integer.parseInt(attributes.getNamedItem("end").getNodeValue());
            int repeat = Integer.parseInt(attributes.getNamedItem("repeat").getNodeValue());
            boolean active = Boolean.parseBoolean(attributes.getNamedItem("active").getNodeValue());
            boolean repeated = Boolean.parseBoolean(attributes.getNamedItem("repeated").getNodeValue());

            if (!repeated) {
                Task task = new Task(title, time);
                task.setActive(active);
                tasks.add(task);
            } else {
                Task task = new Task(title, start, end, repeat);
                task.setActive(active);
                tasks.add(task);
            }

        }
        return tasks;

        }

}

