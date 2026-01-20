# 🧬 Game of Life

A console-based implementation of Conway’s Game of Life that simulates cellular automata on a 20×20 grid, allowing users to 
control the evolution of generations through interactive input.

## 🕹️ How It Works

Live cells are represented by O and dead cells by . on a 20×20 grid.
The user defines the initial cell population for generation 0 and chooses whether to continue to subsequent generations by entering 1. 
Entering exit ends the simulation.

Each generation updates based on the following rules:

Any cell with exactly 3 neighbors becomes alive in the next generation

A live cell with 2 neighbors remains alive; otherwise, it dies

Neighbors are counted horizontally, vertically, and diagonally

## 🛠 Built With

Java – Core simulation logic and user interaction

Console I/O – User input and grid-based visual output

## 📍 The Process
This project served as a deep dive into 2D arrays and spatial data management within a simulation environment. The implementation 
required a robust approach to grid navigation, specifically when calculating neighbor states across all directions, including diagonals. 
A significant technical hurdle involved the precision of boundary logic, ensuring that edge and corner cases were handled without 
causing out-of-bounds errors. To maintain the integrity of each generation, the simulation logic enforced a strict separation of states, 
calculating the next stage independently of the current one to prevent data corruption. The result is a stable system that successfully 
translates abstract rules into functional game mechanics.
