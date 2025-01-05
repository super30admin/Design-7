import { boolean } from "yargs";

class LinkedList1 {
  key: number;
  value: number;
  frequency: number;
  createdAt: number;
  updatedAt: number;
  next: LinkedList1 | null;
  prev: LinkedList1 | null;

  constructor(
    key: number,
    value: number,
    frequency: number,
    createdAt: number,
    updatedAt: number
  ) {
    this.value = value;
    this.key = key;
    this.frequency = frequency;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.next = null;
    this.prev = null;
  }
}

class LFUCache {
  capacity: number;
  head: LinkedList1;
  tail: LinkedList1;
  map: Map<number, LinkedList1>;
  counterMap: Map<number, number>;
  timestamp: number;

  constructor(capacity: number) {
    this.capacity = capacity;
    this.head = new LinkedList1(
      -1,
      -1,
      Number.MAX_SAFE_INTEGER,
      Number.MAX_SAFE_INTEGER,
      Number.MAX_SAFE_INTEGER
    );
    this.tail = new LinkedList1(
      -1,
      -1,
      Number.MIN_SAFE_INTEGER,
      Number.MIN_SAFE_INTEGER,
      Number.MIN_SAFE_INTEGER
    );
    this.head.next = this.tail;
    this.tail.prev = this.head;
    this.timestamp = 1;
    this.map = new Map();
    this.counterMap = new Map();
  }

  get(key: number): number {
    if (!this.map.has(key)) return -1;
    let node = this.map.get(key)!;
    this.increaseFrequency(node);
    this.rebalanceIfRequired(node);
    return node.value;
  }

  put(key: number, value: number): void {
    if (this.map.has(key)) {
      let node = this.map.get(key)!;
      node.value = value;
      this.increaseFrequency(node);
      this.rebalanceIfRequired(node);
    } else {
      if (this.map.size == this.capacity) {
        this.removeNode(this.tail.prev!);
      }
      let node = new LinkedList1(key, value, 1, this.timestamp, this.timestamp);
      this.insertNode(node);
      this.rebalanceIfRequired(node);
      this.timestamp++;
    }
  }

  increaseFrequency(node: LinkedList1) {
    node.frequency += 1;
    node.updatedAt = this.timestamp;
    this.timestamp++;
  }

  rebalanceIfRequired(node: LinkedList1) {
    // Move Up based on frequency and timestamp if frequency is same
    let current = node;
    let isCurrentAndNextAvailable: boolean =
      current != null && current.prev != null;
    let isCurrentFrequencyLessThanPrev = (): boolean => {
      return current.frequency > current.prev!.frequency;
    };
    let isCurrentTimestampLessThanPrev = (): boolean => {
      return (
        current.frequency == current.prev!.frequency &&
        current.updatedAt > current.prev!.updatedAt
      );
    };
    while (
      isCurrentAndNextAvailable &&
      (isCurrentFrequencyLessThanPrev() || isCurrentTimestampLessThanPrev())
    ) {
      // Swap Current with prev
      let prev = current.prev!;
      prev.next = current.next;
      prev.prev!.next = current;
      current.next!.prev = prev;
      current.prev = prev.prev;
      prev.prev = current;
      current.next = prev;
    }
  }

  removeNode(current: LinkedList1) {
    // Remove tail.prev
    current.prev!.next = current.next;
    current.prev!.next!.prev = current.prev;
    current.prev = null;
    current.next = null;
    this.map.delete(current.key);
  }

  insertNode(current: LinkedList1) {
    // Store in map
    this.map.set(current.key, current);
    // Insert at tail.prev
    current.next = this.tail;
    this.tail.prev!.next = current;
    current.prev = this.tail.prev;
    this.tail.prev = current;

    // Reposition if required
    this.rebalanceIfRequired(current);
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
