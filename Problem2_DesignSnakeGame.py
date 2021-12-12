# Time Complexity: O(n), where n - length of snake
# Space Complexity: O(n)

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """

        self.width = width
        self.height = height
        self.food = food
        self.snake = list()
        self.snakehead = [0, 0]
        self.snake.append(self.snakehead)


    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
        @return The game's score after the move. Return -1 if game over.
        Game over when snake crosses the screen boundary or bites its body.
        """

        if direction == 'U':
            self.snakehead[0] -= 1
        if direction == 'D':
            self.snakehead[0] += 1
        if direction == 'L':
            self.snakehead[1] -= 1
        if direction == 'R':
            self.snakehead[1] += 1

        # When snake hits the wall
        if self.snakehead[0] < 0 or self.snakehead[0] == self.height or self.snakehead[1] < 0 or self.snakehead[
            1] == self.width:
            return -1

        # When the snake runs into itself
        for i in range(1, len(self.snake)):
            if self.snake[i] == self.snakehead:
                return -1

        # When snake encounters the food, add the head
        if self.food:
            if self.food[0] == self.snakehead:
                self.snake.append([self.snakehead[0], self.snakehead[1]])
                self.food.pop(0)
                return len(self.snake) - 1

        # When snake doesn't encounter the food, add head and remove tail
        self.snake.pop(0)
        self.snake.append([self.snakehead[0], self.snakehead[1]])
        return len(self.snake) - 1
