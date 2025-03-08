class SnakeGame {
    int width, height;
    boolean[][] visited;
    int[][] food;
    int[] snakehead;
    int foodindex=0;
    LinkedList<int[]> q = new LinkedList<>();
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        visited = new boolean[height][width];
        this.food = food;
        snakehead = new int[] {0,0};
        q.addFirst(new int[] {0, 0});
    }
    
    public int move(String direction) {
        if (direction.equals("R")) {
            snakehead[1]++;
        } else if (direction.equals("D")) {
            snakehead[0]++;
        } else if (direction.equals("U")) {
            snakehead[0]--;
        } else if (direction.equals("L")) {
            snakehead[1]--;
        }
        if((snakehead[0] < 0 || snakehead[0] == this.height) || (snakehead[1] < 0 || snakehead[1] == this.width)) {
            System.out.println("here1");
            return -1;
        }
        if(visited[snakehead[0]][snakehead[1]] == true) {
            System.out.println("here2");
            return -1;
        }
        if (foodindex < food.length) {
            if (snakehead[0] == food[foodindex][0] && snakehead[1] == food[foodindex][1]) {
                foodindex++;
                visited[snakehead[0]][snakehead[1]]=true;
                int[] head = new int[]{snakehead[0],snakehead[1]};
                q.addFirst(head);
                return q.size()-1;
            }
        }
        visited[snakehead[0]][snakehead[1]]=true;
        System.out.println(snakehead[0]);
        System.out.println(snakehead[1]);
        int[] head = new int[]{snakehead[0],snakehead[1]};
        q.addFirst(head);
        q.removeLast();
        int[] tail = q.getLast();
        System.out.println(tail[0]);
        System.out.println(tail[1]);
        visited[tail[0]][tail[1]] = false;
        return q.size()-1;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
