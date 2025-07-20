//time complexity- O(1 ) for very move
//space complexity- O(n+v) where it length of snake and the visited matrix
import java.util.*;
public class SnakeGame {
    private int[][] food;
    private LinkedList<int[]>  snake;
    int i;
    private int[] head;
    private boolean [][] visited;
    int h, w;
    public SnakeGame(int w, int h, int[][] food) {
        this.food = food;
        this.w = w;
        this.h = h;
        this.head = new int[]{0,0};
        this.visited = new boolean[h][w];
        this.snake = new LinkedList<>();
        this.snake.addFirst(head);
    }
    public int move(String directions) {
        int[] currHead = snake.getFirst();
        int r = currHead[0];
        int c = currHead[1];
        if(directions.equals("L")) {
            c -= 1;
        } else if(directions.equals("R")) {
           c += 1;
        } else if(directions.equals("U")) {
            r -= 1;
        } else {
            r += 1;
        }
        if(r < 0 ||  r == h || c< 0 || c == w) {
            return -1;
        }

        int[] tail = snake.removeLast();
        visited[tail[0]][tail[1]] = false;
        if(visited[r][c]) {
            return -1;
        }

        int[] newhead = new int[] {r, c};
        snake.addFirst(newhead);
        visited[r][c] = true;

        if(i < food.length && r == food[i][0] && c == food[i][1]) {
            i++;
            snake.addLast(tail);
            visited[tail[0]][tail[1]] = true;
        }
        return snake.size()-1;
    }

    public static void main(String[] args) {
        int[][] food = {{1,2}, {0,1}};
        SnakeGame snk = new SnakeGame(3,2,food );
        System.out.println("Scores");
        System.out.println(snk.move("R"));
        System.out.println(snk.move("D"));
        System.out.println(snk.move("R"));
        System.out.println(snk.move("U"));
        System.out.println(snk.move("L"));
        System.out.println(snk.move("U"));

    }
}
