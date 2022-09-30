# Time complexity : O(L) --> lenght of food array
# Space complexity : O(n) --> lenght of the snake
# Leetcode : Solevd and submiited

from collections import deque
class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
      # maintain a queue for snake head, and tail and also a set to check for the body of the snake
        self.snake = deque([])
        self.w = width
        self.h = height
        self.food = food
        self.snake.append((0,0))
        self.visited = set()
        self.visited.add((0,0))
        self.i = 0
        
    def move(self, direction: str) -> int:
        n = len(self.snake)
        # change the head using the durection of move
        head_x, head_y = self.snake[-1]
        if direction == 'U':
            head_x -= 1
        elif direction == 'L':
            head_y -= 1
        elif direction == 'D':
            head_x += 1
        elif direction == 'R':
            head_y += 1
            
        # boundary check
        if head_x < 0 or head_x == self.h or head_y < 0 or head_y == self.w:
            return -1
        
        # snake bites itself and also it's head should not be the tail
        if (head_x,head_y) in self.visited and (head_x,head_y) != self.snake[0]:
            return -1
        
        # eat food
        # if snake eats food, then append to the queue and also the set
        if self.i < len(self.food):
            curr_food = self.food[self.i]
            if curr_food[0] == head_x and curr_food[1] == head_y:
                self.visited.add((head_x,head_y))
                self.snake.append((head_x,head_y))
                self.i += 1
                return len(self.snake) - 1
        
        # normal move
        # remove from queue and set the tail and append head to both of them
        temp = self.snake.popleft()
        self.visited.remove(temp)
        self.snake.append((head_x,head_y))
        self.visited.add((head_x,head_y))
        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
