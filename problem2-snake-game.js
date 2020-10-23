//https://leetcode.com/problems/design-snake-game/
//runtime:
//complexity:
//leetcode?:
//obstacles:
//approach:

function ListNode(id, data) {
  this._id = (typeof id === 'number') ? id : null;
  this._data = data || null;
  this.next = null;
  this.prev = null;
}

ListNode.prototype = {

  id: function (id) {
    if(id !== null && id !== undefined) {
      if(typeof id === 'number') {
        this._id = id;
      } else {
        throw new Error('Id must be an integer.');
      }
    } else {
      return this._id;
    }
  },

  data: function (data) {
    if(data !== null && data !== undefined) {
      this._data = data;
    } else {
      return this._data;
    }
  },

  hasNext: function () {
    if(this.next !== null) {
      return this.next.id() !== null;
    }

    return false;
  },

  hasPrev: function () {
    if(this.prev !== null) {
      return this.prev.id() !== null;
    }

    return false;
  }
};

function LinkedList() {
  //initialize end buffer nodes
  this.start = new ListNode();
  this.end = new ListNode();

  //initialize node pointers
  this.start.next = this.end;
  this.start.prev = null;

  this.end.prev = this.start;
  this.end.next = null;

  //initialize counters
  this._idCounter = 0;
  this._numNodes = 0;
}

LinkedList.prototype = {

  /**
   *   Inserts a node before another node in the linked list
   *   @param {Node} toInsertBefore
   *   @param {Node} node
   */
  insertBefore: function (toInsertBefore, data) {
    var newNode = new ListNode(this._idCounter, data);

    newNode.next = toInsertBefore;
    newNode.prev = toInsertBefore.prev;

    toInsertBefore.prev.next = newNode;
    toInsertBefore.prev = newNode;

    ++this._idCounter;
    ++this._numNodes;
  },

  /**
   *   Adds data wrapped in a Node object to the end of the linked list
   *   @param {object} data
   */
  addLast: function (data) {
    this.insertBefore(this.end, data);
  },

  /**
   *   Alias for addLast
   *   @param {object} data
   */
  add: function (data) {
    this.addLast(data);
  },

  /**
   *   Gets and returns the first node in the linked list or null
   *   @return {Node/null}
   */
  getFirst: function () {
    if (this._numNodes === 0) {
      return null;
    } else {
      return this.start.next;
    }
  },

  /**
   *   Gets and returns the last node in the linked list or null
   *   @return {Node/null}
   */
  getLast: function () {
    if (this._numNodes === 0) {
      return null;
    } else {
      return this.end.prev;
    }
  },

  /**
   *   Gets and returns the size of the linked list
   *   @return {number}
   */
  size: function () {
    return this._numNodes;
  },

  /**
   *   (Internal) Gets and returns the node at the specified index starting from the first in the linked list
   *   Use getAt instead of this function
   *   @param {number} index
   */
  getFromFirst: function (index) {
    var count = 0,
      temp = this.start.next;

    if(index >= 0) {
      while (count < index && temp !== null) {
        temp = temp.next;
        ++count;
      }
    } else {
      temp = null;
    }

    if(temp === null) {
      throw 'Index out of bounds.';
    }

    return temp;
  },

  /**
   *   Gets and returns the Node at the specified index in the linked list
   *   @param {number} index
   */
  get: function (index) {
    var temp = null;

    if (index === 0) {
      temp = this.getFirst();
    } else if (index === this._numNodes - 1) {
      temp = this.getLast();
    } else {
      temp = this.getFromFirst(index);
    }

    return temp;
  },

  /**
   *   Removes and returns node from the linked list by rearranging pointers
   *   @param {Node} node
   *   @return {Node}
   */
  remove: function (node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;

    --this._numNodes;

    return node;
  },

  /**
   *   Removes and returns the first node in the linked list if it exists, otherwise returns null
   *   @return {Node/null}
   */
  removeFirst: function () {
    var temp = null;

    if (this._numNodes > 0) {
      temp = this.remove(this.start.next);
    }

    return temp;
  },

  /**
   *   Removes and returns the last node in the linked list if it exists, otherwise returns null
   *   @return {Node/null}
   */
  removeLast: function () {
    var temp = null;

    if (this._numNodes > 0) {
      temp = this.remove(this.end.prev);
    }

    return temp;
  },

  /**
   *   Removes all nodes from the list
   */
  removeAll: function() {
    this.start.next = this.end;
    this.end.prev = this.start;
    this._numNodes = 0;
    this._idCounter = 0;
  },

  /**
   *		Iterates the list calling the given fn for each node
   *		@param {function} fn
   */
  each: function(iterator) {
    var temp = this.start;

    while(temp.hasNext()) {
      temp = temp.next;
      iterator(temp);
    }
  },

  find: function(iterator) {
    var temp = this.start,
      found = false,
      result = null;

    while(temp.hasNext() && !found) {
      temp = temp.next;
      if(iterator(temp)) {
        result = temp;
        found = true;
      }
    }

    return result;
  },

  map: function(iterator) {
    var temp = this.start,
      results = [];

    while(temp.hasNext()) {
      temp = temp.next;
      if(iterator(temp)) {
        results.push(temp);
      }
    }

    return results;
  },

  /**
   *		Alias for addLast
   *		@param {object} data
   */
  push: function(data) {
    this.addLast(data);
  },

  /**
   *		Performs insertBefore on the first node
   *		@param {object} data
   */
  unshift: function(data) {
    if(this._numNodes > 0) {
      this.insertBefore(this.start.next, data);
    }else {
      this.insertBefore(this.end, data);
    }
  },

  /**
   *		Alias for removeLast
   */
  pop: function() {
    return this.removeLast();
  },

  /**
   *		Alias for removeFirst()
   */
  shift: function() {
    return this.removeFirst();
  }
};

