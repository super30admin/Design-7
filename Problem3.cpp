// Problem: Snake Game
// Time Complexity : O(1) per move
// Space Complexity : O(w*h)  for visited and O(N) n is max length of snake body
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only
// 1. On the first move, we first increment the head to the directed row and
// column
// 2. We check if it is in bounds and we have not visited it previously
// 3. If we are at a postion where food is there, we add to snake body, mark it
// visited
// 4. Otherwise, if we mark the previous tail as unvisited and mark the cell we
// moved to visited

#include <bits/stdc++.h>
using namespace std;

class SnakeGame {
public:
  int w;
  int h;
  vector<vector<int>> food;
  list<vector<int>> snakeBody;
  vector<vector<bool>> vis;
  int foodIdx;
  SnakeGame(int width, int height, vector<vector<int>> &food) {
    this->w = width;
    this->h = height;
    this->food = food;
    this->foodIdx = 0;
    this->snakeBody.push_front({0, 0});
    this->vis = vector<vector<bool>>(h, vector<bool>(w, false));
    vis[0][0] = true;
  }

  bool checkBounds(int r, int c) {
    if (r < h && r >= 0 && c < w && c >= 0) {
      return true;
    }
    return false;
  }

  int move(string direction) {
    vector<int> pos = snakeBody.front();
    int r = pos[0];
    int c = pos[1];
    if (direction == "U") {
      r--;
    } else if (direction == "D") {
      r++;
    } else if (direction == "L") {
      c--;
    } else if (direction == "R") {
      c++;
    }

    auto tail = snakeBody.back();
    if (!checkBounds(r, c)) {
      return -1;
    }

    if (vis[r][c]) {
      return -1;
    }

    if (foodIdx < food.size()) {
      if (food[foodIdx][0] == r && food[foodIdx][1] == c) {
        snakeBody.push_front({r, c});
        foodIdx++;
        vis[r][c] = true;
        return snakeBody.size() - 1;
      }
    }

    tail = snakeBody.back();
    snakeBody.pop_back();
    vis[tail[0]][tail[1]] = false;

    snakeBody.push_front({r, c});
    vis[r][c] = true;
    return snakeBody.size() - 1;
  }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
