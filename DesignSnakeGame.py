from collections import deque
class snakeGame:
    """Designing snake game using queue---append to the right and remove from the left
    Time complexity-O(1) except for snake hitting itself, for snake hitting itself, its O(l) where n is the length of snake
    Space complexity-O(l) where l is the length of snake"""
    def __init__(self, width, height, Food):
        self.width=width
        self.height=height
        self.food=Food
        self.foodIndex=0
        self.head=[0,0]
        self.snake=deque()
        self.snake.append([0,0])

    def move(self, direction):
        if direction=="R":
            self.head[1]+=1
        if direction=="U":
            self.head[0]-=1
        if direction=="D":
            self.head[0]+=1
        if direction=="L":
            self.head[1]-=1
        # border hitting
        if self.head[0]==self.height or self.head[0]<0 or self.head[1]==self.width or self.head[1]<0:
            return -1
        # snake hitting itself
        for i in range(1,len(self.snake)):
            if self.head[0]==self.snake[i][0] and self.head[1]==self.snake[i][1]:
                return -1
        # snake eats food
        if self.head[0]==self.food[self.foodIndex][0] and self.head[1]==self.food[self.foodIndex][1]:
            self.snake.append(self.food[self.foodIndex])
            self.foodIndex+=1
            return len(self.snake)-1
        # normal move
        self.snake.popleft()
        self.snake.append(list(self.head))
        
        return len(self.snake)-1

        


sol=snakeGame(3,2,[[1,2],[0,1]])
print(sol.move("R"))
print(sol.move("D"))
print(sol.move("R"))
print(sol.move("U"))
print(sol.move("L"))
print(sol.move("U"))