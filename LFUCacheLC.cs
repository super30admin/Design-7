using System;
using System.Collections.Generic;
using System.Text;

namespace Design
{
    internal class LFUCacheLC
    {
        //TC:O(1)
        //SC: O(n) -- capacity
        class LFUCache
        {
            class Node
            {
                internal int key, value, cnt;
                internal Node prev, next;
                public Node(int key, int value) { 
                    this.key = key;
                    this.value = value;
                    cnt = 1;
                }
            }
            class DLL
            {
                internal Node head, tail;
                internal int size;
                public DLL()
                {
                    head = new Node(-1,- 1);
                    tail = new Node(-1,- 1);
                    head.next = tail;
                    tail.prev = head;
                }
                //add a new node to the head of the DLL
                public void addToHead(Node node)
                {
                    node.next = head.next;
                    node.next.prev = node;
                    node.prev = head;
                    head.next = node;
                    size++;
                }
                //remove a node from the DLL
                public void removeNode(Node node)
                {
                    node.next.prev = node.prev;
                    node.prev.next = node.next;
                    size--;
                }
                //remove node from the end
                public Node removeFromEnd()
                {
                    Node lastNode = tail.prev;
                    removeNode(tail.prev);
                    return lastNode;
                }
            }
            Dictionary<int, Node> map;
            Dictionary<int, DLL> freqMap;
            int capacity, min;
            public LFUCache(int capacity)
            {
                map = new Dictionary<int, Node>();
                freqMap = new Dictionary<int, DLL>();
                this.capacity = capacity;
            }
            public int get(int key)
            {
                if (!map.ContainsKey(key)) return -1;
                Node node = map[key];
                update(node);
                return node.value;
            }
            public void put(int key, int value)
            {
                //case1 node exisits
                if (map.ContainsKey(key))
                {
                    Node node = map[key];
                    node.value = value;
                    update(node);
                    return;
                }
                if (capacity == 0) return;
                if (capacity == map.Count)
                {
                    DLL oldlist1 = freqMap[min];
                    Node lastNode = oldlist1.removeFromEnd();
                    map.Remove(lastNode.key);
                }
                Node newnode = new Node(key, value);
                map.Add(key, newnode);
                min = 1;
                DLL oldlist = freqMap.GetValueOrDefault(min, new DLL());
                oldlist.addToHead(newnode);
                freqMap.Add(min, oldlist);
            }
            private void update(Node node)
            {
                int frequency = node.cnt;
                node.cnt++;
                DLL oldlist = freqMap[frequency];
                oldlist.removeNode(node);
                if (min == frequency && oldlist.size == 0)
                {
                    min++;
                }
                DLL newlist = freqMap.GetValueOrDefault(node.cnt, new DLL());
                newlist.addToHead(node);
                freqMap.Add(node.cnt, newlist);
            }
        }



    }
}
