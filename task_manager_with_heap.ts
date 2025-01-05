import { Heap } from "../../helpers/heap_helper";

class Task {
  user_id: number;
  task_id: number;
  priority: number;
  is_removed: boolean = false;
  constructor(user_id: number, task_id: number, priority: number) {
    this.user_id = user_id;
    this.task_id = task_id;
    this.priority = priority;
  }
}

class TaskManagerV2 {
  // Task Id to Node
  taskMap: Map<number, Task> = new Map();
  // Priority To Node
  taskHeap: Heap<Task> = new Heap((a, b) => {
    if (a.priority == b.priority) return b.task_id - a.task_id;
    return b.priority - a.priority;
  });

  constructor(tasks: number[][]) {
    for (const task of tasks) {
      this.add(task[0], task[1], task[2]);
    }
  }

  add(userId: number, taskId: number, priority: number): void {
    let task = new Task(userId, taskId, priority);
    this.taskMap.set(task.task_id, task);
    this.taskHeap.insert(task);
  }

  edit(taskId: number, newPriority: number): void {
    if (this.taskMap.has(taskId)) {
      let node: Task = this.taskMap.get(taskId)!;
      this.rmv(node.task_id);
      this.add(node.user_id, node.task_id, newPriority);
    }
  }

  rmv(taskId: number): void {
    if (this.taskMap.has(taskId)) {
      let task = this.taskMap.get(taskId)!;
      task.is_removed = true;
      this.taskMap.delete(task.task_id);
    }
  }

  execTop(): number {
    while (this.taskHeap.size() > 0) {
      let top = this.taskHeap.poll()!;
      if (top.is_removed == false) {
        this.rmv(top.task_id);
        return top.user_id;
      }
    }
    return -1;
  }
}

describe("3408. Design Task Manager", () => {
  it("New Logic - 02", () => {
    let taskManager: TaskManagerV2 = new TaskManagerV2([
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
    let taskManager: TaskManagerV2 = new TaskManagerV2([[10, 25, 31]]);
    taskManager.edit(25, 9);
    expect(taskManager.execTop()).toStrictEqual(10);
  });

  it("Failed Submission 2", () => {
    //      ["TaskManager","edit","execTop"]
    //      [[[[2,12,32],[3,27,33],[10,5,23],[8,4,3]]],[4,48],[]]
    let taskManager: TaskManagerV2 = new TaskManagerV2([
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
    let taskManager: TaskManagerV2 = new TaskManagerV2([
      [6, 6, 44],
      [2, 7, 27],
    ]);
    taskManager.edit(7, 9);
    taskManager.rmv(6);
    expect(taskManager.execTop()).toStrictEqual(2);
  });
});
