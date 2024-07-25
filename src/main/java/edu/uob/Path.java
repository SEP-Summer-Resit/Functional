/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package edu.uob;

/**
 *
 * @author liumu
 */
 public class Path {
    private final Location endLocation;

    public Path(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Location getEnd() {
        return endLocation;
    }
}
