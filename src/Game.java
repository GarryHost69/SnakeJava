import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game implements Runnable{
	
	private Window gameWindow;
	private KeyManager keyManager;
	private boolean running = false;
	private Thread thread;
	private Snake[] snake;
	private Random random;
	private BufferStrategy b;
	private Graphics2D g;
	private int width, height, head, tail, elapsedTime;
	private String title;
	
	Game(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
		snake = new Snake[1000];
		head = 0;
		tail = 0;
		random = new Random();
		elapsedTime = 0;
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		else {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public synchronized void stop() {
		if (!running) {
			return;
		}
		else {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		init();
		int fps = 60;
		double tickTime = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / tickTime;
			timer += now - lastTime;
			lastTime = now;
			if (delta >= 1) {
				render();
				delta--;
			}
			if (timer >= 1000000000) {
				timer = 0;
			}
		}
	}
	
	public void render() {
		b = gameWindow.getCanvas().getBufferStrategy();
		if (b == null) {
			gameWindow.getCanvas().createBufferStrategy(3);
			return;
		}
		g = (Graphics2D) b.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		g.setColor(Color.RED);
		for (int i = 0; i < tail; i++) {
			g.fillRect(snake[i].x, snake[i].y, 10, 10);
			g.fillOval(snake[i].x, snake[i].y, 10, 10);
			if (i == 0) {
				g.setColor(Color.BLACK);
			}
		}
		g.setColor(Color.BLUE);
		g.fillRect(Snake.fruit.x, Snake.fruit.y, 10, 10);
		update();
		if (checkCollision()) {
			int x, y;
			x = random.nextInt(500);
			y = random.nextInt(500);
			if (x < 100) {
				x += 100;
			}
			if (y < 100) {
				y += 100;
			}
			Snake.fruit = new Snake.Fruit(x, y);
			Snake s = new Snake(snake[head].x + Snake.xSpeed, snake[head].y + Snake.ySpeed);
			snake[tail] = s;
			tail++;
			elapsedTime = 0;
		}
		if (snake[head].x <= 0) {
			snake[head].x = width;
			Snake.xSpeed = -1;
			Snake.ySpeed = 0;
		}
		else 
		if (snake[head].x >= width) {
			snake[head].x = 0;
			Snake.xSpeed = 1;
			Snake.ySpeed = 0;
		}
		else 
		if (snake[head].y >= height) {
			snake[head].y = 0;
			Snake.xSpeed = 0;
			Snake.ySpeed = 1;	
		}
		else 
		if (snake[head].y <= 0) {
			snake[head].y = height;
			Snake.xSpeed = 0;
			Snake.ySpeed = -1;
		}
		b.show();
		g.dispose();
	}
	
	private boolean checkCollision() {
		Snake head = snake[0];
		if (head.y + 10 >= Snake.fruit.y && head.y <= Snake.fruit.y + 10) {
			if (head.x >= Snake.fruit.x && head.x <= Snake.fruit.x + 10) {
				return true;
			}
		}
		if (head.y >= Snake.fruit.y && head.y <= Snake.fruit.y + 10) {
			if (head.x >= Snake.fruit.x && head.x <= Snake.fruit.x + 10) {
				return true;
			}
		}
		if (head.x + 10 >= Snake.fruit.x && head.x <= Snake.fruit.x + 10) {
			if (head.y >= Snake.fruit.y && head.y <= Snake.fruit.y + 10) {
				return true;
			}
		}
		if (head.x >= Snake.fruit.x && head.x <= Snake.fruit.x + 10) {
			if (head.y >= Snake.fruit.y && head.y <= Snake.fruit.y + 10) {
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		for (int i = tail; i >= head; i--) {
			snake[i+1] = snake[i];
		}
		Snake s = new Snake(snake[head].x + Snake.xSpeed * 3, snake[head].y + Snake.ySpeed * 3);
		snake[head] = s;
		elapsedTime++;
		if (elapsedTime == 500) {
			int x, y;
			x = random.nextInt(500);
			y = random.nextInt(500);
			if (x < 100) {
				x += 100;
			}
			if (y < 100) {
				y += 100;
			}
			Snake.fruit = new Snake.Fruit(x, y);
			elapsedTime = 0;
		}	
	}
	
	public void init() {
		gameWindow = new Window(width, height, title);
		gameWindow.getFrame().addKeyListener(keyManager);
		Snake s = new Snake(width/2, height/2);
		snake[head] = s;
		tail++;
		int x, y;
		x = random.nextInt(500);
		y = random.nextInt(500);
		if (x < 100) {
			x += 100;
		}
		if (y < 100) {
			y += 100;
		}
		Snake.fruit = new Snake.Fruit(x, y);
	}
	
	public class KeyManager implements KeyListener {	

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			switch (e.getKeyChar()) {
				case 'w':	if (Snake.xSpeed != 0 && Snake.ySpeed != -1) {
								Snake.xSpeed = 0;
								Snake.ySpeed = -1;
							}
							break; 
				case 'a':	if (Snake.xSpeed != -1 && Snake.ySpeed != 0) {
								Snake.xSpeed = -1;
								Snake.ySpeed = 0;
							}
							break;
				case 's':	if (Snake.xSpeed != 0 && Snake.ySpeed != 1) {
								Snake.xSpeed = 0;
								Snake.ySpeed = 1;
							}
							break;
				case 'd':	if (Snake.xSpeed != 1 && Snake.ySpeed != 0) {
								Snake.xSpeed = 1;
								Snake.ySpeed = 0;
							}	
							break;
			}	
		}
		
	}
	
}
