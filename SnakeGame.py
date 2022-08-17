class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = [(0,0)] 
        self.width = width
        self.height = height
        self.curr_row = 0
        self.curr_col = 0
        self.food = food
        self.score = 0
        
    def move(self, direction: str) -> int:
        print(self.curr_row, self.curr_col, self.snake, self.score)
        if direction == "R":
            self.curr_col += 1 

        elif direction == "L":
            self.curr_col -= 1
        
        elif direction == "U":
            self.curr_row -= 1 
            
        elif direction == "D":
            self.curr_row += 1
            
        if  self.curr_col < 0 or  self.curr_col >= self.width :
            return -1
        elif self.curr_row < 0 or self.curr_row >= self.height:
            # print("ok")
            return -1
        elif (self.curr_row, self.curr_col) in self.snake and (self.curr_row, self.curr_col) != self.snake[0] :
            # print("sanke himself")
            return -1 
        elif self.food and [self.curr_row, self.curr_col ] == self.food[0]:
            # print("self.snake", self.snake  )
            self.score += 1
            self.food.pop(0)
            self.snake.append( ( self.curr_row, self.curr_col   ) )
            return self.score
        else:
            # print("self.snake", self.snake  )
            self.snake.append( ( self.curr_row, self.curr_col   ) )
            self.snake.pop(0)
            return self.score 
          

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
