#Time Complexity :o(1)
#Space Complexity :o(1)
#Did this code successfully run on Leetcode :yes
#Any problem you faced while coding this :no
class SnakeGame(object):

    body=None
    foodlist=None
    w,h=None,None
    head=None
    def __init__(self, width, height, food):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        :type width: int
        :type height: int
        :type food: List[List[int]]
        """
        self.head=[0,0]
        self.body=collections.deque([self.head])
        self.foodlist=collections.deque(food)
        self.w=width
        self.h=height
        
        
    def move(self, direction):
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        :type direction: str
        :rtype: int
        """
        if(direction=='U'):
            self.head[0]-=1
        elif(direction=='D'):
            self.head[0]+=1
        elif(direction=='L'):
            self.head[1]-=1
        elif(direction=='R'):
            self.head[1]+=1
        if(self.head[0]<0 or self.head[1]<0 or self.head[0]>=self.h or self.head[1]>=self.w ):
            return -1
        for i in range(1,len(self.body)):
            curr=self.body[i]
            if(self.head[0]==curr[0] and self.head[1]==curr[1]):
                return -1
        
        if(self.foodlist and ( self.head[0]==self.foodlist[0][0] 
                              and self.head[1]==self.foodlist[0][1])):
            self.body.append(self.foodlist.popleft())
            return len(self.body)-1
        self.body.popleft()
        self.body.append([self.head[0],self.head[1]])
        return len(self.body)-1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)