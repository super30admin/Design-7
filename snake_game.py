from collections import deque
class SnakeGame(object):
#O(W*H)>search in queue>eats itself function
#O(w*H)>queue size>snake size>entire matrix
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
        self.width=width
        self.height=height
        self.food=food
        self.queue=deque([(0,0)])
        self.score=0

    def move(self, direction):
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        :type direction: str
        :rtype: int
        """
        #head of snake is at end of queue
        headr,headc=self.queue[-1]
       
        #if snake moves up, its row index reduces>similarly other directions
        if direction=='U':
            headr-=1
        if direction=='L':
            headc-=1
        if direction=='R':
            headc+=1
        if direction=='D':
            headr+=1
        #if boundary>end of game
        if headr<0 or headr>self.height-1 or headc<0 or headc>self.width-1:
            return -1
        #if food is in wayof new head>increase size>dont change tail>increase score>pop food item
        if self.food and [headr,headc]==self.food[0]:
            self.queue.append([headr,headc])
            self.food.pop(0)
            self.score+=1
        #if didnt eat food>move ahead and pop tail indices
        else:
            #tail
            self.queue.popleft()
            
            #//O(n)> search in queue
            #if new head is pointing to any body> eats itself>end game
            if [headr,headc] in self.queue:
                return -1
            #if all good> enter head cooordinates
            else:
                self.queue.append([headr,headc])
        return self.score
            
        
        

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)