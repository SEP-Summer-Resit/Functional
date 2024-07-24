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

class Player {
    private Location location;
    private List<Artefact> inventory;
    private final String name;

    public Player(String Name, Location Location) {
        this.name = Name;
        this.location = Location;
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



