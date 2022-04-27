// Time Complexity : O(1)
// Space Complexity : O(N) // length of food
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// Create a queue to store the snake and hashset to have coordinates of all parts of its body
// Now When snake eats queue grows, if it doesn't eat the queue will not grow 
// We will add and remove from the queue. We will simultaneously update the hashset
// If snakes next move  hits wall or its body in hashset, we will return -1
// Otherwise we know the size of the snake from the queue and return it.
class Pair{
    int x, y;
     Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
}
class SnakeGame {


HashSet<Integer> hs;
Pair head;
int[][] food;
int m, n;
Queue<Pair> q;
int foodIndex;
public SnakeGame(int width, int height, int[][] food) {
    head = new Pair(0,0);
    hs = new HashSet<>();
    q = new LinkedList<>();
    hs.add(0);
    q.add(new Pair(0,0));
    this.food = food;
    m = height;
    n = width;
    foodIndex = 0;
}

public int move(String direction) {
    Pair next;
    int a  = head.x;
    int b = head.y;
    // calculate next coordinate
    if(direction.equals("L")){
        b--;
    }
    if(direction.equals("R")){
        b++;
    }
    if(direction.equals("U")){
        a--;
    }
    if(direction.equals("D")){
        a++;
    }
    next = new Pair(a, b);
    head.x = a;
    head.y = b;
    q.add(next);
    //System.out.println(a+" "+b);
    //boundary check
    if(a < 0 || a == m || b < 0 || b == n){      
        return -1;
    } 
    // food check    
    if(foodIndex < food.length && food[foodIndex][0] == a && food[foodIndex][1] == b){
        foodIndex++;
        hs.add(a*n+b);
        return q.size() - 1; 
    }
    else{
        Pair node = q.poll();
        hs.remove(node.x * n + node.y);
    }
    // body check
    int code = a*n+b;
    if(hs.contains(code)){
        return -1;
    } 
    hs.add(code);
    return q.size() - 1;
}
}

/**
* Your SnakeGame object will be instantiated and called as such:
* SnakeGame obj = new SnakeGame(width, height, food);
* int param_1 = obj.move(direction);
*/