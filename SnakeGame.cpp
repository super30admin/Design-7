// Time Complexity : O(n) where n is the size of the snake 
// Space Complexity : O(height * width) 
// Did this code successfully run on Leetcode : Yes 

/*
mantaining a vector for the snake body
head at its end and tail in the beginning 
we basically need access to add/remove elemetns from both directions of the vector
*/

class SnakeGame {
public:
    int width, height;
    vector<vector<int>> food;
    vector<pair<int, int>> snakeBody;
    pair<int,int> snakeHead;
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        this->width = width;
        this->height = height;
        this->food = food;
        snakeHead = {0,0};
        snakeBody.push_back(snakeHead);
    }
    
    int move(string direction) {
        // move the snake in the direction given
        if(direction.compare("U") == 0) {
            snakeHead.first --;
        }
        else if (direction.compare("D") == 0) {
            snakeHead.first ++;
        }
        else if (direction.compare("R") == 0) {
            snakeHead.second ++;
        }
        else if (direction.compare("L") == 0) {
            snakeHead.second --;
        }
        // if snake hits the boundary
        int first = snakeHead.first;
        int second = snakeHead.second;
        if(snakeHead.first < 0 || snakeHead.first == height || snakeHead.second < 0 || snakeHead.second == width)
            return -1;
        
        // if the snake hits its own body 
        for(int i=1; i<snakeBody.size(); i++){
            if (snakeBody[i].first == snakeHead.first && snakeBody[i].second == snakeHead.second)
            return -1;
        }

        // check if snake head is on a food item
        if(food.size() > 0 && snakeHead.first == food[0][0] && snakeHead.second == food[0][1]) {
            // add the new head to the end of the vector and remove the food item
            snakeBody.push_back(snakeHead);
            food.erase(food.begin());
            return snakeBody.size()-1;
        }
        
        // if its just a move, move the head to the new position and remove the tail from the beginnning of the array
        snakeBody.push_back(snakeHead);
        snakeBody.erase(snakeBody.begin());
        vector<pair<int, int>> snakeBody_ = snakeBody;
        return snakeBody.size()-1;
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */