import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
	private JFrame frame;
	private int width, height;
	private String title;
	private Canvas canvas;
	Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		createWindow();
	}
	
	private void createWindow() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setBackground(Color.GREEN);
		frame.add(canvas);
		frame.pack();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
