class SnakeGame:
    '''
    Time: O(1)
    '''
    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.height = height
        self.width = width
        self.food = food
        self.snake = deque([(0,0)])
        self.snakeset = set([(0,0)])
        self.c = 1

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        hi, hj = self.snake[-1]
        
        if(direction=="U"):
            hi -= 1
        elif(direction=="D"):
            hi += 1
        elif(direction=="R"):
            hj += 1
        else:
            hj -= 1
        
        # collides with the border
        if(hi<0 or hi==self.height or hj<0 or hj==self.width):
            return -1
        
        # food check
        if(self.c<=len(self.food)) and (hi==self.food[self.c-1][0] and hj==self.food[self.c-1][1]):
            # add food to the head
            self.snake.append((hi,hj))
            self.snakeset.add((hi,hj))
            self.c += 1
            return self.c-1
        else:
            # check if snake hit itself
            if((hi,hj) in self.snakeset and (hi,hj)!=self.snake[0]):
                return -1
            
            z =self.snake.popleft()
            self.snakeset.remove(z)
            self.snake.append((hi,hj))
            self.snakeset.add((hi,hj))
            return self.c-1
            


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
