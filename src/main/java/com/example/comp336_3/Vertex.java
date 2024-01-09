package application;

import java.util.LinkedList;

public class Vertex {
	College college;
	Vertex previous;
	int num;
	double distance=Integer.MAX_VALUE;
	boolean visited;
	LinkedList<edges> e = new LinkedList<edges>();

	public Vertex(College college, int number) {
		super();
		this.college = college;
		this.num = number;
	}

	public College getCollege() {
		return college;
	}


	public void setCollege(College college) {
		this.college = college;
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

	public LinkedList<edges> getE() {
		return e;
	}

	public void setE(LinkedList<edges> e) {
		this.e = e;
	}

	public boolean FindEdge(String ss) {

		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).getD().getCollege().getName().compareToIgnoreCase(ss) == 0)
				return true;
		}
		return false;
	}

	public String ttoString() {
		String r = college.getName()+":";
		for (int i = 0; i < e.size(); i++) {
			r = r + e.get(i).desination.college.getName() + "\n";
		}
		return r;
	}

}
