from typing import List, collections
# class SnakeGame:

#     def __init__(self, width: int, height: int, food: List[List[int]]):
#         self.h = height
#         self.w = width
#         self.food = food
#         self.idx = 0
#         self.visited = [[False]*self.w for _ in range(self.h)]
#         self.snakebody = collections.deque()
#         self.snakebody.appendleft([0,0])

#     def move(self, direction: str) -> int:
#         head = self.snakebody[0]
#         r,c = head[0],head[1]
#         if direction == "L":
#             c-=1
#         elif direction == "R":
#             c+=1
#         elif direction == "U":
#             r-=1
#         elif direction == "D":
#             r+=1
        
#         if r==self.h or r<0 or c==self.w or c<0 or self.visited[r][c]: return -1
#         if self.idx < len(self.food):  
#             if self.food[self.idx][0] == r and self.food[self.idx][1] == c :
#                 self.snakebody.appendleft([r,c])
#                 self.visited[r][c] = True
#                 self.idx+=1
#                 return len(self.snakebody)-1
        
#         self.snakebody.appendleft([r,c])
#         self.visited[r][c] = True
#         self.snakebody.pop()
#         tail = self.snakebody[-1]
#         self.visited[tail[0]][tail[1]] = False
#         return len(self.snakebody)-1

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.h = height
        self.w = width
        self.food = food
        self.idx = 0
        self.visited = [[False]*self.w for _ in range(self.h)]
        self.snakebody = collections.deque()
        self.snakebody.appendleft([0,0])
        self.visited[0][0] = True

    def move(self, direction: str) -> int:
        head = self.snakebody[0]
        r,c = head[0],head[1]
        if direction == "L":
            c-=1
        elif direction == "R":
            c+=1
        elif direction == "U":
            r-=1
        elif direction == "D":
            r+=1
        
        if r==self.h or r<0 or c==self.w or c<0 : return -1
        if self.idx < len(self.food):  
            if self.food[self.idx][0] == r and self.food[self.idx][1] == c :
                if self.visited[r][c] : return -1
                self.snakebody.appendleft([r,c])
                self.visited[r][c] = True
                self.idx+=1
                return len(self.snakebody)-1
        
        # Normal move: remove tail first
        tail_r, tail_c = self.snakebody.pop()
        self.visited[tail_r][tail_c] = False
        # Check for collision after freeing tail
        if self.visited[r][c]:
            return -1
        self.snakebody.appendleft([r, c])
        self.visited[r][c] = True
        return len(self.snakebody)-1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)