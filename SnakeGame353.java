import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class SnakeGame353 {


    private int rows,cols;
    int[][] food;

    LinkedList<Cell> snake = new LinkedList<>();
    Set<Cell> set = new HashSet<>();
    int foodConsumeCount;



    public SnakeGame353(int width, int height, int[][]food){
        this.rows = height;
        this.cols = width;
        this.food = food;

        foodConsumeCount = 0;
        Cell cell = new Cell(0,0);
        snake.add(cell);
        set.add(cell);


    }

    
    public int move(String direction){

        Cell head = snake.getFirst();
        Cell newHead  = new Cell(head.row,head.col);

        Cell tail = snake.removeLast();
        set.remove(tail);

        if(direction.equals("U")) newHead.row--;
        if(direction.equals("L")) newHead.col--;
        if(direction.equals("R")) newHead.col++;
        if(direction.equals("D")) newHead.row++;

        if(newHead.row < 0|| newHead.row == rows ||  newHead.col <0 || newHead.col==cols){
            return -1;
        }

        if(set.contains(newHead)) return -1;

        snake.addFirst(newHead);
        set.add(newHead);

        if(foodConsumeCount < food.length && food[foodConsumeCount][0] == newHead.row && food[foodConsumeCount][1]==newHead.col){
            snake.addLast(tail);
            set.add(tail);
            foodConsumeCount++;
        }

        return snake.size()-1;
    }

}

class Cell{
    int row;
    int col;

    Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

public boolean equals(Object object){
    Cell otherCell = (Cell) object;
    return this.row==otherCell.row && this.col==otherCell.col;
}

public int hashCode(){
    return 31 * this.row+this.col;
}

}