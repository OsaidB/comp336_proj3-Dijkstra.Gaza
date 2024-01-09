package com.example.comp336_3;

public class edges {
	Vertex desination;
	Vertex source;
	double weight;
	//destination

	public edges(Vertex source,Vertex destination, double weight) {
		super();
		this.desination = destination;
		this.weight = weight ;
	}
	
	public Vertex getD() {
		return desination;
	}
	public void setD(Vertex desination) {
		this.desination = desination;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex s) {
		this.source = s;
	}

	@Override
	public String toString() {
		return "edges [desination=" + desination + ", source=" + source + ", weight=" + weight + "]";
	}
	
	

	public static void add(edges edges) {
		// TODO Auto-generated method stub
		
	}

}
