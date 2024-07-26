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
import java.util.Arrays;
import java.util.List;

import com.alexmerz.graphviz.ParseException;

import edu.uob.Character;

public final class GameServer {

    private GameGraph gameGraph; // Game graph
    private Player player; // Player
    private final AmbiguityRefusal ambiguityRefusal; // Ambiguity handler
    private final PartialMatcher partialMatcher; // Partial matcher
    private final DecorationFilter decorationFilter; // Decoration filter
    private final InvertedMatcher invertedMatcher; // Inverted matcher
    private final List<String> possibleActions; // List of possible actions

    public static void main(String[] args) throws IOException, ParseException {
        GameServer server = new GameServer();
        server.blockingListenOn(8888); // Listen on port 8888
    }

    public GameServer() throws FileNotFoundException, ParseException {
        loadConfig();
        this.ambiguityRefusal = new AmbiguityRefusal();
        this.partialMatcher = new PartialMatcher();
        this.decorationFilter = new DecorationFilter();
        this.invertedMatcher = new InvertedMatcher();
        this.possibleActions = Arrays.asList("look", "inv", "get", "drop", "goto", "reset", "find", "use");
        // Create player with initial location
        this.player = new Player(this.gameGraph.getFirstNode().getLocationEntity());
    }

    // Load configuration file
    private void loadConfig() throws FileNotFoundException, ParseException {
        String entityFile = "config" + File.separator + "entities.dot";
        this.gameGraph = EntitiesFileParser.parseGameGraph(entityFile);
    }

    // Get location by name
    private Location getLocationByName(String name) {
        GameGraphNode node = this.gameGraph.getNode(name);
        return null != node ? node.getLocationEntity() : null;
    }

    // Handle 'get' command
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

    // Handle 'drop' command
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

    // Handle 'inventory' command
    private String inventory() {
        return "You are carrying: " + player.listArtefacts();
    }

    // Handle 'goto' command
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

    // Handle 'look' command
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

    // Handle 'reset' command
    private String reset() throws FileNotFoundException, ParseException {
        loadConfig();
        player = new Player(this.gameGraph.getFirstNode().getLocationEntity());
        return "Game has been reset. All your progress is lost, but a new adventure begins!";
    }

    // Handle 'find' command using InvertedMatcher class
    private String find(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            return "Please specify what you are looking for.";
        }
        String searchQuery = parts[1].trim();
        List<String> matches = invertedMatcher.findMatches(searchQuery, possibleActions);
        if (matches.isEmpty()) {
            return "No actions found containing '" + searchQuery + "'.";
        } else {
            return invertedMatcher.handleAmbiguity(searchQuery, matches);
        }
    }

    // Handle 'use' command
    private String use(String command) {
        String[] parts = command.split(" ", 2);
        if (parts.length < 2) {
            return ambiguityRefusal.handleInvalidFormat(command);
        }
        String actionDetails = parts[1].trim();
        Action action = new Action(); // Create and configure the action accordingly
        // Add logic to set up the action with triggers, subjects, consumed, produced, and narration
        // Example:
        action.addConsumed("someItem");
        action.addProduced("newItem");
        action.setNarration("You have used the item successfully.");

        return action.execute(player, player.getLocation());
    }

    // Handle incoming commands
    public String handleCommand(String incoming) throws FileNotFoundException, ParseException {
        String command = incoming.split(":")[1].trim();
        if (command.isEmpty()) {
            return ambiguityRefusal.handleEmptyCommand();
        }

        // Filter out decorative words from the command
        command = decorationFilter.filterDecorations(command);

        String[] parts = command.split(" ", 2);
        String action = parts[0];
        List<String> matches = partialMatcher.findMatches(action, possibleActions);

        if (matches.size() > 1) {
            return partialMatcher.handleAmbiguity(action, matches);
        } else if (matches.size() == 1) {
            action = matches.get(0); // Use the matched action
        } else {
            String suggestion = partialMatcher.provideSuggestions(action, possibleActions);
            return ambiguityRefusal.handleUnknownCommand(command) + " " + suggestion;
        }

        switch (action) {
            case "look":
                return look();
            case "inv":
                return inventory();
            case "goto":
                return gotoLocation(command);
            case "reset":
                return reset();
            case "get":
                return get(command);
            case "drop":
                return drop(command);
            case "find":
                return find(command);
            case "use":
                return use(command);
            default:
                return ambiguityRefusal.handleUnknownCommand(command);
        }
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
                System.out.println("Received message: " + incomingCommand);
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
