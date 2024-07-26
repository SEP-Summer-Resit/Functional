package edu.uob;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, who has a location and an inventory of artefacts.
 */
class Player {
    private Location location;
    private List<Artefact> inventory;

    public Player(Location location) {
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
