// Time Complexity :O(n) where n is the length of the linked list(snake)
// Space Complexity : O(width * height) Worse case   
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SnakeGame {
    list<pair<int,int>> snake;
    list<pair<int,int>> foodList;
    int width;
    int height;
    pair<int,int> snakeHead;
    
public:
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        this->width = width;
        this->height = height;
        for(auto f : food){
            foodList.push_back({f[0],f[1]});
        }
        snakeHead = make_pair(0,0);
        snake.push_back(snakeHead);
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    int move(string direction) {
        if(direction == "U"){
            snakeHead.first--;
        }
        if(direction == "D"){
            snakeHead.first++;
        }
        if(direction == "L"){
            snakeHead.second--;
        }
        if(direction == "R"){
            snakeHead.second++;
        }
        if(snakeHead.first < 0 || snakeHead.first == height 
           || snakeHead.second < 0 || snakeHead.second == width) return -1;
        int flag = false;
        for(auto c :snake){
            if(flag){
                pair<int,int> curr = c;
                if(snakeHead == curr) return -1;
            }
            else flag = true;
        }
        if(foodList.size() > 0){
            pair<int,int> curr = foodList.front();
            if(snakeHead == curr){
                foodList.pop_front();
                snake.push_back(curr);
                return snake.size()-1;
            }
        }
        snake.pop_front();
        snake.push_back(snakeHead);
        return snake.size()-1;
        
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */