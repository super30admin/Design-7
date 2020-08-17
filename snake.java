// Time Complexity : O(n)
// Space Complexity : O(n)

class SnakeGame {
    int[] head;

    int height;
    int width;

    LinkedList<int[]> snake;
    LinkedList<int[]> food;

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */

    public SnakeGame(int width, int height, int[][] food) {
        this.snake = new LinkedList<>();
        this.food = new LinkedList<>(Arrays.asList(food));

        this.height = height;
        this.width = width;

        this.head = new int[]{0,0};
        snake.add(this.head);
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {        
        if(direction.equals("U")){
            head[0] -= 1;
        } else if(direction.equals("D")){
            head[0] += 1;
        } else if(direction.equals("L")){
            head[1] -= 1;
        } else if(direction.equals("R")){
            head[1] += 1;
        }

        if(head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width){
            return -1;
        }

        for(int x = 1; x < snake.size(); x++){
            int[] curr = snake.get(x);
            if(curr[0] == head[0] && curr[1] == head[1]){
                return -1;
            }
        }

        if(food.size() > 0){
            int[] foodCurr = food.get(0);
            if(head[0] == foodCurr[0] && head[1] == foodCurr[1]){              
                snake.add(food.remove(0));
                return snake.size() - 1;
            }
        }

        snake.remove(0);
        snake.add(new int[]{head[0], head[1]});

        return snake.size() - 1;
    }
}