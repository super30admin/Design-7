class SnakeGame:

    def __init__(self, width, height, food):
        '''
        Time complexity - O(1)
        Space complexity - O(n)
        '''
        # Initialize the game attributes
        self.width = width
        self.height = height
        self.food = food
        # Initialize the snake's path with the head at [0, 0]
        self.path = [[0, 0]]
        self.score = 0  # Initialize the initial score
        self.direction_map = {'D': [1, 0],
                              'U': [-1, 0], 'L': [0, -1], 'R': [0, 1]}

    def is_valid_move(self, a, b):
        # Check if the move is valid within the game board
        return a >= 0 and a < self.height and b >= 0 and b < self.width \
            and (self.path[0][0] == a and self.path[0][1] == b or [a, b] not in self.path)

    def move(self, direction):
        # Get the direction's movement mapping
        map_ = self.direction_map[direction]
        head_pos = self.path[-1]  # Get the current head position of the snake
        i, j = head_pos[0] + map_[0], head_pos[1] + \
            map_[1]  # Calculate the new head position

        # Check if the new head position is valid
        if self.is_valid_move(i, j):
            # Check if there is food at the new position
            food = [] if not self.food else self.food[0]
            if food and food[0] == i and food[1] == j:
                self.score += 1  # Increase score and remove the eaten food
                self.food.pop(0)
            else:
                tail = self.path.pop(0)  # Remove the tail if no food was eaten
            # Append the new head position to the path
            self.path.append([i, j])
        else:
            return -1  # Return -1 if the move is invalid

        return self.score  # Return the updated score
