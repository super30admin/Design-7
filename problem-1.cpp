// https://leetcode.com/problems/lfu-cache/description/
// Time Complexity:
// get(): O(1)
// put(): O(1)
// update(): O(1)
// remvoeLRU(): O(1)

// Space Complexity: 
// get(): O(1)
// put(): O(1)
// update(): O(1)
// remvoeLRU(): O(1)

class Node {
    public:
        int key;
        int val;
        int freq;
        Node* next;
        Node* prev;

        Node(int k, int v, int f) : key(k), val(v), freq(f), next(NULL), prev(NULL){} 
};
class DLL{
    public:
        Node* head; Node* tail;

        DLL(){
            head = new Node(0,0,0);
            tail = new Node(0,0,0);
            head->next = tail;
            tail->prev = head;
        }

        void addHead(Node* node){
            node->prev = head;
            node->next = head->next;
            head->next = node;
            node->next->prev = node;
        }
        void removeNode(Node* node){
            node->prev->next = node->next;
            node->next->prev = node->prev;
        }
        bool empty(){
            return head->next == tail;
        }
        Node* getTail(){
            if(empty()) return NULL;
            Node* tailNode = tail->prev;
            removeNode(tailNode);
            return tailNode;
        }
};
class LFUCache {
public:
    unordered_map<int, Node*> mp;
    unordered_map<int, DLL*> fl;
    int cap;
    int min;

    LFUCache(int capacity)  {
        cap = capacity;
        min = 0;
    }

    void update(Node* node){
        int freq = node->freq;
        fl[freq]->removeNode(node);
        if(fl[freq]->empty() && min == freq) min++;
        node->freq++;
        if(fl.find(node->freq) == fl.end()) fl[node->freq] = new DLL();
        fl[node->freq]->addHead(node);
        
    }

    void removeLRU(){
        Node* node = fl[min]->getTail();
        mp.erase(node->key);
        delete(node);
    }
    
    int get(int key) {
        if(mp.find(key) != mp.end()){
            Node* node = mp[key];
            int res = node->val;
            //update frequency
            update(node);

            return res;
        }
        return -1;
    }
    
    void put(int key, int value) {
        if(cap <= 0) return;
        if(mp.find(key) != mp.end()){
            mp[key]->val = value;
            update(mp[key]);
        }
        else{
            if(cap == mp.size()) removeLRU();
            Node* node = new Node(key, value, 1);
            mp[key] = node;
            if(fl.find(1) == fl.end()) fl[1] = new DLL();
            fl[1]->addHead(node);
            min = 1;
        }
    }
};

