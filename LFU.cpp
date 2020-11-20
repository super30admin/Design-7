//time complexity:O(1)
//space complexity:O(n)
//executed on leetcode: yes
//approach:using 3 hashmaps
//any issues faced? yes, its very confusing. will have to prac it multiple times

class LFUCache {
public:
    unordered_map<int,list<int>>freqkeylist;
    unordered_map<int,vector<int>>keyvaluefreq;
    unordered_map<int,list<int>::iterator>keyiterator;
    int c;
    int size;
    int minfreq;
    LFUCache(int capacity) {
        c=capacity;
        size=0;
        minfreq=0;
    }
    
    int get(int key) {
        if(!keyvaluefreq.count(key))
            return -1;
        int value=keyvaluefreq[key][0];
        int freq=keyvaluefreq[key][1];
        freqkeylist[freq].erase(keyiterator[key]);
        freqkeylist[freq+1].push_back(key);
        keyvaluefreq[key][1]++;
        keyiterator[key]=--freqkeylist[freq+1].end();
        if(!freqkeylist[minfreq].size())
            minfreq++;
        return value;
    }
    
    void put(int key, int value) {
        if(c<=0)
            return;
        if(get(key)!=-1)
        {
            keyvaluefreq[key][0]=value;
            return;
        }
        if(size==c)
        {
            int keytoremove=freqkeylist[minfreq].front();
            freqkeylist[minfreq].pop_front();
            keyvaluefreq.erase(keytoremove);
            keyvaluefreq.erase(keytoremove);
        }
        keyvaluefreq[key]={value,1};
        freqkeylist[1].push_back(key);
        keyiterator[key]=--freqkeylist[1].end();
        minfreq=1;
        if(size<c)
            size++;
        
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */