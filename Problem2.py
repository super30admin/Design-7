from collections import deque
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake=deque()
        self.headptr=[0,0]
        self.snake.append((0,0))
        self.foodList=food
        self.h=height
        self.w=width
        self.i=0

    def move(self, direction: str) -> int:
        if direction=='U':
            self.headptr[0]-=1
        elif direction=='D':
            self.headptr[0]+=1
        elif direction=='R':
            self.headptr[1]+=1
        else:
            self.headptr[1]-=1

        if self.headptr[0]<0 or self.headptr[0]>=self.h or self.headptr[1]<0 or self.headptr[1]>=self.w:
            return -1

        tr = self.snake[-1][0]
        tc = self.snake[-1][1]

        if (self.headptr[0],self.headptr[1]) in self.snake and (self.headptr[0],self.headptr[1])!=(tr,tc):
            return -1
        
        if self.i<len(self.foodList):
            currFood=self.foodList[self.i]
            if currFood[0]==self.headptr[0] and currFood[1]==self.headptr[1]:
                self.snake.appendleft((self.headptr[0],self.headptr[1]))
                self.i+=1
                return len(self.snake)-1

        tail=self.snake.pop()
        self.snake.appendleft((self.headptr[0],self.headptr[1]))

        return len(self.snake)-1


        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)