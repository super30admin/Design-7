// Time Complexity : O(1) 
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes 


/*
2 hashmaps 
1. Key value pair but add the value as a Node 
2. Frequency and Doubly Linked List to mantain the list of key/node values of that frequency and the order in which they come
*/

class LFUCache {
public:
    class Node {
    public:
        int key, value, frequency;
        Node* previous;
        Node* next;
        Node (int key, int value) {
            this->key = key;
            this->value = value;
            this->frequency = 1;
            previous = NULL;
            next = NULL;
        } 
    };

    class DLinkedList {
    public:
        // If size is 0, the minimum changes 
        // i.e., if minimum = 2 and the nodes become 0, the minimum changes to the new nodes with least frequency
        int size;
        Node* head;
        Node* tail;
        DLinkedList () {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head->next = tail;
            tail->previous = head;
            this->size = 0;
        }

        // Removing the least frequently used element from the linkedList of minimum
        Node* removeLastNode () {
            Node* lastNode = tail->previous;
            removeNode(lastNode);
            return lastNode;
        }

        // removing a node and replacing it with a new value
        void removeNode (Node* node) {
            node->next->previous = node->previous;
            node->previous->next = node->next;
            size--;
            return;
        }

        // Adding the new element to the head of the new frequency
        void addToHead (Node* node) {
            node->next = head->next;
            node->next->previous = node;
            node->previous = head;
            head->next = node;
            size++;
            // return;
        }
    };

    unordered_map<int, Node*> map;
    unordered_map<int, DLinkedList*> frequencyMap;
    int min; 
    int capacity;

    LFUCache(int capacity) {
        this->capacity = capacity;
        // start with minimum as 1
        min = 1;
    }
    
    int get(int key) {
        if(map.find(key) == map.end())
            return -1;
        Node* node = map[key];

        // remove it from curret frequency 
        // update frequency
        // add it to the new frequency DLL
        updateNode(node);
        return node->value;
    }
    
    void put(int key, int value) {
        if(capacity == 0)
            return;
        // if the key was found in the map - just update the value and frequency
        if(map.find(key) != map.end()){
            Node* node = map[key];
            node->value = value;
            updateNode(node);
            return;
        }

        if(map.size() == capacity) {
            // remove the least frequenctly used node
            // add a new node 
            DLinkedList* LFUList = frequencyMap[min];
            Node* LFUNode = LFUList->removeLastNode();
            map.erase(LFUNode->key);
        }

        // Add the new node to frequency 1 doubly linked list
        Node* node = new Node(key, value);
        map[key] = node;
        min = 1;
        if(frequencyMap.find(1) == frequencyMap.end())
            frequencyMap[1] = new DLinkedList();
        frequencyMap[1]->addToHead(node);

        return;
    }

    void updateNode(Node* node){
        // Get the frequency of the current node 
        // and the doubly linked list at that frequency
        DLinkedList* oldList = frequencyMap[node->frequency];

        // remove that node from the doubly linked list
        oldList->removeNode(node);

        // if the frequency of the current node was the minimum, it needs to be updated to min++ since we are updating the frequency value to +1
        if(min == node->frequency && oldList->size == 0)
            min++;

        // update the frequency
        node->frequency++;

        // if there is no DLL in the new frequency
        if(frequencyMap.find(node->frequency) == frequencyMap.end()) {
            frequencyMap[node->frequency] = new DLinkedList();
        }
        // add the new node with the new frequency to the doubl linked list
        frequencyMap[node->frequency]->addToHead(node);
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */