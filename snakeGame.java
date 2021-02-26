import java.util.*;

public class snakeGame {

	int width, height, score, foodIndex;
	int[][] food;
	Set<Integer> set;
	Deque<Integer> body;
	
	public snakeGame(int width, int height, int[][] food) {
	
		this.width = width;
		this.height = height;
		this.food = food;
		foodIndex = 0;
		set = new HashSet<>();
		// add 0 for food [0][0]
		set.add(0);
		body = new LinkedList<>();
		body.offer(0); // since the body will be at position 0
	}
	
	public int move(String direction) {
	
		// Case 0: if the game is over, return -1 or do nothing
		if(score == -1)  
			return -1;
		
		// initiliaze rowhead and colhead of the snake so that we can move it according to the direction given
		int rowHead = body.peekFirst()/width;
		int colHead = body.peekFirst() % width;
		
		// update rowHead or colhead for the given direction
		switch(direction) {
		
			case "U" : rowHead--;
						break;
			case "D" : rowHead++;
						break;
			case "R" : colHead++;
						break;
			case "L" : colHead--;
						break;
		}
		
		// calculate the head of the snake for the width given
		int head = rowHead * width + colHead;
		// Case 1: remove from set if the body is eating itself or return score -1 if the set already contains head which is its body itself 
		set.remove(body.peekLast());  // remove the last value added to body since its its already visited
		if(rowHead < 0 || colHead < 0 || rowHead == width || colHead == height || set.contains(head))
			return score = -1;
		
		// adding head for case 2 and case 3
		set.add(head);  // add the head value onto Set so that we can check if the food positions are visited or not
		body.offerFirst(head);  // add the head into body since we want to increase the body length if the snake has eaten the food
		
		// case 2: eating food, keep tail and add head
		// if the rowhead and colhead are same as food position, add the last snake body position onto set so that we do not revisit 
		// the same food position and increase our food index since we have already vsiited once food place and increase our score, 
		// since we know that once the food is found, we add score and increase length of body of snake.
		if(foodIndex < food.length && rowHead == food[foodIndex][0] && colHead == food[foodIndex][1])
		{
			set.add(body.peekLast());
			foodIndex++;
			return ++score;
		}
		
		// case 3: if the snake moves normally without finding any food and without going out of boundary,
		// we update the head by adding head and remove tail
		body.pollLast();
		return score; // return the existing score

	}
	
	
}
