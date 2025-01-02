# Explain your approach in briefly only at top of your code
# Approach:
# - Use a deque to represent the snake's body, where the head is at the front and the tail is at the back.
# - Maintain a set for quick lookup of positions occupied by the snake to detect self-collision efficiently.
# - Process each move by determining the new head position based on the direction, check for boundary collision or self-collision, and update the snake's body.
# - If the snake eats food, extend its body. Otherwise, remove its tail to maintain its current length.
# - Return the score, or -1 if the game is over.

# Time Complexity: O(1) per move.
# Space Complexity: O(n), where n is the maximum length of the snake.
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this: Handling edge cases like boundary and self-collision.

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize the game with the given screen size and food positions.
        """
        self.width = width  # Width of the game board
        self.height = height  # Height of the game board
        self.food = deque(food)  # Queue of food positions
        self.snake = deque([(0, 0)])  # Snake's body, starting at the top-left corner
        self.snake_set = {(0, 0)}  # Set to track positions occupied by the snake
        self.score = 0  # Current score of the game
        self.directions = {'U': (-1, 0), 'D': (1, 0), 'L': (0, -1), 'R': (0, 1)}  # Movement directions

    def move(self, direction: str) -> int:
        """
        Process the snake's movement in the given direction.
        """
        # Determine the new head position
        head = self.snake[0]
        dx, dy = self.directions[direction]
        new_head = (head[0] + dx, head[1] + dy)

        # Check for boundary collision
        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1  # Game over
        
        # Check for self-collision (excluding the tail, as it will move unless the snake grows)
        if new_head in self.snake_set and new_head != self.snake[-1]:
            return -1  # Game over

        # Check if the new head position is on food
        if self.food and self.food[0] == list(new_head):
            self.food.popleft()  # Remove the food item
            self.score += 1  # Increment the score
        else:
            # Remove the tail as the snake moves forward
            tail = self.snake.pop()
            self.snake_set.remove(tail)

        # Add the new head to the snake
        self.snake.appendleft(new_head)
        self.snake_set.add(new_head)

        return self.score  # Return the current score

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
