package Kepler;

public class Force implements Cloneable {
	double x, y, z;

	public Force(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	void setX(double x) {
		this.x = x;
	}

	void setY(double y) {
		this.y = y;
	}

	void setZ(double z) {
		this.z = z;
	}

	static Force sum(Force f1, Force f2) {
		return new Force(f1.x + f2.x, f1.y + f2.y, f1.z + f2.z);
	}

	static double value(Force f) {
		return Math.sqrt(f.x * f.x + f.y * f.y + f.z * f.z);
	}

	public Object clone() {
		Force newObject = null;
		try {
			newObject = (Force) super.clone();
			newObject.x = this.x;
			newObject.y = this.y;
			newObject.z = this.z;

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newObject;
	}

}
