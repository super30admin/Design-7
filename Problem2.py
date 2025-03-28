# Problem 2 : Design Snake Game
# Time Complexity : O(1)
# Space Complexity : O(N + F) where N is the maximum length of the snake and F is the number of food items
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this :
'''
None
'''

# Your code here along with comments explaining your approach
from collections import deque
from typing import List

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        # initialize the foodList with the list of food
        self.foodList = food
        # initialize the deque for the snake
        self.snake = deque()
        # initialize the set for all the snake part except the tail
        self.visited = set()
        # set the snakeHead as [0,0]
        self.snakeHead = [0, 0]
        # add the snakeHead to the snake queue
        self.snake.appendleft(tuple(self.snakeHead))
        # set the width and height of the board
        self.width = width
        self.height = height
        # set the index to 0 which is used for foodList
        self.index = 0

    def move(self, direction: str) -> int:
        # check the direction and set the snakeHead variable accordingly
        if direction == "R":
            self.snakeHead[1] += 1
        elif direction == "L":
            self.snakeHead[1] -= 1
        elif direction == "D":
            self.snakeHead[0] += 1
        else:
            self.snakeHead[0] -= 1

        # check if the snakeHead is hitting the boundary of the board
        if (self.snakeHead[0] < 0 or self.snakeHead[0] >= self.height or self.snakeHead[1] < 0 or self.snakeHead[0] >= self.width):
            return -1
        
        # check if the snakeHead is hitting/bitting its own body
        if tuple(self.snakeHead) in self.visited:
            return -1
        
        # check if the position is having food
        # check if index is less than the length of foodList
        if self.index < len(self.foodList):
            # if it is then get the x and y position of the food for index in foodList
            foodX, foodY = self.foodList[self.index]
            # check if those position are matching with snakeHead
            if (foodX == self.snakeHead[0] and foodY == self.snakeHead[1]):
                # if it is then increment the index for next food in foodList
                self.index += 1
                # append the new snakeHead to snake
                self.snake.appendleft(tuple(self.snakeHead))
                # add the snakeHead to visited set
                self.visited.add(tuple(self.snakeHead))
                # return the score as length of snake - 1
                return len(self.snake) - 1 
        
        # if it is a normal move
        # append the new snakeHead to snake
        self.snake.appendleft(tuple(self.snakeHead))
        # add the snakeHead to visited set
        self.visited.add(tuple(self.snakeHead))
        
        # pop the tail from the snake queue
        tail = self.snake.pop()
        # remove the tail from the visited set
        self.visited.remove(tail)
        
        # return the score as length of snake - 1
        return len(self.snake) - 1
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)