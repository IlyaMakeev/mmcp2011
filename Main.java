package makeev.mmcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {	
		BodyList bodyList = new BodyList(initFile("circle_16.csv"));
		
		Thread thread;
		if (bodyList.list.size() < 100) {
			thread = new SimpleMove(bodyList).t;
		} else {
			thread = new KlastersMove(bodyList).t;
		}
		thread.join();
		
		fileWriter(bodyList, "ans.csv");
	}
	
	static ArrayList<Body> initFile(String filePath) throws IOException {		
		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader in = new BufferedReader(isr);
	        
		ArrayList<Body> list = new ArrayList<Body>();
		String s = in.readLine();
		while (s != null) {
			String[] a = s.split(" ");
	      
	        	Point p = new Point(Double.parseDouble(a[1]), Double.parseDouble(a[2]), Double.parseDouble(a[3]));
	        	Velocity v = new Velocity(Double.parseDouble(a[4]), Double.parseDouble(a[5]), Double.parseDouble(a[6]));
	        	Body b = new Body(Double.parseDouble(a[0]), p, v, new Force(0, 0, 0));
	        	
	        	list.add(b);
				s = in.readLine();
			}	       
	        
		return list;
	}
	
	static void fileWriter(BodyList v, String filePath) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		ArrayList<Body> list = v.list;
		for (int i = 0; i < v.list.size(); i++) {
			Body b = list.get(i);
			out.println(b.mass+" "+b.p.x+" "+b.p.y+" "+b.p.z+" "+b.v.x+" "+b.v.y+" "+b.v.z);
		}		
		out.flush();
	}
	
}
