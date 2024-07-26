package edu.uob;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game, who has a location and an inventory of artefacts.
 */
public class Player {
    private Location location;
    private final List<Artefact> inventory;

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

    /**
     * Transfers an artefact from the storeroom to the player's inventory.
     * 
     * @param storeroom The storeroom location.
     * @param artefactName The name of the artefact to transfer.
     * @return True if the artefact was successfully transferred, false otherwise.
     */
    public boolean transferFromStoreroom(Location storeroom, String artefactName) {
        Artefact artefact = storeroom.removeArtefact(artefactName);
        if (artefact != null) {
            this.addArtefact(artefact);
            return true;
        }
        return false;
    }

    /**
     * Transfers an artefact from the player's inventory to the storeroom.
     * 
     * @param storeroom The storeroom location.
     * @param artefactName The name of the artefact to transfer.
     * @return True if the artefact was successfully transferred, false otherwise.
     */
    public boolean transferToStoreroom(Location storeroom, String artefactName) {
        Artefact artefact = this.removeArtefact(artefactName);
        if (artefact != null) {
            storeroom.addArtefact(artefact);
            return true;
        }
        return false;
    }
}
