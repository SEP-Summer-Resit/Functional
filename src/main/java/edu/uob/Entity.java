package edu.uob;

public class Entity {
    private final String name;
    private final String description;

    public Entity(String Name, String Description){
        this.name = Name;
        this.description = Description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
