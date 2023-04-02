import collections


class SnakeGame(object):

  def __init__(self, width, height, food):
      self.snake = [0,0]
      matrix = [[0]*width for i in range(height)]
      self.food = food
      self.q = collections.deque()
      self.visited = set()
      self.m = height
      self.n = width
      self.foodIdx = 0
      self.q.append((0,0))
      self.visited.add((0,0))

  #Here when snake moves in given direction we add row and col enteries to right of 
  # queue and in visited set. If the cur row and col of snake is boundry then return -1, 
  # else if cur row and col of snake is present in visited set then it hit itself, return -1, 
  # else if cur row and col of snake is curFood's row and col the entry is added to q and visited set and size of q is returned, 
  # else if cur cur row and col of snake is just a normal move then entry entry is added to q and visited set and left entry of q is poped from q and visisted and size of q is returned,
  #Time complexity - O(1)
  #Space complexity - O(n) - size of visited set and queue
  def move(self, direction):
      #hits boundry
      if direction=="U":
          self.snake[0]-=1
      elif direction=="L":
          self.snake[1]-=1
      elif direction=="R":
          self.snake[1]+=1
      else:
          self.snake[0]+=1

      if self.snake[0]<0 or self.snake[1]<0 or self.snake[0]>=self.m or self.snake[1]>=self.n:
          return -1

      #hits itself
      if (self.snake[0], self.snake[1]) in self.visited:
          return -1

      #eats food
      if self.foodIdx<len(self.food):
          curFood = self.food[self.foodIdx]
          if curFood[0]==self.snake[0] and curFood[1]==self.snake[1]:
              self.foodIdx+=1
              self.q.append((self.snake[0], self.snake[1]))
              self.visited.add((self.snake[0], self.snake[1]))
              return len(self.q)-1


      #moves normally
      self.q.append((self.snake[0], self.snake[1]))
      r, c = self.q.popleft()
      self.visited.add((self.snake[0], self.snake[1]))
      self.visited.remove((r,c))
      return len(self.q)-1


s = SnakeGame(3, 2, [[1,2],[0,1]])
print(s.move("R"))
print(s.move("D"))
print(s.move("R"))
print(s.move("U"))
print(s.move("L"))
print(s.move("U"))




