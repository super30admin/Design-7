type LFUCache struct {
    keyToNode map[int]*listNode
    freqToDll map[int]*dll
    min int
    capacity int
}

func Constructor(capacity int) LFUCache {
    return LFUCache{
        keyToNode: map[int]*listNode{},
        freqToDll: map[int]*dll{},
        min: -1,
        capacity: capacity,
    }
}

// time: o(1)
// space: o(1)
func (this *LFUCache) Get(key int) int {
    nodeRef, ok := this.keyToNode[key]
    if !ok {
        return -1
    }
    this.update(nodeRef)
    return nodeRef.val
}

// time: o(1)
// space: o(1)
func (this *LFUCache) update(n *listNode) {
    currFreq := n.count
    currDll := this.freqToDll[currFreq]
    currDll.deleteNode(n)
    if currFreq == this.min && currDll.size == 0 {
        this.min++
    }
    
    n.count++
    targetFreqDLL, exists := this.freqToDll[n.count]
    if !exists {
        ndll := newDLL()
        ndll.addToHead(n)
        this.freqToDll[n.count] = ndll
    } else {
        targetFreqDLL.addToHead(n)
    }
}

func (this *LFUCache) Put(key int, value int)  {
    nodeRef, ok := this.keyToNode[key]
    if ok {
        nodeRef.val = value
        this.update(nodeRef)
    } else {
        // brand new node
        if this.capacity == 0 {
            return 
        }
        if len(this.keyToNode) == this.capacity {
            targetDll := this.freqToDll[this.min]
            lastNodeRmd := targetDll.removeLastNode()
            delete(this.keyToNode, lastNodeRmd.key)
        }
        this.min=1
        newNode := &listNode{key: key, val: value, count: 1}
        this.keyToNode[key] = newNode
        targetDll, exists := this.freqToDll[1]
        if !exists {
            ndll := newDLL()
            ndll.addToHead(newNode)
            this.freqToDll[1] = ndll
        } else {
            targetDll.addToHead(newNode)
        }
    }
}


/**
 * Your LFUCache object will be instantiated and called as such:
 * obj := Constructor(capacity);
 * param_1 := obj.Get(key);
 * obj.Put(key,value);
 */

type listNode struct {
    key int
    val int
    next *listNode
    prev *listNode
    count int
}

type dll struct {
    head *listNode
    tail *listNode
    size int
}

func newDLL() *dll {
    return new(dll)
}

func (d *dll) addToHead(n *listNode) {
    if d.head == nil {
        d.head = n
        d.tail = n
        d.size++
        return
    }
    n.next = d.head
    d.head.prev = n
    d.head = n
    d.size++
}

func (d *dll) deleteNode(n *listNode) {
    // head is nil
    if d.head == nil || n == nil {
        return
    }
    prev := n.prev
    next := n.next
    
    if prev == nil && next == nil {
        d.size = 0
        d.head = nil
        d.tail = nil
        return
    }
    if prev != nil {    
        prev.next = next
        if next != nil {
            next.prev = prev
        } else {
            d.tail = prev
        }
    } else {
        // this is head
        next.prev = nil
        d.head = next
    }
    d.size--
    n.next = nil
    n.prev = nil

}

func (d *dll) removeLastNode() *listNode {
    toRemove := d.tail
    d.deleteNode(toRemove)
    return toRemove
}
