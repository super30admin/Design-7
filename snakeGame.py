class ListNode:
    def __init__(self, coords):
        self.coords = coords
        self.next = None

class LinkedList:
    def __init__(self):
        self.head = self.tail = ListNode([0,0]) 

class SnakeGame:
    def __init__(self, width: int, height: int, foods):
        self.m = height
        self.n = width
        self.foods = foods
        self.idx = 0
        self.score = 0
        self.visited = set()
        self.snake = LinkedList()
        self.visited.add((0,0))  

    """
    @param direction: the direction of the move
    @return: the score after the move
    """
    def move(self, direction: str) -> int:
        curr_head = self.snake.head.coords[:]

        if direction == 'r':
            curr_head[1] += 1
        elif direction == 'd':
            curr_head[0] += 1
        elif direction == 'l':
            curr_head[1] -= 1
        else:  # 'u'
            curr_head[0] -= 1

        # out-of-bounds
        if curr_head[0] < 0 or curr_head[0] >= self.m or curr_head[1] < 0 or curr_head[1] >= self.n:
            return -1

        # check if eating food
        is_food = self.idx < len(self.foods) and curr_head == self.foods[self.idx]

        if not is_food:
            old_tail = self.snake.tail
            self.snake.tail = self.snake.tail.next
            self.visited.remove(tuple(old_tail.coords))

        # self-collision
        if tuple(curr_head) in self.visited:
            return -1

        new_node = ListNode(curr_head)
        self.snake.head.next = new_node
        self.snake.head = new_node
        self.visited.add(tuple(curr_head))

        if is_food:
            self.score += 1
            self.idx += 1

        return self.score

""" SG = SnakeGame(3, 3, [[0,1], [0,2]])  
print(SG.visited)
print(SG.move('r'))  # eat [0,1] -> score 1
print(SG.visited)
print(SG.move('r'))  # eat [0,2] -> score 2
print(SG.visited)
print(SG.move('d'))  # move down, not eating -> score 2
print(SG.visited)
print(SG.move('l'))  # move left, not eating -> score 2
print(SG.visited)
print(SG.move('u'))  # move up into tail cell -> should be allowed!
print(SG.visited) """

