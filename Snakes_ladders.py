# -*- coding: utf-8 -*-
"""
TC:O(MN) M,N = height and width of the foodboard
SC:O(MN) deque, dirs list, hashset used
"""

pos_add = lambda tup1, tup2: (tup1[0] + tup2[0], tup1[1] + tup2[1])
from collections import deque

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.food_idx = 0
        self.snake = deque([(0, 0)])
        self.snake_set = {(0, 0)} # need queue and set to track order and inclusion in O(1)
        
    def __is_food(self, pos: Tuple[int, int]) -> bool:
        # always return false if no food left
        if self.foodIdx >= len(self.food):
            return False
        return pos == tuple(self.food[self.foodIdx]) # tup == list always false
        
    def __inbounds(self, y:int, x:int) -> bool:
        return (y > -1 and y < self.height) and (x > -1 and x < self.width)

    def move(self, direction: str) -> int:
        directions = {'U': (-1, 0), 'D': (1, 0), 'L': (0, -1), 'R': (0, 1)}
        last_pos = self.snake[-1]
        next_pos = pos_add(last_pos, directions[direction])
        
        # move tail if not moving into food
        if not self.__is_food(next_pos):
            tail = self.snake.popleft()
            self.snakeSet.remove(tail)
        else:
            self.food_idx += 1
        
        # return -1 if snake hits wall or itself
        if (not self.__inbounds(*next_pos)) or (next_pos in self.snake_set):
            return -1
        
        self.snake.append(next_pos)
        self.snakeSet.add(next_pos)
        return self.food_idx