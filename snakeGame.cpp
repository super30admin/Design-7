class SnakeGame {
public:
    int H; 
    int W;
    int index;
    vector<vector<int>> snakeFood;
    
    deque<pair<int,int>> q;
    unordered_set<int> hashset;
    
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        H = height;
        W = width;
        snakeFood = food;
        index = 0;
        q.push_back({0,0});
    }
    
    //q - front = snake's head
    //q - back = snake's tail
    int move(string direction) {
        int row = q.front().first;
        int col = q.front().second;
        
        pair<int,int> p = q.back(); q.pop_back();
        int tailRow = p.first;
        int tailCol = p.second;
        
        int curTail = tailRow * W + tailCol;
        hashset.erase(curTail);
        
        if(direction=="U") row--;
        else if(direction=="D") row++;
        else if(direction=="L") col--;
        else if(direction=="R") col++;
        
        int newHead = row * W + col;
        
        //Death
        //Boundary Death
        if(row<0 || row>=H || col<0 || col>=W) return -1;
        //Body Death
        if(hashset.find(newHead)!=hashset.end()) return -1;
        
        //No Death
        //Push New Head
        //Normal Move
        q.push_front({row,col});
        hashset.insert(newHead);
        
        if(index>=snakeFood.size()) return q.size()-1;
        
        //Eat food if its in new head position
        if(row == snakeFood[index][0] && col == snakeFood[index][1]){
            index++;
            q.push_back(p);
            hashset.insert(curTail);
        }
        return q.size()-1;
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
