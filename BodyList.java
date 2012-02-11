package makeev.mmcp;

import java.util.ArrayList;

public class BodyList {
	ArrayList<Body> list;
	Body CenterMass;
	
	public BodyList() {
		this.list = new ArrayList<Body>();		
	}
	
	public BodyList(ArrayList<Body> v) {
		this.list = v;
	}	
	
	void move(int n) {
		System.out.println("SimpleMove...");
		ArrayList<Body> v = this.list;
		double dt = 1.0 / (double) n;
		for (int i = 0; i < n; i++) {
			step(v, dt, v.size());
		}
	}

	static void step(ArrayList<Body> v, double dt, int num) {		
		for (int i = 0; i < v.size(); i++) {
			 v.get(i).setF(Body.forces(v.get(i), v, i));			
		}
		
		for (int i = 0; i < num; i++) {
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

			Velocity newV = new Velocity(vx + ax * dt, vy + ay * dt, vz + az * dt);

			v.get(i).setP(newPoint);
			v.get(i).setV(newV);
		}
	}
	
	
	void klastersMove(int num, int n) {
		ArrayList<Body> v = this.list;
		double dt = 1.0 / (double) n;
		for (int i = 0; i < n; i++) {
			klastersStep(v, num, dt);
		}
	}
	
	static void klastersStep(ArrayList<Body> v, int num, double dt){		
		
		double xmin = v.get(0).p.x;
		double xmax = v.get(0).p.x;
		double ymin = v.get(0).p.y;
		double ymax = v.get(0).p.y;
		double zmin = v.get(0).p.z;
		double zmax = v.get(0).p.z;
		
		for (int i = 1; i < v.size(); i++) {
			Body b = v.get(i);
			xmin = Math.min(xmin, b.p.x);
			ymin = Math.min(ymin, b.p.y);
			zmin = Math.min(zmin, b.p.z);			
			xmax = Math.max(xmax, b.p.x);
			ymax = Math.max(ymax, b.p.y);
			zmax = Math.max(zmax, b.p.z);			
		}
		
		xmin -=0.01;
		ymin -=0.01;
		zmin -=0.01;
		xmax +=0.01;
		ymax +=0.01;
		zmax +=0.01;
		
		double dx = (xmax - xmin) / num;
		double dy = (ymax - ymin) / num;
		double dz = (zmax - zmin) / num;
		
		BodyList[][][] a = new BodyList[num][num][num];	
		
		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			int x = 0;
			int y = 0;
			int z = 0;
			for (int j = 0; j < num; j++) {
				if (xmin + j * dx < b.p.x && b.p.x < xmin + (j+1) * dx) {
					x = j;
				}
				if (ymin + j * dy < b.p.y && b.p.y < ymin + (j+1) * dy) {
					y = j;
				}
				if (zmin + j * dz < b.p.z && b.p.z < zmin + (j+1) * dz) {
					z = j;
				}
			}
			
			if (a[x][y][z] == null) {
				a[x][y][z] = new BodyList();
			}
			
			a[x][y][z].list.add(b);
			
		}
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				for (int k = 0; k < a.length; k++) {
					if (a[i][j][k] != null) {
						a[i][j][k].CenterMass = initCenterMass(a[i][j][k].list);
					}
				}
			}
		}
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				for (int k = 0; k < a.length; k++) {					
					if (a[i][j][k] != null && a[i][j][k].list.size() > 0) {
						
						ArrayList<Body> v1 = new ArrayList<Body>();
						for (int h = 0; h < a[i][j][k].list.size(); h++) {
							v1.add(a[i][j][k].list.get(h));
						}
						
						for (int i1 = 0; i1 < a.length; i1++) {
							for (int j1 = 0; j1 < a.length; j1++) {
								for (int k1 = 0; k1 < a.length; k1++) {
									if (a[i1][j1][k1] != null && a[i1][j1][k1].list.size() > 0) {
										if (!((i == i1) && (j == j1) && (k == k1))) {											
											v1.add(a[i1][j1][k1].CenterMass);											
										}
									}
								}
							}
						}
								
						step(v1, dt, a[i][j][k].list.size());	
					}					
				}
			}
		}		
	}
	
	
	
	static Body initCenterMass(ArrayList<Body> v) {
		double Mx = 0;
		double My = 0;
		double Mz = 0;
		double CenterMass = 0;

		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			Mx += b.p.x * b.mass;
			My += b.p.y * b.mass;
			Mz += b.p.z * b.mass;
			CenterMass += b.mass;
		}
		
		Mx /= CenterMass;
		My /= CenterMass;
		Mz /= CenterMass;
		
		return new Body(CenterMass, new Point(Mx, My, Mz), new Velocity(0, 0, 0), new Force(0, 0, 0));
	}
	
	
	static void stepRK(ArrayList<Body> v, double dt) {
		for (int i = 0; i < v.size(); i++) {
			v.get(i).setF(Body.forces(v.get(i), v, i));
		}
		
		ArrayList<Body> supp = new ArrayList<Body>();		

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
			
			Body b2 = new Body(b.mass, newPoint, newV, new Force(0, 0, 0));
			
			supp.add(b2);			
		}
		
		for (int i = 0; i < supp.size(); i++) {
			supp.get(i).setF(Body.forces(supp.get(i), supp, i));
		}		
		
		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			Body b2 = supp.get(i);

			double x = b.p.x;
			double y = b.p.y;
			double z = b.p.z;

			b.p.x = (x + dt * (b.v.x + b2.v.x) / 2);
			b.p.y = (y + dt * (b.v.y + b2.v.y) / 2);
			b.p.z = (z + dt * (b.v.z + b2.v.z) / 2);

			double vx = b.v.x;
			double vy = b.v.y;
			double vz = b.v.z;

			b.v.x = (vx + dt * (b.f.x + b2.f.x) / 2);
			b.v.y = (vy + dt * (b.f.y + b2.f.y) / 2);
			b.v.z = (vz + dt * (b.f.z + b2.f.z) / 2);

		}
		
	}
	
	
	static void initMomentum(BodyList list) {
		double px = 0;
		double py = 0;
		double pz = 0;
		ArrayList<Body> v = list.list;

		for (int i = 0; i < v.size(); i++) {
			Body b = v.get(i);
			px += b.v.x * b.mass;
			py += b.v.y * b.mass;
			pz += b.v.z * b.mass;
		}

		System.out.println("P(" + px + ", " + py + ", " + pz + ")");
	}

	static double initEnergy(BodyList list) {
		return potentialEnergy(list) + kineticEnergyEnergy(list);
	}

	static double potentialEnergy(BodyList list) {
		ArrayList<Body> v = list.list;
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

	static double kineticEnergyEnergy(BodyList list) {
		ArrayList<Body> v = list.list;
		double Ek = 0;
		for (int i = 0; i < v.size(); i++) {
			Body b1 = v.get(i);
			Ek += Body.kineticEnergy(b1);
		}

		return Ek;
	}
	
}
