class SnakeGame:
    
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.foodList = food
        self.snakeHead = [0, 0]
        self.size = 0
        self.width = width
        self.height = height
        self.snake = []
        self.snake.append(self.snakeHead)
        
    def move(self, direction: str) -> int:
        if direction == 'U':
            self.snakeHead[0] -= 1
        if direction == 'D':
            self.snakeHead[0] += 1
        if direction == 'L':
            self.snakeHead[1] -= 1
        if direction == 'R':
            self.snakeHead[1] += 1
         
        # border hitting
        if self.snakeHead[0] == self.height or self.snakeHead[0] < 0 or self.snakeHead[1] == self.width or self.snakeHead[1] < 0:
            return -1
        
        # snake hits itself
        for i in range(1, len(self.snake)):
            curr = self.snake[i]
            if curr[0] == self.snakeHead[0] and curr[1] == self.snakeHead[1]:
                return -1
        
        # snake eats food
        if self.foodList:
            foodAppear = self.foodList[0]
            if foodAppear[0] == self.snakeHead[0] and foodAppear[1] == self.snakeHead[1]:
                self.snake.append(self.foodList.pop(0))
                return len(self.snake) - 1
        
        # normal move
        newCell = [self.snakeHead[0], self.snakeHead[1]]
        self.snake.append(newCell)
        self.snake.pop(0)
        return len(self.snake) -1
            
        
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)