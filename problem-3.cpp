// https://leetcode.com/problems/design-snake-game/description/
// Time Complexity: O(n) n is the current length of the snake
// Space Complexity: O(s) s is the max snake length


class SnakeGame {
    public:
        int n;
        int m;
        vector<vector<int>> target;
    
        int row=0;
        int col=0;
        int i=0;
        deque<int> dq;
        SnakeGame(int width, int height, vector<vector<int>>& food) {
            n = height-1;
            m = width-1;
            target = food;
            dq.emplace_back(0);
        }
        
        int move(string direction) {
            if(direction == "U"){
                row--;
            }
            else if(direction == "D"){
                row++;
            }
            else if(direction == "L"){
                col--;
            }
            else{
                col++;
            }
            if(row < 0 || row > n || col < 0 || col > m) return -1;
    
            //if snake eats the food
            if(i < target.size() && row == target[i][0] && col == target[i][1]) i++;
            //else snake moves a step forward
            else dq.pop_front();
    
            //if snake runs into itself
            if(find(dq.begin(), dq.end(), row*(m+1) + col) != dq.end()) return -1;
    
            dq.emplace_back(row*(m+1) + col);
    
            return i;
        }
    };
    
    /**
     * Your SnakeGame object will be instantiated and called as such:
     * SnakeGame* obj = new SnakeGame(width, height, food);
     * int param_1 = obj->move(direction);
     */