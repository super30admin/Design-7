# // Time Complexity : O(HW) where W and H are width and height respectively
# // Space Complexity : O(1)
# // Did this code successfully run on Leetcode : Yes
# // Any problem you faced while coding this : No
#
#
# // Your code here along with comments explaining your approach

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
        self.width = width
        self.height = height
        self.food = food
        self.bitecheck = set()  # Hashset

        self.bitecheck.add((0, 0))
        self.index = 0
        self.dirs = {"D": [1, 0],
                     "U": [-1, 0],
                     "R": [0, 1],
                     "L": [0, -1]}

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """
        # Check for new head
        curr_head = self.snake[-1]
        update_head = [0, 0]
        update_head[0] = curr_head[0] + self.dirs[direction][0]
        update_head[1] = curr_head[1] + self.dirs[direction][1]

        row, col = update_head

        # if the snake hits the boundries or it touches itself, return -1
        if row < 0 or row >= self.height or col < 0 or col >= self.width or (
                (row, col) in self.bitecheck and [row, col] != self.snake[0]):
            return -1

        elif self.index < len(self.food) and self.food[self.index] == update_head:
            self.index += 1
        else:
            curr_tail = self.snake.popleft()
            r, c = curr_tail
            self.bitecheck.remove((r, c))

        self.bitecheck.add((row, col))
        self.snake.append(update_head)
        return len(self.snake) - 1

# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)


