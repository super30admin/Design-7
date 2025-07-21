"""
I used a deque to track the snake's body and a set to track occupied cells for O(1) collision detection. Each move updates the head based on direction, checks for wall or self-collision (excluding the tail if it moves), and grows the snake if it eats food by adding a new head without removing the tail.
Time Complexity: O(1)
Space Complexity: O(n) + O(w*h)
"""
class SnakeGame:

    def __init__(self, width, height, food):
        self.w = width
        self.h = height
        self.food = food
        self.foodIdx = 0
        self.snakeBody = collections.deque([(0, 0)])
        self.snakeSet = set([(0, 0)])

    def move(self, direction):
        head_r, head_c = self.snakeBody[0]

        if direction == "U":
            head_r -= 1
        elif direction == "D":
            head_r += 1
        elif direction == "L":
            head_c -= 1
        elif direction == "R":
            head_c += 1

        if head_r < 0 or head_r >= self.h or head_c < 0 or head_c >= self.w:
            return -1

        new_head = (head_r, head_c)
        tail = self.snakeBody[-1]

        if new_head in self.snakeSet and new_head != tail:
            return -1

        if self.foodIdx < len(self.food) and [head_r, head_c] == self.food[self.foodIdx]:
            self.foodIdx += 1
            self.snakeBody.appendleft(new_head)
            self.snakeSet.add(new_head)
            return len(self.snakeBody) - 1

        tail = self.snakeBody.pop()
        self.snakeSet.remove(tuple(tail))

        self.snakeBody.appendleft(new_head)
        self.snakeSet.add(new_head)

        return len(self.snakeBody) - 1