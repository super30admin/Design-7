/* 
    Time Complexity                              :  move operation - O(height*width) as explained below in the answer
    Space Complexity                             :  O(height*width) - size of the snake 
    Did this code successfully run on Leetcode   :  Yes
    Any problem you faced while coding this      :  No
*/

#include <bits/stdc++.h> 
using namespace std; 

// https://leetcode.com/problems/design-snake-game/

class Node {
    public:
        Node *next, *prev;
        int x, y;
        Node(int xc, int yc) {
            x = xc;
            y = yc;
            next = nullptr;
            prev = nullptr;
        } 
};

class SnakeGame {
private:
    Node *head, *tail;
    int h, w;
    vector<vector<int>> snakeFood;
    int foodIdx, snakeSize;
public:
    SnakeGame(int width, int height, vector<vector<int>>& food) {
        
        // tail->[0,0]->head
        tail = new Node(INT_MIN,INT_MIN);
        head = new Node(INT_MAX,INT_MAX);
        Node *newNode = new Node(0,0);
        tail->next = newNode;
        newNode->prev = tail;
        newNode->next = head;
        head->prev = newNode;
        
        h = width;
        w = height;
        snakeFood = food;
        foodIdx = 0;
        snakeSize = 0;
    }
    
    int move(string direction) {
        int nhx, nhy;
        int ohx = head->prev->x;
        int ohy = head->prev->y;
        
        // T.C. - O(1)
        if(direction == "U") {
            nhy = ohy;
            nhx = ohx - 1;
        } else if (direction == "D") {
            nhy = ohy;
            nhx = ohx + 1;
        }else if (direction == "L") {
            nhy = ohy - 1;
            nhx = ohx;
        } else { // direction == "R"
            nhy = ohy + 1;
            nhx = ohx;
        }
        
        // return -1 if the snake is going to hit the wall
        // T.C. O(1) 
        if(nhx < 0 or nhx >= w or nhy < 0 or nhy >= h) {
            return -1;
        }
        
        // return -1 if the snake is going to hit itself
        // T.C. - size of the snake which can be a maximum of height*width
        Node *sNode = tail->next->next;
        while(sNode != head) {
            if(sNode->x == nhx and sNode->y == nhy) 
                return -1;
            sNode = sNode->next;
        }
        
        // insert the new node between the head and the last co-ordinate at which the snake head was present
        // tail->.....->[lastX, lastY]->[newHeadX, newHeadY]->head
        // T.C. O(1)
        Node *newHead = new Node(nhx, nhy); 
        head->prev->next = newHead;
        newHead->next = head;
        newHead->prev = head->prev;
        head->prev = newHead;

        // T.C. - O(1)
        if(foodIdx < snakeFood.size() and snakeFood[foodIdx][0] == nhx and snakeFood[foodIdx][1] == nhy) {
            // do not move the tail and increase the snake's length
            snakeSize++;
            foodIdx++;
        } else {
             // move the tail
            Node *curTailNext = tail->next;
            tail->next = curTailNext->next;
            curTailNext->next->prev = tail;
            delete curTailNext;
        }
        
        return snakeSize;   
    }
};

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame* obj = new SnakeGame(width, height, food);
 * int param_1 = obj->move(direction);
 */