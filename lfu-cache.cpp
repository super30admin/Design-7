// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes

#include <unordered_map>
#include <list>
  
using namespace std;


class LFUCache {
private:
    int minFrequency;
    int capacity;
    std::unordered_map<int, int> keyToValue;
    std::unordered_map<int, int> keyToFrequency;
    std::unordered_map<int, std::list<int>> frequencyToKeys;
    std::unordered_map<int, std::list<int>::iterator> keyToIterator;

public:
    LFUCache(int capacity) {
        this->minFrequency = 0;
        this->capacity = capacity;
    }

    void updateFrequency(int key) {
        int freq = keyToFrequency[key];
        frequencyToKeys[freq].erase(keyToIterator[key]);
        keyToFrequency[key]++;

        if (frequencyToKeys[freq].empty()) {
            frequencyToKeys.erase(freq);
            if (minFrequency == freq) {
                minFrequency++;
            }
        }

        frequencyToKeys[keyToFrequency[key]].push_back(key);
        keyToIterator[key] = std::prev(frequencyToKeys[keyToFrequency[key]].end());
    }

    int get(int key) {
        if (keyToValue.find(key) == keyToValue.end()) {
            return -1;
        }

        updateFrequency(key);
        return keyToValue[key];
    }

    void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        if (keyToValue.find(key) != keyToValue.end()) {
            keyToValue[key] = value;
            updateFrequency(key);
        } else {
            if (keyToValue.size() >= capacity) {
                int keyToRemove = frequencyToKeys[minFrequency].front();
                frequencyToKeys[minFrequency].pop_front();
                keyToValue.erase(keyToRemove);
                keyToFrequency.erase(keyToRemove);
                keyToIterator.erase(keyToRemove);
            }

            keyToValue[key] = value;
            keyToFrequency[key] = 1;
            frequencyToKeys[1].push_back(key);
            keyToIterator[key] = std::prev(frequencyToKeys[1].end());
            minFrequency = 1;
        }
    }
};
