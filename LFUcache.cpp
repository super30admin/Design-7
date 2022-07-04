class Node{
  public:
    int key;
    int value;
    int frequency;
    Node *next;
    Node *prev;
    Node(int k, int v){
        key = k;
        value = v;
        frequency = 1; //every new node, freq = 1
    }
};
class DDList{
    public:
    Node* head;
    Node* tail;
    int size=0;
    DDList(){
        head = new Node(-1,-1);
        tail = new Node(-1,-1);
        head->prev = NULL;
        tail->next = NULL;
        //head--><---tail
        head->next = tail;
        tail->prev = head;
    }
    //Remove a node, given its address
    void removeNode(Node* node){
        node->next->prev = node->prev;
        node->prev->next = node->next;
        size--;
    }
    
    //Adding a node to the front of my Doubly LL - most recently used
    void addToHead(Node* node){
        node->next = head->next;
        node->prev = head;
        head->next = node;
        node->next->prev = node;
        size++;
    }
    
    //Removing the node from my tail - aka - LRU (in case of freq tie)
    Node* removeTail(){
        Node* tailPrev = tail->prev;
        removeNode(tailPrev);
        return tailPrev; //we are returning because we have to remove from main map{key --> Node}
    }

    // void printList(){
    // 	Node* curr = head;
    // 	while(curr!=NULL){
    // 		cout<<curr->key<<","<<curr->value<<" ## ";
    // 		curr = curr->next;
    // 	}
    // 	cout<<endl;
    // }
};
class LFUCache {
public:
    int maxSizeCache; 
    int min; //To get my latest min freq in O(1)
    unordered_map<int,Node*> m; //{key --> Node}
    unordered_map<int,DDList*> freq; //{freq ---> Doubly Linked List}
    LFUCache(int capacity) {
        maxSizeCache = capacity;
    }
    
    //Remove the node from oldFreqList and add to newFreqList
    void update(Node* node){
        int oldFreq = node->frequency;
        //Remove node from old DDList using current Freq
        DDList* oldList = freq[oldFreq]; 

        oldList->removeNode(node);
        //edge case
        //If node belongs to min freq list, min freq list will become empty
        //So increment min
        if(oldFreq==min && oldList->size==0){
        	cout<<"Updating Minimum"<<endl;
        	min++;
        }
        
        node->frequency = oldFreq + 1;
        int newFreq = node->frequency;
     
        if(freq.find(newFreq)==freq.end()){
        	DDList* newDDList = new DDList();
        	newDDList->addToHead(node);
        	freq[newFreq] = newDDList;
        }else{
        	freq[newFreq]->addToHead(node);
        }
        cout<<"Update Func Called"<<endl;
        cout<<"Old Freq = "<<newFreq-1<<" :";
        cout<<"Updated Freq = "<<newFreq<<" :";        
    }
    
    int get(int key) {
    	cout<<"Get Called :"<<key<<endl;
    	//Key Not Present
    	if(m.find(key)==m.end()) return -1;
    	//Key Present
    	Node* node  = m[key];
    	update(node);
    	return node->value;
    }
    
    void put(int key, int value) {
    	 if (maxSizeCache == 0) {
            return;
        }
    	//key already there - not fresh node
    	if(m.find(key)!=m.end()){
    		Node *node = m[key];
    		node->value = value;
    		update(node);
    	}else{//key not there - fresh node
    		//case 1 - capacity is full
    		//Step 1 - create space
    		if(maxSizeCache==m.size()){
    		    //Create space
    			//remove least frequenty used
    			//removing the tail ensures we are removing LRU in case of tie
    			DDList* minFreqList = freq[min]; 
    			Node* toRemove = minFreqList->removeTail();
    			m.erase(toRemove->key);
    		}
    		//Anyways new element comes in with frequency 1
    		//Create new Node //case 2 - capacity is there
    		Node* newNode = new Node(key,value);
    		min = 1; //since the new node freq = 1
    		//Check if there is a freq = 1 doubleLL
    		if(freq.find(min)==freq.end()){
    			DDList* dlist = new DDList();
    			dlist->addToHead(newNode);
                freq[min] = dlist;
    		}else{ // freq = 1 DoubleLL is already there
    			freq[min]->addToHead(newNode);
    		}
    		m[key] = newNode;
    	}
    } 
    
};
