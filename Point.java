
package Kepler;
public class Point implements Cloneable{
	double x, y, z;

	public Point(double x, double y, double z) {
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
	
	public Object clone() {
		Point newObject = null;
		try {
			newObject = (Point) super.clone();
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