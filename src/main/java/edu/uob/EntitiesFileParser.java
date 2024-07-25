package edu.uob;

import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Utility class for the parsing of DOT file
 */
public class EntitiesFileParser {

    public static GameGraph parseGameGraph(String dotFileName) {
        GameGraph graph = new GameGraph();
        try {
            // Load file
            Parser parser = new Parser();
            FileReader reader = new FileReader(dotFileName);
            parser.parse(reader);
            // Get primary parts
            Graph wholeDocument = parser.getGraphs().get(0);
            ArrayList<Graph> parts = wholeDocument.getSubgraphs();
            ArrayList<Graph> locations = parts.get(0).getSubgraphs();
            ArrayList<Edge> paths = parts.get(1).getEdges();
            // Read through all locations and add Node to GameGraph
            for (Graph location : locations) {

                // Retrieve location name and description
                Node locationDetail = location.getNodes(false).get(0);
                String locationName = locationDetail.getId().getId();
                String locationDescription = locationDetail.getAttribute("description");

                // Create Location entity
                Location locEnt = new Location(locationName, locationDescription);

                // Retrieve game entities in location
                ArrayList<Graph> locationEntities = location.getSubgraphs();

                for (Graph locationEntity : locationEntities) {
                    String entityType = locationEntity.getId().getId();
                    if (entityType.equalsIgnoreCase("artefacts")) {
                        // Handle loading of artefacts
                        for (Node artefact : locationEntity.getNodes(false)) {
                            String artefactName = artefact.getId().getId();
                            String artefactDescription = artefact.getAttribute("description");
                            locEnt.addArtefact(new Artefact(artefactName, artefactDescription));
                        }
                    } else if (entityType.equalsIgnoreCase("furniture")) {
                        // Handle loading of furnitures
                        for (Node furniture : locationEntity.getNodes(false)) {
                            String furnitureName = furniture.getId().getId();
                            String furnitureDescription = furniture.getAttribute("description");
                            locEnt.addFurniture(new Furniture(furnitureName, furnitureDescription));
                        }
                    } else if (entityType.equalsIgnoreCase("characters")) {
                        // Handle loading of furnitures
                        for (Node character : locationEntity.getNodes(false)) {
                            String characterName = character.getId().getId();
                            String characterDescription = character.getAttribute("description");
                            locEnt.addCharacter(new Character(characterName, characterDescription));
                        }
                    }
                }

                // Add location to graph
                graph.addNode(new GameGraphNode(locEnt.getName(), locEnt));

            }

            // Read through all paths and add Path to GameGraph
            for (Edge path : paths) {
                String fromNode = path.getSource().getNode().getId().getId();
                String toNode = path.getTarget().getNode().getId().getId();
                graph.addPath(fromNode, toNode);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

}
