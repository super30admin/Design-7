import java.util.LinkedList;

//Time Complexity: 0(n) where n is the fooditems
//Space Complexity: 0(n)
//Did it successfully run on leetcode: No, I don't have leetcode premium
//Did you face any problem while coding: No

//In brief explain your approach

public class SnakeGame {
    LinkedList<int []> snakebody;   //initializing a LL to keep a track of the size of the body
    int width, height;  //making height, fooditem and width as global parameter
    LinkedList<int []> fooditems;
    int [] snakehead;   //keeping a track of the snake's head

    public SnakeGame(int width, int height, int[][] food){
        snakehead = new int [] {0, 0};
        snakebody = new LinkedList<>();
        snakebody.add(snakehead);
        this.width = width;
        this.height = height;
        fooditems = new LinkedList<>(food);
    }

    public int move(String direction){
        if(direction.equals('U')){
            snakehead[0]--;
        }
        else if (direction.equals('D')){
            snakehead[0]++;
        }
        else if (direction.equals('R')){
            snakehead[1]++;
        }
        else if (direction.equals('L')){
            snakehead[1]--;
        }
        //If snake touches the boundaries
        if(snakehead[0] < 0 || snakehead[0] == height || snakehead[1] < 0 || snakehead[1] == width){
            return -1;
        }
        //If snake touches itself
        for(int i = 1; i < snakebody.size(); i++){  //iterating through thte entire body of the snake
            int [] curr = snakebody.get(i); //getting the current index
            if(snakehead[0] == curr[0] && snakehead[1] == curr[1]){ //if head's index and current's index is same
                return -1;  //meaning it is about to crash in itself
            }
        }

        //Snake eats food
        if(fooditems.size() > 0){   //until the fooditems array has food
            int [] food = fooditems.get(0); //getting the index of food item
            if(snakehead[0] == food[0] && snakehead[1] == food[1]){ //checking if the snake head is at the food item's index or not
                snakebody.add(new int[]{snakehead[0], snakehead[1]});   //if yes, then adding the index to the body
                fooditems.remove(); //remove the eaten food from fooditems list
                return snakebody.size() - 1;    //return size-1 as it starts at 0,0 so its size 1 already
            }
        }

        //Snake moves ahead
        snakebody.add(new int[]{snakehead[0], snakehead[1]});   //add the coordinates to the body
        snakebody.remove(); //remove the last element in the linked list as it has moved ahead
        return snakebody.size() - 1;    //return size
    }
}
