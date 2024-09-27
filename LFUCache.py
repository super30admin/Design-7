#TC:O(1)-each functions
#SC:O(n)
#Ran successfully on leetcode:Yes

from collections import OrderedDict
class Node():
	def __init__(self):
		self.key = 0
		self.val = 0
		self.freq = 0
    
class LFUCache:
	def __init__(self, capacity: int):
		self.cache = {}
		self.freqcache = {}
		self.capacity = capacity
		self.size = 0
		self.minFreq = 0
		
	def _pop_least_freq_node(self): 
        # Remove last item with minfreq key
        odict = self.freqcache[self.minFreq]
        popped = odict.popitem(last=False)
        
        del self.cache[popped[1].key]
        #if no items in ordered list, delete from freq cache
        if len(odict) < 1:
            del self.freqcache[self.minFreq]
        return popped[0]
    
	def _add_node_freq_cache(self, node):
		odict = OrderedDict()
		if node.freq in self.freqcache:
			odict = self.freqcache[node.freq]

		odict[node.key] = node
		self.freqcache[node.freq] = odict 

	def _update_freq_cache_by1(self,node):
		currfreq = node.freq
		odict = self.freqcache[currfreq]
		node_to_be_upd = odict[node.key]
		if currfreq+1 not in self.freqcache:
			node_to_be_upd.freq += 1
			new_dict = OrderedDict()
			new_dict[node.key] = node_to_be_upd
			self.freqcache[currfreq+1] = new_dict
		else:
			ol = self.freqcache[currfreq+1]
			node.freq += 1
			ol[node.key] = node

		# Delete the node from old list and update minfreq
		del self.freqcache[currfreq][node.key]
		if len(self.freqcache[currfreq]) < 1:
			del self.freqcache[currfreq]
			if self.minFreq == currfreq:
				self.minFreq += 1

	def get(self, key):
		if key not in self.cache:
			return -1
		node = self.cache[key]  
		# update nodes' freq += 1 in freq_cache
		self._update_freq_cache_by1(node)
		return node.val

	def put(self, key: int, value: int) -> None:
		if self.capacity < 1:
			return None
		# case 1: Not in list
		if key not in self.cache:
			node = Node()
			node.val = value
			node.key = key
			node.freq = 1 
			self.cache[key] = node
			self.size += 1

			if self.size > self.capacity:
				#remove the least freq used (lowest from freq cache)
				tail_key = self._pop_least_freq_node()
				self.size -= 1
			self._add_node_freq_cache(node)
			self.minFreq = 1

		else:
			# case 2 : In list, needs updation in freq and val
			node = self.cache[key]
			node.val = value
			self._update_freq_cache_by1(node)


# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
