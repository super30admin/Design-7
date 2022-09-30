# Time complexity : O(n)
# Space complexity : O(w*h + m) --> m = length of snake
# Leetcode : Time and Memory limit exceeded

from collections import deque
class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = deque([])
        self.w = width
        self.h = height
        self.food = food
        self.snake.append((0,0))
        self.visited = [[False for _ in range(width)] for _ in range(height)]
        self.i = 0
        
    def move(self, direction: str) -> int:
        n = len(self.snake)
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
        
        # snake bites itself
        # use the visited array to check if the snake bites itself
        if self.visited[head_x][head_y] and (head_x,head_y) != self.snake[0]:
            return -1
        
        # eat food
        if self.i < len(self.food):
            curr_food = self.food[self.i]
            if curr_food[0] == head_x and curr_food[1] == head_y:
                # mark the current food as visited and append to the queue
                self.visited[head_x][head_y] = True
                self.snake.append((head_x,head_y))
                self.i += 1
                return len(self.snake) - 1
        
        # normal move
        # move from the previous step by marking it unvisited and remove from the queue
        temp = self.snake.popleft()
        self.visited[temp[0]][temp[1]] = False
        # mark the new step as visited and add to the queue
        self.snake.append((head_x,head_y))
        self.visited[head_x][head_y] = True
        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
