import java.util.LinkedList;
import java.util.Scanner;
public class SnakeGame {

        // LinkedList - Time O(1)

        // global
        LinkedList<int[]> snake;
        int w;    int h;   int i;
        int[][] food;
        int[] head;
        boolean[][] bodySansLast;


        public SnakeGame(int width, int height, int[][] food) {

            // linked list consists of snake's head at its tail and snake's tail at its head
            this.snake = new LinkedList<>();
            this.head = new int[]{0,0};
            this.snake.addLast(this.head);

            // inputs
            this.w = width;
            this.h = height;
            this.food = food;

            // boolean of snake body positions except tail will be true
            this.bodySansLast = new boolean[h][w];


        }


        public int move(String direction) {

            //System.out.println(this.head[0]);
            //System.out.println(this.head[1]);

            // move
            switch (direction) {
                case "L":
                    this.head[1]--;
                    break;
                case "R":
                    this.head[1]++;
                    break;
                case "D":
                    this.head[0]++;
                    break;
                default:
                    this.head[0]--;
                    break;
            }

            // check if head in bounds
            if(head[0] < 0 || head[0] == h || head[1] < 0 || head[1] == w) {
                return -1;
            }

            // check if snake is hitting its body
            // hitting tail does not harm and tail boolean is false as other default positions
            if(bodySansLast[head[0]][head[1]]) {
                return -1;
            }

            // check if food is found
            if(i < food.length) {

                // if found food appeared recently
                int[] foodPiece = food[i];
                if(foodPiece[0] == head[0] && foodPiece[1] == head[1]) {

                    // eat and grow
                    this.snake.addLast(new int[]{head[0], head[1]});

                    // add boolean of new head of snake's body true
                    bodySansLast[head[0]][head[1]] = true;

                    // make appear next food piece appear for further search
                    i++;

                    //score of game is one less than snake size as snake has size 1 at the start of game
                    return this.snake.size()-1;
                }
            }

            // normal move

            // add new head of snake at tail end of linked list and make its boolean true
            this.snake.addLast(new int[]{head[0], head[1]});
            bodySansLast[head[0]][head[1]] = true;

            // remove old tail of snake which at head end of linked list, thus size of snake is balanced
            this.snake.removeFirst();

            // make new tail of snake which is at head end of linked list now and make its boolean false
            int[] newTail = this.snake.get(0);
            bodySansLast[newTail[0]][newTail[1]] = false;

            //score of game is one less than snake size as snake has size 1 at the start of game
            return this.snake.size()-1;
        }

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("width: ");
            int w = scanner.nextInt();

            System.out.println("height: ");
            int h = scanner.nextInt();

            System.out.println("Number of food pieces on game: ");
            int f = scanner.nextInt();

            int[][] food = new int[f][2];

            for(int i = 0; i < f; i++) {

                System.out.println("food piece row and column: ");
                int fr = scanner.nextInt();
                int fc = scanner.nextInt();

                food[i][0] = fr;
                food[i][1] = fc;
            }

            SnakeGame sg = new SnakeGame(w, h, food);


            System.out.println("scores: ");
            System.out.println(sg.move("R"));
            System.out.println(sg.move("D"));
            System.out.println(sg.move("R"));
            System.out.println(sg.move("U"));
            System.out.println(sg.move("L"));
            System.out.println(sg.move("U"));
        }

}

/*
TIME COMPLEXITY = O(1)
SPACE COMPLEXITY = O(w*h)
*/

/*
["R"], ["D"], ["R"], ["U"], ["L"], ["U"

["SnakeGame", "move", "move", "move", "move", "move", "move"]
[[3, 2, [[1, 2], [0, 1]]], ["R"], ["D"], ["R"], ["U"], ["L"], ["U"]]
 */
/*
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */