// Time Complexity : O(1)
// Space Complexity : O(2n)
// Any problem you faced while coding this :
// Problem with updation of the node

// Your code here along with comments explaining your approach
//1. Create two hash_maps one for <key, node> and other <fre, DLL>
//2. For put: Add a node whenever to the freq 1 when node is not present.
//3. For put: Update the existing node.
//4. For get: Get the value and update the node

class LFUCache {
public:
    class Node{
      public:
        Node* prev; Node* next;
        int key, value;
        int freq;
        Node(int key, int value){
           this->key=key; this->value =value;
           freq=1;
           prev= NULL; next = NULL; 
        }
    };
    
    class DLL{
      public:
        Node* head; Node* tail;
        int size;
        DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head->next = tail;
            tail->prev = head;
            size=0;
        }
        //remove the given node
        void remove_node(Node* node){
            node->prev->next = node->next;
            node->next->prev = node->prev;
            size--;
        }
        //at begining
        void add_node(Node* node){
            node->next = head->next;
            node->prev = head;
            head->next = node;
            node->next->prev = node;
            size++;
        }
        //remove from the last
        Node* remove_from_last(){
            Node* node_removed = tail->prev;
            remove_node(tail->prev);
            return node_removed;
        }
    };
    
    
    unordered_map<int, DLL*> freq_map;
    unordered_map<int, Node*> key_map;
    int capacity; int min;
    
    LFUCache(int capacity) {
        this->capacity= capacity;
        this->min = 0;
    }
    
    void update_node(Node * curr_node){
        int key_freq = curr_node->freq;
        DLL* old_list = freq_map[key_freq];
        old_list->remove_node(curr_node);
        if(old_list->size==0 && min == key_freq){
           freq_map.erase(key_freq);
           min++;
        }
        key_freq++;
        curr_node->freq = key_freq;
        if(freq_map.find(key_freq) == freq_map.end()){
            DLL* new_list = new DLL();
            freq_map[key_freq] = new_list;
        }
        DLL* new_list  =freq_map[key_freq];
        new_list->add_node(curr_node);
    }
    
    int get(int key) {
        if(key_map.find(key) == key_map.end()){
            return -1;
        }
        Node * curr_node = key_map[key];
        int value = curr_node->value;
        update_node(curr_node);
        return value;
    }
    
    void put(int key, int value) {
        if(key_map.find(key) != key_map.end()){
            Node *curr_node = key_map[key];
            curr_node->value = value;
            update_node(curr_node);
            return;
        } else if(capacity == key_map.size()){
            if(capacity == 0)
                return;
            DLL *dll_node_remove = freq_map[min];
            //cout<<"min "<<min<<endl;
            Node* node_removed = dll_node_remove->remove_from_last();
            if(dll_node_remove->size==0){
                freq_map.erase(min); 
            }
            key_map.erase(node_removed->key);
        }
        if(freq_map.find(1) == freq_map.end()){
            DLL* new_list = new DLL();
            freq_map[1] = new_list;
        }
        min =1;
        DLL* new_list = freq_map[1];
        Node * curr_node = new Node(key, value);
        new_list->add_node(curr_node);
        key_map[curr_node->key] = curr_node;
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
