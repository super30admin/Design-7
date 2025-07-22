import java.util.*;

class LFUCache {
    private final int capacity;
    private int minFreq;
    private final Map<Integer, Integer> keyToValue;
    private final Map<Integer, Integer> keyToFreq;
    private final Map<Integer, LinkedHashSet<Integer>> freqToKeys;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyToValue = new HashMap<>();
        this.keyToFreq = new HashMap<>();
        this.freqToKeys = new HashMap<>();
    }

    public int get(int key) {
        if (!keyToValue.containsKey(key)) return -1;
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);
        freqToKeys.get(freq).remove(key);

        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            if (minFreq == freq) minFreq++;
        }

        freqToKeys.computeIfAbsent(freq + 1, k -> new LinkedHashSet<>()).add(key);
        return keyToValue.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyToValue.containsKey(key)) {
            keyToValue.put(key, value);
            get(key); // update frequency
            return;
        }

        if (keyToValue.size() >= capacity) {
            int evict = freqToKeys.get(minFreq).iterator().next();
            freqToKeys.get(minFreq).remove(evict);
            if (freqToKeys.get(minFreq).isEmpty()) {
                freqToKeys.remove(minFreq);
            }
            keyToValue.remove(evict);
            keyToFreq.remove(evict);
        }

        keyToValue.put(key, value);
        keyToFreq.put(key, 1);
        freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }
}
//
import java.util.*;

class SnakeGame {
    private int width, height, score;
    private int[][] food;
    private int foodIndex;
    private Deque<int[]> snake;
    private Set<String> bodySet;

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        this.foodIndex = 0;
        this.score = 0;
        this.snake = new LinkedList<>();
        this.snake.offerFirst(new int[] { 0, 0 });
        this.bodySet = new HashSet<>();
        this.bodySet.add("0,0");
    }

    public int move(String direction) {
        int[] head = snake.peekFirst();
        int x = head[0], y = head[1];
        switch (direction) {
            case "U":
                x--;
                break;
            case "D":
                x++;
                break;
            case "L":
                y--;
                break;
            case "R":
                y++;
                break;
        }
        int[] newHead = new int[] { x, y };
        String newHeadStr = x + "," + y;

        // Check wall collision
        if (x < 0 || x >= height || y < 0 || y >= width)
            return -1;

        // Check self collision (remove tail first if not eating food)
        int[] tail = snake.peekLast();
        if (foodIndex < food.length && x == food[foodIndex][0] && y == food[foodIndex][1]) {
            // Eat food
            foodIndex++;
            score++;
        } else {
            // Move tail
            snake.pollLast();
            bodySet.remove(tail[0] + "," + tail[1]);
        }

        // Check self collision after moving tail
        if (bodySet.contains(newHeadStr))
            return -1;

        // Add new head
        snake.offerFirst(newHead);
        bodySet.add(newHeadStr);

        return score;
    }
}