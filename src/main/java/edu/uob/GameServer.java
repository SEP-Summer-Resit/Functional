package edu.uob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.alexmerz.graphviz.ParseException;

import edu.uob.Character;

public final class GameServer {

    private GameGraph gameGraph;
    private Player player;
    private final AmbiguityRefusal ambiguityRefusal;

    public static void main(String[] args) throws IOException, ParseException {
        GameServer server = new GameServer();
        server.blockingListenOn(8888);
    }

    public GameServer() throws FileNotFoundException, ParseException {
        loadConfig();
        this.ambiguityRefusal = new AmbiguityRefusal();
        // PLACEHOLDER - REPLACE locations.get(0) WITH STARTING LOCATION WHEN LOADCONFIG FULLY IMPLEMENTED
        this.player = new Player(this.gameGraph.getFirstNode().getLocationEntity());
    }

    private void loadConfig() throws FileNotFoundException, ParseException {
        String entityFile = "config" + File.separator + "entities.dot";
        this.gameGraph = EntitiesFileParser.parseGameGraph(entityFile);
    }

    // Takes a string input and returns the location with that name. Returns null if no such location exists.
    private Location getLocationByName(String name){
        GameGraphNode node = this.gameGraph.getNode(name);
        return null != node ? node.getLocationEntity() : null;
    }

    private String get(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            return ambiguityRefusal.handleInvalidFormat(command);
        }
        String artefactName = parts[1].trim();
        Artefact artefact = player.getLocation().removeArtefact(artefactName);
        if (artefact != null) {
            player.addArtefact(artefact);
            return "You picked up the " + artefactName;
        }
        return "No such artefact here. Are you sure you spelled it correctly?";
    }

    private String drop(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            return ambiguityRefusal.handleInvalidFormat(command);
        }
        String artefactName = parts[1].trim();
        Artefact artefact = player.removeArtefact(artefactName);
        if (artefact != null) {
            player.getLocation().addArtefact(artefact);
            return "You dropped the " + artefactName;
        }
        return "You don't have such an artefact. Double-check your inventory.";
    }

    private String inventory() {
        return "You are carrying: " + player.listArtefacts();
    }

    private String gotoLocation(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            return ambiguityRefusal.handleInvalidFormat(command);
        }
        String name = parts[1].trim();
        Location destination = getLocationByName(name);
        if (destination == null) {
            return "There is no such location. Perhaps you meant something else?";
        }
        if (player.getLocation().equals(destination)) {
            return "You are already here!";
        }
        if (player.getLocation().pathExists(destination)) {
            player.setLocation(destination);
            return "You moved to " + destination.getName();
        }
        return "There is no path to the specified location. Try a different route.";
    }

    private String look() {
        StringBuilder response = new StringBuilder();
        // Location
        response.append("You are currently in: ");
        response.append(player.getLocation().getName());
        // Artefacts
        response.append("\nYou see the following artefacts here: ");
        List<Artefact> artefacts = player.getLocation().getArtefacts();
        if (artefacts.isEmpty()) {
            response.append("None");
        } else {
            for (Artefact artefact : artefacts) {
                response.append(artefact.getName()).append(" ");
            }
        }
        // Furniture
        response.append("\nYou notice the following furniture: ");
        List<Furniture> furniture = player.getLocation().getFurniture();
        if (furniture.isEmpty()) {
            response.append("None");
        } else {
            for (Furniture furniture1 : furniture) {
                response.append(furniture1.getName()).append(" ");
            }
        }
        // Characters
        response.append("\nYou see the following characters: ");
        List<Character> characters = player.getLocation().getCharacters();
        if (characters.isEmpty()) {
            response.append("None");
        } else {
            for (Character character : characters) {
                response.append(character.getName()).append(" ");
            }
        }
        // Paths
        response.append("\nThere are paths leading to: ");
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
        loadConfig();
        player = new Player(this.gameGraph.getFirstNode().getLocationEntity());
        return "Game has been reset. All your progress is lost, but a new adventure begins!";
    }

    public String handleCommand(String incoming) throws FileNotFoundException, ParseException {
        String command = incoming.split(":")[1].trim();
        if (command.isEmpty()) {
            return ambiguityRefusal.handleEmptyCommand();
        }
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
        return ambiguityRefusal.handleUnknownCommand(command);
    }

    // Networking method - you shouldn't need to change this method!
    public void blockingListenOn(int portNumber) throws IOException {
        try (ServerSocket s = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (!Thread.interrupted()) {
                try {
                    blockingHandleConnection(s);
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            }
        }
    }

    // Networking method - you shouldn't need to change this method!
    private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
        final char END_OF_TRANSMISSION = 4;
        try (Socket s = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            System.out.println("Connection established.");
            String incomingCommand = reader.readLine();
            if (incomingCommand != null) {
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
