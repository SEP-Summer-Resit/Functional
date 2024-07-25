package edu.uob;

public class GameGraphNode {

    private String name;
    private Location locationEntity;

    public GameGraphNode(String name, Location locationEntity) {
        this.name = name;
        this.locationEntity = locationEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocationEntity() {
        return locationEntity;
    }

    public void setLocationEntity(Location locationEntity) {
        this.locationEntity = locationEntity;
    }


}