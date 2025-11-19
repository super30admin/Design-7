// Problem: Design LFU
// Time Complexity : O(1) for all operations
// Space Complexity : O(n) for two hashmaps we used
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only
// 1. We use two hashmaps one to store key -> node and other one freqMap where
// we store freq -> doubly linked list to handle most recently used
// 2. For the get, we first check if key exists, take it's freq remove it from
// freqMap and add it to new frequency (freq+1) and add to head since it is most
// recently used
// 3. For the put, we first check if we have the key, we update the value, call
// the get method again to update freq, if we reach max capacity, we
// removeTail() from freqMap[mini] and delete the key from map
// 4. And if we this is is the first time, we update mini to 1, addtohead of
// freqMap[1]

#include <bits/stdc++.h>
using namespace std;

class Node {
public:
  Node *next;
  Node *prev;
  int key;
  int val;
  int cnt;
  Node(int key, int val) {
    this->key = key;
    this->val = val;
    this->next = nullptr;
    this->prev = nullptr;
    cnt = 1;
  }
};

class Dll {
  Node *head;
  Node *tail;

public:
  Dll() {
    this->head = new Node(-1, -1);
    this->tail = new Node(-1, -1);
    head->next = tail;
    tail->prev = head;
  }

  void addToHead(Node *node) {
    // Node* temp = head->next;
    node->next = head->next;
    node->prev = head;
    head->next = node;
    node->next->prev = node;
  }

  void removeNode(Node *node) {
    node->prev->next = node->next;
    node->next->prev = node->prev;
    node->next = nullptr;
    node->prev = nullptr;
  }

  Node *removeTail() {
    if (isEmpty())
      return nullptr;
    Node *node = tail->prev;
    removeNode(node);
    return node;
  }
  bool isEmpty() { return tail->prev == head; }
};

class LFUCache {
public:
  int cap;
  int mini;
  unordered_map<int, Node *> um;
  unordered_map<int, Dll *>
      freqMap; // to store freq as DLL to get least frequency/ least recently
  LFUCache(int capacity) { this->cap = capacity; }

  int get(int key) {
    if (um.count(key) == 0) { // does not contain key
      return -1;
    }
    Node *node = um[key];
    int freqK = node->cnt;
    freqMap[freqK]->removeNode(node);
    if (freqK == mini && freqMap[freqK]->isEmpty())
      mini++;
    node->cnt++;
    if (freqMap.count(freqK + 1) == 0) {
      freqMap[freqK + 1] = new Dll();
    }
    freqMap[freqK + 1]->addToHead(node);
    return node->val;
  }

  void put(int key, int value) {
    if (cap == 0)
      return; // eedge case
    if (um.count(key) > 0) {
      um[key]->val = value;
      get(key);
      return;
    }

    if (cap == um.size()) {
      Node *node = freqMap[mini]->removeTail();
      um.erase(node->key);
    }
    mini = 1;
    if (freqMap.count(1) == 0) {
      freqMap[1] = new Dll();
    }
    um[key] = new Node(key, value);
    freqMap[1]->addToHead(um[key]);
  }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */
