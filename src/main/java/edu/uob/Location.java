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
import java.util.List;

import edu.uob.Character;

class Location extends Entity {
    private final List<Character> characters;
    private final List<Furniture> furniture;
    private final List<Artefact> artefacts;
    private final List<Path> paths;

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
        super(Name, description);
        this.artefacts = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.furniture = new ArrayList<>();
        this.characters = new ArrayList<>();
    }

    public void addFurniture(Furniture furniture) { this.furniture.add(furniture); }

    public void addCharacter(Character character) { this.characters.add(character); }

    public void addArtefact(Artefact artefact) {
        this.artefacts.add(artefact);
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
        paths.add(new Path(destination));
    }
}