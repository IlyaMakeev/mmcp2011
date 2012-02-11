package makeev.mmcp;

public class Velocity extends RVector {

	public Velocity(double x, double y, double z) {
		super(x, y, z);
	}

	static double value(Velocity v) {
		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}

}
