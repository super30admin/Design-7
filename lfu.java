// Time Complexity: get → O(n) (PriorityQueue.remove(node) is O(n) + reinsert O(log n));
// put (update) → O(n); put (insert/evict) → O(log n). Overall worst-case O(n).
// Space Complexity: O(C) — stores up to capacity nodes in both the map and the heap.

// Maintain a HashMap<key, Node> and a min-heap ordered by (freq asc, timestamp asc) to achieve LFU with LRU tie-break.
// On get/put-hit: remove the node from heap, increment its freq, refresh timestamp, reinsert; return/update value.
// On put-miss: if at capacity, evict pq.poll() (least freq, oldest); create node with freq=1 and current time, add to map and heap.

class LFUCache {
    
    int capacity;
    int time; 
    HashMap<Integer, Node> map; 
    PriorityQueue<Node> pq; 

    private class Node {
        int key;
        int value; 
        int freq;
        int timestamp;

        public Node(int key, int value, int freq, int timestamp) {
            this.key = key;
            this.value = value;
            this.freq = freq;
            this.timestamp = timestamp;
        }
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.time = 0;
        this.map = new HashMap<>();
        this.pq = new PriorityQueue<>((a, b) -> {
            if (a.freq != b.freq) return a.freq - b.freq;
            return a.timestamp - b.timestamp;
        });
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;

        Node node = map.get(key);

        pq.remove(node);
        node.freq++;
        node.timestamp = time++;

        pq.add(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (map.containsKey(key)) {
            Node node = map.get(key);
            pq.remove(node);
            node.value = value;
            node.freq++;
            node.timestamp = time++;
            pq.add(node);
        } else {
            if (map.size() == capacity) {
                Node lfu = pq.poll();
                map.remove(lfu.key);
            }

            Node newNode = new Node(key, value, 1, time++);
            map.put(key, newNode);
            pq.add(newNode);
        }
    }
}
class SnakeGame {
    boolean[][] visited;
    LinkedList<int[]> snakeBody;
    int w, h;
    int[][] food;
    int idx;

    public SnakeGame(int width, int height, int[][] food) {
        this.h = height;
        this.w = width;
        this.visited = new boolean[h][w];
        this.food = food;
        this.idx = 0;
        this.snakeBody = new LinkedList<>();
        snakeBody.addFirst(new int[]{0,0});
    }

    public int move(String direction) {
        int[] head = snakeBody.getFirst();
        int r = head[0], c = head[1];
        if(direction.equals("L")){
            c--;
        }else if(direction.equals("R")){
            c++;
        }else if(direction.equals("D")){
            r++;
        }else if(direction.equals("U")){
            r--;
        }

        if(r < 0 || c < 0 || r == h || c == w || visited[r][c]) return -1;

        if(idx < food.length){
            if(food[idx][0] == r && food[idx][1] == c){
                snakeBody.addFirst(new int[]{r, c});
                visited[r][c] = true;
                idx++;
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
