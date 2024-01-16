package com.example.comp336_3;

import java.util.LinkedList;

public class Vertex {
	Location location;
	Vertex previous;
	int num;
	double distance=Integer.MAX_VALUE;//
	boolean visited;
	LinkedList<Edge> adjacentsList = new LinkedList<Edge>();

	public Vertex(Location location, int number) {
		super();
		this.location = location;
		this.num = number;
	}

	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public LinkedList<Edge> getAdjacentsList() {
		return adjacentsList;
	}

	public void setAdjacentsList(LinkedList<Edge> adjacentsList) {
		this.adjacentsList = adjacentsList;
	}

	public boolean FindEdge(String ss) {

		for (int i = 0; i < adjacentsList.size(); i++) {
			if (adjacentsList.get(i).getD().getLocation().getName().compareToIgnoreCase(ss) == 0)
				return true;
		}
		return false;
	}

	public String ttoString() {
		String r = location.getName()+":";
		for (int i = 0; i < adjacentsList.size(); i++) {
			r = r + adjacentsList.get(i).targetVer.location.getName() + "\n";
		}
		return r;
	}

}
