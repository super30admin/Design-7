# Time Complexity : O(N)
# Space Complexity : O(W*H+N)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach
# Using 2 queues for the snake and food
# Get the pointer from initial location to when it moves any direction and add it to new location
# Check if the snake crosses it the boundary of screen then return -1
# If the new location is already in queue or the new location is the first element in queue as the head of the snake will bite its tail then return -1
# If the new location is at the food location it eats the food so append the food location to queue as the size of snake and score will be increased by 1 and remove it from food queue
# Else if the sanke is just moving then remove its tail that is first pointer in queue and append the new location to queue
# Return score

from collections import deque


class SnakeGame:
    def __init__(self, width, height, food):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.snake = deque([(0, 0)])
        self.food = deque(food)
        self.width = width
        self.height = height
        self.score = 0

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        new = 0
        pointer = self.snake[-1]
        if direction == "U":
            new = [pointer[0] - 1, pointer[1]]
        if direction == "D":
            new = [pointer[0] + 1, pointer[1]]
        if direction == "L":
            new = [pointer[0], pointer[1] - 1]
        if direction == "R":
            new = [pointer[0], pointer[1] + 1]
        if new[0] < 0 or new[0] >= self.height or new[1] < 0 or new[
                1] >= self.width:
            #snake crosses the screen boundary
            return -1
        #touches its existing pixel
        if new in self.snake and new != self.snake[0]:
            # snake bites its body
            return -1
        if self.food and new == self.food[0]:
            self.food.popleft()
            self.snake.append(new)
            self.score += 1
        else:
            self.snake.popleft()
            self.snake.append(new)

        return self.score


# Your SnakeGame object will be instantiated and called as such:
snake = SnakeGame(3, 2, [[1, 2], [0, 1]])
print(snake.move("R"))
print(snake.move("D"))
print(snake.move("R"))
print(snake.move("U"))
print(snake.move("L"))
print(snake.move("U"))
