//Time O(1)
//Space O(N)
public class SnakeGame {

Deque<Integer> snakeBody = new LinkedList();
int width = 0;
int height = 0;
int[][] food;
int count = 0;
boolean gameOver = false;

public SnakeGame(int width, int height, int[][] food) {
    this.width = width;
    this.height = height;
    this.food = food;
    snakeBody.addLast(0);
}


public int move(String direction) {
    if(gameOver) return -1;
    int curValue = snakeBody.getFirst();
    int lastValue = snakeBody.removeLast();
    int[] head = new int[2];
    head[0] = curValue/width;
    head[1] = curValue%width;
    switch(direction) {
        case "U": head[0]--;break;
        case "L": head[1]--;break;
        case "R": head[1]++;break;
        case "D": head[0]++;break;
    }
    if(head[0]<0||head[1]<0||head[0]>=height||head[1]>=width||snakeBody.contains(valueOf(head))) {
        gameOver = true;
        return -1;
    }
    snakeBody.addFirst(valueOf(head));
    if(count<food.length&&food[count][0]==head[0]&&food[count][1]==head[1]) {
        snakeBody.addLast(lastValue);
        count++;
    }
    return snakeBody.size()-1;
}

public int valueOf(int[] head) {
    return head[0]*width+head[1];
}
}