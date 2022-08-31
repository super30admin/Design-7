# Time complexity: O(n) where n is length of snake- to check if snake head is hitting with body
# Space complexity: O(n)-snakes length for snake body list
# Approach: create an array of list for snake body to check if the new head is already in snakebody.
# whenever there is food that matches with current snake head, add the indices to snake body.
# if no food matching with indices, add this to snake body and remove the tail, which is at index 0 of snake body
# Check for edge cases to return -1 and check for new head in snake body to return -1


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.snake = [(0,0)] 
        self.width = width
        self.height = height
        self.curr_row = 0
        self.curr_col = 0
        self.food = food
        self.score = 0

    def move(self, direction: str) -> int:
        if direction == "R":
            self.curr_col += 1 

        elif direction == "L":
            self.curr_col -= 1

        elif direction == "U":
            self.curr_row -= 1 

        elif direction == "D":
            self.curr_row += 1

        if  self.curr_col < 0 or  self.curr_col >= self.width :
            return -1
        elif self.curr_row < 0 or self.curr_row >= self.height:
            return -1
        elif (self.curr_row, self.curr_col) in self.snake and (self.curr_row, self.curr_col) != self.snake[0] :
            # print("sanke himself")
            return -1 
        elif self.food and [self.curr_row, self.curr_col ] == self.food[0]:
            # print("self.snake", self.snake  )
            self.score += 1
            self.food.pop(0)
            self.snake.append( ( self.curr_row, self.curr_col   ) )
            return self.score
        else:
            # print("self.snake", self.snake  )
            self.snake.append( ( self.curr_row, self.curr_col   ) )
            self.snake.pop(0)
            return self.score 
