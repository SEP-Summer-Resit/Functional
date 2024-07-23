/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.uob;

/**
 *
 * @author liumu
 */
import java.util.List;

public class Locations {
    private String name;
    private String description;
    private List<Artefacts> artefacts;
    private List<Characters> characters;
    private List<Furniture> furniture;
    private List<Paths> paths;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Artefacts> getArtefacts() {
        return artefacts;
    }

    public List<Characters> getCharacters() {
        return characters;
    }

    public List<Furniture> getFurniture() {
        return furniture;
    }

    public List<Paths> getPaths() {
        return paths;
    }

    public void removeArtefact(Artefacts artefact) {
        artefacts.remove(artefact);
    }

    public void addArtefact(Artefacts artefact) {
        artefacts.add(artefact);
    }

    public void addCharacter(Characters character) {
        characters.add(character);
    }

    public void removeCharacter(Characters character) {
        characters.remove(character);
    }
}
