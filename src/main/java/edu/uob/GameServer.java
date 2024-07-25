package edu.uob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameServer {
    private final Player player;
    private final ArrayList<Location> locations;

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.blockingListenOn(8888);
    }

    public GameServer() {
        this.locations = new ArrayList<Location>();
        loadConfig();
        // PLACEHOLDER - REPLACE locations.get(0) WITH STARTING LOCATION WHEN LOADCONFIG FULLY IMPLEMENTED
        this.player = new Player(locations.get(0));
    }

    private void loadConfig() {
        // We need this to load the dot file into our data structures!
        // Keeping this as a placeholder *for now*

        // 加载配置文件并初始化游戏状态
        // 简单示例：创建一些位置和物品
        Location start = new Location("Start", "This is the starting location.");
        Location forest = new Location("Forest", "This is a dark forest.");
        start.createPath(forest);
        forest.createPath(start);

        Artefact sword = new Artefact("sword", "sword");
        start.addArtefact(sword);

        locations.add(start);
        locations.add(forest);
    }

    private String get(String artefactName) {
        Artefact artefact = player.getLocation().removeArtefact(artefactName);
        if (artefact != null) {
            player.addArtefact(artefact);
            return "You picked up the " + artefactName;
        }
        return "No such artefact here";
    }

    private String drop(String artefactName) {
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
        List<Path> paths = player.getLocation().getPaths();
        // CAPITALISATION MATTERS
        if (name.isEmpty()){
            return "No location provided";
        }
        for (Path path : paths) {
            if (name.equals(path.getEnd().getName())){
                player.setLocation(path.getEnd());
                return "You moved to " + name;
            }
        }
        return "No such location";
    }

    private String look() {
        StringBuilder response = new StringBuilder();
        // Location
        response.append("The location you are currently in is ");
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

    private String reset() {
        loadConfig();
        // PLACEHOLDER - REPLACE locations.get(0) WITH STARTING LOCATION WHEN LOADCONFIG FULLY IMPLEMENTED
        player.setLocation(locations.get(0));
        player.clearInventory();
        return "Game has been reset";
    }

    public String handleCommand(String incoming) {
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
        if (command.startsWith("reset")) {}
        if (command.startsWith("get")) {}
        if (command.startsWith("drop")) {}
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
        }
    }
}
