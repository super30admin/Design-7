'''
Problem: Design Snake Game
Time Complexity: O(1)
Space Complexity: O(n), for snake length in queue and set
Did this code successfully run on Leetcode: Yes
Any problem you faced while coding this: No
Your code here along with comments explaining your approach:
        created snake with double ended queue
        head at left and tail at right
        if row and col is already occupie dthen maintained it in visited array
'''

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.food = food
        self.width = width
        self.height = height
        self.row = 0
        self.col = 0
        self.snake = deque()
        self.visited = set()
        self.i = 0
        self.snake.append((0,0))
        self.visited.add((0,0))

    def move(self, direction: str) -> int:
        if direction =='U':
            self.row-=1
        elif direction =='D':
            self.row+=1
        elif direction =='L':
            self.col-=1
        else:
            self.col+=1
        
        #bound check
        if self.row<0 or self.col<0 or self.row==self.height or self.col==self.width:
            return -1
        
        #hits itself
        #get tail
        tr = self.snake[-1][0]
        tc = self.snake[-1][1]
        if (self.row, self.col) in self.visited and (self.row, self.col)!=(tr, tc) :
            return -1
        
        #eat food
        if self.i < len(self.food) and self.row == self.food[self.i][0] and self.col == self.food[self.i][1]:
            self.i+=1
        else:
            tail = self.snake.pop()
            self.visited.remove(tail)
        
        self.snake.appendleft((self.row, self.col))
        self.visited.add((self.row, self.col))
        
        return len(self.snake)-1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)