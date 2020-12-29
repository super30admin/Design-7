// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
/*
start at 0,0
update snakehead according to direction
if snakehead is out of bounds return -1
if snakehead matches any of the positions of snake then it hit itself return -1
if food remaining anf snakehead is at next food location then append location and return size - 1
if its not food location then remove tail and append head return size - 1
*/
package main

import "fmt"

type SnakeGame struct {
	Snake     [][]int
	Food      [][]int
	SnakeHead []int
	Width     int
	Height    int
	FoodCount int
}

func ConstructorSnake(w, h int, f [][]int) *SnakeGame {
	return &SnakeGame{
		Width:     w,
		Height:    h,
		Food:      f,
		SnakeHead: []int{0, 0},
		Snake:     [][]int{{0, 0}},
	}
}

func (s *SnakeGame) Move(dir string) int {
	if dir == "U" {
		s.SnakeHead[0]--
	}
	if dir == "D" {
		s.SnakeHead[0]++
	}
	if dir == "L" {
		s.SnakeHead[1]--
	}
	if dir == "R" {
		s.SnakeHead[1]++
	}
	//if it hits boundaries
	if s.SnakeHead[0] < 0 || s.SnakeHead[0] >= s.Height || s.SnakeHead[1] < 0 || s.SnakeHead[1] >= s.Width {
		return -1
	}
	//snake hits itself
	for i := 1; i < len(s.Snake); i++ {
		if s.Snake[i][0] == s.SnakeHead[0] && s.Snake[i][1] == s.SnakeHead[1] {
			return -1
		}
	}

	if s.FoodCount < len(s.Food) {
		if s.Food[s.FoodCount][0] == s.SnakeHead[0] && s.Food[s.FoodCount][1] == s.SnakeHead[1] {
			s.Snake = append(s.Snake, s.Food[s.FoodCount])
			s.FoodCount++
			return len(s.Snake) - 1
		}
	}

	s.Snake = s.Snake[1:]
	s.Snake = append(s.Snake, []int{s.SnakeHead[0], s.SnakeHead[1]})
	return len(s.Snake) - 1
}

func MainSnakeGame() {
	obj := ConstructorSnake(3, 2, [][]int{{1, 2}, {0, 1}})
	fmt.Println(obj.Move("R"))
	fmt.Println(obj.Move("D"))
	fmt.Println(obj.Move("R"))
	fmt.Println(obj.Move("U"))
	fmt.Println(obj.Move("L"))
	fmt.Println(obj.Move("U"))
}
