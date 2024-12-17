# Given Conditions: Snake should not bite itself and Snake should not hit boundaries
# If new food positon appears and snake consumes it length of snake increases and tail remains same. else if snake doesnt consume food: remove the tail
# Using Queue for this is a best choice as everytime snake moves the new position is pushed into queue and the tail is popped( if no food)
# We can also implement this Queue using LinkedList
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.food_index = 0  # To track current food positon and increment as it should be cosumeed only once
        self.snake = deque([(0, 0)])
        self.snake_set = set([(0, 0)])  # To check for collisions
        self.score = 0
        self.directions = {'U': (-1, 0), 'D': (1, 0), 'L': (0, -1), 'R': (0, 1)}

    def move(self, direction: str) -> int:
        "Move the snake in given direction"

        dx, dy = self.directions[direction]
        head_x, head_y = self.snake[0]

        new_head = (head_x + dx, head_y + dy)

        # Check for boundary collisions for the new_head
        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1

        # Check if the snake is colliding with its body
        if new_head in self.snake_set and new_head != self.snake[-1]:
            return -1

        # Check if its consuming food and increase the length
        if (self.food_index < len(self.food)) and ([new_head[0], new_head[1]] == self.food[self.food_index]):
            self.food_index += 1
            self.score += 1
        else:
            # Remove the tail
            tail = self.snake.pop()
            self.snake_set.remove(tail)

        # Append this new_head to the snake Queue and snake_set
        self.snake.appendleft(new_head)
        self.snake_set.add(new_head)

        return self.score
    # Note: Use linkedlist next time you visit this question

# Time complexity: O(1), Space complexity: O(S) + O(F) + O(S) (Snake + Food + Snake body positions)


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)