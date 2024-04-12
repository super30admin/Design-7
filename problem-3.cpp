class SnakeGame {
public:
    class ListNode{
    public:
        int row;
        int col;
        ListNode* next;
        ListNode(int row, int col) : row(row), col(col), next(NULL){}
    };
    ListNode* head = new ListNode(0,0);
    ListNode* tail = head;

    vector<vector<int>> screen;
    vector<vector<int>> food;
    int height, width;

    SnakeGame(int width, int height, vector<vector<int>>& food) : food(food), height(height), width(width){
        screen = vector<vector<int>>(height, vector<int>(width, 0));
        screen[0][0] = 1;
    } 
    int row = 0, col = 0, f = 0, score = 0, fsize = food.size();
    
    int move(string direction) {
        if(direction == "L"){
            col -= 1;
        }
        else if(direction == "U"){
            row -= 1;
        }
        else if(direction == "D"){
            row += 1;
        }
        else{
            col += 1;
        }
        if(row >= height || row < 0 || col >= width || col < 0 || (screen[row][col] == 1 && (tail->row != row || tail->col != col))){
            return -1;
        }
        // if(screen[row][col] == 1 && (tail->row != row || tail->col != col)){
        //     return -1;
        // }

        ListNode* node = new ListNode(row, col);
        head->next = node;
        head = node;

        if(f < fsize && food[f][0] == row && food[f][1] == col){
            f++;
            score++;
        }
        else{
            screen[tail->row][tail->col] = 0;
            // ListNode* curr = tail;
            tail = tail->next;
            // delete(curr);
        }
        screen[row][col] = 1;
        return score;
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
