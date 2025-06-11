#time complexity :O(1)
#space complexity:O(n) where n is the number of moves

class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.w=width
        self.h=height
        self.food=[]
        self.body=set()
        for i in food:
            self.food.append(i)
        self.score=0
        self.food_index=0
        self.body_coordinates=[(0,0)]
        self.body.add((0,0))



    def isValid(self,head):
        if(head[0]<0 or head[0]>=self.h or head[1]<0 or head[1]>=self.w):
            return False
        if(self.food_index<len(self.food) and head[0]==self.food[self.food_index][0] and head[1]==self.food[self.food_index][1]):
            self.score+=1
            self.body_coordinates.append(head)
            self.body.add(head)
            self.food_index+=1
            return True
        else:
            self.body.remove(self.body_coordinates[0])
            del(self.body_coordinates[0])
            if(head in self.body):
                return False
            else:
                self.body_coordinates.append(head)
                self.body.add(head)
                return True


    def move(self, direction: str) -> int:
        head=self.body_coordinates[-1]
        if(direction=='U'):
            curr_head=(head[0]-1,head[1])
        elif(direction=='D'):
            curr_head=(head[0]+1,head[1])
        elif(direction=='L'):
            curr_head=(head[0],head[1]-1)
        elif(direction=='R'):
            curr_head=(head[0],head[1]+1)
        if(not self.isValid(curr_head)):
            return -1
        else:
            return self.score
