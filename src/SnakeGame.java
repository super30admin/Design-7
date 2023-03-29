import java.util.Deque;
import java.util.LinkedList;
//Time Complexity : O(width * height)
//Space Complexity : O(width * height)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

class SnakeGame {
	int width;
	int height;
	int[][] food;
	int len;
	Deque<Pair> path;

	public SnakeGame(int width, int height, int[][] food) {
		this.width = width;
		this.height = height;
		this.food = food;
		len = 0;
		path = new LinkedList<>();
		path.add(new Pair(0, 0));
	}

	public int move(String direction) {
		Pair first = path.peekFirst(), last = path.pollLast();
		int x = first.x, y = first.y;
		switch (direction) {
		case "U":
			x--;
			break;
		case "D":
			x++;
			break;
		case "L":
			y--;
			break;
		case "R":
			y++;
			break;
		default: // invalid input
			return -1;
		}

		if (x < 0 || x >= height || y < 0 || y >= width)
			return -1;
		for (Pair p : path) {
			if (p.x == x && p.y == y)
				return -1;
		}
		path.addFirst(new Pair(x, y));
		System.out.println(len);
		if (len < food.length && food[len][0] == x && food[len][1] == y) {
			len++;
			path.addLast(last);
		}
		return path.size() - 1;
	}
}

class Pair {
	int x;
	int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

/**
 * Your SnakeGame object will be instantiated and called as such: SnakeGame obj
 * = new SnakeGame(width, height, food); int param_1 = obj.move(direction);
 */