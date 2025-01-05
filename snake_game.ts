class SnakeBoard {
  food: number[][];
  height: number;
  width: number;

  private visitedSnakeBody: boolean[][];
  // TODO: Replace array with LinkedList for optimisation
  private snake: number[][] = [];
  private foodIdx: number;

  constructor(width: number, height: number, food: number[][]) {
    this.food = food;
    this.height = height;
    this.width = width;
    this.visitedSnakeBody = Array.from({ length: height }, () =>
      Array(width).fill(false)
    );
    this.visitedSnakeBody[0][0] = true;
    // 0 = head
    // last = tail
    this.snake.push([0, 0]);
    this.foodIdx = 0;
  }

  move(direction: string): number {
    let nextRow: number = this.snake[0][0];
    let nextCol: number = this.snake[0][1];
    console.log("Head " + nextRow + " " + nextCol);
    console.log(this.snake);
    if (direction == "U") {
      nextRow--;
    } else if (direction == "D") {
      nextRow++;
    } else if (direction == "L") {
      nextCol--;
    } else if (direction == "R") {
      nextCol++;
    }

    if (
      nextRow >= 0 &&
      nextRow < this.height &&
      nextCol >= 0 &&
      nextCol < this.width
    ) {
      // We are at the valid Next Position
      let foodPos = this.food[this.foodIdx];
      if (nextRow == foodPos[0] && nextCol == foodPos[1]) {
        // Eat the food
        this.snake.unshift([nextRow, nextCol]);
        this.visitedSnakeBody[nextRow][nextCol] = true;
        this.foodIdx++;
      } else {
        // Shrink the tail
        let tail = this.snake.pop()!;
        this.visitedSnakeBody[tail[0]][tail[1]] = false;
        // Check if next pos is a body
        if (this.visitedSnakeBody[nextRow][nextCol] == true) return -1;
        // Advance the head
        this.snake.unshift([nextRow, nextCol]);
        this.visitedSnakeBody[nextRow][nextCol] = true;
      }
    } else {
      return -1;
    }

    return this.snake.length - 1;
  }
}

// https://leetcode.com/problems/design-snake-game/description/
// https://leetcode.ca/2016-11-17-353-Design-Snake-Game/
describe("Design Snake Game", () => {
  it("Happy Path", () => {
    let snakeGame = new SnakeBoard(3, 2, [
      [1, 2],
      [0, 1],
    ]);
    expect(snakeGame.move("R")).toStrictEqual(0); // return 0
    expect(snakeGame.move("D")).toStrictEqual(0); // return 0
    expect(snakeGame.move("R")).toStrictEqual(1); // return 1, snake eats the first piece of food. The second piece of food appears at (0, 1).
    expect(snakeGame.move("U")).toStrictEqual(1); // return 1
    expect(snakeGame.move("L")).toStrictEqual(2); // return 2, snake eats the second food. No more food appears.
    expect(snakeGame.move("U")).toStrictEqual(-1); // return -1, game over because snake collides with border
  });
});
