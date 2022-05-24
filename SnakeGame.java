//TC : O(L) --- Snake length

//SC : O(W*H + N)
       // Here W anb B is width and height and n = length of foodlist 


       class SnakeGame {
        LinkedList<int[]> snake;
        LinkedList<int[]> foodlist;
        int[] head;
        int width, height;
        public SnakeGame(int width, int height, int[][] food) {
            this.head = new int[]{0,0};
            this.width = width;
            this.height = height;
            snake = new LinkedList<>();
            foodlist = new LinkedList<>(Arrays.asList(food));
            
            this.snake.addLast(this.head);
        }
        
        public int move(String direction) {
            if(direction.equals("L"))
                head[1]--;
            else if(direction.equals("R"))
                head[1]++;
            else if(direction.equals("U"))
                head[0]--;
            else // Direction ===== Down
                head[0]++;
            
            if(head[0] < 0 || head[0] == height || head[1] < 0 || head[1] == width) return -1;
            
            
            //Snake hits to itself
            for(int i = 1; i < snake.size(); i++){
                int[] curr = snake.get(i);
                if(head[0] == curr[0] && head[1] == curr[1])    return -1;   
            }
            
            //Eats food
            if(!foodlist.isEmpty()){
                int[] currfood = foodlist.get(0);
                if(head[0] == currfood[0] && head[1] == currfood[1]){
                    foodlist.removeFirst();
                    
                    snake.addLast(new int[]{head[0], head[1]});
                    return snake.size() - 1;
                } 
            }
            
            //Not eating the food
            //Remove last/tail node
            snake.removeFirst();
            //Add head
            snake.addLast(new int[]{head[0], head[1]});
            return snake.size() - 1;
            
            
        }
    }
    