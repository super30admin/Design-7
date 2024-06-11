class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.score = 0
        self.snake = [(0, 0)]  # The snake is initially positioned at the top left corner
        self.directions = {'U': (-1, 0), 'L': (0, -1), 'R': (0, 1), 'D': (1, 0)}

def move(self, direction: str) -> int:
        #Tc: O(n) Sc: O(n) where n is length of the snake 
        head_x, head_y = self.snake[0]
        move_x, move_y = self.directions[direction]
        new_head = (head_x + move_x, head_y + move_y)

        # Check if the new head position is out of bounds
        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1

        # Check if the new head position collides with the body, excluding the tail
        if new_head in self.snake[:-1]:
            return -1

        # Check if the new head position is on the food
        if self.food and new_head == tuple(self.food[0]):
            self.score += 1
            self.snake.insert(0, new_head)  # Add new head
            self.food.pop(0)  # Remove the eaten food
        else:
            self.snake.insert(0, new_head)  # Add new head
            self.snake.pop()  # Remove tail

        return self.score
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)