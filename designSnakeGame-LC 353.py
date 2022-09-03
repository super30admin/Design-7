# Time Complexity = O(n), iterating over to check if snake is touching himself
# Space Complexity = O(n)


# Start with snake head at (0, 0), score at 0, curr row = 0, col = 0
# If dir = R, col inc; if dir = L, col dec; if dir = U, row dec; if dir = D, col dec
# If food is present, snake will eat (curr row col should be where food item is), score inc, remove the food, append the curr row col in snake body
# If food not present, tail of the snake is popped and moved ahead, new head is appended
# If snake touching boundaries or himself, -1 returned



class SnakeGame:

    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.snake = [(0, 0)]
        self.width = width
        self.height = height
        self.currRow = 0
        self.currCol = 0
        self.food = food
        self.score = 0

    def move(self, direction: str) -> int:
        if direction == "R":
            self.currCol += 1
            
        elif direction == "L":
            self.currCol -= 1
            
        elif direction == "U":
            self.currRow -= 1
            
        elif direction == "D":
            self.currRow += 1
            
        # Check if it is touching boundaries
        if self.currRow < 0 or self.currRow == self.height or self.currCol < 0 or self.currCol == self.width:
            return -1
        
        # Check if the snake is touching itself
        if (self.currRow, self.currCol) in self.snake and (self.currRow, self.currCol) != self.snake[0]:
            return -1
        
        # If food is present, snake should eat and return score
        if self.food and [self.currRow, self.currCol] == self.food[0]:
            self.score += 1
            # self.snake.append(self.food[0])     #curr Row col
            # print("snake", self.snake)
            # print("food", self.food[0])
            # print("row col", (self.currRow, self.currCol))
            self.snake.append((self.currRow, self.currCol))
            self.food.pop(0)
            return self.score
            
        # If food is not present, the tail of the snake should be popped as it has moved ahead
        self.snake.pop(0)
        self.snake.append((self.currRow, self.currCol))
        return self.score


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)