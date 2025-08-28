'''
353 Design Snake Game
https://leetcode.com/problems/design-snake-game/description/

Design a Snake game that is played on a device with screen size height x width. Play the game online if you are not familiar with the game.

The snake is initially positioned at the top left corner (0, 0) with a length of 1 unit.

You are given an array food where food[i] = (ri, ci) is the row and column position of a piece of food that the snake can eat. When a snake eats a piece of food, its length and the game's score both increase by 1.

Each piece of food appears one by one on the screen, meaning the second piece of food will not appear until the snake eats the first piece of food.

When a piece of food appears on the screen, it is guaranteed that it will not appear on a block occupied by the snake.

The game is over if the snake goes out of bounds (hits a wall) or if its head occupies a space that its body occupies after moving (i.e. a snake of length 4 cannot run into itself).

Implement the SnakeGame class:
    SnakeGame(int width, int height, int[][] food) Initializes the object with a screen of size height x width and the positions of the food.
    int move(String direction) Returns the score of the game after applying one direction move by the snake. If the game is over, return -1.

Example 1:
Input:
["SnakeGame", "move", "move", "move", "move", "move", "move"]
[[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
Output:
[null, 0, 0, 1, 1, 2, -1]
Explanation:
SnakeGame snakeGame = new SnakeGame(3, 2, [[1, 2], [0, 1]]);
snakeGame.move("R"); // return 0
snakeGame.move("D"); // return 0
snakeGame.move("R"); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
snakeGame.move("U"); // return 1
snakeGame.move("L"); // return 2, snake eats the second food. No more food appears.
snakeGame.move("U"); // return -1, game over because snake collides with border

Constraints:
1 <= width, height <= 10^4
1 <= food.length <= 50
food[i].length == 2
0 <= ri < height
0 <= ci < width
direction.length == 1
direction is 'U', 'D', 'L', or 'R'.
At most 10^4 calls will be made to move.

Solution:
1. Use a deque and a matrix  to design the game

Key idea: Track the snake's body using a deque and mark visited cells in a visited matrix.

Init:
Enque the snake's initial position in the queue. Mark the cell as visited.

Move:
Step 1: On each move, go to the new cell
Step 2: Check if there is food at the new cell:
    a) Food absent: Unvisit the cell at the tail, pop the cell (occupied by the tail) from the queue
    b) Food present: Don't remove the tail.
Step 3: Check for wall or body collisions
Step 4: Push the cell (occupied by the new head) to the queue
Step 5: Return the score (len of queue - 1)

https://www.youtube.com/watch?v=HN7Vy27zDZY&t=1920s
Time: O(1) (move()), Space: O(N + W*H), N = max snake length, W = width of visited matrix, H = height of visited matrix
'''
from typing import List
from collections import deque

# Solution given in lecture
# class SnakeGame:
#     def __init__(self, width, height, food):
#         self.h = height
#         self.w = width
#         self.visited = [[False]*self.w for _ in range(self.h)]
#         self.food = food
#         self.idx = 0
#         self.snakeBody = deque()
#         self.snakeBody.appendleft([0, 0])

#     def move(self, direction):
#         head = self.snakeBody[0]
#         r, c = head[0], head[1]
#         if direction == "L":
#             c -= 1
#         elif direction == "R":
#             c += 1
#         elif direction == "D":
#             r += 1
#         elif direction == "U":
#             r -= 1

#         if r < 0 or c < 0 or r == self.h or c == self.w or self.visited[r][c]:
#             return -1

#         if self.idx < len(self.food):
#             if self.food[self.idx][0] == r and self.food[self.idx][1] == c:
#                 self.snakeBody.appendleft([r, c])
#                 self.visited[r][c] = True
#                 self.idx += 1
#                 return len(self.snakeBody) - 1

#         self.snakeBody.appendleft([r, c])
#         self.visited[r][c] = True
#         self.snakeBody.pop()
#         tail = self.snakeBody[-1]
#         self.visited[tail[0]][tail[1]] = False
#         return len(self.snakeBody) - 1

# My solution (similar to lecture solution)
class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.h = height
        self.w = width
        self.food = food
        #self.snakeHead = [0, 0] # starting position (row,col) of snake head
        self.snakeBody = deque() # q.head = snake tail, q.tail = snake head
        self.snakeBody.append([0,0]) # body and head at the same posn
        self.visited = [[False]*self.w for _ in range(self.h)]
        self.visited[0][0] = True
        self.score = len(self.snakeBody) - 1 # score = len(snakeBody) - 1
        return

    def __str__(self):
        tail = " (Snake Tail) "
        body = ""
        if self.snakeBody:
            for cell in self.snakeBody:
                body += " " + str(cell) + " " + "-"
        result = tail + body[0:-1] + " (Snake Head)"
        result += "\n" + "food: "
        if self.food:
            result += str(self.food)
        else:
            result += str(None)
        return result

    def move(self, direction: str) -> int:
        row, col = self.snakeBody[-1]
        if direction == "U":
            row -= 1
        elif direction == "D":
            row += 1
        elif direction == "L":
            col -= 1
        elif direction == "R":
            col += 1

        # Process tail
        if self.food and row == self.food[0][0] and col == self.food[0][1]:
            # Case 1: snake eats food
            # don't remove tail
            # remove the current food
            self.food.pop(0)
            pass
        else: # Case 2: snake does not eat food
            # remove tail (pop left of queue)
            tail = self.snakeBody.popleft()
            # mark the cell as unvisited
            self.visited[tail[0]][tail[1]]=False
            # remove the food from the list of available foods

        # Check for wall or body collisions
        # Wall: Check if either head or tail is out of bounds
        # Body: Check if head and tail are at the same cell
        if not 0<=row<=self.h - 1 or \
           not 0<=col<=self.w - 1 or \
           self.visited[row][col] == True:
            self.score = -1
            self.snakeBody.clear()
            return self.score

        # Process head (add new head)
        new_head = [row, col]
        self.snakeBody.append(new_head)
        self.visited[row][col]=True

        self.score = len(self.snakeBody) - 1
        return self.score

def run_SnakeGame():
    tests = [ (["SnakeGame", "move", "move", "move", "move", "move", "move"],
               [[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]],
               [None, 0, 0, 1, 1, 2, -1]),
               (["SnakeGame","move","move","move","move","move","move","move","move","move","move","move","move"],
                [[3,3,[[2,0],[0,0],[0,2],[2,2]]],["D"],["D"],["R"],["U"],["U"],["L"],["D"],["R"],["R"],["U"],["L"],["D"]],
                [None,0,1,1,1,1,2,2,2,2,3,3,3]),
    ]
    for test in tests:
        operations, nums, ans = test[0], test[1], test[2]
        result = []
        for operation, num in zip(operations, nums):
            if operation == "SnakeGame":
                game = SnakeGame(num[0], num[1], num[2])
                x = None
                print(f"\n --- Game Starts --- ")
            elif operation == "move":
                x=game.move(num[0])
            print(f"\n{operation}({num}): {x}")
            print(f"{game}\n")
            result.append(x)

        print(f"\nOperations = {operations}")
        print(f"nums = {nums}")
        print(f"result = {result}")
        success = (ans==result)
        print(f"Pass: {success}")
        if not success:
            print("Failed")
            return

run_SnakeGame()