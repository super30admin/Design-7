class SnakeGame(object):

    def __init__(self, width, height, food):
        """
        :type width: int
        :type height: int
        :type food: List[List[int]]
        """
        self.snakeHead=[0,0]
        self.foodList=food
        self.w=width
        self.h=height
        self.visited=[[False for _ in range(self.w)] for _ in range(self.h)]
        self.snake=collections.deque()
        self.snake.append(self.snakeHead)
        self.i=0


    def move(self, direction):
        """
        :type direction: str
        :rtype: int
        """
        if direction=="D":
            self.snakeHead[0]+=1
        elif direction=="U":
            self.snakeHead[0]-=1
        elif direction=="L":
            self.snakeHead[1]-=1
        else:
            self.snakeHead[1]+=1
        if self.snakeHead[0]<0 or self.snakeHead[1]<0 or self.snakeHead[0]==self.h or self.snakeHead[1]==self.w:
            return -1
        
        if self.visited[self.snakeHead[0]][self.snakeHead[1]]:
            return -1

        if self.i<len(self.foodList) and self.snakeHead[0]==self.foodList[self.i][0] and self.snakeHead[1]==self.foodList[self.i][1]:
            newHead=[self.snakeHead[0],self.snakeHead[1]]
            self.snake.append(newHead)
            self.visited[self.snakeHead[0]][self.snakeHead[1]]=True
            self.i+=1
            return len(self.snake)-1
        
        newHead=[self.snakeHead[0],self.snakeHead[1]]
        self.snake.append(newHead)
        self.visited[self.snakeHead[0]][self.snakeHead[1]]=True
        self.snake.popleft()

        first=self.snake[0]
        self.visited[first[0]][first[1]]=False
        return len(self.snake)-1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)