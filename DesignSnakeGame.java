// Time Complexity : O(1) for move
// Space Complexity : O(height * width)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SnakeGame {
    //visited matrix
    boolean[][] mat;
    //To keep track of which food we are on
    int cnt = 0;
    int[][] food;
    //height and width
    int r;
    int c;
    public SnakeGame(int width, int height, int[][] food) {
        r = height;
        c = width;
        mat = new boolean[r][c];
        this.food = food;
        //Head of the snake starting from 0,0 in the beginning of the gamme
        Node node = new Node(0,0);
        mat[0][0] = true;
        head = node;
        tail = node;

    }

    int size = 0;
    //Using linked list to keep track of the snake
    class Node{
        Node next;
        int r;
        int c;
        private Node(int r, int c){
            this.r = r;
            this.c = c;
        }
    }

    Node head;
    Node tail;


    public int move(String direction) {

        //Get the head and move the head in the direction given
        Node node = new Node(head.r,head.c);
        if(direction.equals("R")){
            node.c++;
        } else if(direction.equals("L")){
            node.c--;
        } else if(direction.equals("U")){
            node.r--;
        } else{
            node.r++;
        }

        //Check if head is at the location of the food
        if(cnt < food.length && node.r == food[cnt][0] && node.c == food[cnt][1]){


            cnt++;
            size++;
            head.next = node;
            head = head.next;
            mat[head.r][head.c] = true;
            return size;
            //Else check if head is out of bounds or if it is already visited
        }else{
            mat[tail.r][tail.c] = false;

            if(node.r < 0 || node.r >= r || node.c <0 || node.c >= c || mat[node.r][node.c]){
                return -1;
            }
            else{

                head.next = node;
                head = head.next;
                mat[head.r][head.c] = true;
                tail = tail.next;
                return size;
            }
        } 
    }
}
