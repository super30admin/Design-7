# Time Complexity : O(1)
# Space Complexity : O(N) n is total number of food place
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : Nope


# Your code here along with comments explaining your approach


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.snake = deque([(0,0)])
        self.food = deque(food)
        self.width = width
        self.height = height
        self.snakeSize = 0
        self.seen = set()
        self.seen.add((0,0))
        
    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        head = self.snake[-1]
    
        if direction == 'U':
            row = head[0] - 1
            col = head[1] 
        if direction == 'L':
            row = head[0] 
            col = head[1] - 1
        if direction == 'R':
            row = head[0] 
            col = head[1] + 1
        if direction == 'D':
            row = head[0] + 1
            col = head[1]
        

        if self.food:
            food = self.food[0]
        else:
            food = None
        #if at food increase size
        if [row,col] == food:
            self.snakeSize += 1
            self.food.popleft() 
        else: 
            tail = self.snake.popleft()
            self.seen.remove((tail[0], tail[1]))
        
        #checks
        if row < 0 or row >= self.height or col < 0 or col >= self.width or (row,col) in self.seen:
            return -1
        
        self.seen.add((row,col))
        self.snake.append((row,col))
        
        return self.snakeSize
            
            
            
            
            

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)