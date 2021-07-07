from typing import List
from collections import deque


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.width = width
        self.height = height
        self.snake = deque()
        self.head = [0, 0]
        self.snake.append(self.head.copy())
        self.s_pos = {tuple(self.head)}
        self.food = deque(food)

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """
        if direction == 'R':
            self.head[1] += 1
        elif direction == 'L':
            self.head[1] -= 1
        elif direction == 'U':
            self.head[0] -= 1
        elif direction == 'D':
            self.head[0] += 1

        if self.head[0] < 0 or self.head[0] >= self.height or self.head[1] < 0 or self.head[1] >= self.width:
            return -1

        if self.food:
            cur_food = self.food[0]
            if self.head[0] == cur_food[0] and self.head[1] == cur_food[1]:
                self.s_pos.add(tuple(cur_food))
                self.snake.append(self.food.popleft())
                return len(self.snake) - 1

        # snake tail
        tobe_removed = self.snake[0]
        self.s_pos.remove(tuple(tobe_removed))
        self.snake.popleft()

        if tuple(self.head) in self.s_pos:
            return -1

        self.snake.append(self.head.copy())
        self.s_pos.add(tuple(self.head))
        return len(self.snake) - 1


if __name__ == '__main__':
    snake = SnakeGame(3, 3, [[2, 0], [0, 0], [0, 2], [2, 2]])
    print(snake.move("D"))
    print(snake.move("D"))
    print(snake.move("R"))
    print(snake.move("U"))
    print(snake.move("U"))
    print(snake.move("L"))
    print(snake.move("D"))
    print(snake.move("R"))
    print(snake.move("R"))
    print(snake.move("U"))
    print(snake.move("L"))
    print(snake.move("D"))
