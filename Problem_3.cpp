353. Design Snake Game



class SnakeGame {
public:
    SnakeGame(int width, int height, vector<vector<int>>& food) : 
        food_(&food), width_(width), height_(height)  
    {
        snake_.emplace_back(make_pair(0,0));
        pos_[0].emplace(0);
    }
    
    int move(string direction) {
        Coor next = step(direction, snake_.back());

        if (!valid(next)) return -1;
        if (!ProcessFood(next)) 
        {
            Coor tail = snake_.front();
            pos_[tail.first].erase(tail.second);
            snake_.pop_front();
        }
        if (pos_[next.first].count(next.second) > 0) return -1;
        pos_[next.first].emplace(next.second);
        snake_.emplace_back(next);
        return snake_.size() - 1;
    }
private:
    using Coor = pair<int, int>;
    bool valid(Coor coor)
    {
        const bool lower_bounded = (coor.first >= 0 && coor.second >= 0);
        const bool upper_bounded = (coor.first < height_ && coor.second < width_);
        return lower_bounded && upper_bounded;
    }

    Coor step(string dir, Coor coor)
    {
        Coor next = coor;
        if (dir == "U") next.first--;
        else if (dir == "R") next.second++;
        else if (dir == "D") next.first++;
        else if (dir == "L") next.second--;
        return next;
    }

    // returns true if food was eaten, otherwise false
    bool ProcessFood(Coor coor)
    {
        if (food_->size() == food_i_) return false;

        const vector<int>& f = food_->at(food_i_);
        int res = false;
        if (f[0] == coor.first && f[1] == coor.second)
        {
            res = true;
            food_i_++;
        }
        return res;
    }

    int food_i_ = 0;
    vector<vector<int>>* food_{};
    list<Coor> snake_{};
    unordered_map<int, unordered_set<int>> pos_{}; // y : x
    int width_{};
    int height_{};
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
