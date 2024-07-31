#I created a simulation of the classic Snake game where the snake moves on a grid and eats food items. The __init__ method initializes the game with a grid of given dimensions, a list of food locations, and starts the snake at the top-left corner. The move method handles the snake's movement based on the direction input ('U' for up, 'L' for left, 'R' for right, 'D' for down). It updates the snake's position, checks for collisions with the grid boundaries or itself, and manages food consumption by checking if the snake's new head position matches the current food location. If the snake eats the food, it grows by not removing the tail segment; otherwise, the tail segment is removed to maintain the snake's length. The time complexity for each move operation is O(N), where N is the length of the snake, due to the need to check for collisions and update the snake's body. The space complexity is O(N), where N is the length of the snake, as we need to store the snake's body coordinates in the path list.

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.path = [[0,0]]
        self.eat = 0
        
    def move(self, direction: str) -> int:
        x, y = self.path[0]
        if direction == 'U':
            x = x - 1
        elif direction == 'L':
            y = y - 1
        elif direction == 'R':
            y = y + 1
        elif direction == 'D':
            x = x + 1
        if [x,y] in self.path[:-1] or x<0 or x>=self.height or y < 0 or y >=self.width:
            return -1
        self.path.insert(0, [x,y])
        
        if self.eat < len(self.food) and [x,y] == self.food[self.eat]:
            self.eat+=1
        else:
            self.path.pop()
            
        return self.eat