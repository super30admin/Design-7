// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes

#include <vector>
#include <deque>
#include <unordered_set>
#include <string>

using namespace std;

class SnakeGame {
private:
    int width;
    int height;
    std::deque<std::pair<int, int>> snake;
    std::unordered_set<int> snakeSet;
    std::vector<std::vector<int>> food;
    int foodIndex;
    int score;

public:
    SnakeGame(int width, int height, std::vector<std::vector<int>>& food) {
        this->width = width;
        this->height = height;
        this->food = food;
        this->foodIndex = 0;
        this->score = 0;
        this->snake.clear();
        this->snakeSet.clear();
        this->snake.emplace_back(0, 0);
        this->snakeSet.insert(0);
    }

    int move(string direction) {
        int newRow = snake.back().first;
        int newCol = snake.back().second;

        if (direction == "U")
            newRow--;
        else if (direction == "D")
            newRow++;
        else if (direction == "L")
            newCol--;
        else if (direction == "R")
            newCol++;

        int newHead = newRow * width + newCol;

        // Check if the new head position is valid
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width)
            return -1;

        // Check if the new head hits the snake itself
        if (snakeSet.find(newHead) != snakeSet.end() && newHead != snake.front().first * width + snake.front().second)
            return -1;

        // Check if the new head eats the food
        if (foodIndex < food.size() && newRow == food[foodIndex][0] && newCol == food[foodIndex][1]) {
            score++;
            foodIndex++;
        } else {
            snakeSet.erase(snake.front().first * width + snake.front().second);
            snake.pop_front();
        }

        snake.push_back(std::make_pair(newRow, newCol));
        snakeSet.insert(newHead);

        return score;
    }
};
