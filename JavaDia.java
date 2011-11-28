package Kepler;
 import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;

import javax.swing.JFrame;
 
 public class JavaDia implements Runnable {
     static Thread displayT = new Thread(new JavaDia());
     static boolean bQuit = false;
 
     public static void main(String[] args) {
         displayT.start();
     }
 
     public void run() {
         JFrame frame = new JFrame();
         GLCanvas canvas = new GLCanvas();
         canvas.addGLEventListener(new JavaRenderer());
         frame.add(canvas);
         frame.setSize(1024, 768);
         int size = frame.getExtendedState();
         frame.setExtendedState(size);
 
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {            	
                 bQuit = true;
             }
         });
         
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setVisible(true);
         canvas.requestFocus();
         while( !bQuit ) {
             canvas.display();
         }
     }
 }