#Time Complexity : O(1)
#Space Complexity : O(width*height)
#Did this code successfully run on Leetcode : Yes

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.snake = deque([[0, 0]])
        self.foodlist = deque(food)
        self.width = width
        self.height = height
        self.direct = {'U': [-1, 0], 'L': [0, -1], 'R': [0, 1], 'D': [1, 0]}

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """
        newHead = [self.snake[0][0]+self.direct[direction][0], self.snake[0][1]+self.direct[direction][1]]

        #check if snake head is hitting any of the boundaries
        if newHead[0] < 0 or newHead[0] >= self.height or newHead[1] < 0 or newHead[1] >= self.width or (newHead in self.snake and newHead != self.snake[-1]):
            return -1


        #check if snake head is reaching a food item
        if self.foodlist and self.foodlist[0] == newHead:
            self.snake.appendleft(newHead)
            self.foodlist.popleft()
        #if snake is hitting empty space
        else:
            self.snake.appendleft(newHead)
            self.snake.pop()

        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)
