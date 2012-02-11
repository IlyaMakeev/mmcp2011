package makeev.mmcp;

public class KlastersMove extends Move implements Runnable{	
	
	public KlastersMove(BodyList v) {
		super(v);
	}	

	public void run() {
		System.out.println("KlastersMove...");
		int n = 10000;
		int num = (int) Math.floor(Math.pow((double) bodyList.list.size(), 1.0 / 6.0));
		
		synchronized (bodyList) {			
			bodyList.klastersMove(num, n);
		}		
	}

}
