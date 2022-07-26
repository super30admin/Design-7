//time complexity : O(1) for move funtion
//space complexity : O(food.length) + O(width X height) //snake can max cover entire board

class SnakeGame {

    LinkedList<int[]> snake; //head of LL is tail of snake, tail of LL is head of snake
    LinkedList<int[]> foodList;
    int width;
    int height;
    int[] head;

    public SnakeGame(int width, int height, int[][] food) {
        this.head = new int[]{0,0};
        this.snake = new LinkedList();
        this.foodList = new LinkedList(Arrays.asList(food));
        this.width = width;
        this.height = height;
        this.snake.add(this.head);
    }

    public int move(String direction) {

        //calculate new head position
        if(direction.equals("L"))
            head[1]--; //column chnage
        else if(direction.equals("R"))
            head[1]++; //column chnage
        else if(direction.equals("U"))
            head[0]--; //row chnage
        else
            head[0]++; //row chnage

        //snake hits the border
        if(head[0] < 0 || head[0] == height || head[1] < 0 || head[1] == width)
            return -1;

        //snake hits itself
        //iterate over snake LL and check if head pixel is already in snake body, except for tail becaue when snake moves, tail also moves
        for(int i=1; i<snake.size(); i++) {
            int[] currPixel = snake.get(i);
            if(head[0] == currPixel[0] && head[1] == currPixel[1])
                return -1;
        }

        //snake eats food - add head to snake head(tail of LL)
        if(!foodList.isEmpty()) {
            int[] foodPixel = foodList.get(0);
            if(head[0] == foodPixel[0] && head[1] == foodPixel[1]) {
                foodList.removeFirst();
                snake.addLast(new int[] {head[0], head[1]});
                return snake.size()-1;
            }
        }

        //normal move - add to LL tail and remove from LL head
        snake.addLast(new int[] {head[0], head[1]});
        snake.removeFirst();
        return snake.size()-1;

    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
