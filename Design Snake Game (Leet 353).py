'''
Time: O(n), where n is the length of the snake
Space: O(1)
'''


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.ind = 0
        self.snake = collections.deque()
        self.snake.append([0,0])
        

    def move(self, direction: str) -> int:
        
        head_r, head_c = self.snake[-1]
        
        if direction == 'D':
            head_r += 1
        elif direction == 'L':
            head_c -= 1
        elif direction == 'R':
            head_c += 1
        elif direction == 'U':
            head_r -= 1
        
        if head_r < 0 or head_c < 0 or head_r >= self.height or head_c >= self.width:
            return -1
        
        for i in range(1,len(self.snake)):
            if head_r == self.snake[i][0] and head_c == self.snake[i][1]:
                return -1
        
        if self.ind < len(self.food):
            fd = self.food[self.ind]
            if head_r == fd[0] and head_c == fd[1]:
                self.snake.append([head_r, head_c])
                self.ind += 1
                return len(self.snake) - 1
        
        self.snake.append([head_r, head_c])
        self.snake.popleft()
        return len(self.snake) - 1
        
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)