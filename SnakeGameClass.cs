// TC => O(1)
// SC => O(n)
public class SnakeGame {
    int[][] food;
    int width, height;
    int index;
    LinkedList<int[]> snakeBody;
    int[] snakeHead;
    bool[][] visited;
    public SnakeGame(int width, int height, int[][] food) {
        this.food = food;
        this.width = width;
        this.height = height;
        index = 0;
        snakeBody = new LinkedList<int[]>();
        snakeBody.AddFirst(new int[2]{0,0});
        snakeHead = [0,0];
        visited = new bool[height][];
        for(int i = 0; i < height; i++){
            visited[i] = new bool[width];
        }
    }
    
    public int Move(string direction) {
        switch(direction){
            case "U":
                snakeHead[0]--;
                break;
            case "D":
                snakeHead[0]++;
                break;
            case "L":
                snakeHead[1]--;
                break;
            case "R":
                snakeHead[1]++;
                break;
        }
        if(snakeHead[0] < 0 || snakeHead[0] == height || snakeHead[1] < 0 || snakeHead[1] == width){
            return -1;
        }
        if(visited[snakeHead[0]][snakeHead[1]]){
            return -1;
        }
        int[] head;
        if(index < food.Length && (snakeHead[0] == food[index][0] && snakeHead[1] == food[index][1])){
            head = new int[2]{snakeHead[0], snakeHead[1]};
            snakeBody.AddFirst(head);
            index++;
            visited[snakeHead[0]][snakeHead[1]] = true;
            return snakeBody.Count - 1;
        }
        head = new int[2]{snakeHead[0], snakeHead[1]};
        snakeBody.AddFirst(head);
        snakeBody.RemoveLast();
        visited[snakeHead[0]][snakeHead[1]] = true;
        int[] tail = snakeBody.Last.Value;
        visited[tail[0]][tail[1]] = false;
        
        return snakeBody.Count - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.Move(direction);
 */