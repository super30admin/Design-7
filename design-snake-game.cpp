//Time - O(1)
//Space - O(size(food))

class SnakeGame{
    int width,height;
    queue<pair<int,int>> snake;
    list<pair<int,int>> food;
    unordered_set<pair<int,int>> snakeBody;
    pair<int,int> head;
    SnakeGame(int width, int height, vector<vector<int>> food){
        head = make_pair(0,0);
        this->width = width;
        this->height = height;
        for(int i=0;i<food.size();i++){
            food.push_back(make_pair(food[i][0], food[i][1]));
        }
        snake.push(head);
        snakeBody.insert(head);
    }
    
    int move(String dir){
        if(dir == "U"){
            head.first--;
        }else if(dir == "D"){
            head.first++;
        }else if(dir == "L"){
            head.second--;
        }else if(dir == "R"){
            head.second++;
        }
        
        if(head.first<0 || head.first>=height || head.second<0 || head.second>=width) return -1;
        
        if(snakeBody.find(head) != snakeBody.end()) return -1;
        
        if(!food.empty()){
            pair<int,int> foodLoc = food.front();
            
            if(head.first == foodLoc.first && head.second == foodLoc.second){
                snake.push(head);
                snakeBody.insert(head);
                food.pop_front();
                return snake.size()-1;
            }
        }
        
        snake.pop();
        snake.push(head);
        return snake.size()-1;
        
    }
    
};