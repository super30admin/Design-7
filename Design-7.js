TC - O(1)
SC - O(1)

class LFUCache {
    constructor(capacity) {
        this.capacity = 0
        this.cache = new Map()
        this.frequencyMap = new Map()
        this.minFrequency = 0
        this.capacity = capacity
    }
    get(key) {
        if (!this.capacity || !this.cache.has(key)) return -1

        const { value, count } = this.cache.get(key)
        const newCount = count + 1
        this.cache.set(key, { value, count: newCount })
        const frequencySet = this.frequencyMap.get(count) ?? new Set()
        frequencySet.delete(key)
        if (count <= this.minFrequency && frequencySet.size === 0) {
            this.minFrequency = newCount
        }
        if (this.frequencyMap.has(newCount)) {
            this.frequencyMap.get(newCount).add(key)
        } else {
            const set = new Set([key])
            this.frequencyMap.set(newCount, set)
        }
        if (
            this.frequencyMap.has(count) &&
            this.frequencyMap.get(count).size === 0
        ) {
            this.frequencyMap.delete(count)
        }

        return value
    }

    put(key, value) {
        if (this.capacity === 0) return
      
        if (this.cache.has(key)) {
            const { count } = this.cache.get(key)
            this.cache.set(key, { value, count }) // update value
            return this.get(key) // handles the frequencyMap
        }
        
        while (this._isFull()) {
            this._removeLeastFrequentlyUsed()
        }
      
        this.cache.set(key, { value, count: 0 })
        this.get(key) 
    }

    _isFull() {
        return this.cache.size >= this.capacity
    }

    _removeLeastFrequentlyUsed() {
     
        const frequencySet = this.frequencyMap.get(this.minFrequency)
        const [first] = frequencySet
        frequencySet.delete(first)

        this.cache.delete(first)
        // if it's the last element in the set, we need to find the _next_ minFrequency
        if (frequencySet.size === 0) {
            this.frequencyMap.delete(this.minFrequency)
           
            const frequencies = [...this.frequencyMap.keys()]
            if (frequencies.length == 0) {
                this.minFrequency = 0
            } else {
                const newMinFrequency = frequencies.reduce(
                    (acc, cur) => (acc < cur ? acc : cur),
                    Infinity
                )
                this.minFrequency = newMinFrequency
            }
        }
    }
}