package com.example.comp336_3;

public class PathTable {
	private double distance;
	private String source;
	private String target;
	
	public PathTable() {
	}
	
	public PathTable(double d, String source, String target) {
		super();
		this.distance = d;
		this.source = source;
		this.target = target;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return  source + "->" + target +"\n";
	}

}
