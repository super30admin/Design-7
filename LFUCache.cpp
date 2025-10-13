#include<iostream>
#include<bits/stdc++.h>
#include<vector>
using namespace std;
/*
We use a priority queue (min-heap) to track nodes with the lowest frequency and oldest timestamp.
On every get() or put(), we remove the node from the heap, update its frequency/timestamp, and reinsert it.
But since heap.remove() is O(n), this approach is not optimal for LFU — a HashMap + DoublyLinkedList gives true O(1) performance.
*/
class Node{
public:
    int key, value, freq, timeStamp;
    Node(int key, int value, int freq, int timeStamp){
        this->key = key;
        this->value = value;
        this->freq = freq;
        this->timeStamp = timeStamp;
    }
};
struct cmp{
    bool operator()(Node* a, Node* b){
        if(a->freq != b->freq) return a->freq > b->freq;
    }
};
class LFUCache{
    int capacity, time;
    unordered_map<int, Node*> map;
    priority_queue<Node*, vector<Node*>, cmp> pq;
public:
    LFUCache(int capacity){
        this->capacity = capacity;
        this->time = 0;
    }

    int get(int key){
        if(map.find(key) == map.end()) return -1;

        Node* node = map[key];
        vector<Node*> temp;
        while(!pq.empty() && pq.top() != node){
            temp.push_back(pq.top());
            pq.pop();
        }
        pq.pop();
        for(Node* n : temp) pq.push(n);

        node->freq++;
        node->timeStamp = time++;

        pq.push(node);
        return node->value;
    }

    void put(int key, int value){
        if(capacity == 0) return;

        if(map.find(key) != map.end()){
            Node* node = map[key];
            vector<Node*> temp;
            while(!pq.empty() && pq.top() != node){
                temp.push_back(pq.top());
                pq.pop();
            }
            pq.pop();
            for(Node* n : temp) pq.push(n);

            node->value = value;
            node->freq++;
            node->timeStamp = time++;
            pq.push(node);
        }else{
            if(map.size() == capacity){
                Node* lfu = pq.top();
                pq.pop();
                map.erase(lfu->key);
            }

            Node* newNode = new Node(key, value, 1, time++);
            map[key] = newNode;
            pq.push(newNode);
        }
    }
};
