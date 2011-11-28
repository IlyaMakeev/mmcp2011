package Kepler;

import javax.media.opengl.GL;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class JavaRenderer implements GLEventListener, KeyListener {
	private float rotateT = 0.0f;
	private static final GLU glu = new GLU();
	private static ArrayList<Body> primary = Main.init();


	public void display(GLAutoDrawable gLDrawable) {

		final GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(-0.0f, 0.0f, -100.0f);
		gl.glPushMatrix();


	//	gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
	//	gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
	//	gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
	//	gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

		gl.glLineWidth(1.0f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);

		double l = 200;
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(0, -l, 0);
		gl.glVertex3d(0, l, 0);
		gl.glVertex3d(l, 0, 0);
		gl.glVertex3d(-l, 0, 0);
		gl.glVertex3d(0, 0, -l);
		gl.glVertex3d(0, 0, l);
		gl.glEnd();

		gl.glColor3d(0, 1, 0);
		double r = 0.5;
		int k = 5;
		for (int i = 0; i < primary.size(); i++) {
			Body b = primary.get(i);
			float x = (float) b.p.x / k;
			float y = (float) b.p.y / k;
			float z = (float) b.p.z / k;

			gl.glTranslatef(x, y, z);
			GLUquadric quad = glu.gluNewQuadric();
			glu.gluSphere(quad, r, 10, 10);
			glu.gluDeleteQuadric(quad);
			gl.glPopMatrix();
		}

		Main.move(primary, 10);

		// rotateT += 0.05f;
	}

	public void init(GLAutoDrawable gLDrawable) {
		final GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gLDrawable.addKeyListener(this);

	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width,
			int height) {
		final GL gl = gLDrawable.getGL();
		if (height <= 0) {
			height = 1;
		}
		final float h = (float) width / (float) height;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			JavaDia.bQuit = true;
			JavaDia.displayT = null;
			System.exit(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Main.initMomentum(primary);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("E = " + Main.initEnergy(primary));
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// TODO Auto-generated method stub

	}

}