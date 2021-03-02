'''
Solution:
1.  The main idea is to maintain a Doubly Linked List or a Double Ended Queue along with a 
    HashSet both of which maintain already traversed cells.
2.  Whenever you move to another cell => Head changed; remove the tail only if the snake 
    doen't eat the food and otherwise increment the score.

Time Complexity:    O(1) for each move operation
Space Complexity:   O(N) where N is total number of food items placed as score and length can't
                    exceed N size

--- Passed all testcases successfully on Leetcode.
'''


from collections import deque

class BoardCell:

    #   Custom Class with custom '=' operator and custom 'hash' function
    
    def __init__(self, row, col):
        self.row = row
        self.col = col
        
    def __eq__(self, other):
        return (self.row == other.row and self.col == other.col)
    
    def __hash__(self):
        return (31 * self.row + self.col)
    
    def __repr__(self):
        return '{} {}'.format(self.row, self.col)


class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        """
        Initialize your data structure here.
        @param width - screen width
        @param height - screen height 
        @param food - A list of food positions
        E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
        """

        #   initializations
        firstCell = BoardCell(0, 0)         #   first cell of the path
        self.dll = deque([firstCell])       #   put it in the DLL
        self.index = 0                      #   initialize the index pointer
        self.width = width                  #   height, width and food
        self.height = height
        self.food = food                    
        self.hashSet = set([firstCell])     #   put first cell in the HashSet
    
    #   self explanatory helper functions for operating on DLL (just for convenience)
    def __getFirst(self) -> 'BoardCell':
        return self.dll[0]
    
    def __addFirst(self, cell: 'BoardCell') -> None:
        self.dll.appendleft(cell)
        
    def __addLast(self, cell: 'BoardCell') -> None:
        self.dll.append(cell)
        
    def __removeFirst(self) -> 'BoardCell':
        return self.dll.popleft()
    
    def __removeLast(self) -> 'BoardCell':
        return self.dll.pop()
    
    def __validCell(self, cell: 'BoardCell') -> bool:
        if ( (cell.row < self.height and cell.col < self.width) and 
             (cell.row >= 0 and cell.col >= 0) and 
            (cell not in self.hashSet) ):
            return True
        
        return False

    def move(self, direction: str) -> int:
        """
        Moves the snake.
        @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
        @return The game's score after the move. Return -1 if game over. 
        Game over when snake crosses the screen boundary or bites its body.
        """

        #   get the head cell and create a new head cell
        head = self.__getFirst()
        newHead = BoardCell(head.row, head.col)
        
        #   get the tail node and note its info (just to avoid collision)
        tail = self.__removeLast()
        self.hashSet.remove(tail)
        tailRow = tail.row
        tailCol = tail.col
        
        #   act according to the given direction
        if (direction == 'U'):  newHead.row -= 1
        if (direction == 'L'):  newHead.col -= 1
        if (direction == 'R'):  newHead.col += 1
        if (direction == 'D'):  newHead.row += 1
            
        #   if not a valid cell (out of bounds or already present in HashSet)
        if not self.__validCell(newHead):
            return -1
        
        #   add the new head to the first node and also to the HashSet
        self.__addFirst(newHead)
        self.hashSet.add(newHead)
        
        #   check it with the food cell and if yes => add back the old tail and increment index
        if (self.index < len(self.food) and 
            newHead.row == self.food[self.index][0] and 
            newHead.col == self.food[self.index][1]):
            
            oldTail = BoardCell(tailRow, tailCol)
            self.__addLast(oldTail)
            self.hashSet.add(oldTail)
            self.index += 1
        
        #   return length of our DLL - 1
        return ( len(self.dll) - 1 )


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)