class SnakeGame:

    def __init__(self, width, height, food):

        self.m = height
        self.n = width
        self.snake_body = deque([(0, 0)])
        self.visited = set()
        self.visited.add((0, 0))
        self.food = food
        self.food_index = 0

    def move(self, direction):
        # Current head of the snake
        head = self.snake_body[0]
        r, c = head

        # Determine new head position based on direction
        if direction == "U":
            r -= 1
        elif direction == "D":
            r += 1
        elif direction == "L":
            c -= 1
        elif direction == "R":
            c += 1

        # Check for out-of-bounds or self-collision
        if r < 0 or r >= self.m or c < 0 or c >= self.n:
            return -1
        if (r, c) in self.visited and (r, c) != self.snake_body[-1]:
            return -1

        if self.food_index < len(self.food) and [r, c] == self.food[self.food_index]:
            self.food_index += 1
        else:
            # Remove the tail if not eating food
            tail = self.snake_body.pop()
            self.visited.remove(tail)

        # Add new head position
        self.snake_body.appendleft((r, c))
        self.visited.add((r, c))

        # Return the current score (snake length - 1)
        return len(self.snake_body) - 1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
# time complexity is O(1)
# space complexity is O(n)
