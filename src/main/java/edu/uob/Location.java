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

class Location {
    private final String name;
    private final String description;
    private List<Character> characters;
    private List<Furniture> furniture;
    private List<Artefact> artefacts;
    private final Map<String, Location> paths;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Furniture> getFurniture() {
        return furniture;
    }

    public Map<String, Location> getPaths() {
        return paths;
    }

    public Location(String Name, String description) {
        this.name = Name;
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