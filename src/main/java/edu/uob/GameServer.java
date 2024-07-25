package edu.uob;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import com.alexmerz.graphviz.*;
import com.alexmerz.graphviz.objects.*;

public final class GameServer {
    private Player player;
    private ArrayList<Location> locations;

    public static void main(String[] args) throws IOException, ParseException {
        GameServer server = new GameServer();
        server.blockingListenOn(8888);
    }

    public GameServer() throws FileNotFoundException, ParseException {
        this.locations = new ArrayList<>();
        loadConfig();
        // PLACEHOLDER - REPLACE locations.get(0) WITH STARTING LOCATION WHEN LOADCONFIG FULLY IMPLEMENTED
        this.player = new Player(locations.get(0));
    }

    private void loadConfig() throws FileNotFoundException, ParseException {
        Parser parser = new Parser();
        String file = "config" + File.separator + "entities.dot";
        FileReader reader = new FileReader(file);
        parser.parse(reader);
        Graph wholeDocument = parser.getGraphs().get(0);
        ArrayList<Graph> configParts = wholeDocument.getSubgraphs();
        ArrayList<Graph> configLocations = configParts.get(0).getSubgraphs();
        ArrayList<Edge> configPaths = configParts.get(1).getEdges();

        // LOCATIONS
        for (Graph location : configLocations) {
            // Make location
            Location newLocation = new Location(location.getNodes(false).get(0).getId().getId());
            // Populate location with entities
            ArrayList<Graph> entities = location.getSubgraphs();
            for (Graph entity : entities) {
                if (entity.getId().getId().equals("artefacts")) {
                    for (Node artefact : entity.getNodes(false)) {
                        newLocation.addArtefact(new Artefact(artefact.getId().getId(), artefact.getAttribute("description")));
                    }
                }
                if (entity.getId().getId().equals("furniture")) {
                    for (Node furniture : entity.getNodes(false)) {
                        newLocation.addFurniture(new Furniture(furniture.getId().getId(), furniture.getAttribute("description")));
                    }
                }
                if (entity.getId().getId().equals("characters")) {
                    for (Node character : entity.getNodes(false)) {
                        newLocation.addCharacter(new Character(character.getId().getId(), character.getAttribute("description")));
                    }
                }
            }
            locations.add(newLocation);
        }

        // PATHS
        for (Edge edge : configPaths) {
            String start = edge.getSource().getNode().getId().getId();
            String end = edge.getTarget().getNode().getId().getId();
            Objects.requireNonNull(getLocationByName(start)).createPath(getLocationByName(end));
        }

    }

    // Takes a string input and returns the location with that name. Returns null if no such location exists.
    private Location getLocationByName(String name){
        for (Location location : locations) {
            if(location.getName().equalsIgnoreCase(name)){
                return location;
            }
        }
        return null;
    }

    private String get(String command) {
        String artefactName = command.split(" ")[1].trim();
        Artefact artefact = player.getLocation().removeArtefact(artefactName);
        if (artefact != null) {
            player.addArtefact(artefact);
            return "You picked up the " + artefactName;
        }
        return "No such artefact here";
    }

    private String drop(String command) {
        String artefactName = command.split(" ")[1].trim();
        Artefact artefact = player.removeArtefact(artefactName);
        if (artefact != null) {
            player.getLocation().addArtefact(artefact);
            return "You dropped the " + artefactName;
        }
        return "You don't have such an artefact";
    }

    private String inventory() {
        return "You are carrying: " + player.listArtefacts();
    }

    private String gotoLocation(String command) {
        String name = command.split(" ")[1];
        // CAPITALISATION MATTERS
        Location destination = getLocationByName(name);
        if (name.isEmpty()) {
            return "No location provided";
        }
        if (player.getLocation().equals(destination)){
            return "You are already here!";
        }
        if (player.getLocation().pathExists(destination)) {
            player.setLocation(destination);
            return "You moved to " + destination.getName();
        }
        return "No such location";
    }

    private String look() {
        StringBuilder response = new StringBuilder();
        // Location
        response.append("The location you are currently in is: ");
        response.append(player.getLocation().getName());
        // Artefacts
        response.append("\nThere are the following artefacts in this location: ");
        List<Artefact> artefacts = player.getLocation().getArtefacts();
        if (artefacts.isEmpty()) {
            response.append("None");
        } else {
            for (Artefact artefact : artefacts) {
                response.append(artefact.getName()).append(" ");
            }
        }
        // Furniture
        response.append("\nThere is the following furniture in this location: ");
        List<Furniture> furniture = player.getLocation().getFurniture();
        if (furniture.isEmpty()) {
            response.append("None");
        } else {
            for (Furniture furniture1 : furniture) {
                response.append(furniture1.getName()).append(" ");
            }
        }
        // Characters
        response.append("\nThere are the following characters in this location: ");
        List<Character> characters = player.getLocation().getCharacters();
        if (characters.isEmpty()) {
            response.append("None");
        } else {
            for (Character character : characters) {
                response.append(character.getName()).append(" ");
            }
        }
        // Paths
        response.append("\nThere are paths to the following locations: ");
        List<Path> paths = player.getLocation().getPaths();
        if (paths.isEmpty()) {
            response.append("None");
        } else {
            for (Path path : paths) {
                response.append(path.getEnd().getName()).append(" ");
            }
        }
        return response.toString().trim();
    }

    private String reset() throws FileNotFoundException, ParseException {
        locations = new ArrayList<>();
        loadConfig();
        // PLACEHOLDER - REPLACE locations.get(0) WITH STARTING LOCATION WHEN LOADCONFIG FULLY IMPLEMENTED
        player = new Player(locations.get(0));
        return "Game has been reset";
    }

    public String handleCommand(String incoming) throws FileNotFoundException, ParseException {
        String command = incoming.split(":")[1].trim();
        //
        if (command.startsWith("look")) {
            return look();
        }
        if (command.startsWith("inv")) {
            return inventory();
        }
        if (command.startsWith("goto")) {
            return gotoLocation(command);
        }
        if (command.startsWith("reset")) {
            return reset();
        }
        if (command.startsWith("get")) {
            return get(command);
        }
        if (command.startsWith("drop")) {
            return drop(command);
        }
        return "Command not recognised";
    }

    // Networking method - you shouldn't need to chenge this method !
    public void blockingListenOn(int portNumber) throws IOException {
        try (ServerSocket s = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (!Thread.interrupted()) {
                try {
                    blockingHandleConnection(s);
                } catch (IOException e) {
                    System.out.println("Connection closed");
                }
            }
        }
    }

    // Networking method - you shouldn't need to chenge this method !
    private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
        final char END_OF_TRANSMISSION = 4;
        try (Socket s = serverSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            System.out.println("Connection established");
            String incomingCommand = reader.readLine();
            if(incomingCommand != null) {
                System.out.println("Received message from " + incomingCommand);
                String result = handleCommand(incomingCommand);
                writer.write(result);
                writer.write("\n" + END_OF_TRANSMISSION + "\n");
                writer.flush();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
