// Time Complexity : O(1) for both put and get  
// Space Complexity : O(n) // 2 maps
// Did this code succesfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// 1. Maintain a map of nodes like LRU cache and a map of freq to a DLL; tail of DLL will point to LRU node at that freq; 
// 2. Maintain global count of lru freq

// get function:
// 1. If key is not present then return -1
// 2. Else, update that node in freq map while taking care of global min and return value

// put function:
// 1. If key already present then update it like in get function
// 2. If new element and cache is full remove lru node of min freq
// 3. For new element, always create new node and add to nodes map and freq map at freq = 1 and update global min to 1

class LFUCache {
public:
    class Node{
    public:
        int key; int val; int freq;
        Node* prev; Node* next;
        Node(int key, int val){
            this->key = key;
            this->val = val;
            this->freq = 1;
            this->prev = nullptr;
            this->next = nullptr;
        }
    };
    
    class DLL{
    public:
        Node* head;
        Node* tail;
        int size;
        DLL(){
            this->head = new Node(0,0);
            this->tail = new Node(0,0);
            this->head->next = this->tail;
            this->tail->prev = this->head;
            this->size =0;
        }
    };
    
    int capacity;
    int LFU;
    map<int, Node*> nodes;
    map<int, DLL*> freq_map;
    
    LFUCache(int capacity) {
        this->capacity = capacity;   
        this->LFU = 0;
    }
    
    void removeNode(DLL* dll, Node* node){
        dll->size--;
        node->next->prev = node->prev;
        node->prev->next = node->next;
        node->prev = nullptr;
        node->next = nullptr;
    }
    
    void moveToFront(DLL* dll, Node* node){
        dll->size++;
        node->next = dll->head->next;
        node->prev = dll->head;
        dll->head->next= node;
        node->next->prev = node;
    }
    
    void update (int key, int value, bool onlyUpdate){
        Node* node = nodes[key];
        if(onlyUpdate)
            node->val = value;
        
        // remove node and take care of min freq
        removeNode(freq_map[node->freq],node);
        if(node->freq == LFU && freq_map[LFU]->size==0)
            LFU++;
        node->freq++;
        
        // make changes to new dll 
        // no dll with new freq
        if(freq_map.find(node->freq) == freq_map.end()){
            DLL* dll = new DLL();
            freq_map[node->freq] = dll;
            moveToFront(dll, node);
        }
        // dll already present with that freq
        else
            moveToFront(freq_map[node->freq], node);
    }
    
    int get(int key) {
        // key not present
        if(nodes.find(key) == nodes.end())
            return -1;
        // key present: update freq map and return value
        Node* node = nodes[key];
        update(key, -1, false);
        return node->val;            
    }
    
    void put(int key, int value) {
        // if cap is 0 then nothing can be added
        if(capacity == 0)
            return;
        
        // existing element
        if(nodes.find(key) != nodes.end()){
           update(key, value, true); 
        }
        // new element
        else{
            // cache full
            if(nodes.size() == capacity){
                Node* lfu_node = freq_map[LFU]->tail->prev;
                nodes.erase(lfu_node->key);
                removeNode(freq_map[LFU], lfu_node);                
            }
            Node* node = new Node(key, value);
            nodes[key] = node;
            // add new node to DLL with freq = 1
            LFU = 1; // since adding new element which will have freq=1
            if(freq_map.find(1) == freq_map.end()){
                DLL* dll = new DLL();
                freq_map[1] = dll;
                moveToFront(dll, node);
            }
            // dll already present with freq=1
            else
                moveToFront(freq_map[1], node);
        }
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */