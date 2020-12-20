import collections
class SnakeGame:

    def __init__(self, width: int, height: int, food):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.width=width
        self.height=height
        self.food=collections.deque(food)
        self.snake=collections.deque([[0,0]])
        self.snakebody=set()
        self.snakebody.add((0,0))
        
        

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        dirs={'U':[-1,0],'L':[0,-1],'D':[1,0],'R':[0,1]}
        i,j=dirs[direction]
        k,l=self.snake[-1][0],self.snake[-1][1]
        if 0<=i+k<self.height and 0<=j+l<self.width:
            m,n=self.snake.popleft()
            self.snakebody.remove((m,n))
            if self.food and [i+k,j+l]==self.food[0]:
                self.food.popleft()
                self.snake.appendleft([m,n])
                self.snakebody.add((m,n))
            elif (i+k,j+l) in self.snakebody:
                return -1
            self.snake.append([i+k,j+l])
            self.snakebody.add((i+k,j+l))
        else:
            return -1
        return len(self.snakebody)-1
                
                
obj=SnakeGame(3,3,[[2,0],[0,0],[0,2],[2,2]])

for i,j in zip(["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move"],
[[3,3,[[2,0],[0,0],[0,2],[2,2]]],["D"],["D"],["R"],["U"],["U"],["L"],["D"],["R"],["R"],["U"],["L"],["D"]]):
    if i=="move":
        obj.move(j[0])