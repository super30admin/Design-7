class TaskNode {
  user_id: number;
  task_id: number;
  priority: number;
  next: TaskNode | null = null;
  prev: TaskNode | null = null;
  constructor(user_id: number, task_id: number, priority: number) {
    this.user_id = user_id;
    this.task_id = task_id;
    this.priority = priority;
  }
}

class TaskManager {
  // Task Id to Node
  taskMap: Map<number, TaskNode> = new Map();
  // Priority To Node
  priorityMap: Map<number, TaskNode> = new Map();

  highestPriority: number = Number.MIN_SAFE_INTEGER;

  constructor(tasks: number[][]) {
    for (const task of tasks) {
      this.add(task[0], task[1], task[2]);
    }
  }

  add(userId: number, taskId: number, priority: number): void {
    let task = new TaskNode(userId, taskId, priority);
    // Update the highest priority if required.
    this.highestPriority = Math.max(task.priority, this.highestPriority);
    // Add it to the map
    this.taskMap.set(task.task_id, task);

    this.insertNodeToPriorityMap(task);
  }

  edit(taskId: number, newPriority: number): void {
    if (this.taskMap.has(taskId)) {
      let node: TaskNode = this.taskMap.get(taskId)!;
      this.rmv(node.task_id);
      this.add(node.user_id, node.task_id, newPriority);
    }
  }

  rmv(taskId: number): void {
    if (this.taskMap.has(taskId)) {
      let node: TaskNode = this.taskMap.get(taskId)!;
      this.removeNodeFromPriorityMap(node);
      if (this.isLinkedListEmpty(this.priorityMap.get(node.priority)!)) {
        // Highest Priority task removed
        this.priorityMap.delete(node.priority);
      }
      if (node.priority == this.highestPriority) {
        this.highestPriority = this.getHighestFrequency();
      }
      this.taskMap.delete(taskId);
    }
  }

  execTop(): number {
    let head: TaskNode | null | undefined = this.priorityMap.get(
      this.highestPriority
    );
    if (
      head != null &&
      this.isLinkedListEmpty(head) == false &&
      head.next != null
    ) {
      let task = head.next;
      let userId = task.user_id;
      this.rmv(task.task_id);
      return userId;
    } else {
      return -1;
    }
  }

  private createSkeletonOfLinkedList(): TaskNode {
    let head = new TaskNode(
      Number.MAX_SAFE_INTEGER,
      Number.MAX_SAFE_INTEGER,
      Number.MAX_SAFE_INTEGER
    );
    let tail = new TaskNode(
      Number.MIN_SAFE_INTEGER,
      Number.MIN_SAFE_INTEGER,
      Number.MIN_SAFE_INTEGER
    );
    head.next = tail;
    tail.prev = head;
    return head;
  }

  private insertNodeToPriorityMap(task: TaskNode) {
    // Attach it to the priority
    if (!this.priorityMap.has(task.priority)) {
      this.priorityMap.set(task.priority, this.createSkeletonOfLinkedList());
    }
    let current: TaskNode | null = this.priorityMap.get(task.priority)!;
    while (current != null && current.task_id > task.task_id) {
      current = current.next;
    }

    // attach the node as previous of current
    task.prev = current?.prev || null;
    task.next = current;
    if (current != null) {
      if (current.prev != null) current.prev.next = task;
      current.prev = task;
    }
  }

  private removeNodeFromPriorityMap(task: TaskNode) {
    // Detach it from the priority list
    if (!this.priorityMap.has(task.priority)) {
      return;
    }
    if (task.prev != null) task.prev.next = task.next;
    if (task.next != null) task.next.prev = task.prev;
    task.next = null;
    task.prev = null;
  }

  private isLinkedListEmpty(node: TaskNode): boolean {
    // if head is pointing to tail then there are no nodes in between
    return node.next?.priority == Number.MIN_SAFE_INTEGER;
  }

  private getHighestFrequency(): number {
    let max: number = Number.MIN_SAFE_INTEGER;
    for (const info of this.priorityMap) {
      max = Math.max(info[0], max);
    }
    return max;
  }
}

describe("3408. Design Task Manager", () => {
  it("New Logic - 02", () => {
    let taskManager: TaskManager = new TaskManager([
      [1, 101, 10],
      [2, 102, 20],
      [3, 103, 15],
    ]); // Initializes with three tasks for Users 1, 2, and 3.
    taskManager.add(4, 104, 5); // Adds task 104 with priority 5 for User 4.
    taskManager.edit(102, 8); // Updates priority of task 102 to 8.
    expect(taskManager.execTop()).toStrictEqual(3); // return 3. Executes task 103 for User 3.
    taskManager.rmv(101); // Removes task 101 from the system.
    taskManager.add(5, 105, 15); // Adds task 105 with priority 15 for User 5.
    expect(taskManager.execTop()).toStrictEqual(5); // return 5. Executes task 105 for User 5.
  });

  it("Failed Submission", () => {
    //     ["TaskManager","edit","execTop"]
    //      [[[[10,25,31]]],[25,9],[]]
    let taskManager: TaskManager = new TaskManager([[10, 25, 31]]);
    taskManager.edit(25, 9);
    expect(taskManager.execTop()).toStrictEqual(10);
  });

  it("Failed Submission 2", () => {
    //      ["TaskManager","edit","execTop"]
    //      [[[[2,12,32],[3,27,33],[10,5,23],[8,4,3]]],[4,48],[]]
    let taskManager: TaskManager = new TaskManager([
      [2, 12, 32],
      [3, 27, 33],
      [10, 5, 23],
      [8, 4, 3],
    ]);
    taskManager.edit(4, 48);
    expect(taskManager.execTop()).toStrictEqual(8);
  });

  it("Failed Submission 2", () => {
    // ["TaskManager","edit","rmv","execTop"]
    // [[[[6,6,44],[2,7,27]]],[7,9],[6],[]]
    let taskManager: TaskManager = new TaskManager([
      [6, 6, 44],
      [2, 7, 27],
    ]);
    console.log(taskManager.highestPriority);
    taskManager.edit(7, 9);
    console.log(taskManager.highestPriority);
    taskManager.rmv(6);
    console.log(taskManager.highestPriority);
    console.log(taskManager.taskMap);
    console.log(taskManager.priorityMap);
    expect(taskManager.execTop()).toStrictEqual(2);
  });
});
