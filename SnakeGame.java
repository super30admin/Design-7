import java.util.HashMap;
import java.util.Map;

class SnakeGame {
    //head of the linkedList is the tail of the snake
    class ListNode{
        int row;
        int col;
        ListNode next;
        ListNode(int row, int col){
            this.row = row;
            this.col = col;
            this.next= null;
        }
    }
    ListNode head;
    ListNode tail;
    int height;//row
    int width;//col
    int[][] food;
    int size;
    int foodCtr = 0;
    Map<String, int[]> dirs;
    boolean eaten;
    public SnakeGame(int width, int height, int[][] food) {
        eaten = false;
        this.food = food;
        head = new ListNode(0,0);
        size = 0;
        dirs = new HashMap<>();
        tail = head;
        this.height = height;
        this.width = width;
        dirs.put("U", new int[]{-1,0});
        dirs.put("D", new int[]{1,0});
        dirs.put("L", new int[]{0,-1});
        dirs.put("R", new int[]{0,1});
    }

    public int move(String direction) {

        int[] dir = dirs.get(direction);
        int nr = dir[0]+tail.row;
        int nc = dir[1]+tail.col;

        if(nr < 0 || nr >= height || nc < 0 || nc >= width){
            return -1;
        }
        tail.next = new ListNode(nr, nc);
        tail = tail.next;
        if(eaten){
            eaten = false;
        }else{
            head = head.next;
        }

        if(foodCtr < food.length && nr ==  food[foodCtr][0] && nc == food[foodCtr][1])//found food
        {
            size++;
            foodCtr++;
            eaten = true;
        }

        if(contains(nr, nc)){
            return -1;
        }
        return size;
    }

    private boolean contains(int r, int c){
        ListNode curr = head;
        while(curr != tail){
            if(curr.row == r && curr.col == c) return true;
            curr = curr.next;
        }

        return false;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */