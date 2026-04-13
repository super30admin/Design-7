// Time Complexity : O(1)
// Space Complexity : O(N) where n = length of the snake and O(w*h) for visited array
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
/*
We maintain a linked list to keep track of snake's body and a boolean visited array to check if any of the
snake's body is present or that cell is already visited.For every move, depending upon the direction, we increment
or decrement our head's coordinates.We also make sure to check boundaries, if so, we return -1.Then, we check if
we our food index is within the limit,and when we approach food cell, we mark it as visited and snake's body
grows.Similarly, we also move the tail forward.
 */
class SnakeGame {
    boolean[][] visited;
    int w;
    int h;
    int foodIdx;
    int[][] food;
    LinkedList<int[]> snakeBody;

    public SnakeGame(int width, int height, int[][] food) {
        this.w = width;
        this.h = height;
        this.visited = new boolean[h][w];
        this.food = food;
        this.foodIdx = 0;
        this.snakeBody = new LinkedList<>();
        snakeBody.add(new int[]{0, 0});
    }

    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0] , c = head[1];

        if(direction.equals("U")) {
            r--;
        }
        else if(direction.equals("D"))  {
            r++;
        }
        else if(direction.equals("R")) {
            c++;
        }
        else if(direction.equals("L"))  {
            c--;
        }

        if(r < 0 || c < 0 || r == h || c == w || visited[r][c])
            return -1;

        if(foodIdx < food.length) {
            if(food[foodIdx][0] == r && food[foodIdx][1] == c) {
                snakeBody.addFirst(new int[]{r, c});
                visited[r][c] = true;
                foodIdx++;
                return snakeBody.size() - 1;
            }
        }

        snakeBody.addFirst(new int[]{r, c});
        visited[r][c] = true;

        snakeBody.removeLast();
        int[] tail = snakeBody.getLast();
        visited[tail[0]][tail[1]] = false;

        return snakeBody.size() - 1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */