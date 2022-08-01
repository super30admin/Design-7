//Time Complexity- O(1)
//Space Complexity- O(W*H+N)

class SnakeGame {
public:
    deque<pair<int,int>> dq;
    map<pair<int,int>,bool> mp;
    int _width;
    int _height;
    int idx;
    vector<vector<int>> _food;
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        idx=0;
        _width=width;
        _height=height;
        mp[make_pair(0,0)]=true;
        dq.push_front(make_pair(0,0));
        _food=food;
    }
    
    int move(string direction) {
        
        auto curr=dq.front();
        int row=curr.first;
        int col=curr.second;
        if(direction=="U") row--;
        if(direction=="D") row++;
        if(direction=="R") col++;
        if(direction=="L") col--;
        auto newHead=make_pair(row,col);
        auto tail=dq.back();
        if(row<0 || row>=_height || col<0 || col>=_width){
            return -1;
        }
        
        if(idx<_food.size() && _food[idx][0]==row && _food[idx][1]==col){
            idx++;
            if(mp.find(newHead)!=mp.end()){
                return -1;
            }
        }
        else{
            dq.pop_back();
            mp.erase(tail);
            if(mp.find(newHead)!=mp.end()){
                return -1;
            }
        }
        dq.push_front(newHead);
        mp[newHead]=true;
        return mp.size()-1;
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */