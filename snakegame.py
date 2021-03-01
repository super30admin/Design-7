
#Time Complexity: O(1)
#Space Complexity: O(W * H + N)
#Run on Leetcode: Yes
#Any Issues: No


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
        self.food = deque(food)
        self.position = OrderedDict({(0,0): 0})

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        (i, j), _ = self.position.popitem(last=True) # current position
        self.position[(i, j)] = 0 # add back 
        if direction == "U": i -= 1
        elif direction == "L": j -= 1
        elif direction == "R": j += 1
        else: i += 1
        if self.food and self.food[0] == [i, j]: self.food.popleft()
        else: self.position.popitem(last=False)
        if not (0 <= i < self.height and 0 <= j < self.width) or (i, j) in self.position: return -1 # game over 
        self.position[(i, j)] = 0
        return len(self.position)-1