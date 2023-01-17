#time complexity :O(1)
#space complexity:O(n)
#ran on leetcode: Yes
#have a hash map to map a key to its node. Also have a hashmap that maps a frequency key to its head and tail. Within a frequncy, we will have a double linked list that consist of all the nodes that have that freq. This linked list itself helps for LRU operation. maintain a min_freq variable to give LFU element. 
class Node:
    def __init__(self,key,val,freq):
        self.left=None
        self.right=None
        self.val=val
        self.key=key
        self.freq=freq

class LFUCache:

    def __init__(self, capacity: int):
        self.key_node_map={}
        self.freq_map={}
        self.capacity=capacity
        self.current=0
        self.min_freq=1

    def removenode(self,node):
        temp=node
        temp.left.right=temp.right
        temp.right.left=temp.left

    def inserttohead(self,node,head):
        temp=node
        temp.right=head.right
        temp.left=head
        head.right=temp
        temp.right.left=temp
    
    def create_freq(self,freq):
        head=Node(-1,-1,-1)
        tail=Node(-1,-1,-1)
        head.right=tail
        tail.left=head
        self.freq_map[freq]=[head,tail]
    
    def delete_freq(self,freq):
        #print("FREQ"+str(freq))
        if(freq in self.freq_map):
            H=self.freq_map[freq][0]
            T=self.freq_map[freq][1]
            if(H.right==T and T.left==H):
                del(self.freq_map[freq])
                if(self.min_freq==freq):
                    self.min_freq+=1
  
    def get(self, key: int) -> int:
        print("GET")
        if(key not in self.key_node_map):
            return -1
        node=self.key_node_map[key]
        newfreq=node.freq+1
        self.removenode(node)
        if(newfreq not in self.freq_map):
            self.create_freq(newfreq)
        self.inserttohead(node,self.freq_map[newfreq][0])
        self.delete_freq(node.freq)
        node.freq+=1
        return node.val

        

    def put(self, key: int, value: int) -> None:
        print("PUT")
        if(self.capacity==0):
            return
        if(key not in self.key_node_map):
            if(self.current==self.capacity):
                print("HERE!!!")
                T=self.freq_map[self.min_freq][1]
                node=T.left
                #key=node.key
                self.removenode(node)
                self.delete_freq(node.freq)
                del(self.key_node_map[node.key])
                self.current-=1
            if(1 not in self.freq_map):
                self.create_freq(1)
            new_node=Node(key,value,1)
            self.inserttohead(new_node,self.freq_map[1][0])
            self.key_node_map[key]=new_node
            self.min_freq=1
            self.current+=1
        else:
            node=self.key_node_map[key]
            node.val=value
            newfreq=node.freq+1
            self.removenode(node)
            if(newfreq not in self.freq_map):
                self.create_freq(newfreq)
            self.inserttohead(node,self.freq_map[newfreq][0])
            self.delete_freq(node.freq)
            node.freq+=1
            #self.current+=1



# Your LFUCache object will be instantiated and called as such:
# obj = LFUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)
