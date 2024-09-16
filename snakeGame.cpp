//time complexity:O(1)
//space complexity:O(w*h+food)
//executed on leetcode: yes
//approach:using pair
//any issues faced? yes

class SnakeGame {
public:
    vector<pair<int,int>>snake;
    vector<vector<int>>food;
    int width;
    int height;
    int score;
    int curi;
    int curj;
    /** Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        this->width=width;
        this->height=height;
        this->food=food;
        curi=0;
        curj=0;
        score=0;
    }
    
    /** Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body. */
    int move(string direction) {
        if(direction=="U")
            curi--;
        else if(direction=="D")
            curi++;
        else if(direction=="L")
            curj--;
        else if(direction=="R")
            curj++;
        if(!inbound(curi,curj))
            return -1;
        if(find(snake.begin(), snake.end(), make_pair(curi, curj))!=snake.end())
            return -1;
        snake.push_back({curi,curj});
        if(score<food.size() && food[score][0]==curi && food[score][1]==curj)
            score++;
        else
            snake.erase(snake.begin());
        return score;
    }
    bool inbound(int i, int j)
    {
        return i>=0 && j>=0 && i<height && j<width;
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */