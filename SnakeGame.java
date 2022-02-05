// Time Complexity:  O(n) n = width * height 
// Space Complexity:  O(n) n = width * height 
public class SnakeGame {
    
    LinkedList<int[]> body;
    LinkedList<int[]> foodItems;
    int[] head;
    int width, height;
     
    public SnakeGame(int width, int height, int[][] food)
    {
        body = new LinkedList<>();
        foodItems = new LinkedList<>(Arrays.asList(food));
        head = new int[]{0,0};
        body.add(head);
        this.width = width;
        this.height = height;
    }

    // return score at each move
    // O(n) n = width * height 
    public int move(String direction)
    {
        // move the snake head
        if(direction.equals("U"))
        {
            // row decreases
            head[0]--;
        }
        else if(direction.equals("D"))
        {
            // row increases
            head[0]++;
        }
        else if(direction.equals("L"))
        {
            // column decreases
            head[1]--;
        }
        else if(direction.equals("R"))
        {
            // column increases
            head[1]++;
        }

        // check head boundaries
        if(head[0] < 0 || head[0] == height || head[1] < 0 || head[1] == width)
            return -1; // game over
        
        // check head touched in body
        for(int i = 1; i < body.size() ; i ++ ) // tail it at i == 0
        {
            int [] bodypart = body.get(i);
            if(head[0] == bodypart[0] && head[1] == bodypart[1])
                return -1;
        }
        
        // check if snake size increase
        if(foodItems.size() > 0)
        {
            int currFood[] = foodItems.peek();
            // head at food
            if(head[0] == currFood[0] && head[1] == currFood[1])
            {
                body.add(new int[]{head[0],head[1]});
                // remove food
                foodItems.remove();
                return body.size()-1;// -1 because snake size 1 but score 0
            }
        }
        // if did not eat, reduces body
        body.add(new int[]{head[0],head[1]});
        // reduce size of snake
        body.remove();
        return body.size()-1;// -1 because snake size 1 but score 0
    }
}
