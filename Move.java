package makeev.mmcp;

public abstract class Move implements Runnable {
	Thread t;
	BodyList bodyList;

	public Move(BodyList v) {
		t = new Thread(this);
		this.bodyList = v;
		t.start();
	}

	public abstract void run();

}
