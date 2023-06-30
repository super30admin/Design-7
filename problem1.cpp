// Time Complexity : O(1)
// Space Complexity : O(capacity)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach

// Inorder to get and put values in O(1) time, we will use HashMaps
// In question, it is given that we should mainter a counter to know the LFU, and if counter values
// are equal in any case, then we should consider LRU one. so, our idea is to use an LRU concept for
// each counter frequency.
// In LRU concept, we used a DLL and a hashmap to store key and Node.
//     In LRU=>(we cant use queue and LL. reason queue(updating takesO(n)) , LL (deletion O(n)) );
// Here we add additional int (freq) to the node. and that hashmap will be same.
// Next we use another HashMap (freqMap) to store (freq,DLL).
// One problem that arises is what to delete whenever size == capacity. To avoid that problem,
// we use a variable min. we properly keep on updating, so that it always points to a DLL with \
// atleast one node present in it;


class LFUCache {
public:
    class Node{
        public:
        int key,val,freq;
        Node* prev,*next;
        Node(int k,int v){
            key = k;
            val = v;
            freq = 1;
            prev = NULL;
            next = NULL;
        }
    };
    class DLL{
        public:
        Node* head,*tail;
        int size;
        DLL(){
            head = new Node(-1,-1);
            tail = new Node(-1,-1);
            head->next = tail;
            tail->prev = head;
            size = 0;
        }
        void removeNode(Node* nn){
            nn->prev->next = nn->next;
            nn->next->prev = nn->prev;
            size--;
        }
        void addToHead(Node* nn){
            nn->next = head->next;
            nn->prev = head;
            head->next = nn;
            nn->next->prev = nn;
            size++;
        }
    };
    void update(Node* nn){
        DLL *oldlist = freqMap[nn->freq];
        oldlist->removeNode(nn);
        if(min == nn->freq && oldlist->size==0) min++;
        nn->freq++;
        if(freqMap.find(nn->freq)==freqMap.end()) freqMap.emplace(nn->freq,new DLL());
        DLL *newlist = freqMap[nn->freq];
        newlist->addToHead(nn);
    }
    int min;
    int cap;
    unordered_map<int,Node*>map;
    unordered_map<int,DLL*>freqMap;
    LFUCache(int capacity) {
        cap = capacity;
        min = 0;
    }
    
    int get(int key) {
        if(map.find(key)==map.end()) return -1;
        Node * nn = map[key];
        update(nn);
        return nn->val;
    }
    
    void put(int key, int value) {
        if(map.find(key)!=map.end()){
            Node* nn = map[key];
            nn->val = value;
            update(nn);
        }
        else{
            if(map.size()==cap){
                DLL *oldlist = freqMap[min];
                Node* nn = oldlist->tail->prev;
                oldlist->removeNode(nn);
                map.erase(nn->key);
                delete nn;
            }
            min = 1;
            Node* nn = new Node(key,value);
            map[key] = nn;
            if(freqMap.find(nn->freq)==freqMap.end()) freqMap.emplace(nn->freq,new DLL());
            freqMap[nn->freq]->addToHead(nn);
        }
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */

