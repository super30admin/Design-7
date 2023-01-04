# Time Complexity : O(width+height), Where width,height are inputs
# Space Complexity : O(width+height), Where width,height are inputs
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : Nothing specific
from typing import List
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width=width
        self.height=height
        self.food=food#This will represent the food 
        self.snake=[[0,0]]#This will represent the entire snake
        self.length=1

    def move(self, direction: str) -> int:
        #what ever might be the direction the basic idea is to add the next row,col and remove all the elements after the length
        snakehead=self.snake[0]
    
        if(direction=="U"):
            #reduce the row
            nextplace=[snakehead[0]-1,snakehead[1]]    
        if(direction=="D"):
            #increase the row
            nextplace=[snakehead[0]+1,snakehead[1]]
        if(direction=="L"):
            #decrease the column
            nextplace=[snakehead[0],snakehead[1]-1]
        if(direction=="R"):
            #increase the column
            nextplace=[snakehead[0],snakehead[1]+1]
        #Now we should check wheter the snake is still alive or not
        if(nextplace[0] not in range(0,self.height) or nextplace[1] not in range(0,self.width)):
            return -1
        if(nextplace in self.snake[:-1]):
            return -1
        if(len(self.food)!=0):
            foodplace=self.food[0]
            if(foodplace==nextplace):
                self.snake.insert(0,nextplace)
                self.food.pop(0)#removing the fooditem
                self.length+=1#increasing the size of the snake
            else:
                #Here we are removing tail elment and add the nextplace
                self.snake.insert(0,nextplace)
                self.snake.pop(-1)
        else:
            #When the food is empty, It should still move on 
            #Here we are removing tail element and add the nextplace
            self.snake.insert(0,nextplace)
            self.snake.pop(-1)
        #print(self.snake)    
        return self.length-1
                
        
            


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)