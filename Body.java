package Kepler;

import java.util.ArrayList;

public class Body implements Cloneable {
	double mass;
	Velocity v;
	Point p;
	Force f;
	static final double G = 6.6725 * Math.pow(0.1, 11);

	public Body(double mass, Point p, Velocity v, Force f) {
		this.mass = mass;
		this.v = v;
		this.p = p;
		this.f = f;
	}

	void setMass(double x) {
		this.mass = x;
	}

	void setV(Velocity x) {
		this.v = x;
	}

	void setF(Force x) {
		this.f = x;
	}

	void setP(Point x) {
		this.p = x;
	}

	static boolean equals(Body b1, Body b2) {
		return (b1.p.x == b2.p.x) && (b1.p.y == b2.p.y) && (b1.p.z == b2.p.z);
	}

	static double dist(Body b1, Body b2) {
		return Math.sqrt((b1.p.x - b2.p.x) * (b1.p.x - b2.p.x)
				+ (b1.p.y - b2.p.y) * (b1.p.y - b2.p.y) + (b1.p.z - b2.p.z)
				* (b1.p.z - b2.p.z));
	}

	static double potentialEnergy(Body b1, Body b2) {
		double r = dist(b1, b2);
		return -G * b1.mass * b2.mass / r;
	}

	static double kineticEnergy(Body b1) {
		double v = Velocity.value(b1.v);
		return b1.mass * v * v / 2;
	}

	static Force force(Body b1, Body b2) {
		double rx = b1.p.x - b2.p.x;
		double ry = b1.p.y - b2.p.y;
		double rz = b1.p.z - b2.p.z;

		double k = 1;

		double dist = dist(b1, b2);
		double eps = 1;
		if (dist < eps) {
			// dist += eps;
			k = 0;
		}

		double f = -k * G * (b1.mass * b2.mass) / Math.pow(dist, 3);

		return new Force(f * rx, f * ry, f * rz);
	}

	static Force forces(Body b1, ArrayList<Body> bodySet, int n) {
		Force f = new Force(0, 0, 0);

		for (int i = 0; i < bodySet.size(); i++) {
			if (i != n) {
				Force f1 = force(b1, bodySet.get(i));
				f = Force.sum(f, f1);
			}
		}

		return f;
	}

	public Object clone() {
		Body newObject = null;
		try {
			newObject = (Body) super.clone();
			newObject.v = (Velocity) this.v.clone();
			newObject.p = (Point) this.p.clone();
			newObject.f = (Force) this.f.clone();

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newObject;
	}

}
