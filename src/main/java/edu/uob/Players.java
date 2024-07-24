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

public class Players {
    private String name;
    private String username; //
    private List<Artefact> inventory;

    public Players(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public void pickup(Artefact artefact) {
        inventory.add(artefact);
    }

    public void drop(Artefact artefact) {
        inventory.remove(artefact);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Artefact> getInventory() {
        return inventory;
    }
}

