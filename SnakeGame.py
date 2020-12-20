#Time Complexity : O(1) for move
#Space Complexity : O(W*H) where the worst case snake can grow to entire board

from collections import deque
class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = deque()
        for fo in food:
            self.food.appned(fo)
        self.snake=[]
        self.snakeHead=[0,0]
        self.snake.append(self.snakeHead)
    
    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        
        if direction=="U":
            self.snakeHead[0]-=1
        elif direction=="D":
            self.snakeHead[0]+=1
        elif direction=="L":
            self.snakeHead[1]-=1
        else:
            self.snakeHead[1]+=1
        #snake its boundary
        if(self.snakeHead[0]<0 or self.snakeHead[0]>= self.height or self.snakeHead[1]<0 or self.snakeHead[1]>=self.width):
            return -1
        
        #snake hits itself
        for i in range(1,len(self.snake)):
            curr=self.snake[i]
            if(curr[0]==self.snakeHead[0] and curr[1]==self.snakeHead[1]):
                return -1
        #foodlist
        if(self.food):
            appeared=self.food[0]
            if(appeared[0]==self.snakeHead[0] and appeared[1]==self.snakeHead[1]):
                self.snake.append(self.food.popleft())
                return len(self.snake)-1
        #remove the head and ass 
        self.snake.remove(self.snake.pop(0))
        self.snake.append(self.snakeHead)
        
# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)