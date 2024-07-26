/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uob;

import java.util.ArrayList;
import java.util.List;

import edu.uob.Character;

/**
 * Represents a location in the game, which can contain characters, furniture, artefacts, and paths.
 * A location has a name and description inherited from the Entity class.
 */
public class Location extends Entity {
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

    public Location(String name, String description) {
        super(name, description);
        this.artefacts = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.furniture = new ArrayList<>();
        this.characters = new ArrayList<>();
    }

    /**
     * Adds a piece of furniture to the location.
     * 
     * @param furniture The furniture to add.
     */
    public void addFurniture(Furniture furniture) { 
        this.furniture.add(furniture); 
    }

    /**
     * Adds a character to the location.
     * 
     * @param character The character to add.
     */
    public void addCharacter(Character character) { 
        this.characters.add(character); 
    }

    /**
     * Adds an artefact to the location.
     * 
     * @param artefact The artefact to add.
     */
    public void addArtefact(Artefact artefact) {
        this.artefacts.add(artefact);
    }

    /**
     * Removes an artefact from the location by name.
     * 
     * @param artefactName The name of the artefact to remove.
     * @return The removed artefact, or null if no artefact with the given name was found.
     */
    public Artefact removeArtefact(String artefactName) {
        for (Artefact artefact : artefacts) {
            if (artefact.getName().equalsIgnoreCase(artefactName)) {
                artefacts.remove(artefact);
                return artefact;
            }
        }
        return null;
    }

    /**
     * Checks if a path exists to a given destination.
     * 
     * @param destination The destination location to check.
     * @return True if a path exists to the destination, false otherwise.
     */
    public boolean pathExists(Location destination) {
        for (Path path : paths) {
            if (path.getEnd().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a path to a given destination location.
     * 
     * @param destination The destination location to create a path to.
     */
    public void createPath(Location destination){
        paths.add(new Path(destination));
    }
}
