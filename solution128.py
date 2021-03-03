#Time Complexity:O(n)
#Space Complexity:O(n)
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.width=width                                                        #create a board of given width and height
        self.height=height                                      
        self.snake=deque()                                                      #use deques to store the current state of snake and the food
        self.foodList=deque(food)
        self.snakehead=[0,0]                                                    #begin with head at the top left corner of the board
        self.snake.append(deepcopy(self.snakehead))
        
    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """
        if direction=='U':                                                      #move the head to the directions as instructed
            self.snakehead[0]-=1
        if direction=='D':
            self.snakehead[0]+=1
        if direction=='L':
            self.snakehead[1]-=1
        if direction=='R':
            self.snakehead[1]+=1
        if self.snakehead[0]<0 or self.snakehead[0]==self.height or self.snakehead[1]==self.width or self.snakehead[1]<0:
            return -1                                                           # if the snake touches the walls return -1
        for i in range(1,len(self.snake)-2):                                    #check if the head bites the body of snake and return -1 if yes
            curr=self.snake[i]
            if self.snakehead[0]==curr[0] and self.snakehead[1]==curr[1]:
                return -1
        self.snake.append(deepcopy(self.snakehead))                             # move the snake forward by appending head
        if self.foodList and self.snakehead==self.foodList[0]:                  # if head is at the food remove the food from deque
            self.foodList.popleft()
        else:                                                                   #else move forward by one and hence remove last value from deque
            self.snake.popleft()
        return len(self.snake)-1                                                #return the length of the snake -1 which is usually the score
        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)