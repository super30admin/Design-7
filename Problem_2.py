# from collections import deque


# class SnakeGame:

#     def __init__(self, width: int, height: int, food: List[List[int]]):
#         self.rows, self.cols = height, width
#         self.food = food
#         self.foodIndex = 0
#         self.score = 0
#         self.q = deque()
#         self.q.append([0, 0])
#         self.visited = [[False for _ in range(self.cols)] for _ in range(self.rows)]
#         self.visited[0][0] = True
#         self.direction = {
#             "R": [0, 1],
#             "L": [0, -1],
#             "U": [-1, 0],
#             "D": [1, 0],
#         }

#     def move(self, direction: str) -> int:
#         cr, cc = self.q[-1][0], self.q[-1][1]
#         nr, nc = cr + self.direction[direction][0], cc + self.direction[direction][1]
#         if nr < 0 or nr >= self.rows or nc < 0 or nc >= self.cols:
#             return -1
#         tr, tc = self.q.popleft()
#         self.visited[tr][tc] = False
#         if self.visited[nr][nc]:
#             return -1
#         self.visited[tr][tc] = False
#         if self.foodIndex < len(self.food):
#             fr, fc = self.food[self.foodIndex]
#             if nr == fr and nc == fc:
#                 self.foodIndex += 1
#                 self.score += 1
#                 self.q.appendleft([tr, tc])
#         self.q.append([nr, nc])
#         self.visited[nr][nc] = True
#         return self.score


# # Your SnakeGame object will be instantiated and called as such:
# # obj = SnakeGame(width, height, food)
# # param_1 = obj.move(direction)


from collections import deque


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.rows, self.cols = height, width
        self.food = food
        self.foodIndex = 0
        self.score = 0
        self.q = deque()
        self.q.append([0, 0])
        self.visited = set()
        self.visited.add((0, 0))
        self.direction = {
            "R": [0, 1],
            "L": [0, -1],
            "U": [-1, 0],
            "D": [1, 0],
        }

    def move(self, direction: str) -> int:
        cr, cc = self.q[-1][0], self.q[-1][1]
        nr, nc = cr + self.direction[direction][0], cc + self.direction[direction][1]
        if nr < 0 or nr >= self.rows or nc < 0 or nc >= self.cols:
            return -1
        tr, tc = self.q.popleft()
        self.visited.remove((tr, tc))
        if (nr, nc) in self.visited:
            return -1
        if self.foodIndex < len(self.food):
            fr, fc = self.food[self.foodIndex]
            if nr == fr and nc == fc:
                self.foodIndex += 1
                self.score += 1
                self.q.appendleft([tr, tc])
                self.visited.add((tr, tc))
        self.q.append([nr, nc])
        self.visited.add((nr, nc))
        return self.score


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
