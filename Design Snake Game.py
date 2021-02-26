from collections import deque

class SnakeGame:
    #Approach: Queue w/ hashset
    #Time Complexity: O(1)
    #Space Complexity: O(width * height + len(food))

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
        self.occupied = set()
        self.snake = deque()
        self.food = deque(food)
        self.score = 0
        
        self.dir = {'U' : (-1, 0), 'L' : (0, -1), 'R' : (0, 1), 'D' : (1, 0)}
        
        self.occupied.add((0, 0))
        self.snake.append((0, 0))

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        head = self.snake[-1]
        move = self.dir[direction]
        r, c = head[0] + move[0], head[1] + move[1]
        
        food = self.food[0] if self.food else None
        if [r, c] == food:
            self.score += 1
            self.food.popleft()
        else:
            tail = self.snake.popleft()
            self.occupied.remove((tail[0], tail[1]))
        
        if r < 0 or r >= self.height or c < 0 or c >= self.width or (r, c) in self.occupied:
            return -1
        
        self.occupied.add((r, c))
        self.snake.append((r, c))
            
        return self.score

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)