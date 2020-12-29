// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
/*
lfu cache has 2 maps mainMap contains key and Node
freqMap contains key and DLList
Node contains the key, value and count along with prev, next noe
DLList contains a double linked list with head, tail and size

for LFU get if mainMap contains key then call update and return val else return -1
for LFU put if it exists in main map then update node value and call update function
else
if capacity full the remove last node of dll of least frequency
then add new node into dll of min or 1 freq

update function used when either we get or put and need to update dlls
remove node from old list and add increment its count and add to new list of new count
*/
package main

import "fmt"

type Node struct {
	Key  int
	Val  int
	Cnt  int
	Prev *Node
	Next *Node
}

func NodeConstructor(k, v int) *Node {
	return &Node{
		Key: k,
		Val: v,
		Cnt: 1,
	}
}

type DLList struct {
	Head *Node
	Tail *Node
	Size int
}

func DLListConstructor() *DLList {
	obj := &DLList{
		Head: NodeConstructor(-1, -1),
		Tail: NodeConstructor(-1, -1),
	}
	obj.Head.Next = obj.Tail
	obj.Tail.Prev = obj.Head
	return obj
}

func (d *DLList) addToHead(node *Node) {
	node.Next = d.Head.Next
	node.Prev = d.Head
	d.Head.Next = node
	node.Next.Prev = node
	d.Size++
}

func (d *DLList) removeNode(node *Node) {
	node.Prev.Next = node.Next
	node.Next.Prev = node.Prev
	d.Size--
}

func (d *DLList) removeLast() *Node {
	tailPrev := d.Tail.Prev
	d.removeNode(tailPrev)
	return tailPrev
}

type LFUCache struct {
	mainMap  map[int]*Node
	freqMap  map[int]*DLList
	min      int
	capacity int
}

func Constructor(capacity int) LFUCache {
	return LFUCache{
		mainMap:  map[int]*Node{},
		freqMap:  map[int]*DLList{},
		capacity: capacity,
	}
}

func (this *LFUCache) Get(key int) int {
	v, e := this.mainMap[key]
	if e {
		this.update(v)
		return v.Val
	}
	return -1
}

func (this *LFUCache) Put(key int, value int) {
	v, e := this.mainMap[key]
	if e {
		v.Val = value
		this.update(v)
	} else {
		if this.capacity == 0 {
			return
		}
		if this.capacity == len(this.mainMap) {
			//capacity full
			minLi := this.freqMap[this.min]
			toRemove := minLi.removeLast()
			delete(this.mainMap, toRemove.Key)
		}
		newNode := NodeConstructor(key, value)
		this.min = 1
		v1, e1 := this.freqMap[this.min]
		var minLis *DLList
		if e1 {
			minLis = v1
		} else {
			minLis = DLListConstructor()
		}
		minLis.addToHead(newNode)
		this.freqMap[this.min] = minLis
		this.mainMap[key] = newNode

	}
}

func (this *LFUCache) update(node *Node) {
	cnt := node.Cnt
	oldList, e := this.freqMap[cnt]
	if e {
		oldList.removeNode(node)
		if cnt == this.min && oldList.Size == 0 {
			this.min++
		}
		node.Cnt++
		var newList *DLList
		v1, e1 := this.freqMap[node.Cnt]
		if e1 {
			newList = v1
		} else {
			newList = DLListConstructor()
		}
		newList.addToHead(node)
		this.freqMap[node.Cnt] = newList
	} else {

	}
}

func MainLFUCache() {
	lfu := Constructor(2)
	lfu.Put(1, 1)
	lfu.Put(2, 2)
	fmt.Println(lfu.Get(1)) // return 1
	lfu.Put(3, 3)           // evicts key 2
	fmt.Println(lfu.Get(2)) // return -1 (not found)
	fmt.Println(lfu.Get(3)) // return 3
	lfu.Put(4, 4)           // evicts key 1.
	fmt.Println(lfu.Get(1)) // return -1 (not found)
	fmt.Println(lfu.Get(3)) // return 3
	fmt.Println(lfu.Get(4)) // return 4
}
