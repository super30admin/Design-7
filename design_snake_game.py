
class SnakeGame:

    ## T.C = O(n)
    ## S.C = O(n)
    
    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.food = food
        self.head = [0,0]
        self.body = []
        self.body.append(list(self.head))

    def move(self, direction: str) -> int:
        

        if direction == 'R':
            self.head[1] += 1
        elif direction == 'L':
            self.head[1] -= 1
        elif direction == 'U':
            self.head[0] -= 1
        elif direction == 'D':
            self.head[0] += 1
        
        #print(self.body , self.head)
        if self.head[0] < 0 or self.head[0] == self.height or self.head[1] < 0 or self.head[1] == self.width:
            return -1
        
        for i in range(1, len(self.body)):
            if self.body[i][0] == self.head[0] and self.body[i][1] == self.head[1]:
                return -1

        if len(self.food) > 0:
            fi = self.food[0]
            if self.head == fi:
                self.body.append(list(self.head))
                #print('ate food: ', self.body)
                self.food.pop(0)
                return len(self.body) - 1
        
        self.body.append(list(self.head))
        self.body.pop(0)
        return len(self.body) - 1

        


# Your SnakeGame object will be instantiated and called as such:
# obj = SnakeGame(width, height, food)
# param_1 = obj.move(direction)