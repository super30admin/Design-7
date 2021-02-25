/*
class SnakeGame:

    def __init__(self, width: int, height: int, food: List[List[int]]):
        self.width = width
        self.height = height
        self.snake = list()
        self.head = [0,0]
        self.snake.append(self.head)
        self.foodlist = collections.deque()
        
        for i in food:
            self.foodlist.append(i)

    def move(self, direction: str) -> int:
        if direction == "U":
            self.head[0] -= 1
        
        if direction == "D":
            self.head[0] += 1
        
        if direction == "L":
            self.head[1] -= 1
        
        if direction == "R":
            self.head[1] += 1
        
        # boundary
        if (self.head[0] < 0 or self.head[0] >= self.height or self.head[1] < 0 or self.head[1] >= self.width):
            return -1
        
        # touches itself
        for i in range(1, len(self.snake)):                 // can use a hashset here as searching will be O(1) and then entire program would be O(1)
            cur = self.snake[i]
            if self.head[0] == cur[0] and self.head[1] == cur[1]:
                return -1
        
        # food 
        if len(self.foodlist) > 0:
            food = self.foodlist[0]
            if food[0] == self.head[0] and food[1] == self.head[1]:
                self.snake.append(self.foodlist.popleft())
                return len(self.snake) - 1
        
        self.snake.pop(0)                   # use linkedlist though because in a normal list popleft and append                                             # from right will cause O(n) operation
        self.snake.append([]+self.head) 
        return len(self.snake) - 1
*/

// Time - O(width*height) it comes from for loop which checks whether snake touches itself and snake can be as long as matrix
// Space - O(width*height) for storing snake and food
// logic - stored snake and food in linkedlist and the head of linkedlist will be tail of snake. Everytime snake eats food I am adding that food
// coordinates to snake's head i.e tail of linkedlist. Everytime a snake moves I am removing from tail i.e head of linkedlist and adding next
// coordinate to head of snake i.e tail of linkedlist. Can also use a queue here but then all snake body coordinates will have to be present in 
// hashset as while checking if snakes touches itself we cant iterate over queue
class SnakeGame {

    LinkedList<int[]> snake;
    LinkedList<int[]> foodList;
    int[] head;
    int width, height;
    public SnakeGame(int width, int height, int[][] food) {
        head = new int[] {0,0};
        snake = new LinkedList<>();
        foodList = new LinkedList<>(Arrays.asList(food));
        snake.add(head);
        this.width = width;
        this.height = height;
    }
    
    public int move(String direction) {
        if (direction.equals("U"))
            head[0]--;
        
        if (direction.equals("D"))
            head[0]++;
        
        if (direction.equals("R"))
            head[1]++;
        
        if (direction.equals("L"))
            head[1]--;
        
        // if snake touchese boundary
        if (head[0] < 0 || head[0] >= height || head[1] < 0 || head[1] >= width)
            return -1;
/*
----
 ^ |
 |_|          even this test case will pass as after move the tail we have is no longer valid and hence we should 
            start looking from index 1
*/
        // touches itself
        for (int i=1; i<snake.size(); i++){
            int[] body = snake.get(i);
            if (head[0] == body[0] && head[1] == body[1])
                return -1;
        }
        
        if (foodList.size() > 0){
            int[] food_index = foodList.get(0);
            if (head[0] == food_index[0] && head[1] == food_index[1]){
                snake.add(foodList.remove());
                return snake.size() - 1;        // -1 because initially we add head which doesnt count                                                           //   towawrds point
            }
                               
        }
        snake.remove();
        snake.add(new int[] {head[0], head[1]});
        return snake.size() - 1;  
    }
}