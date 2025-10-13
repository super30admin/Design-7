#include<iostream>
#include<bits/stdc++.h>
#include<vector>
using namespace std;
/*
We track the snake's body using a deque and mark visited cells except the tail.
On each move, we update the head position and check for wall or body collisions.
If the snake eats food, we grow it; otherwise, we move the tail forward.
*/
class SnakeGame{
    vector<vector<bool>> visited;
    deque<vector<int>> snakeBody;
    int w, h;
    vector<vector<int>> food;
    int idx;
public:
    SnakeGame(int width, int height, vector<vector<int>>& food){
        this->h = height;
        this->w = width;
        this->visited = vector<vector<bool>>(h, vector<bool>(w, false));
        this->food = food;
        this->idx = 0;
        snakeBody.push_front({0, 0});
    }

    int move(string direction){
        vector<int> head = snakeBody.front();
        int r = head[0], c = head[1];

        if(direction == "L")c--;
        else if(direction == "R") c++;
        else if(direction == "D") r++;
        else if(direction == "U") r--;

        if(r < 0 || c < 0 || r == h || c == w || visited[r][c]) return -1;

        if(idx < food.size()){
            if(food[idx][0] == r && food[idx][1] == c){
                snakeBody.push_front({r, c});
                visited[r][c] = true;
                idx++;
                return snakeBody.size() - 1;
            }
        }

        snakeBody.push_front({r, c});
        visited[r][c] = true;
        snakeBody.pop_back();
        vector<int> tail = snakeBody.back();
        visited[tail[0]][tail[1]] = false;
        return snakeBody.size() - 1;
    }
};
