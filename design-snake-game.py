class SnakeGame:
    def __init__(self, width, height, food):
        self.w = width
        self.h = height
        self.food = food
        self.foodIdx = 0 #1
        self.visited = [[False]*self.w for _ in range(self.h)]
        self.snakeBody = collections.deque()
        self.snakeBody.appendleft([0, 0])
        self.visited[0][0] = True
# snakeBody = {[1,2],[1,1],[0,1],[0,0]}
# snakeBody = {[0,1],[0,2],[1,2]}
# r,c=0,2
    def move(self, direction):
        # [[False, False, False], [False, False, False]]
        head = self.snakeBody[0]
        r, c = head[0], head[1]

        if direction == "U":
            r -= 1
        elif direction == "D":
            r += 1
        elif direction == "R":
            c += 1
        elif direction == "L":
            c -= 1
        # check if out of grid
        if r < 0 or c < 0 or r == self.h or c == self.w:
            return -1

        tail = self.snakeBody[-1]
        # check if we hit our own body (visited)
        # the 2nd check is to make sure its not the tail - we are actually allowed to move to the tail because the snake is moving and we're about to pop it
        if self.visited[r][c] and (r != tail[0] or c != tail[1]):
            return -1
        if self.foodIdx < len(self.food):
            if self.food[self.foodIdx][0] == r and self.food[self.foodIdx][1] == c:
                self.foodIdx += 1
                self.snakeBody.appendleft([r, c])
                self.visited[r][c] = True
                return len(self.snakeBody) - 1
        # slice off the tip of the tail
        tail = self.snakeBody.pop()
        # mark the tail's old position as safe and empty
        self.visited[tail[0]][tail[1]] = False
        # push the head forward into the new square
        self.snakeBody.appendleft([r, c])
        # mark the new head position as occupied
        self.visited[r][c] = True
        # return the player's current score
        return len(self.snakeBody) - 1

        
#[[False, True, True], 
# [False, False, True]]

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)