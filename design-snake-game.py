# Time Complexity: O(1), dispaly size will be limited
# Space Complexity: O(m*n)
# class SnakeGame:
#   def __init__(self,height,width,food):
#     from collections import deque
#     self.snake=deque()
#     self.h=height
#     self.w=width
#     self.food=food
#     self.head=[0,0]
#     self.snake.append(self.head)
#     self.idx=0
#   def move(self,direction):
#     if direction=='L': self.head[1]-=1
#     elif direction=='R': self.head[1]+=1 
#     elif direction=='U': self.head[0]-=1
#     elif direction=='D': self.head[0]+=1
#     # hitting the borders
#     if self.head[0]<0 or self.head[1]<0 or self.head[0]==self.h or self.head[1]==self.w: return -1
#     # hitting the snake
#     for i in range(1,len(self.snake)):
#       if self.snake[i][0]==self.head[0] and self.snake[i][1]==self.head[1]: return -1
#     # food move
#     if self.idx<len(self.food) and self.head[0]==self.food[self.idx][0] and self.head[1]==self.food[self.idx][1]:
#       self.snake.append(self.head.copy())
#       self.idx+=1
#       return len(self.snake)-1
#     # normal move
#     self.snake.append(self.head.copy())
#     self.snake.popleft()
#     return len(self.snake)-1
# snake=SnakeGame(2,3,[[1,2],[0,1]])
# print(snake,snake.move("R"),snake.move("D"),snake.move("R"),snake.move("U"),snake.move("L"),snake.move("U"))
# Time Complexity: O(1), dispaly size will be limited
# Space Complexity: O(m*n)
class SnakeGame:
  def __init__(self,height,width,food):
    from collections import deque
    self.snake=deque()
    self.h=height
    self.w=width
    self.food=food
    self.head=[0,0]
    self.board=[[False for j in range(self.w)] for i in range(self.h)]
    self.snake.append(self.head)
    self.idx=0
  def move(self,direction):
    if direction=='L': self.head[1]-=1
    elif direction=='R': self.head[1]+=1 
    elif direction=='U': self.head[0]-=1
    elif direction=='D': self.head[0]+=1
    # hitting the borders
    if self.head[0]<0 or self.head[1]<0 or self.head[0]==self.h or self.head[1]==self.w: return -1
    # hitting the snake
    if self.board[self.head[0]][self.head[1]]==True: return -1
    # food move
    if self.idx<len(self.food) and self.head[0]==self.food[self.idx][0] and self.head[1]==self.food[self.idx][1]:
      self.snake.append(self.head.copy())
      self.board[self.head[0]][self.head[1]]=True
      self.idx+=1
      return len(self.snake)-1
    # normal move
    self.snake.append(self.head.copy())
    self.board[self.head[0]][self.head[1]]=True
    self.snake.popleft()
    temp=self.snake[0]
    self.board[temp[0]][temp[1]]=False
    return len(self.snake)-1
snake=SnakeGame(2,3,[[1,2],[0,1]])
print(snake,snake.move("R"),snake.move("D"),snake.move("R"),snake.move("U"),snake.move("L"),snake.move("U"))