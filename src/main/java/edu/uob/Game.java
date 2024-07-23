/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.uob;

/**
 *
 * @author liumu
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<String, Location> locations;
    private final Player player;

    public Game() {
        locations = new HashMap<>();
        loadConfig();
        player = new Player("Player1", locations.get("Start"));
    }

    private void loadConfig() {
        // 加载配置文件并初始化游戏状态
        // 简单示例：创建一些位置和物品
        Location start = new Location("Start", "This is the starting location.");
        Location forest = new Location("Forest", "This is a dark forest.");
        start.addPath("north", forest);
        forest.addPath("south", start);

        Artefact sword = new Artefact("sword");
        start.addArtefact(sword);

        locations.put("Start", start);
        locations.put("Forest", forest);
    }

    public String executeCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];

        switch (action) {
            case "get":
                return get(parts[1]);
            case "drop":
                return drop(parts[1]);
            case "inv":
                return inventory();
            case "goto":
                return gotoLocation(parts[1]);
            case "look":
                return look();
            case "reset":
                return reset();
            default:
                return "Unknown command";
        }
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

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.executeCommand("look"));
        System.out.println(game.executeCommand("get sword"));
        System.out.println(game.executeCommand("inv"));
        System.out.println(game.executeCommand("goto Forest"));
        System.out.println(game.executeCommand("look"));
        System.out.println(game.executeCommand("drop sword"));
        System.out.println(game.executeCommand("inv"));
        System.out.println(game.executeCommand("reset"));
        System.out.println(game.executeCommand("look"));
    }
}

class Player {
    private Location location;
    private final List<Artefact> inventory;

    public Player(String name, Location location) {
        this.location = location;
        this.inventory = new ArrayList<>();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addArtefact(Artefact artefact) {
        inventory.add(artefact);
    }

    public Artefact removeArtefact(String artefactName) {
        for (Artefact artefact : inventory) {
            if (artefact.getName().equalsIgnoreCase(artefactName)) {
                inventory.remove(artefact);
                return artefact;
            }
        }
        return null;
    }

    public String listArtefacts() {
        if (inventory.isEmpty()) {
            return "nothing";
        }
        StringBuilder sb = new StringBuilder();
        for (Artefact artefact : inventory) {
            sb.append(artefact.getName()).append(" ");
        }
        return sb.toString().trim();
    }

    public void clearInventory() {
        inventory.clear();
    }
}

class Location {
    private final String description;
    private final List<Artefact> artefacts;
    private final Map<String, Location> paths;

    public Location(String name, String description) {
        this.description = description;
        this.artefacts = new ArrayList<>();
        this.paths = new HashMap<>();
    }

    public void addPath(String direction, Location location) {
        paths.put(direction, location);
    }

    public Location getPath(String direction) {
        return paths.get(direction);
    }

    public void addArtefact(Artefact artefact) {
        artefacts.add(artefact);
    }

    public Artefact removeArtefact(String artefactName) {
        for (Artefact artefact : artefacts) {
            if (artefact.getName().equalsIgnoreCase(artefactName)) {
                artefacts.remove(artefact);
                return artefact;
            }
        }
        return null;
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append(description).append("\nArtefacts here: ");
        if (artefacts.isEmpty()) {
            sb.append("none");
        } else {
            for (Artefact artefact : artefacts) {
                sb.append(artefact.getName()).append(" ");
            }
        }
        sb.append("\nPaths: ");
        if (paths.isEmpty()) {
            sb.append("none");
        } else {
            for (String direction : paths.keySet()) {
                sb.append(direction).append(" ");
            }
        }
        return sb.toString().trim();
    }
}

class Artefact {
    private final String name;

    public Artefact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
