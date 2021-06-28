class Node{
    public: 
        Node* prev;
        Node* next;
        int val;
        int  freq;
        int key;
        Node(int KEY, int VAL){
            val = VAL;
            key = KEY;
            freq = 1;
        }

};
class DLL{
    public: 
        Node* head;
        Node* tail;
        int size;
        DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head->next = tail;
            tail->prev = head;
            size = 0;
        }
        
        void moveToHead(Node* node){
            node->next = head->next;
            node->prev = head;
            head->next = node;
            node->next->prev = node;
            size++;
        }
    
        void removeNode(Node* node){
            node->prev->next = node->next;
            node->next->prev = node->prev;
            size--;
        }
        Node* removeLast(){
            auto node = tail->prev;
            removeNode(node);
            return node;
        }
};

class LFUCache {
public:
    map<int, DLL*> freqMap;
    map<int, Node*> map;
    int min;
    int capacity;
    LFUCache(int CAP) {
        capacity = CAP;
        min = 0;
    }
    
    int get(int key) {
        if ( map.find(key) == map.end()) return -1;
        auto node = map[key];
        update(node);
        return node->val;
        
    }
    
    void put(int key, int value) {
        if ( map.find(key) != map.end()){
            Node* node = map[key];
            node->val = value;
            update(node);
        }
        else{
            if ( capacity == 0) return;
            if ( capacity == map.size()){
                // auto minList = ;
                cout<<""<<map.size()<<endl;
                Node* node = freqMap[min]->removeLast();
                map.erase(node->key);
            
            }
            Node* newNode = new Node(key, value);
            min = 1;
            DLL* newList;
            if ( freqMap.find(min) == freqMap.end()){
                auto newList = new DLL();
            }
            else{
                newList = freqMap[min];
            }
            newList->moveToHead(newNode);
            map[key] =  newNode;
            freqMap[min] = newList;

        }
    }
    
    void update(Node* node){
        
        int count = node->freq;
        auto oldList = freqMap[count];
        oldList->removeNode(node);
        if ( count == min and oldList->size == 0 ){
            min++;
        }
        count++;
        node->freq = count;
        DLL* newList;
        if ( freqMap.find(count) == freqMap.end()){
            newList = new DLL();
        }
        else{
            newList = freqMap[count];
        }
        newList->moveToHead(node);
        freqMap[count]= newList;
        
        
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */