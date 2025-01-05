import { boolean } from "yargs";

class LinkedList {
  key: number;
  value: number;
  frequency: number;
  next: LinkedList | null;
  prev: LinkedList | null;

  constructor(key: number, value: number, frequency: number) {
    this.key = key;
    this.value = value;
    this.frequency = frequency;
    this.next = null;
    this.prev = null;
  }
}

class LRUNodeCache {
  map: Map<number, LinkedList>;
  head: LinkedList;
  tail: LinkedList;
  capacity: number;
  constructor(capacity: number) {
    this.capacity = capacity;
    this.map = new Map();
    this.head = new LinkedList(-1, -1, Number.MAX_SAFE_INTEGER);
    this.tail = new LinkedList(-1, -1, Number.MIN_SAFE_INTEGER);
    this.head.next = this.tail;
    this.tail.prev = this.head;
  }

  size(): number {
    return this.map.size;
  }

  get(key: number): number {
    if (!this.map.has(key)) return -1;
    let node = this.map.get(key)!;
    this.removeNode(node);
    this.insertNode(node);
    return node.value;
  }

  put(node: LinkedList): void {
    if (!this.map.has(node.key)) {
      if (this.map.size == this.capacity) {
        this.map.delete(this.tail.prev!.key);
        this.removeNode(this.tail.prev!);
      }
      this.map.set(node.key, node);
      this.insertNode(node);
    } else {
      this.map.set(node.key, node);
      this.removeNode(node);
      this.insertNode(node);
    }
  }

  // Remove from tail
  removeNode(current: LinkedList | null): LinkedList {
    if (current == null) {
      current = this.tail.prev;
    }
    current!.prev!.next = current!.next;
    current!.prev!.next!.prev = current!.prev;
    current!.prev = null;
    current!.next = null;
    this.map.delete(current!.key);
    return current!;
  }

  // Insert at head
  insertNode(current: LinkedList) {
    this.map.set(current.key, current);
    current.next = this.head.next;
    current.prev = this.head;
    this.head.next = current;
    current.next!.prev = current;
  }
}

class LFUCache {
  capacity: number;
  current_capacity: number;
  map: Map<number, LinkedList>;
  frequency_map: Map<number, LRUNodeCache>;
  least_frequency: number;
  constructor(capacity: number) {
    this.capacity = capacity;
    this.map = new Map();
    this.frequency_map = new Map();
    this.least_frequency = 1;
    this.current_capacity = 0;
  }

  get(key: number): number {
    if (this.map.has(key)) {
      // get the node
      let node = this.map.get(key)!;
      this.update_frequency(node);
      return node.value;
    }
    return -1;
  }

  put(key: number, value: number): void {
    if (this.map.has(key)) {
      // get the node
      let node = this.map.get(key)!;
      node.value = value;
      this.update_frequency(node);
    } else {
      if (this.current_capacity == this.capacity) {
        let lruNode = this.frequency_map
          .get(this.least_frequency)
          ?.removeNode(null);
        this.map.delete(lruNode!.key);
        this.current_capacity -= 1;
      }
      let node = new LinkedList(key, value, 1);
      this.map.set(key, node);
      this.least_frequency = 1;
      if (!this.frequency_map.has(this.least_frequency)) {
        this.frequency_map.set(1, new LRUNodeCache(this.capacity));
      }
      this.frequency_map.get(this.least_frequency)?.insertNode(node);
      this.current_capacity += 1;
    }
  }

  update_frequency(node: LinkedList) {
    // get the frequency
    let frequency = node.frequency;
    if (this.frequency_map.has(frequency)) {
      let current_frequency_cache = this.frequency_map.get(frequency)!;
      // remove it from lru cache list of that frequency
      current_frequency_cache.removeNode(node);
      // check the size of list at frequecny
      // if it's == least_frequency and size is 0 than remove it from map and update least_frequency
      if (
        frequency == this.least_frequency &&
        current_frequency_cache.size() == 0
      ) {
        this.least_frequency += 1;
      }
    }
    // update frequecy
    frequency += 1;
    if (!this.frequency_map.has(frequency)) {
      this.frequency_map.set(frequency, new LRUNodeCache(this.capacity));
    }
    node.frequency = frequency;
    // append it to new frequency maps
    this.frequency_map.get(frequency)?.insertNode(node);
  }
}

describe("LFU Cache", () => {
  it("Happy Path", () => {
    let lfu = new LFUCache(2);
    lfu.put(1, 1); // cache=[1,_], cnt(1)=1
    expect(lfu.map.size).toStrictEqual(1);
    lfu.put(2, 2); // cache=[2,1], cnt(2)=1, cnt(1)=1
    expect(lfu.map.size).toStrictEqual(2);
    expect(lfu.get(1)).toStrictEqual(1); // return 1
    // cache=[1,2], cnt(2)=1, cnt(1)=2
    lfu.put(3, 3); // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
    // cache=[3,1], cnt(3)=1, cnt(1)=2
    expect(lfu.map.size).toStrictEqual(2);
    expect(lfu.get(2)).toStrictEqual(-1); // return -1 (not found)
    expect(lfu.get(3)).toStrictEqual(3); // return 3
    // cache=[3,1], cnt(3)=2, cnt(1)=2
    lfu.put(4, 4); // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
    // cache=[4,3], cnt(4)=1, cnt(3)=2
    expect(lfu.map.size).toStrictEqual(2);
    expect(lfu.get(1)).toStrictEqual(-1); // return -1 (not found)
    expect(lfu.get(3)).toStrictEqual(3); // return 3
    // cache=[3,4], cnt(4)=1, cnt(3)=3
    expect(lfu.get(4)).toStrictEqual(4); // return 4
    // cache=[4,3], cnt(4)=2, cnt(3)=3
  });
});
