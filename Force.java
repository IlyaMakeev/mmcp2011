package makeev.mmcp;

public class Force extends RVector{	

	public Force(double x, double y, double z) {
		super(x, y, z);
	}

	static Force sum(Force f1, Force f2) {
		return new Force(f1.x + f2.x, f1.y + f2.y, f1.z + f2.z);
	}

	static double value(Force f) {
		return Math.sqrt(f.x * f.x + f.y * f.y + f.z * f.z);
	}


}
