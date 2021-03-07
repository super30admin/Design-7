// Time Complexity : The time complexity is O(n) where n is the length of the snake
// Space Complexity : The space complexity is O(n) where n is the length of the snake
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach

class SnakeGame {

    /** Initialize your data structure here.
     @param width - screen width
     @param height - screen height
     @param food - A list of food positions
     E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */

    int foodIndex;
    int height;
    int width;
    List<int[]> list;
    int[] head;
    int[][] food;

    public SnakeGame(int width, int height, int[][] food) {

        this.height = height;
        this.width = width;
        this.foodIndex = 0;
        this.food = food;
        head = new int[]{0,0};
        list = new ArrayList<>();
        list.add(head);
    }

    /** Moves the snake.
     @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
     @return The game's score after the move. Return -1 if game over.
     Game over when snake crosses the screen boundary or bites its body. */
    public int move(String direction) {

        // next position
        if(direction.equals("R")){
            head[1]++;
        }
        else if(direction.equals("L")){
            head[1]--;
        }
        else if(direction.equals("U")){
            head[0]--;
        }
        else{
            head[0]++;
        }

        // out of bounds
        if(head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width || contains(head)){
            return -1;
        }

        //snake eats food
        if(foodIndex < food.length && food[foodIndex][0] == head[0] && food[foodIndex][1] == head[1]){
            list.add(food[foodIndex]);
            foodIndex++;
        }
        // just move
        else{
            list.add(new int[]{head[0],head[1]});
            list.remove(0);
        }

        return list.size()-1;
    }

    public boolean contains(int[] cur){
        for(int i=1;i<list.size();i++){
            if(cur[0] == list.get(i)[0] && cur[1] == list.get(i)[1]){
                return true;
            }
        }
        return false;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */