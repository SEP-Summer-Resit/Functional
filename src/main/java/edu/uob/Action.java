package edu.uob;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private List<String> triggers;
    private List<String> subjects;
    private List<String> consumed;
    private List<String> produced;
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

}
