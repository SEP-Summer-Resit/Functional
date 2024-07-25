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
    private List<Path> paths;

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

    public List<Path> getPaths() {
        return paths;
    }

    public Location(String Name, String description) {
        this.name = Name;
        this.description = description;
        this.artefacts = new ArrayList<Artefact>();
        this.paths = new ArrayList<Path>();
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

    public boolean pathExists(Location destination) {
        for (Path path : paths) {
            if (path.getEnd().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public void createPath(Location destination){
        paths.add(new Path(this, destination));
    }

}