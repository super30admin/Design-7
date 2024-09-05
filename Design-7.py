# Design-7

## Problem1 LFU Cache (https://leetcode.com/problems/lfu-cache/)

from collections import defaultdict

class Node:
    def __init__(self, key: int, value: int) -> None:
        self.key = key
        self.value = value
        self.freq = 1
        self.prev = None
        self.next = None


class DoublyLinkedList:
    def __init__(self) -> None:
        self.head = Node(-1, -1)  # Sentinel head node
        self.tail = Node(-1, -1)  # Sentinel tail node
        self.head.next = self.tail
        self.tail.prev = self.head

    def add_first(self, node: Node) -> None:
        """Add a node right after the head."""
        node.prev = self.head
        node.next = self.head.next
        self.head.next.prev = node
        self.head.next = node

    def remove(self, node: Node) -> Node:
        """Remove an existing node from the list."""
        node.prev.next = node.next
        node.next.prev = node.prev
        # Clear the removed node's pointers
        node.next, node.prev = None, None
        return node

    def remove_last(self) -> Node:
        """Remove the last node and return it."""
        return self.remove(self.tail.prev)

    def is_empty(self) -> bool:
        """Check if the list is empty."""
        return self.head.next == self.tail


class LFUCache:
    def __init__(self, capacity: int):
        self.capacity = capacity
        self.min_freq = 0
        self.key_node_map = defaultdict(Node)
        self.freq_node_list_map = defaultdict(DoublyLinkedList)

    def get(self, key: int) -> int:
        """Get the value of the key if the key exists in the cache."""
        if self.capacity == 0 or key not in self.key_node_map:
            return -1

        node = self.key_node_map[key]
        self._increase_freq(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        """Set or insert the value if the key is not already present."""
        if self.capacity == 0:
            return

        if key in self.key_node_map:
            node = self.key_node_map[key]
            node.value = value
            self._increase_freq(node)
            return

        if len(self.key_node_map) == self.capacity:
            least_freq_list = self.freq_node_list_map[self.min_freq]
            node_to_remove = least_freq_list.remove_last()
            del self.key_node_map[node_to_remove.key]

        new_node = Node(key, value)
        self._add_node(new_node)
        self.key_node_map[key] = new_node
        self.min_freq = 1  # Reset min_freq to 1 as a new node is added

    def _increase_freq(self, node: Node) -> None:
        """Increase the frequency count of a node."""
        freq = node.freq
        node_list = self.freq_node_list_map[freq]
        node_list.remove(node)
        if node_list.is_empty():
            del self.freq_node_list_map[freq]
            if freq == self.min_freq:
                self.min_freq += 1

        node.freq += 1
        self._add_node(node)

    def _add_node(self, node: Node) -> None:
        """Add a node to the list that corresponds to its frequency count."""
        freq = node.freq
        node_list = self.freq_node_list_map[freq]
        node_list.add_first(node)
'''
TC =
get: O(1)
put: O(1)
incr_freq: O(1)
add_node: O(1)

SC = O(capacity)
'''

## Problem2 Snake game (https://leetcode.com/problems/design-snake-game/)

from collections import deque

class SnakeGame:
    def __init__(self, width: int, height: int, food: List[List[int]]):
        # Initialize the game board with the given width and height
        self.width = width
        self.height = height
      
        # Load the food positions onto the game board
        self.food = deque(food)
      
        # Initialize the score of the game as 0
        self.score = 0
      
        # The snake's body is represented as a queue with initial position at the top-left
        self.snake = deque([(0, 0)])
      
        # A set to keep track of all positions occupied by the snake
        self.snake_positions = set([(0, 0)])

    def move(self, direction: str) -> int:
        # Get the snake's current head position
        head_row, head_col = self.snake[0]
      
        # Move based on the provided direction
        if direction == 'U':
            head_row -= 1
        elif direction == 'D':
            head_row += 1
        elif direction == 'L':
            head_col -= 1
        elif direction == 'R':
            head_col += 1
      
        # Check if the new position is out of bounds
        if head_row < 0 or head_row >= self.height or head_col < 0 or head_col >= self.width:
            return -1
      
        # Check if the snake has moved to a cell containing food
        if self.food and [head_row, head_col] == self.food[0]:
            self.food.popleft()  # Eat the food
            self.score += 1      # Increase the score
        else:
            # Remove the tail if no food is eaten
            tail = self.snake.pop()
            self.snake_positions.remove(tail)
      
        # Check if the snake crashes into itself
        if (head_row, head_col) in self.snake_positions:
            return -1
      
        # Add the new head position of the snake
        self.snake.appendleft((head_row, head_col))
        self.snake_positions.add((head_row, head_col))
      
        # Return the current score of the game
        return self.score
# TC = O(1), SC = O(N + M) where N is the length of the snake and M is the total number of food items