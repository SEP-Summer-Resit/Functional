package edu.uob;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public final class GameServer {
    private final Player player;
    private final Map<String, Location> locations;

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
        server.blockingListenOn(8888);
    }

    public GameServer() {
        this.locations = new HashMap<>();
        loadConfig();
        this.player = new Player(locations.get("Start"));
    }

    private void loadConfig() {
        // 加载配置文件并初始化游戏状态
        // 简单示例：创建一些位置和物品
        Location start = new Location("Start", "This is the starting location.");
        Location forest = new Location("Forest", "This is a dark forest.");
        start.addPath("north", forest);
        forest.addPath("south", start);

        Artefact sword = new Artefact("sword", "sword");
        start.addArtefact(sword);

        locations.put("Start", start);
        locations.put("Forest", forest);
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

    private String gotoLocation(String locationName) {
        Location newLocation = player.getLocation().getPath(locationName);
        if (newLocation != null) {
            player.setLocation(newLocation);
            return "You moved to " + locationName;
        }
        return "No such path";
    }

    private String look() {
        return player.getLocation().describe();
    }

    private String reset() {
        loadConfig();
        player.setLocation(locations.get("Start"));
        player.clearInventory();
        return "Game has been reset";
    }

    public String handleCommand(String incoming) {
        String command = incoming.split(":")[1].trim();
        String response = "";
        if (command.startsWith("look")) {
            response += "The location you are currently in is ???\n";
            response += "There are the following artefacts in this location ???\n";
            response += "There are paths to the following locations ???";
        }
        if (command.startsWith("inv")) {
            response += "You have the following items in your inventory ???";
        }
        return response;
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
