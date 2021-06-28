/*
Intuition: The idea is to insert all the possible numbers in the set and queue.
///////////////////////////////////////////
move Method():
Time Complexity : O(N), N = Size of Linked List.
///////////////////////////////////////////

*/

class SnakeGame {
    list<pair<int,int>> snake;
    list<pair<int,int>> foodLL;
    int width;
    int height;
    pair<int,int> snakeHead;

public:

    SnakeGame(int width, int height, vector<vector<int>>& food) {
        this->width = width;
        this->height = height;
        for(auto f : food){
            foodLL.push_back({f[0],f[1]});
        }
        snakeHead = make_pair(0,0);
        snake.push_back(snakeHead);
    }

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
        if(snakeHead.first < 0 or snakeHead.first == height or snakeHead.second < 0 or snakeHead.second == width) return -1;
        int flag = false;
        for(auto c :snake){
            if(flag){
                pair<int,int> curr = c;
                if(snakeHead == curr) return -1;
            }
            else flag = true;
        }
        if(foodLL.size() > 0){
            pair<int,int> curr = foodLL.front();
            if(snakeHead == curr){
                foodLL.pop_front();
                snake.push_back(curr);
                return snake.size()-1;
            }
        }
        snake.pop_front();
        snake.push_back(snakeHead);
        return snake.size()-1;

    }
};