// Time Complexity : O(n) where n = length of food matrix
// Space Complexity : O(n) where n = length of food matrix
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
//353. Design Snake Game (Medium) - https://leetcode.com/problems/design-snake-game/
//class SnakeGame {
//
//    LinkedList<int []> snake; // first elements is tail and last element is head
//    int [] head;
//    int i; // moving on the food array
//    int height, width;
//    int [][] foodList;
//    
//    public SnakeGame(int width, int height, int[][] food) {
//        this.snake = new LinkedList<>();
//        this.width = width;
//        this.height = height;
//        this.head = new int[] {0, 0};
//        this.foodList = food;
//        this.snake.addLast(new int[] {head[0], head[1]});
//    }
//    
//    public int move(String direction) {
//        if (direction.equals("U")) {
//            head[0]--;
//        } else if (direction.equals("D")) {
//            head[0]++;
//        } else if (direction.equals("L")) {
//            head[1]--;
//        } else if (direction.equals("R")) {
//            head[1]++;
//        }
//        
//        // check for boundary condition (snake hits borders)
//        if (head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width) return -1;
//        
//        // check if snake is touching itself by iterating linkedlist leaving behind first element because as soon as the head moves to tail position, the tail gets moved to next position so there won't be any collision
//        for (int i = 1; i < snake.size(); i++) { // O(l) where l = length of snake (linkedlist)
//            int[] curr = snake.get(i);
//            
//            if (head[0] == curr[0] && head[1] == curr[1]) return -1;
//        }
//        
//        // if snake eats food
//        if (i < foodList.length) { // O(n) where n = length of food matrix
//            int[] currFood = foodList[i];
//            
//            if (head[0] == currFood[0] && head[1] == currFood[1]) {
//                snake.add(new int[] {currFood[0], currFood[1]});
//                i++;
//                return snake.size()-1;
//            }
//        }
//        
//        // if normal move, remove tail of snake (head of linkedlist)
//        snake.removeFirst();
//        snake.addLast(new int[] {head[0], head[1]});
//        return snake.size()-1;
//    }
//}

class SnakeGame {

	LinkedList<int[]> snake; // first elements is tail and last element is head
	int[] head;
	int i; // moving on the food array
	int height, width;
	int[][] foodList;
	boolean[][] visited;

	public SnakeGame(int width, int height, int[][] food) {
		this.snake = new LinkedList<>();
		this.width = width;
		this.height = height;
		this.head = new int[] { 0, 0 };
		this.foodList = food;
		this.visited = new boolean[height][width];
		this.snake.addLast(new int[] { head[0], head[1] });
	}

	public int move(String direction) {
		if (direction.equals("U")) {
			head[0]--;
		} else if (direction.equals("D")) {
			head[0]++;
		} else if (direction.equals("L")) {
			head[1]--;
		} else if (direction.equals("R")) {
			head[1]++;
		}

		// check for boundary condition (snake hits borders)
		if (head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width)
			return -1;

		// check if snake is touching itself by iterating linkedlist leaving behind
		// first element because as soon as the head moves to tail position, the tail
		// gets moved to next position so there won't be any collision
//         for (int i = 1; i < snake.size(); i++) { // O(l) where l = length of snake (linkedlist)
//             int[] curr = snake.get(i);

//             if (head[0] == curr[0] && head[1] == curr[1]) return -1;
//         }

		// if my visited array already contains the location except the tail, snake dies
		if (visited[head[0]][head[1]])
			return -1; // O(1)

		// if snake eats food
		if (i < foodList.length) { // O(n) where n = length of food matrix
			int[] currFood = foodList[i];

			if (head[0] == currFood[0] && head[1] == currFood[1]) {
				snake.addLast(new int[] { currFood[0], currFood[1] });
				visited[currFood[0]][currFood[1]] = true;
				i++;
				return snake.size() - 1;
			}
		}

		// if normal move, remove tail of snake (head of linkedlist)
		if (snake.size() > 1) {
			int[] toRemove = snake.get(1); // get the reference to head so as to mark it unvisited after removing from
											// the list
			visited[toRemove[0]][toRemove[1]] = false;
		}

		snake.removeFirst();
		snake.addLast(new int[] { head[0], head[1] });

		if (snake.size() > 1) {
			visited[head[0]][head[1]] = true;
		}

		return snake.size() - 1;
	}
}

/**
 * Your SnakeGame object will be instantiated and called as such: SnakeGame obj
 * = new SnakeGame(width, height, food); int param_1 = obj.move(direction);
 */