package edu.uob;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class ActionsFileParser {

    public static List<Action> parseActions(String fileName) {
        List<Action> actions = new ArrayList<Action>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            Element root = document.getDocumentElement();
            NodeList actionList = root.getElementsByTagName("action");
            // Loop through all the actions
            for (int i = 0; i < actionList.getLength(); i++) {
                // Build Action class
                Action action = new Action();
                Element actionElement = (Element) actionList.item(i);
                NodeList triggers = ((Element) actionElement.getElementsByTagName("triggers").item(0)).getElementsByTagName("keyword");
                for (int j = 0; j < triggers.getLength(); j++) {
                    action.addTrigger(triggers.item(j).getTextContent());
                }
                NodeList subjects = ((Element) actionElement.getElementsByTagName("subjects").item(0)).getElementsByTagName("entity");
                for (int j = 0; j < subjects.getLength(); j++) {
                    action.addSubject(subjects.item(j).getTextContent());
                }
                NodeList consumed = ((Element) actionElement.getElementsByTagName("consumed").item(0)).getElementsByTagName("entity");
                for (int j = 0; j < consumed.getLength(); j++) {
                    action.addConsumed(consumed.item(j).getTextContent());
                }
                NodeList produced = ((Element) actionElement.getElementsByTagName("produced").item(0)).getElementsByTagName("entity");
                for (int j = 0; j < produced.getLength(); j++) {
                    action.addProduced(produced.item(j).getTextContent());
                }
                action.setNarration(actionElement.getElementsByTagName("narration").item(0).getTextContent());
                actions.add(action);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return actions;
    }

}
