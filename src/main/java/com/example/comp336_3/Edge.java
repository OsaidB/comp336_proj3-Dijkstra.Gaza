package com.example.comp336_3;

public class Edge {
    Vertex targetVer;
    Vertex sourceVer;
    double calculatedDistance;//weight //cost


    public Edge(Vertex sourceVer, Vertex targetVer, double calculatedDistance) {
        super();
        this.targetVer = targetVer;
        this.calculatedDistance = calculatedDistance;
    }

    public Vertex getD() {
        return targetVer;
    }

    @Override
    public String toString() {
        return "edges [desination=" + targetVer + ", source=" + sourceVer + ", weight=" + calculatedDistance + "]";
    }


}
