class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = deque(food)
        self.snake = deque([(0, 0)])  # Snake starts at the top-left corner
        self.snake_set = set([(0, 0)])  # For fast collision checking
        self.directions = {
            "U": (-1, 0),
            "D": (1, 0),
            "L": (0, -1),
            "R": (0, 1),
        }  # Direction mapping
        self.score = 0  # Current score (length of the snake - 1)

    # T: O(1), S: O(n), where n is the len of the snake
    def move(self, direction: str) -> int:
        head_row, head_col = self.snake[0]
        drow, dcol = self.directions[direction]
        new_head = (head_row + drow, head_col + dcol)

        # Check for wall collision
        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1  # Game Over

        # Check if the snake collides with itself (excluding the tail)
        if new_head in self.snake_set and new_head != self.snake[-1]:
            return -1  # Game Over

        # If the snake eats an apple
        if self.food and new_head == tuple(self.food[0]):
            self.snake.appendleft(new_head)  # Grow the snake
            self.snake_set.add(new_head)
            self.food.popleft()  # Remove the eaten apple
            self.score += 1  # Increase the score
        else:
            tail = self.snake.pop()  # Remove the tail if no apple is eaten
            self.snake_set.remove(tail)
            self.snake.appendleft(new_head)  # Move the snake
            self.snake_set.add(new_head)

        return self.score  # Return the current score (length - 1)


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
