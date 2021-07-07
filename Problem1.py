# Time Complexity : O(1) 
# Space Complexity :O(n) where n is the max length of the snake. 
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach

class SnakeGame:
    
    def __init__(self, width, height, food):
        self.width = width
        self.height = height
        self.food = food 
        self.snake = []
        self.foodList = food
        self.snakeHead = [0,0]
        self.snake.append(self.snakeHead)
        
    def move(self, direction):
        if direction[0] == 'U':
            self.snakeHead[0] -= 1 
        if direction[0] == 'D':
            self.snakeHead[0] += 1 
        if direction[0] == 'L':
            self.snakeHead[1] -= 1 
        if direction[0] == 'R':
            self.snakeHead[1] += 1 
            
        # if Snake hits the border
        if self.snakeHead[0] < 0 or self.snakeHead[0] >= self.height or self.snakeHead[1] < 0 or self.snakeHead[1] >= self.width:
            return -1 
        
        # if Snake hits itself
        for i in range(1, len(self.snake)):
            temp = self.snake[i]
            if temp[0] == self.snakeHead[0] and temp[1] == self.snakeHead[1]:
                return -1 
        
        # if it eats food 
        if self.foodList:
            food = self.foodList[0]
            if food[0] == self.snakeHead[0] and food[1] == self.snakeHead[1]:
                self.snake.append(self.foodList.pop(0))
            return len(self.snake) - 1 

if __name__ == "__main__":
    s = SnakeGame(3, 2, [[1,2],[0,1]])
    output = [None]
    # Test Case 1 
    output.append(s.move(["R"]))
    
    # Test Case 2
    output.append(s.move(["D"]))
    
    # Test Case 3 
    output.append(s.move(["R"]))
    
    # Test Case 4 
    output.append(s.move(["U"]))
    
    # Test Case 5 
    output.append(s.move(["L"]))
    
    # Test Case 6 
    output.append(s.move(["U"]))
    
    print(output)                   # output = [None, 0, 0, 1, 1, 2, -1]
    
    
    
    