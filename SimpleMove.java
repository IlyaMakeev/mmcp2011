package makeev.mmcp;

public class SimpleMove extends Move implements Runnable{	
	
	public SimpleMove(BodyList v) {
		super(v);
	}
	
	public void run(){
		int n = 100000;
		synchronized (bodyList) {
			bodyList.move(n);	
		}		
	}	
	

}
