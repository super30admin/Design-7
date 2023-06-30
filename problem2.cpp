// Time Complexity : O(log(width * height))
// Space Complexity : O(food.size() + (width * height))
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach


// we take a map to optimize the lookup(here i took ordered_map ==> O(logn)),
// we use a deque to store the food locations and also for path of matrix covered by snake
// mistake to avoid: we miss the case where last cell of path should be considered as available for movement.
// to do this, we remove the last cell entry from map and not from path.


class SnakeGame {
public:
    int width;
    int height;
    vector<int>snakeHead;
    map<vector<int>,int>map;
    deque<vector<int>>foodq;
    deque<vector<int>>path;
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        this->width = width;
        this->height = height;
        foodq.clear();
        foodq.insert(foodq.end(),food.begin(),food.end());
        path.clear();
        map[{0,0}]=1;
        path.push_back({0,0});
        snakeHead = {0,0};
    }
    
    int move(string direction) {
        map.erase({path.front()[0],path.front()[1]});
        if(direction == "D"){
            snakeHead[0]++;
        }
        else if(direction == "U"){
            snakeHead[0]--;
        }
        else if(direction == "L"){
            snakeHead[1]--;
        }
        else if(direction == "R"){
            snakeHead[1]++;
        }
        //check snakeHead is valid
        if(snakeHead[0]<0 || snakeHead[0]>=height || snakeHead[1]<0 || snakeHead[1]>=width ){
            return -1;
        }
        // check if the present snakeHead is touching body of snake
        if(map.count({snakeHead[0],snakeHead[1]})>0){
            return -1;
        }
        // check if there is no food at present snakeHead
        if(foodq.size()>0)
        {
            if(snakeHead[0]!=foodq.front()[0] || snakeHead[1]!=foodq.front()[1]){
                map.erase({path.front()[0],path.front()[1]});
                path.pop_front();
                // push curr path
                //map
            }
            else{
                foodq.pop_front();
                // push curr path
                //map
            }
        }
        else{
            map.erase({path.front()[0],path.front()[1]});
            path.pop_front();
            // push curr path
                //map
        }
        
        map[{snakeHead[0],snakeHead[1]}] = 1;
        path.push_back({snakeHead[0],snakeHead[1]});
        return path.size()-1;
        
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */