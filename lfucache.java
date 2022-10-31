//Time complexity : O(1)
//Space complexity: O(n)
class LFUCache {

    Map<Integer, Bucket> bucketMap; // count -> bucket
    Map<Integer, Item> itemMap;     // key   -> item
    Bucket bucketHead;  // dummy head.
    int capacity;

    public LFUCache(int capacity) {
        bucketMap = new HashMap<>();
        itemMap = new HashMap<>();
        bucketHead = new Bucket(0);
        bucketMap.put(0, bucketHead);
        this.capacity = capacity;
    }

    public int get(int key) {
        if (capacity == 0 || !itemMap.containsKey(key)){
            return -1;
        }

        Item cur = itemMap.get(key);
        addToTail(cur);

        return cur.val;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        Item cur = itemMap.containsKey(key)? itemMap.get(key) : new Item(value, 0, key);
        cur.val = value;

        // delete accordingly if over capacity
        if (cur.count == 0 && itemMap.size() == capacity){
            Bucket leastBucket = bucketHead.next;
            Item rev = leastBucket.head.next;
            itemMap.remove(rev.key);
            rev.prev.next = rev.next;
            rev.next.prev = rev.prev;
            if (--leastBucket.sz == 0){
                bucketHead.next = leastBucket.next;
                bucketMap.remove(leastBucket.count);
            }
        }

        // add and update everything
        addToTail(cur);
    }

    private void addToTail(Item cur){
        if (cur.count > 0){ // remove it from current linkedlist
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }

        Bucket oldBucket = bucketMap.get(cur.count);
        if (!bucketMap.containsKey(cur.count + 1)){ // create next bucket.
            Bucket newbucket = new Bucket(cur.count + 1);
            newbucket.head = new Item(-1, -1, -1);
            newbucket.tail = new Item(-1, -1, -1);
            newbucket.head.next = newbucket.head.prev = newbucket.tail;
            newbucket.tail.next = newbucket.tail.prev = newbucket.head;
            oldBucket.next = newbucket;
            newbucket.prev = oldBucket;
            bucketMap.put(cur.count + 1, newbucket);
        }

        if (--oldBucket.sz == 0){ // remove old bucket
            oldBucket.prev.next = bucketMap.get(cur.count + 1);
            bucketMap.remove(cur.count);
        }

        // add to bucket
        Bucket bucket = bucketMap.get(++cur.count);
        cur.next = bucket.tail;
        cur.prev = bucket.tail.prev;
        bucket.tail.prev.next = cur;
        bucket.tail.prev = cur;
        bucket.sz++;
        itemMap.put(cur.key, cur);
    }

    private class Item{
        int val;
        int count;
        int key;
        Item prev;
        Item next;
        Item(int val, int count, int key){
            this.val = val;
            this.count = count;
            this.key = key;
        }
    }

    private class Bucket{
        Item head;
        Item tail;
        Bucket next;
        Bucket prev;
        int sz = 0;
        int count = 0;
        Bucket(int count){
            this.count = count;
        }
    }
}