from collections import deque

class SnakeGame:
    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.food_index = 0  # Tracks the current food piece
        self.snake = deque([(0, 0)])  # Snake's body, starting at (0, 0)
        self.snake_positions = set([(0, 0)])  # For O(1) collision detection
        self.score = 0

    def move(self, direction: str) -> int:
        # Determine the new head position
        head_row, head_col = self.snake[0]
        if direction == 'U':
            new_head = (head_row - 1, head_col)
        elif direction == 'D':
            new_head = (head_row + 1, head_col)
        elif direction == 'L':
            new_head = (head_row, head_col - 1)
        elif direction == 'R':
            new_head = (head_row, head_col + 1)

        # Check if the new head is out of bounds
        if (new_head[0] < 0 or new_head[0] >= self.height or
            new_head[1] < 0 or new_head[1] >= self.width):
            return -1  # Game over

        # Check if the new head collides with the snake's body (excluding the tail)
        if new_head in self.snake_positions and new_head != self.snake[-1]:
            return -1  # Game over

        # Check if the new head is on food
        if (self.food_index < len(self.food) and
            new_head == tuple(self.food[self.food_index])):
            self.score += 1
            self.food_index += 1
        else:
            # Remove the tail if no food is eaten
            tail = self.snake.pop()
            self.snake_positions.remove(tail)

        # Add the new head to the snake's body
        self.snake.appendleft(new_head)
        self.snake_positions.add(new_head)

        return self.score
