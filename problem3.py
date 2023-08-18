#Time complexity: O(n^2)
#Space complexity: O(n^2)
#Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


class SnakeGame(object):
    def __init__(self, width, height, food):
        self.width = width
        self.height = height
        self.food = food + [ [-1,-1] ]
        self.foodInx = 0

        self.bodyQueue = deque( [ (0,0) ] ) 
        self.bodySet = { (0,0) }

    def move(self, direction):

        oldHead = self.bodyQueue[-1]
        newHead = ( oldHead[0] + int(direction=='D') - int(direction=='U'),
                    oldHead[1] + int(direction=='R') - int(direction=='L') )
        if not ( 0<=newHead[0]<self.height and 0<=newHead[1]<self.width ):
            return -1
        eatFood = ( newHead == tuple( self.food[ self.foodInx ] ) )
        if eatFood:
            self.foodInx += 1

        if not eatFood:
            oldTail = self.bodyQueue.popleft()
            self.bodySet.remove( oldTail )
        if newHead in self.bodySet:
            return -1
        self.bodyQueue.append( newHead )
        self.bodySet.add( newHead )
        return len(self.bodySet) - 1