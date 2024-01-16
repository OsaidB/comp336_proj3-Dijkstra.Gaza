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

    public void setD(Vertex desination) {
        this.targetVer = desination;
    }

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public void setCalculatedDistance(double calculatedDistance) {
        this.calculatedDistance = calculatedDistance;
    }

    public Vertex getSourceVer() {
        return sourceVer;
    }

    public void setSourceVer(Vertex s) {
        this.sourceVer = s;
    }

    @Override
    public String toString() {
        return "edges [desination=" + targetVer + ", source=" + sourceVer + ", weight=" + calculatedDistance + "]";
    }


}
