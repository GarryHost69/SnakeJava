
public class Snake {
	public int x, y;
	public static int xSpeed = 1, ySpeed = 0;
	public static Fruit fruit;
	
	Snake(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static class Fruit {
		public int x, y;
		Fruit(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}
