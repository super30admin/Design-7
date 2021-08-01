# Time Complexity : O()
# Space Complexity : O()
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


#maintaining the food and snake as linkedlist and moving checking the boundaries etc
class LLNode:
    def __init__(self,x,y):
        self.next = None
        self.x = x
        self.y = y
class LinkedList:
    def __init__(self):
        self.head = None
        self.tail = None
        self.size = 0
    
    def insertAtLast(self,x,y):
        if self.head == None:
            self.head = LLNode(x,y)
            self.head = self.tail
        else:
            self.tail.next = self.LLNode(x,y)
            self.tail = self.tail.next
        self.size += 1



class SnakeGame:

    def __init__(self,width,height,food):
        self.width = width
        self.height = height
        self.foodList = LinkedList()

        for i in food:
            self.foodList.insertAtLast(i[0],i[1])
        self.snake = LinkedList()
        self.snakeHead = [0,0]
        self.snake.insertAtLast(self.snakeHead.copy())

    
    def move(self,direction):
        if direction == "U":
            self.snakeHead[0] -= 1
        elif direction == "L":
            self.snakeHead[1] -= 1
        elif direction == "R":
            self.snakeHead[1] += 1
        else:
            self.snakeHead[0] += 1
        
        if self.snakeHead[0] < 0 or self.snakeHead[0] >= self.height or self.snakeHead[1] < 0 or self.snakeHead[1] >= self.width:
            return -1
        
        for i in range(1,len(self.snake)):
            if self.snakeHead[0] == self.snake[i][0] and self.snakeHead[1] == self.snake[i][1]:
                return -1
        
        if self.foodList.head != None:
            foodAppear = [self.foodList.head.x,self.foodList.head.y]
            
            if self.snakeHead[0] == foodAppear[0] and self.snakeHead[1] == foodAppear[1]:
                node = self.foodList.head
                self.foodList.head = self.foodList.head.next
                self.snake.addToHead(node)
                self.snake.size -= 1
                return self.snake.size()-1
        
        newCell = LLNode(self.snakeHead[0],self.snakeHead[1])

        self.snake.insertAtLast(newCell)
        self.snake.head = self.snake.head.next
        self.snake.size -= 1
        return self.snake.size




        