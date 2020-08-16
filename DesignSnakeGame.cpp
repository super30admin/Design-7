// Time Complexity : O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
//Using Linkedlists

class SnakeGame {
public:
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        snakeFood.assign(food.begin(),food.end());
        w = width;
        h = height;
        snakeHead.push_back(0);
        snakeHead.push_back(0);
        snake.push_back(snakeHead); 
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    int move(string direction) {
        if(direction == "U") snakeHead[0] -= 1;
        if(direction == "D") snakeHead[0] += 1;
        if(direction == "L") snakeHead[1] -= 1;
        if(direction == "R") snakeHead[1] += 1;
        
        if(snakeHead[0] < 0 || snakeHead[0] >= h || 
           snakeHead[1]<0 || snakeHead[1]>=w){
            return -1;
        }
        
        for(auto it=snake.begin();it!=snake.end();it++){
            if(it!=snake.begin())
            {
                vector<int> temp = *it;
                if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]){
                    return -1;
                }
            }
           
        }
        
        if(!snakeFood.empty()){
            vector<int> temp = snakeFood.front();
            if(temp[0] == snakeHead[0] && temp[1] == snakeHead[1]){
                snake.push_back(temp);
                snakeFood.pop_front();
                return snake.size()-1;
            }
        }
        snake.pop_front();
        snake.push_back(snakeHead);
        return snake.size()-1;  
    }
    private:
        int w,h;
        vector<int> snakeHead;
        list<vector<int>> snake;
        list<vector<int>> snakeFood;
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
