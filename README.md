# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

class Node:
        def __init__(self,key,val,freq):
            self.key=key
            self.val=val
            self.freq=freq
            self.next=None
            self.prev=None
class DLL:
    def __init__(self):
        self.head=Node(-1,-1,1)
        self.tail=Node(-1,-1,1)
        self.size=0
    def addnode(self,node):
        node.next=self.head.next
        node.prev=self.head
        self.head.next=node
        node.next.prev=node
        self.size+=1
    def deletenode(self,node):
        node.prev.next=node.next
        node.next.prev=node.prev
        self.size-=1
    def lastnode(self):
        c=self.tail.prev
        self.deletenode(c)
        return c
        
class LFUCache:
    

    def __init__(self, capacity: int):
        self.keys={}
        self.frequencies={}
        self.capacity=capacity
        self.mini=float('inf')
    def get(self, key: int) -> int:
        if key not in self.keys:
            return -1
        else:
            c=self.keys[key]
            self.update(c)
            return c.val
    def update(self,node):
        b=self.frequencies[node.freq]
        b.deletenode(node)
        if node.freq==mini and b.size==0:
            self.mini+=1
        node.freq+=1
        if node.freq not in self.frequencies:
            self.frequencies[node.freq]=DLL(node)
        else:
            self.frequencies[node.freq].addnode(node)
    def put(self, key: int, value: int) -> None:
        if self.capacity==0:
            return
        if key in self.keys:
            k=self.key[keys]
            k.val=value
            self.update(k)
        else:
            if self.capacity==len(self.keys):
                final=self.frequencies[mini]
                lasnode=final.lastnode()
                self.keys.remove(lasnode.key)
            self.mini=1
            newnode=Node(key,value,1)
            self.keys[key]=newnode
            if self.mini not in self.frequencies:
                self.frequencies[self.mini]=DLL()
                self.frequencies[self.mini].addnode(newnode)
            else:
                self.frequencies[self.mini].addnode(newnode)
                
                
        


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
Problem2 H-Index (https://leetcode.com/problems/h-index/)

# Time Complexity = O(n)
# Space Complexity = O(n)

class Solution:
    def hIndex(self, citations: List[int]) -> int:
        helper=[0 for _ in range(len(citations)+1)]
        n=len(citations)
        for i in range(n):
            if citations[i]>n:
                helper[n]+=1
            else:
                helper[citations[i]]+=1
        papers=0
        for i in range(n,-1,-1):
            papers+=helper[i]
            if papers>=i:
                return i
        return -1
        

## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = collections.deque([(0,0)])    
        self.snake_set = {(0,0) : 1}
        self.width = width
        self.height = height
        self.food = food
        self.food_index = 0
        self.movement = {'U': [-1, 0], 'L': [0, -1], 'R': [0, 1], 'D': [1, 0]}
        

    def move(self, direction: str) -> int:        
        newHead = (self.snake[0][0] + self.movement[direction][0],
                   self.snake[0][1] + self.movement[direction][1])
        crosses_boundary1 = newHead[0] < 0 or newHead[0] >= self.height
        crosses_boundary2 = newHead[1] < 0 or newHead[1] >= self.width
        bites_itself = newHead in self.snake_set and newHead != self.snake[-1]
        if crosses_boundary1 or crosses_boundary2 or bites_itself:
            return -1

        next_food_item = self.food[self.food_index] if self.food_index < len(self.food) else None
       
        if self.food_index < len(self.food) and \
            next_food_item[0] == newHead[0] and \
                next_food_item[1] == newHead[1]: 
            self.food_index += 1
        else:                
            tail = self.snake.pop()  
            del self.snake_set[tail]
        self.snake.appendleft(newHead)

        self.snake_set[newHead] = 1

        return len(self.snake) - 1
        

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
