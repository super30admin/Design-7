# Time Complexity: O(1)
# Space Complexity: O(n)
# Approach - Deque for snake body and food, set for collision detection

class SnakeGame:

    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.width = width
        self.height = height
        self.food = deque(food)
        self.snake = deque([[0, 0]])
        self.head = [0, 0]
    
    def move(self, direction: str) -> int:
        # Compute new head position
        if direction == 'U':
            self.head[0] -= 1
        elif direction == 'D':
            self.head[0] += 1
        elif direction == 'L':
            self.head[1] -= 1
        elif direction == 'R':
            self.head[1] += 1
        
        # Check border collision
        if (self.head[0] < 0 or self.head[0] >= self.height or
            self.head[1] < 0 or self.head[1] >= self.width):
            return -1
        
        # Check self-collision (excluding the tail that's moving)
        for i in range(1, len(self.snake)):
            if self.snake[i] == self.head:
                return -1

        # Check if food is eaten
        if self.food and self.food[0] == self.head:
            self.snake.append(self.food.popleft())
        else:
            self.snake.popleft()
            self.snake.append(list(self.head))
        
        return len(self.snake) - 1
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)



# Time Complexity: O(1)
# Space Complexity: O(n)
# Approach - Hashset for collision detection
class SnakeGame:
    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.food_index = 0
        self.snake = deque([[0, 0]])
        self.snake_set = set([(0, 0)])
        self.directions = {'U': (-1, 0), 'D': (1, 0),
                           'L': (0, -1), 'R': (0, 1)}

    def move(self, direction: str) -> int:
        head_x, head_y = self.snake[-1]
        dx, dy = self.directions[direction]
        new_head = [head_x + dx, head_y + dy]

        # Check boundary collision
        if (new_head[0] < 0 or new_head[0] >= self.height or
            new_head[1] < 0 or new_head[1] >= self.width):
            return -1

        tail = self.snake.popleft()
        self.snake_set.remove(tuple(tail))

        # Check self-collision
        if tuple(new_head) in self.snake_set:
            return -1

        # Add new head
        self.snake.append(new_head)
        self.snake_set.add(tuple(new_head))

        # Check food
        if (self.food_index < len(self.food) and
            self.food[self.food_index] == new_head):
            self.snake.appendleft(tail)
            self.snake_set.add(tuple(tail))
            self.food_index += 1

        return len(self.snake) - 1
