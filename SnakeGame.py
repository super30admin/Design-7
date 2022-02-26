"""
Design a Snake game that is played on a device with screen size height x width. 
Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0, 0) with a length of 
1 unit.

You are given an array food where food[i] = (ri, ci) is the row and column 
position of a piece of food that the snake can eat. When a snake eats a piece of 
food, its length and the game's score both increase by 1.

Each piece of food appears one by one on the screen, meaning the second piece of 
food will not appear until the snake eats the first piece of food.

When a piece of food appears on the screen, it is guaranteed that it will not 
appear on a block occupied by the snake.

The game is over if the snake goes out of bounds (hits a wall) or if its 
head occupies a space that its body occupies after moving 
(i.e. a snake of length 4 cannot run into itself).

Implement the SnakeGame class:

SnakeGame(int width, int height, int[][] food) Initializes the object with a 
screen of size height x width and the positions of the food.
int move(String direction) Returns the score of the game after applying 
one direction move by the snake. If the game is over, return -1.
"""

# Time Complexity : O(m*n) or O(length of snake)
# Space Complexity : O(m*n) or O(length of snake)
# Did this code successfully run on VScode : Yes
# Any problem you faced while coding this : No

from typing import List
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = deque()
        self.height = height
        self.width = width
        self.foodItems= deque(food)
        self.snakeHead = [0,0]
        self.snake.append(self.snakeHead)

    def move(self, direction: str) -> int:
        # move a snacke Head
        if direction == "U":
            self.snakeHead[0] -= 1
        elif direction == "L":
            self.snakeHead[1] -= 1
        elif direction == "R":
            self.snakeHead[1] += 1
        elif direction == "D":
            self.snakeHead[0] += 1

        # check if snake touches boundry
        if self.snakeHead[0] < 0 or self.snakeHead[0] == self.height or self.snakeHead[1] < 0 or self.snakeHead[1] == self.width:
            return -1


        # check if snake bit itself
        for i in range(1,len(self.snake)):
            curr = self.snake[i]
            if self.snakeHead[0] == curr[0] and self.snakeHead[1] == curr[1]:
                return -1

        # check foodItems
        if len(self.foodItems) > 0:
            food = self.foodItems[0]
            if food[0] == self.snakeHead[0] and food[1] == self.snakeHead[1]:
                self.snake.append([self.snakeHead[0],self.snakeHead[1]])
                self.foodItems.popleft()
                return len(self.snake)-1

        self.snake.append([self.snakeHead[0],self.snakeHead[1]])
        self.snake.popleft()
        return len(self.snake)-1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)