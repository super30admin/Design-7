//Time Complexity O(n)
// Space Complexity O(n)
//Problem successfully executed on leetcode
//No problems faced while coading this.


#include<iostream>
#include<vector>
#include<queue>
#include<unordered_map>
#include<stack>
using namespace std;


class Node
{
    public:
    int key;
    int val;
    int cnt;
    Node* next;
    Node* prev;
    Node(int key,int val)
    {
        next=NULL;
        prev=NULL;
        this->key=key;
        this->val=val;
        this->cnt=1;
    }
};

class Dll
{
    public:
    Node* head;
    Node* tail;
    int size;
    Dll()
    {
        head=new Node(-1,-1);
        tail=new Node(-1,-1);
        head->next=tail;
        tail->prev=head;
        size=0;
    }
    void AddToHead(Node* node)
    {
        Node* temp=head->next;
        head->next=node;
        node->prev=head;
        node->next=temp;
        temp->prev=node;
        size++;
    }

    void RemoveNode(Node* node)
    {
        (node->prev)->next=node->next;
        (node->next)->prev=node->prev;
        size--;
    }

    Node* RemoveFromTail(Node* node)
    {
        Node* nodeToReturn=node->prev;
        RemoveNode(nodeToReturn);
        return nodeToReturn;
    }
};
class LFUCache {
public:
    int capacity;
    int min=0;
    unordered_map<int,Node*> kToN;
    unordered_map<int,Dll*> cToDll;
    LFUCache(int capacity) {
        this->capacity=capacity;
    }
    
    int get(int key) {
        if(kToN.find(key)==kToN.end())
            return -1;
        Node* node=kToN[key];
        update(node);
        return node->val;
    }
    
    void put(int key, int value) {
        if(capacity==0) return;
        if(kToN.find(key)!=kToN.end())
        {
            Node* node=kToN[key];
            update(node);
            node->val=value;
            return;
        }
        
        if(capacity==kToN.size())
        {
            Dll* oldList=cToDll[min];
            Node* node=oldList->RemoveFromTail(oldList->tail);
            kToN.erase(node->key);
        }
        min=1;
        Node* newNode=new Node(key,value);
        if(cToDll[min]==NULL)
        {
            cToDll[min]=new Dll();
        }
        Dll* list=cToDll[min];
        list->AddToHead(newNode);
        kToN[key]=newNode;
        cToDll[min]=list;
        
    }
    
    void update(Node* node)
    {
        Dll* oldList= cToDll[node->cnt];
        if(oldList!=NULL)
            oldList->RemoveNode(node);
        if(oldList->size==0 && min==node->cnt)
        {
            min++;
        }
        
        node->cnt++;
        if(cToDll[node->cnt]==NULL)
        {
            cToDll[node->cnt]=new Dll();
        }
        Dll* newList=cToDll[node->cnt];
        newList->AddToHead(node);
        cToDll[node->cnt]=newList;
    }
};

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache* obj = new LFUCache(capacity);
 * int param_1 = obj->get(key);
 * obj->put(key,value);
 */