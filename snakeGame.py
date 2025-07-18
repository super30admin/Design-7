"""
Use a deque to represent the snake's body (head at the end) and a set for fast collision detection
On move, calculate the new head position, check if it hits the wall or itself (excluding the tail if it moves)
If the head reaches food, grow the snake; else, move by popping the tail.
"""
"""
Time Complexity: move()	O(1)
Space Complexity: O(N) —  N is the number of cells the snake has occupied
"""

from collections import deque

class snakeGame:

    def __init__(self, width: int, height: int, food: list[list[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.foodIndex = 0
        self.snake = deque([(0, 0)]) 
        self.snakeSet = {(0, 0)}   
        self.directions = {'U': (-1, 0), 'D': (1, 0), 
                           'L': (0, -1), 'R': (0, 1)}

    def move(self, direction: str) -> int:
        head_y, head_x = self.snake[-1]
        dy, dx = self.directions[direction]
        new_head = (head_y + dy, head_x + dx)


        if not (0 <= new_head[0] < self.height and 0 <= new_head[1] < self.width):
            return -1

        tail = self.snake.popleft()
        self.snakeSet.remove(tail)
        if new_head in self.snakeSet:
            return -1


        self.snake.append(new_head)
        self.snakeSet.add(new_head)

   
        if (self.foodIndex < len(self.food) and
                [new_head[0], new_head[1]] == self.food[self.foodIndex]):
            self.snake.appendleft(tail) 
            self.snakeSet.add(tail)
            self.foodIndex += 1

        return len(self.snake) - 1

if __name__ == "__main__":
    game = snakeGame(3, 2, [[1, 2], [0, 1]])
    print(game.move("R"))  
    print(game.move("D")) 
    print(game.move("R")) 
    print(game.move("U")) 
    print(game.move("L")) 
    print(game.move("U"))  
