#TC:O(1)-move function
#SC:O(n)-queue and list for food
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.width = width
        self.height = height
        self.q = deque([(0, 0)])
        self.food_ptr = 0
        self.food = list(map(tuple, food))
        self.directions = { 'U': (-1, 0), 'L': (0, -1), 'R': (0, 1), 'D': (1, 0) }

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """  
        dx, dy = self.directions[direction]
        i, j = self.q[-1]
        next_move = (i + dx, j + dy)
        food = self.food[self.food_ptr] if self.food_ptr < len(self.food) else (-1, -1)
        
        if next_move != food:
            self.q.popleft()

        if not 0 <= next_move[0] < self.height or not 0 <= next_move[1] < self.width or next_move in self.q:
            return -1
        
        self.q.append(next_move)
        
        if next_move == food:
            self.food_ptr += 1
        
        return len(self.q) - 1


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
