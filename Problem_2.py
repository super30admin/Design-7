'''
Time Complexity - O(n)
Space Complexity - O(n^2). We are maintaining a visited matrix
Works on Leetcode
'''
from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.snake = deque()
        self.visited = [[False for j in range(self.width)] for i in range(self.height)]
        self.snakeHead = [0,0]
        self.foodList = food
        self.snake.append(self.snakeHead)
        self.idx = 0

    def move(self, direction: str) -> int:
        #modify the head
        if direction == "U":
            self.snakeHead[0]-=1
        elif direction == "D":
            self.snakeHead[0]+=1
        elif direction == "L":
            self.snakeHead[1]-=1
        else:
            self.snakeHead[1]+=1
        
        #check if head is within boundaries
        if self.snakeHead[0] < 0 or self.snakeHead[0] == self.height or self.snakeHead[1] < 0 or self.snakeHead[1] == self.width:
            return -1
        #check snake is not touching itself else it dies
        if self.visited[self.snakeHead[0]][self.snakeHead[1]] :
            return -1
        #eats food
        if self.idx < len(self.foodList):
            if self.snakeHead[0] == self.foodList[self.idx][0] and self.snakeHead[1] == self.foodList[self.idx][1] :
                self.idx+=1
                self.visited[self.snakeHead[0]][self.snakeHead[1]] = True
                newHead = [self.snakeHead[0], self.snakeHead[1]]
                self.snake.append(newHead)
                return len(self.snake)-1
        self.visited[self.snakeHead[0]][self.snakeHead[1]] = True
        newHead = [self.snakeHead[0], self.snakeHead[1]]
        self.snake.append(newHead)
        self.snake.popleft()
        currTail = self.snake[0]
        self.visited[currTail[0]][currTail[1]] = False
        return len(self.snake)-1



        
        

        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)