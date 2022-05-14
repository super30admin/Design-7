type SnakeGame struct {
    m int
    n int
    foodQueue *ll
    snake *ll
}


func Constructor(width int, height int, food [][]int) SnakeGame {
    foodQ := new(ll)
    for _, pos := range food {
        foodQ.addToTail(pos[0],pos[1])
    }
    snake := new(ll)
    snake.addToTail(0,0)
    return SnakeGame{
        m: height,
        n: width,
        foodQueue: foodQ,
        snake: snake,
    }
}


func (this *SnakeGame) Move(direction string) int {
    
    // grab where the head of the snake is
    snakeRow := this.snake.tail.r
    snakeCol := this.snake.tail.c

    // move the snake head to new direction first
    if direction == "U" {
        snakeRow--
    } else if direction == "D" {
        snakeRow++
    } else if direction == "L" {
        snakeCol--
    } else if direction == "R" {
        snakeCol++
    }
    
    // then run checks to validate the move
    
    
    // check if the snake is out of bounds
    if snakeRow < 0 || snakeRow == this.m || snakeCol < 0 || snakeCol == this.n {
        return -1
    }

    // check if snake is hitting itself
    curr := this.snake.head.next
    for curr != nil {
        if curr.r == snakeRow && curr.c == snakeCol {
            return -1
        }
        curr = curr.next
    }
        
    // check if current position is a food position
    if !this.foodQueue.isEmpty() {
        // peek food queue
        foodRow := this.foodQueue.head.r
        foodCol := this.foodQueue.head.c
        
        if snakeRow == foodRow && snakeCol == foodCol {
            this.foodQueue.removeHead()
            this.snake.addToTail(foodRow,foodCol)
            return this.snake.size - 1
        }
    }
    
    // otherwise when a snake moves in a normal case, just add the new snakeRow and snakeCol to ll tail, and rm head node and move head pointer to next node
    this.snake.removeHead()
    this.snake.addToTail(snakeRow, snakeCol)
    return this.snake.size -1
}


/**
 * Your SnakeGame object will be instantiated and called as such:
 * obj := Constructor(width, height, food);
 * param_1 := obj.Move(direction);
 */



type listNode struct {
    r int
    c int
    next *listNode
}

type ll struct {
    head *listNode
    tail *listNode
    size int
}


func (l *ll) addToTail(r, c int) {
    n := &listNode{r:r,c:c}
    if l.head == nil {
        l.head = n
        l.tail = n
        l.size= 1
        return
    }
    l.tail.next = n
    l.tail = n
    l.size++
}

func (l *ll) isEmpty() bool {
    return l.size == 0
}

func (l *ll) removeHead() {
    if l.head == nil {
        return
    }
    if l.head.next == nil {
        l.head = nil
        l.size = 0
        return
    }
    newHead := l.head.next
    l.head.next = nil
    l.head = newHead
    l.size--
}
