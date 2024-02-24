/*
* Did this code successfully run on Leetcode : YES
* 
* Any problem you faced while coding this : NO
* 
* Time Complexity: O(1)
* 
* Space Complexity: O(1)
* 
*/
import java.util.HashMap;
import java.util.HashSet;

public class SnakeGame {
    class Node {
        int row;
        int col;
        Node prev;
        Node next;

        public Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    };

    class SnakeList {
        Node head;
        Node tail;
        int size;

        public SnakeList(Node firstNode) {
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);

            firstNode.prev = this.head;
            firstNode.next = this.tail;

            this.head.next = firstNode;
            this.tail.prev = firstNode;

            this.size = 0;
        }

        public void addNode(Node node) {
            node.next = this.head.next;
            node.prev = this.head;

            this.head.next = node;
            node.next.prev = node;

            this.size++;
        }

        public Node deleteLastNode() {
            Node removedNode = this.tail.prev;
            this.tail.prev = removedNode.prev;
            removedNode.prev.next = this.tail;
            this.size--;
            return removedNode;
        }

        public Node getHeadNode() {
            return this.head.next;
        }

        public Node getLastNode() {
            return this.tail.prev;
        }
    };

    int width;

    int height;

    int[][] food;

    int nextTargetIdx;

    HashMap<String, int[]> directions;

    HashSet<String> visitedCells;

    SnakeList snakeList;

    private void insertDirections() {
        this.directions = new HashMap<>();

        this.directions.put("U", new int[] { -1, 0 });
        this.directions.put("D", new int[] { 1, 0 });
        this.directions.put("L", new int[] { 0, -1 });
        this.directions.put("R", new int[] { 0, 1 });
    }

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;

        this.height = height;

        this.food = food;

        insertDirections();

        this.visitedCells = new HashSet<>();

        this.visitedCells.add(0 + ":" + 0);

        this.snakeList = new SnakeList(new Node(0, 0));
    }

    private boolean isOutgoing(int row, int col) {
        Node outgoingNode = snakeList.getLastNode();

        if (outgoingNode.row == row && outgoingNode.col == col) {
            return true;
        }

        return false;
    }

    public int move(String direction) {
        int[] target = new int[2];

        if (nextTargetIdx < food.length) {
            target = food[nextTargetIdx];
        }

        int[] dir = this.directions.get(direction);

        Node headNode = this.snakeList.getHeadNode();

        int nrow = headNode.row + dir[0];
        int ncol = headNode.col + dir[1];

        // out of bounds return -1
        if (nrow < 0 || nrow == height || ncol < 0 || ncol == width) {
            return -1;
        }

        // or is conflicting return -1
        if (this.visitedCells.contains(nrow + ":" + ncol) && !isOutgoing(nrow, ncol)) {
            return -1;
        }

        // else add to snake list
        Node newCellNode = new Node(nrow, ncol);

        this.snakeList.addNode(newCellNode);
        this.visitedCells.add(nrow + ":" + ncol);

        if (nextTargetIdx < food.length && nrow == target[0] && ncol == target[1]) {
            nextTargetIdx++;
        } else {
            Node removedNode = this.snakeList.deleteLastNode();
            if (removedNode.row != nrow || removedNode.col != ncol) {
                this.visitedCells.remove(removedNode.row + ":" + removedNode.col);
            }
        }

        return this.snakeList.size;
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