/**
 * Initialize your data structure here.
 @param width - screen width
 @param height - screen height
 @param food - A list of food positions
 E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].
 * @param {number} width
 * @param {number} height
 * @param {number[][]} food
 */
var SnakeGame = function(width, height, food) {
  let snake = new LinkedList()
  let _food = new LinkedList()
  let head = [0,0]

  snake.add(head)

  for(let y = 0; y !== food.length; ++y) {
    _food.add(food[y])
  }

  return Object.assign(
    Object.create(SnakeGame.prototype), {
      width, height, food: _food, snake, head
    }
  )
};

/**
 * Moves the snake.
 @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
 @return The game's score after the move. Return -1 if game over.
 Game over when snake crosses the screen boundary or bites its body.
 * @param {string} direction
 * @return {number}
 */
SnakeGame.prototype.move = function(direction) {
  // move head
  switch(direction) {
    case 'U':
      this.head[0] -= 1
      break;
    case 'D':
      this.head[0] += 1
      break;
    case 'L':
      this.head[1] -= 1
      break;
    default:
      this.head[1] += 1
      break;
  }
  // out of bounds?
  if (this.head[0] < 0 || this.head[1] < 0
    || this.head[0] >= this.height || this.head[1] >= this.width) {
    return -1
  }
  // cycle?
  for(let x = 1; x < this.snake.size(); x++) {
    let curr = this.snake.get(x)
    if (curr._data[0] === this.head[0] &&
      curr._data[1] === this.head[1]) {
      return -1
    }
    curr = curr.next;
  }
  // eat food?
  if(this.food.size() > 0) {
    let curr_food = this.food.get(0)

    if(this.head[0] === curr_food._data[0] && this.head[1] === curr_food._data[1]) {
      this.snake.add(this.food.remove(curr_food))
      return this.snake.size() - 1
    }
  }

  // just move
  this.snake.remove(this.snake.get(0))
  this.snake.add([this.head[0], this.head[1]])

  return this.snake.size() - 1
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * var obj = new SnakeGame(width, height, food)
 * var param_1 = obj.move(direction)
 */
