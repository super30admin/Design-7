// Problem: Snake Game
// Time Complexity : O(1) for all operations
// Space Complexity : O(n) for two hashmaps we used
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only

#include <bits/stdc++.h>
using namespace std;

class SnakeGame {
public:
  int w;
  int h;
  vector<vector<int>> food;
  list<vector<int>> snakeBody;
  int foodIdx;
  SnakeGame(int width, int height, vector<vector<int>> &food) {
    this->w = width;
    this->h = height;
    this->food = food;
    this->foodIdx = 0;
    this->snakeBody.push_front({0, 0});
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

    if (!checkBounds(r, c)) {
      return -1;
    }

    if (foodIdx < food.size()) {
      if (food[foodIdx][0] == r && food[foodIdx][1] == c) {
        snakeBody.push_front({r, c});
        foodIdx++;
        return snakeBody.size() - 1;
      }
    }

    // collision detection
    auto it = snakeBody.begin();
    for (int i = 0; i < snakeBody.size() - 1; i++, ++it) {
      if ((*it)[0] == r && (*it)[1] == c) {
        return -1;
      }
    }

    snakeBody.push_front({r, c});
    snakeBody.pop_back();
    return snakeBody.size() - 1;
  }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */
