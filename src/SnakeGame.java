// Time Complexity: O(1)

public class SnakeGame {
        
    LinkedList<int[]> snake;
    int[] head;
    int idx;
    int h;
    int w;
    int[][] foodArr;
    boolean[][] board;
    
    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.board = new boolean[h][w];
        this.foodArr = food;
        this.snake = new LinkedList<>();
        this.head = new int[] {0, 0};
        this.board[0][0] = true;
        this.snake.addLast(this.head);
    }
    
    public int move(String direction) {
        if(direction.equals("l")) head[1]--;
        else if(direction.equals("r")) head[1]++;
        else if(direction.equals("u")) head[0]--;
        else if(direction.equals("d")) head[0]++;
        
        // System.out.println(head[0]+" "+head[1]);
        // System.out.print("Snake: ");
        // for(int[] arr : snake) {
        //     System.out.print(arr[0]+" "+arr[1]+" -> ");
        // }
        // System.out.println();
        // System.out.println("Board: ");
        // for(int i=0; i<h; i++) {
        //     for(int j=0; j<w; j++) {
        //         System.out.print(board[i][j]+" ");
        //     }
        //     System.out.println();
        // }
        
        // out of range
        if(head[0]<0 || head[1]<0 || head[0]==h || head[1]==w)
            return -1;
        if(board[head[0]][head[1]])
            return -1;
        
        // food left
        if(idx < foodArr.length) {
            int[] curFood = foodArr[idx];
            // and food comes
            if(head[0]==curFood[0] && head[1]==curFood[1]) {
                snake.addLast(new int[]{head[0], head[1]});
                board[head[0]][head[1]] = true;
                idx++;
                return snake.size()-1;
            }
        }
        
        // no food left
        snake.addLast(new int[]{head[0], head[1]});
        board[head[0]][head[1]] = true;
        snake.removeFirst();
        int[] curr = snake.get(0);
        board[curr[0]][curr[1]] = false;
        return snake.size()-1;
    }    






//     boolean[][] board;
//     int foodInd;
//     int[][] food;
//     int[] head;
//     LinkedList<int[]> snakeInd;
//     Map<String, int[]> dir;
    
//     public SnakeGame(int width, int height, int[][] indexes) {
//         this.board = new boolean[height][width];
//         this.foodInd = 0;
//         this.food = indexes;
//         this.head = new int[] {0, 0};
//         this.snakeInd = new LinkedList<>();
//         this.snakeInd.addLast(this.head);
//         this.board[head[0]][head[1]] = true;
//         this.dir = new HashMap<>() {{
//             put("r", new int[]{0, 1});
//             put("d", new int[]{1, 0});
//             put("l", new int[]{0, -1});
//             put("u", new int[]{-1, 0});
//         }};
//     }
    
//     public int move(String direction) {
//         int[] d = dir.get(direction);
//         head[0] += d[0];
//         head[1] += d[1];
        
//         // out of range
//         if(head[0]<0 || head[0]==board.length || head[1]<0 || head[1]==board[0].length || board[head[0]][head[1]])
//             return -1;
        
//         // food left
//         if(foodInd < food.length) {
//             int[] curFood = food[foodInd];
//             // and food comes
//             if(head[0]==curFood[0] && head[1]==curFood[1]) {
//                 snakeInd.addLast(new int[]{head[0], head[1]});
//                 board[head[0]][head[1]] = true;
//                 foodInd++;
//                 return snakeInd.size()-1;
//             }
//         }
        
//         // no food left
//         snakeInd.addLast(new int[]{head[0], head[1]});
//         board[head[0]][head[1]] = true;
//         snakeInd.removeFirst();
//         int[] ind = snakeInd.get(0);
//         board[ind[0]][ind[1]] = false;
//         return snakeInd.size()-1;
//     }


  
  
    
    public static void main(String[] args) {
        int width = 3;
        int height = 2;
        int[][] indexes = new int[][] {{1, 2}, {0, 1}};
        // int[][] indexes = new int[][] {{0, 1}, {1, 1}, {1, 0}, {0, 2}};
        SnakeGame sg = new SnakeGame(width, height, indexes);
        String str = "rdrulu";
        // String str = "rdlurrdlu";
        for(int i=0; i<str.length(); i++) {
            System.out.println("Score: "+sg.move(str.charAt(i)+"")+"\n");
        }
    }
    
}
