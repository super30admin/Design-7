class SnakeGame {
    Integer row;
    Integer col;
    Integer score;
    Deque<Pair<Integer, Integer>> snake;
    Queue<Pair<Integer, Integer>> foodLocation;
    Set<Pair<Integer, Integer>> snakeLocation;

    private static final Map<String, Pair<Integer, Integer>> directions = new HashMap<>() {
        {
            put("U", new Pair(-1, 0));
            put("D", new Pair(1, 0));
            put("L", new Pair(0, -1));
            put("R", new Pair(0, 1));
        }
    };

    public SnakeGame(int width, int height, int[][] food) {
        row = height;
        col = width;
        score = 0;
        snake = new LinkedList<>();
        snakeLocation = new HashSet<>();
        foodLocation = new LinkedList<>();
        for (int[] f : food) {
            foodLocation.offer(new Pair(f[0], f[1]));
        }
        snake.offer(new Pair(0, 0));
        snakeLocation.add(new Pair(0, 0));
    }

    public int move(String direction) {

        Pair<Integer, Integer> food = foodLocation.peek();
        Pair<Integer, Integer> currentHead = snake.peekLast();
        Pair<Integer, Integer> dir = directions.get(direction);
        Pair<Integer, Integer> newHead = new Pair(currentHead.getKey() + dir.getKey(),
                currentHead.getValue() + dir.getValue());

        if (newHead.equals(food)) {
            snake.offerLast(newHead);
            snakeLocation.add(newHead);
            foodLocation.poll();
            return ++score;
        } else {
            Pair<Integer, Integer> currentTail = snake.pollFirst();
            snakeLocation.remove(currentTail);

            if ((snakeLocation.contains(newHead)) ||
                    newHead.getKey() < 0 || newHead.getKey() >= row || newHead.getValue() < 0
                    || newHead.getValue() >= col) {
                return -1;
            }

            snake.offerLast(newHead);
            snakeLocation.add(newHead);
            return score;
        }
    }
}