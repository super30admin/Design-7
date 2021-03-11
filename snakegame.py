class SnakeGame:
    
    """
    Description: Design a snake game, with initial position (0,0) height and width of the screen and position of food along with directions
    
    Time Complexity: O(width * height)
    Space Complexity: O(width * height)
    
    Approach:
    1. convert food list as a queue and start the snake head position at (0,0) and starting size as 0
    2. initialize a path (to check if head hits with the snake itself)
    3. with progress of the snake with given directions if food is on the way, increase it's size
    4. if food is not on the way, just add new element to the queue and remove the last one (tail), increase the size
    5. check if snake is going beyond boundaries or hit itself, return -1, otherwise return the size
    """

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
        self.size = 0
        self.path = set()
        self.path.add((0,0))

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        snakeHead = self.snake[-1]
           
        if direction == "U":
            row = snakeHead[0] - 1; col = snakeHead[1]
        if direction == "D":
            row = snakeHead[0] + 1; col = snakeHead[1]
        if direction == "L":
            row = snakeHead[0]; col = snakeHead[1] - 1
        if direction == "R":
            row = snakeHead[0]; col = snakeHead[1] + 1

        food = self.food[0] if self.food else None
            
        if [row,col] == food:
            self.size += 1
            self.food.popleft() 
        else: 
            tail = self.snake.popleft()
            self.path.remove((tail))

        if row < 0 or row >= self.height or col < 0 or col >= self.width or (row,col) in self.path:
            return -1

        self.path.add((row,col))
        self.snake.append((row,col))

        return self.size

