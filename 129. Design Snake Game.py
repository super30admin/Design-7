from collections import deque


class SnakeGame:

    def __init__(self, width, height, food):
        self.snake = deque()
        self.foodList = food
        self.width = width
        self.height = height
        self.snakeHead = []
        self.snake.append((0, 0))
        self.i = 0

    def move(self, direction):
        if direction == "U":
            self.snakeHead[0] = self.snakeHead[0] - 1
        elif direction == "D":
            self.snakeHead[0] = self.snakeHead[0] + 1
        elif direction == "R":
            self.snakeHead[1] = self.snakeHead[1] + 1
        elif direction == "L":
            self.snakeHead[1] = self.snakeHead[1] - 1
        # Checks the snake if it is going out of bounds
        if self.snakeHead[0] < 0 or self.snakeHead[0] == self.height or self.snakeHead[1] < 0 or self.snakeHead[1] == self.width:
            return -1
        # check if snake is touching itself
        for i in range(1, len(self.snake)):
            curr = self.snake[i]
            if curr[0] == self.snakeHead[0] or curr[1] == self.snakeHead[1]:
                return -1
        # if snake eats the food we append the node in the queue and keep the tail and the return the score
        # which is 1 less than than the size of the list and 1 was added in the beginning
        if len(self.foodList) > 0:
            appearedFood = self.foodList[0]
            if self.snakeHead[0] == appearedFood[0] and self.snakeHead[1] == appearedFood[1]:
                self.snake.append(self.foodList.pop())
            return len(self.snake) - 1
        # Normal move if snake does none of the above i.e die at boundary or die by touching its own node or
        # increase the length by eating food we return the len(snake) - 1
        self.snake.pop()
        self.snake.append((self.snakeHead[0], self.snakeHead[1]))
        return len(self.snake) - 1

