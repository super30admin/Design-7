class LFUCache {
  /** 
   * Time Complexity : O(1) for both get and put operations
   * 
   * Space Complexity: O(capacity)
   *    -> our nodeMap would occupy a capacity proportional to the input capacity
   * 
   * Were you able to solve this on leetcode? Yes
   * 
   */
  class Node {
      int key;
      int value;
      int count;
      Node next, prev;
      Node(int key, int value) {
          this.key = key;
          this.value = value;
          this.count = 1;
          this.next = null;
          this.prev = null;
      }
  }
  
  class DoublyLinkedList {
      Node head;
      Node tail;
      int size;
      DoublyLinkedList() {
          this.head = new Node(-1, -1);
          this.tail = new Node(-1, -1);
          head.next = tail;
          tail.prev = head;
      }
      
      void addToHead(Node node){
          Node temp = head.next;
          head.next = node;
          temp.prev = node;
          node.next = temp;
          node.prev = head;
          this.size++;
      }
      
      void removeNode(Node node){
          if(node == head || node == tail){
              return;
          }
          node.prev.next = node.next;
          node.next.prev = node.prev;
          this.size--;
      }
      
      Node removeLast(){
          Node lastNode = tail.prev;
          if(lastNode != head) {
              removeNode(lastNode);
          }
          return lastNode;
      }
  }

  int capacity;
  Map<Integer, Node> nodeMap;
  Map<Integer, DoublyLinkedList> countMap;
  int minCount;
  public LFUCache(int capacity) {
      this.capacity = capacity;
      this.nodeMap = new HashMap<>();
      this.countMap = new HashMap<>();
      
  }
  
  public int get(int key) {
      // if key isn't available, return -1
      
      if(!nodeMap.containsKey(key)) return -1;
      
      // if there is a Node already, do the following
      //   1. Update the count on the Node
      //   2. Remove the Node from previous count list and put it in the new count list.
      Node currNode = nodeMap.get(key);
      updateNode(currNode);
      
      return currNode.value;
      
  }
  
  private void updateNode(Node node){
      // increase count and update the list it is in.
      
      int oldCount = node.count;
      int newCount = oldCount + 1;
      DoublyLinkedList dll = countMap.get(oldCount);
      dll.removeNode(node);
      
      node.count = newCount;
      if(!countMap.containsKey(newCount)){
          countMap.put(newCount, new DoublyLinkedList());
      }
      
      DoublyLinkedList currentDll = countMap.get(newCount);
      currentDll.addToHead(node);
      
      // Update min count only when the corresponding List is empty
      if(oldCount == this.minCount && dll.size == 0) minCount++;
  }
  
  public void put(int key, int value) {
      if(this.capacity == 0) { return; }
      // if key already exists, just update the value of the node
      if(nodeMap.containsKey(key)){
          Node currNode = nodeMap.get(key);
          currNode.value = value;
          updateNode(currNode);
          return;
      }
      
      // else
      // 1. create new node,
      //  2. check the size of Map
      // invalidate if required and then put
      if(nodeMap.size() == this.capacity){
          // we are at capacity and we need to Invalidate
          DoublyLinkedList minFrequencyList = countMap.get(this.minCount);
          Node removedNode = minFrequencyList.removeLast();
          
          nodeMap.remove(removedNode.key);
          
      }
      Node newNode = new Node(key, value);
      nodeMap.put(key, newNode);
      DoublyLinkedList dll = countMap.getOrDefault(1, new DoublyLinkedList());
      dll.addToHead(newNode);
      countMap.put(1, dll);
      minCount = 1;
  }
}
