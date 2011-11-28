package Kepler;

public class Velocity implements Cloneable{
    double x, y, z;

	public Velocity (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	void setX(double x){
		this.x = x;
	}
	
	void setY(double y){
		this.y = y;
	}
	
	void setZ(double z){
		this.z = z;
	}
	
	static double value(Velocity  v) {
		return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}
	
	public Object clone() {
		Velocity newObject = null;
		try {
			newObject = (Velocity) super.clone();
			newObject.x =   this.x; 
			newObject.y =   this.y; 
			newObject.z =   this.z; 
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return newObject;
  }


}
