package edu.uob;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private final List<String> triggers;
    private final List<String> subjects;
    private final List<String> consumed;
    private final List<String> produced;
    private String narration;

    public Action() {
        this.triggers = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.consumed = new ArrayList<>();
        this.produced = new ArrayList<>();
    }

    public void addTrigger(String trigger) {
        this.triggers.add(trigger);
    }

    public List<String> getTriggers() {
        return triggers;
    }

    public void addSubject(String subject) {
        this.subjects.add(subject);
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void addConsumed(String consumed) {
        this.consumed.add(consumed);
    }

    public List<String> getConsumed() {
        return consumed;
    }

    public void addProduced(String produced) {
        this.produced.add(produced);
    }

    public List<String> getProduced() {
        return produced;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getNarration() {
        return narration;
    }

    /**
     * Executes the action, consuming and producing specified items.
     * 
     * @param player   The player performing the action.
     * @param location The location where the action is performed.
     * @return A narration of the action performed.
     */
    public String execute(Player player, Location location) {
        // Consume items from the player's inventory and location
        for (String item : consumed) {
            Artefact artefact = player.removeArtefact(item);
            if (artefact == null) {
                artefact = location.removeArtefact(item);
            }
            if (artefact == null) {
                return "You do not have the required item: " + item;
            }
        }

        // Produce items and add them to the player's inventory or location
        for (String item : produced) {
            Artefact artefact = new Artefact(item, "Produced item");
            player.addArtefact(artefact);
        }

        return narration;
    }
}

