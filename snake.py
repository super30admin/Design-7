# The SnakeGame class simulates a snake game with a moving snake and food consumption.

# Initialization:
# - Store the grid dimensions, food positions, the snake's path, and a counter for food eaten.

# move:
# - Update the snake's head position based on the direction.
# - Check for collisions with boundaries or itself; return -1 if a collision occurs.
# - If the new position matches the next food position, increase the food counter.
# - Otherwise, remove the snake's tail to maintain its length.
# - Return the current score (number of food items eaten).

# TC: O(n) - Path checks can take up to the snake's length.
# SC: O(n) - Space for the snake's path.


from typing import List


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.path = [[0,0]]
        self.eat = 0
        
    def move(self, direction: str) -> int:
        x, y = self.path[0]
        if direction == 'U':
            x = x - 1
        elif direction == 'L':
            y = y - 1
        elif direction == 'R':
            y = y + 1
        elif direction == 'D':
            x = x + 1
        if [x,y] in self.path[:-1] or x<0 or x>=self.height or y < 0 or y >=self.width:
            return -1
        self.path.insert(0, [x,y])
        
        if self.eat < len(self.food) and [x,y] == self.food[self.eat]:
            self.eat+=1
        else:
            self.path.pop()
            
        return self.eat