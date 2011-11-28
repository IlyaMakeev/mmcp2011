package Kepler;

import java.util.ArrayList;
import java.util.Random;

public class Main {
	static Point M = new Point(0, 0, 0);
	static double MASS = 0;

	static ArrayList<Body> init() {

		ArrayList<Body> v = new ArrayList<Body>();

		Random rnd = new Random();

		// Body b1 = new Body(100000, new Point(-100, -100, 0), new
		// Velocity(0.01, 0.01, 0),
		// new Force(0, 0, 0));

		// v.add(b1);
		/*
		for (int i = 0; i < 30; i++) {
			double k1 = 20;
			// double d = 50;
			double x = 2 * (-0.5 + rnd.nextDouble()) * k1;
			double y = 2 * (-0.5 + rnd.nextDouble()) * k1;
			double z = 2 * (-0.5 + rnd.nextDouble()) * k1;

			double k2 = 0.05;
			double vx = 2 * (-0.5 + rnd.nextDouble()) * k2;
			double vy = 2 * (-0.5 + rnd.nextDouble()) * k2;
			double vz = 2 * (-0.5 + rnd.nextDouble()) * k2;

			v.add(new Body(10000000, new Point(x, y, 0),
					new Velocity(vx, vy, 0), new Force(0, 0, 0)));
		}*/

		/* */
		  double r = 0.02; Body b1 = new Body(1000000000, new Point(0, 50, 0),
		  new Velocity(0, 0, 0), new Force(0, 0, 0)); Body b2 = new
		  Body(10000000, new Point(0, 0, 0), new Velocity(1*r, 0*r, 0), new
		  Force(0, 0, 0)); v.add(b1); v.add(b2);
		
		return v;
	}

	static void move(ArrayList<Body> v, double t) {
		double dt = 0.1;
		int n = (int) (t / dt);
		for (int i = 0; i < n; i++) {
			step(v, dt);
		}
	}

	static void step(ArrayList<Body> v, double dt) {
		for (int i = 0; i < v.size(); i++) {
			v.get(i).setF(Body.forces(v.get(i), v, i));
		}

		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);

			double x = b.p.x;
			double y = b.p.y;
			double z = b.p.z;

			double vx = b.v.x;
			double vy = b.v.y;
			double vz = b.v.z;

			double ax = b.f.x / b.mass;
			double ay = b.f.y / b.mass;
			double az = b.f.z / b.mass;

			double newX = x + vx * dt + ax * dt * dt / 2;
			double newY = y + vy * dt + ay * dt * dt / 2;
			double newZ = z + vz * dt + az * dt * dt / 2;

			Point newPoint = new Point(newX, newY, newZ);

			Velocity newV = new Velocity(vx + ax * dt, vy + ay * dt, vz + az
					* dt);

			v.get(i).setP(newPoint);
			v.get(i).setV(newV);
		}
	}

	static void initMomentum(ArrayList<Body> v) {
		double px = 0;
		double py = 0;
		double pz = 0;

		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			px += b.v.x * b.mass;
			py += b.v.y * b.mass;
			pz += b.v.z * b.mass;
		}

		System.out.println("P(" + px + ", " + py + ", " + pz + ")");
	}

	static double initEnergy(ArrayList<Body> v) {
		return potentialEnergy(v) + kineticEnergyEnergy(v);
	}

	static double potentialEnergy(ArrayList<Body> v) {
		double Ep = 0;
		for (int i = 0; i < v.size(); i++) {
			Body b1 = v.get(i);
			for (int j = 0; j < i; j++) {
				Body b2 = v.get(j);
				Ep += Body.potentialEnergy(b1, b2);
			}
		}

		return Ep;
	}

	static double kineticEnergyEnergy(ArrayList<Body> v) {
		double Ek = 0;
		for (int i = 0; i < v.size(); i++) {
			Body b1 = v.get(i);
			Ek += Body.kineticEnergy(b1);
		}

		return Ek;
	}

	static void initCenterMass(ArrayList<Body> v) {
		double Mx = 0;
		double My = 0;
		double Mz = 0;

		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			Mx += b.p.x * b.mass;
			My += b.p.y * b.mass;
			Mz += b.p.z * b.mass;
		}
		Mx /= MASS;
		My /= MASS;
		Mz /= MASS;

		M = new Point(Mx, My, Mz);

		System.out.println("M(" + M.x + ", " + M.y + ", " + M.z + ")");
	}
	
	static void initMass(ArrayList<Body> v) {
		MASS = 0;
		for (int i = 0; i < v.size(); i++) {
			MASS += v.get(i).mass;
		}
		// System.out.println(MASS);
	}
}
