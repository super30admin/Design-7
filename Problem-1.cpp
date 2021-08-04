//LFU cache
class LFUCache {
public:
    LFUCache(int capacity) {
        size = capacity;
        curSize = 0;
        minFreq = 0;
    }
    int get(int key) {
        if(mp.count(key) == 0) {
            return -1;
        }   
        put(key, mp[key]);
        return mp[key];
    }
    void put(int key, int value) {
        if(mp.count(key) > 0) {
            mp[key] = value;
            auto info = posMap[key];
            int cnt = info.first;
            auto iter = info.second;

            freq[cnt].erase(iter);
            freq[cnt + 1].push_back(key);
            posMap[key] = {cnt + 1, --freq[cnt + 1].end()};

            if(freq[minFreq].size() == 0) {
                minFreq++;
            }
        } else {
            if(size <= 0) return;
            if(curSize == size) {
                int delKey = *freq[minFreq].begin();
                freq[minFreq].erase(posMap[delKey].second);
            //    posMap.erase(delKey);
                mp.erase(delKey);
                
                curSize--;
            }
            mp[key] = value;
            freq[1].push_back(key);
            posMap[key] = {1, --freq[1].end()};
            minFreq = 1;
            curSize++;
        }
    }
    
    int size;
    int curSize;
    int minFreq;
    unordered_map<int, int> mp;
    unordered_map<int, pair<int, list<int>::iterator>> posMap;
    unordered_map<int, list<int>> freq;
};


/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
