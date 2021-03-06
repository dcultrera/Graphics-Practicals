package uk.ac.cam.dc561.graphics.sup2;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.util.FPSAnimator;


public class MyMain extends JFrame {
	// Define constants for the top-level container
	private static String TITLE = "JOGL 2.0 Setup (GLCanvas)";  // window's title
	private static final int CANVAS_WIDTH = 600;  // width of the drawable
	private static final int CANVAS_HEIGHT = 600; // height of the drawable
	private static final int FPS = 60; // animator's target frames per second

	/** Constructor to setup the top-level container and animator */
	public MyMain() {
		// Create the OpenGL rendering canvas
		GLProfile glp = GLProfile.getDefault();  
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new MyGraphics(caps);
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		// Create a animator that drives canvas' display() at the specified FPS.
		final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

		// Create the top-level container frame
		this.getContentPane().add(canvas);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Use a dedicate thread to run the stop() to ensure that the
				// animator stops before program exits.
				new Thread() {
					@Override
					public void run() {
						if (animator.isStarted()) animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});
		this.setTitle(TITLE);
		this.pack();
		this.setVisible(true);
		animator.start(); // start the animation loop
	}

	/** The entry main() method */
	public static void main(String[] args) {
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyMain();  // run the constructor
			}
		});
	}
}