/* 
    Time Complexity                              :  get - O(1)
                                                    put - O(1)
    Space Complexity                             :  O(2*N) where N is the number of key-pair values in the cache. 
                                                    N are stored in the keyNode map and the other N are stored in the freqlist map.
    Did this code successfully run on Leetcode   :  Yes
    Any problem you faced while coding this      :  No
*/

#include <bits/stdc++.h> 
using namespace std; 

// https://leetcode.com/problems/lfu-cache/submissions/

class Node {
  public:
    int key, val, count;
    Node *next, *prev;
    Node(int k, int v, int c = 1) {
        key = k;
        val = v;
        count = c;
        next = nullptr;
        prev = nullptr;
    }
};

class Nodelist {
  public:
    Node *head, *tail;
    int sz;
    Nodelist() {
        head = new Node(INT_MIN, INT_MIN);
        tail = new Node(INT_MIN, INT_MIN);
        head->next = tail;
        tail->prev = head;
        sz = 0;
    }
    void addNode(Node *node) {
        Node *temp = head->next;
        head->next = node;
        node->prev = head;
        temp->prev = node;
        node->next = temp;
        sz++;
    }
    
    void deleteNode(Node *node) {
        node->next->prev = node->prev;
        node->prev->next = node->next;
        node->prev = nullptr;
        node->next = nullptr;
        sz--;
        delete node;
    }
};

class LFUCache {

public:
    unordered_map<int, Node*> keyNode;
    unordered_map<int, Nodelist*> freqlist;
    int cap, ccap, minfreq;
    LFUCache(int capacity) {
        cap = capacity;
        ccap = 0;
    }
    
    void update(Node *node) {
        // obtain the node from the keyNode map
        // delete the node from the keyNode map
        // create a new node with the same key and value and insert in the keyNode map
        
        // since the freq of node increased by 1,get the prev freq from the freqlist map of the node.
        // delete the node from the freqlist map
        // check for if freq + 1 key exists in the freqlist map
        // if it does, add the node obtained to this list.
        // if it doesn't then create a new nodelist where the key is freq + 1
        // when removing the node from the freqlist map, check if for the key freq there are any
        // nodes. 
        // if the freq was minimum freq then update the min freq to freq + 1
        int oldFreq = node->count;
        int nodeVal = node->val;
        int nodeKey = node->key;
        int newFreq = oldFreq + 1;
        
        Node *newNode = new Node(nodeKey,nodeVal,newFreq);
        keyNode.erase(nodeKey);
        keyNode[nodeKey] = newNode;
        
        Nodelist *oldlist = freqlist[oldFreq];
        oldlist->deleteNode(node);
        
        if(oldFreq == minfreq and oldlist->sz == 0) {
            minfreq++;
        }
        
        
        Nodelist *newNodelist = new Nodelist();
        if(freqlist.find(newFreq) != freqlist.end()) {
            newNodelist = freqlist[newFreq];
        }
        
        newNodelist->addNode(newNode);
        freqlist[newFreq] = newNodelist;
    }
    
    int get(int key) {
        if(keyNode.find(key) == keyNode.end())
            return -1;
        Node *node = keyNode[key];
        int val = node->val;
        update(node);
        return val;
        // return the value corresponding to the key
    }
    
    void put(int key, int value) {
        if(cap == 0) 
            return;
        
        /* 
            if key already exists, change the value and call update.
                
        */
        
        if(keyNode.find(key) != keyNode.end()) {
            Node *oldNode = keyNode[key];
            oldNode->val = value;
            update(oldNode);
        } else {
            /*
                key doesn't exist.    
                    check current capacity. 
                    if current capacity = total capacity, delete a node. 
                    Delete the node from keyNode and freqlist maps.
                    create the node with key - key and val - value.
                    check minFreq. 
                    If minFreq = 1, obtain Nodelist from freqlist map for freq = 1 and add the node
            */
            if(ccap == cap) {
                Nodelist *nodelist = freqlist[minfreq];
                keyNode.erase(nodelist->tail->prev->key);
                nodelist->deleteNode(nodelist->tail->prev);
                ccap--;
            }
            
            ccap++;
            minfreq = 1;
            Node *newNode = new Node(key,value,1);
            Nodelist *newNodeList = new Nodelist();
            if(freqlist.find(minfreq) != freqlist.end()) {
                newNodeList = freqlist[minfreq];
            }
            
            newNodeList->addNode(newNode);
            keyNode[key] = newNode;
            freqlist[minfreq] = newNodeList;
        }
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */