class SnakeGame {
    int width;
    int height;
    int [] head;
    LinkedList<int[]> snake = new LinkedList<>();
    LinkedList<int []>foodList;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        head = new int[] {0,0};
        snake.add(head);
        foodList = new LinkedList<>(Arrays.asList(food));
    }
    public int move(String direction) {
        if(direction.equals("U")) head[0] -= 1;
        if(direction.equals("D")) head[0] += 1;
        if(direction.equals("L")) head[1] -= 1;
        if(direction.equals("R")) head[1] += 1;
        if(head[0] < 0 || head[0] > height - 1 || head[1] < 0 || head[1] > width - 1 ) return -1;
        for(int i = 1; i < snake.size(); i++){
            int [] curr = snake.get(i);
            if(curr[0] == head[0] && curr[1] == head[1]) return -1;
        }
        if(foodList.size() != 0){
            int [] appearedFood = foodList.get(0);
            if(appearedFood[0] == head[0] && appearedFood[1] == head[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;
            }
        }
        snake.remove();
        snake.add(new int [] {head[0], head[1]});
        return snake.size() - 1;  
    }
}