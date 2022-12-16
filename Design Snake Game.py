# Time COmplexity: O(1)
# Space Complexity: O(WXH)
from collections import deque
from collections import defaultdict


class SnakeGame:

    def __init__(self, width, height, food):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """
        self.snake = deque([(0, 0)])
        self.width = width
        self.height = height
        self.food = food

        self.dirc = defaultdict()
        self.dirc['U'] = [-1, 0]
        self.dirc['D'] = [1, 0]
        self.dirc['R'] = [0, 1]
        self.dirc['L'] = [0, -1]

        self.curr_findex = 0

        #         For checking if snake bites itself or not in O(1) operation
        self.set = defaultdict()
        self.set[(0, 0)] = 1

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """

        #         We will check for new head as snake will be moving
        x = self.snake[0][0] + self.dirc[direction][0]
        y = self.snake[0][1] + self.dirc[direction][1]
        head = (x, y)

        #         We will chekc for boundries

        heightCheck = head[0] < 0 or head[0] >= self.height
        widthCheck = head[1] < 0 or head[1] >= self.width

        #         Check if hits it self
        biteSelfCheck = False

        if head in self.set and head != self.snake[-1]:
            biteSelfCheck = True

        if heightCheck or widthCheck or biteSelfCheck:
            return -1

        #         We will check for food
        new_food = None
        if self.curr_findex < len(self.food):
            new_food = self.food[self.curr_findex]

        if self.curr_findex < len(self.food) and new_food[0] == head[0] and new_food[1] == head[1]:
            self.curr_findex += 1
        else:
            ele = self.snake.pop()
            del self.set[ele]

        self.snake.appendleft(head)

        self.set[head] = 1

        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)