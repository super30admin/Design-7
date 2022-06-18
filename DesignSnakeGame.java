class SnakeGame {
    LinkedList<int[]> body;
    LinkedList<int[]> foodItems;
    int[] head;
    int width, height;

    public SnakeGame(int width, int height, int[][] food) {
        body = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        head = new int[]{0,0};
        this.width = width;
        this.height = height;
        body.add(head);
    }
    //TC : O(n) where n is length of snake(for linked list traversal)
    //TC : O(n) 
    public int move(String direction) {
        if(direction.equals("U")){
            head[0]--;
        }
        else if(direction.equals("D")){
            head[0]++;
        }
        else if(direction.equals("L")){
            head[1]--;
        }
        else if(direction.equals("R")){
            head[1]++;
        }
        //Does snake touch boundries
        if(head[0] < 0 || head[0]==height || head[1] < 0 || head[1]==width){
            return -1;
        }
        //Does snake touch itself
        for(int i=1; i<body.size(); i++){
            int[] curr = body.get(i);
            if(head[0]==curr[0] && head[1]==curr[1]){
                return -1;
            }
        }
        //Does the snake eats food
        if(foodItems.size() > 0){
            int[] food = foodItems.get(0);
            if(head[0]==food[0] && head[1]==food[1]){
                body.add(new int[]{head[0], head[1]});
                foodItems.remove();
                return body.size()-1;
            }
        }

        //Snake moves ahead
        body.add(new int[]{head[0], head[1]});
        body.remove();
        return body.size()-1;
    }
}