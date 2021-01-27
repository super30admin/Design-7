//Time - O(1)
//Space - O(capacity)

class LFUCache {
    
    class Node{
        public:
        int key,val,count;
        Node* next,* prev;
        Node(int key,int val){
            this->key = key;
            this->val = val;
            this->count = 1;
        }
    };
    
    class DLList {
        public:
        Node* head,* tail;
        int size;
        
        DLList(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head->next = tail;
            tail->prev = head;
            size = 0;
        }
        
        void addToHead(Node * node){
            node->next = head->next;
            head->next->prev = node;
            node->prev = head;
            head->next = node;
            size++;
        }
        
        void removeNode(Node* node){
            node->next->prev = node->prev;
            node->prev->next = node->next;
            size--;
        }
        
        Node* removeLastNode(){
            Node* node = this->tail->prev;
            removeNode(node);
            return node;
        }
        
        
    };
    
    
public:
    
    unordered_map<int,Node*> map;
    unordered_map<int,DLList*> freqMap;
    int min, capacity;
    
    LFUCache(int capacity) {
        this->capacity = capacity;
    }
    
    int get(int key) { //O(1)
        if(map.find(key)!=map.end()){
            //update the use counter in map
            Node* node = map[key];
            update(node);
            return node->val;
        }
        return -1;
    }
    
    void update(Node* node){//O(1)
        //update the freqMap
        //remove from the old dll
        int oldCount = node->count;
        node->count++;

        DLList* oldDll = freqMap[oldCount];
        oldDll->removeNode(node);
        //insert into new dll

        if(min == oldCount && oldDll->size == 0) min++;
        if(freqMap.find(node->count)!=freqMap.end()){
            freqMap[node->count]->addToHead(node);
        }else{
            freqMap[node->count] = new DLList();
            freqMap[node->count]->addToHead(node);
        }
    }
    
    void put(int key, int value) { //O(1)
        if(map.find(key)!=map.end()){
            Node* node = map[key];
            node->val = value;
            update(node);
        }else{
            if(capacity == 0) return;
            if(map.size()==capacity){
                Node *node = freqMap[min]->removeLastNode();
                map.erase(node->key);
            }
            min = 1;
            Node *node = new Node(key, value);
            map[key] = node;
            if(freqMap.find(min)!=freqMap.end()){
            freqMap[min]->addToHead(node);
            }else{
                freqMap[min] = new DLList();
                freqMap[min]->addToHead(node);
            }
        }
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */