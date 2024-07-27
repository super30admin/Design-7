# Time Complexity : O(1)
# Space Complexity : O(W * H + F), where W is the width, H is the height of the game board, and F is the number of food items

from collections import deque
from typing import List, Tuple

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[Tuple[int, int]]):
        self.body = deque([(0, 0)])
        self.set = set(["0#0"])
        self.food = food
        self.score = 0
        self.food_index = 0
        self.width = width
        self.height = height

    def move(self, direction: str) -> int:
        head = self.body[0]
        new_head = [head[0], head[1]]
        if direction == "U":
            new_head[0] -= 1
        elif direction == "D":
            new_head[0] += 1
        elif direction == "L":
            new_head[1] -= 1
        elif direction == "R":
            new_head[1] += 1
        
        if new_head[0] < 0 or new_head[0] >= self.height or new_head[1] < 0 or new_head[1] >= self.width:
            return -1  # out of boundary

        if (self.food_index < len(self.food) and 
                new_head[0] == self.food[self.food_index][0] and 
                new_head[1] == self.food[self.food_index][1]):
            self.score += 1
            self.food_index += 1
        else:
            old_tail = self.body.pop()
            self.set.remove("{}#{}".format(old_tail[0], old_tail[1]))
        
        if "{}#{}".format(new_head[0], new_head[1]) in self.set:
            return -1  # run into itself

        self.body.appendleft(tuple(new_head))
        self.set.add("{}#{}".format(new_head[0], new_head[1]))
        return self.score

# Example 1
width, height = 3, 2
food = [(1, 2), (0, 1)]
game = SnakeGame(width, height, food)
print(game.move("R"))  # returns 0
print(game.move("D"))  # returns 0
print(game.move("R"))  # returns 1
print(game.move("U"))  # returns 1
print(game.move("L"))  # returns 2
print(game.move("U"))  # returns -1 (game over)

# Example 2
width, height = 2, 2
food = [(1, 1)]
game = SnakeGame(width, height, food)
print(game.move("D"))  # returns 0
print(game.move("R"))  # returns 1
print(game.move("U"))  # returns 1

# Example 3
width, height = 2, 2
food = [(0, 1), (1, 0)]
game = SnakeGame(width, height, food)
print(game.move("R"))  # returns 1
print(game.move("D"))  # returns 1
print(game.move("L"))  # returns 2
print(game.move("U"))  # returns 2