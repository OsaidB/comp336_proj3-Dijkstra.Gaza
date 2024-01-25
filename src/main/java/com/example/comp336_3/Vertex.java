package com.example.comp336_3;

import java.util.LinkedList;

public class Vertex {
    Location location;
    ////////////////////////////////////	//
    boolean visited;
    double distance = Integer.MAX_VALUE;//	//after calculating between...
    Vertex previous;
    ////////////////////////////////////	//
    int num;

    LinkedList<Edge> adjacentsList = new LinkedList<Edge>();


    public Vertex(Location location, int number) {
        super();
        this.location = location;
        this.num = number;
    }

    public Location getLocation() {
        return location;
    }

    public LinkedList<Edge> getAdjacentsList() {
        return adjacentsList;
    }

}
