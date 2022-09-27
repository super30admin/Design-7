# Time complexity : O(n*m) --> n - length of snake, m - length of food array
# Space complexity : O(n) --> length of the snake
# Leetcode : Solved and submitted

from collections import deque
class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        # maintain a queue for the snake indices
        self.snake = deque([])
        self.w = width
        self.h = height
        self.food = food
        # append the first index (0,0) as the starting point
        self.snake.append((0,0))
        # global pointer to maintain the food index
        self.i = 0
        
    def move(self, direction: str) -> int:
        # head is the end of queue
        head_x, head_y = self.snake[-1]
        # update the index according to the direction that the snake is moved to
        if direction == 'U':
            head_x -= 1
        elif direction == 'L':
            head_y -= 1
        elif direction == 'D':
            head_x += 1
        elif direction == 'R':
            head_y += 1
            
        # boundary check
        # if the head of snake goes out of bounds, then the score is returned as -1
        if head_x < 0 or head_x == self.h or head_y < 0 or head_y == self.w:
            return -1
        
        # snake bites itself
        # go over the queue and check if the next position of snake is present in the queue, if so then return -1
        for j in range(1,len(self.snake)):
            curr = self.snake[j]
            if curr[0] == head_x and curr[1] == head_y:
                return -1
        
        # eat food
        # if the snake eats food, then append the new head to the queue and return the incremented score
        if self.i < len(self.food):
            curr_food = self.food[self.i]
            if curr_food[0] == head_x and curr_food[1] == head_y:
                self.snake.append((head_x,head_y))
                self.i += 1
                return len(self.snake) - 1
        
        # normal move
        # if none of the above, then we move one step by removing the first element in queue and adding the newHead, maintaining the size of the queue
        self.snake.popleft()
        self.snake.append((head_x,head_y))
        
        # retrun the score of the move as the size doesn't increase in this case
        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
