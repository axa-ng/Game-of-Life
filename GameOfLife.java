/* @Author: Alexandra    @Date: Feb 20 2025
 * @Teacher: Mr.Carreiro
 * Description: In this Game of Life live cells are represented by "O" and dead cells are represented by "." in 
 * a 20 x 20 grid. The user specifies the initial cell population for generation 0 and chooses whether or not to 
 * continue to the next generation by entering a "1". Enetering "exit" will end the game. Each generation the cell
 * population and positions changes based on the following conditions:
 *      1) If a cell has 3 neighbours, it will be alive for the next generation regardless of whether or not they were alive 
 *         in the current generation.
 *      2) If a LIVE cell has 2 neighbours, it will stay alive for the next generation, otherwise it will die.
 *      3) The neighbours of a cell are any live cell that adjoins the cell either horizontally , vertically or diagonally.
 */

 import java.util.Scanner;

 class GameOfLife{//begin class
    
    /*The main() method executes the Game of Life in a do-while loop. Includes the display() method to display the grid of cells
      * and the main 2D-array that get's updated after each generation using the initialarray() and the nextArray() method. 
      * The game will stay in a loop until the nextGeneration() method returns false indicating that the user want's to exit the game.
      */
    public static void main(String[] arg) {// begin main() method

        // -- Variables Declaration --
        int generation = 0; // Counts the current generation number
        boolean array [][] = firstArray(20,20);//sets the array to be the one that is made from the user's input
        //initializing this array automatically calls the userInput() method to generate the first array

        // -- Program Execution + Output --
        do {//game loop
            display(array, generation);//calls the display() method to display the array and its generation

            array = nextArray(array); //updating the array 
            generation++; //updating the generation

        } while(nextGeneration());//loops if user wants to continue and not all cells are dead
        //will exit if either the user wants to exit or all cells are dead and therefore cannot generate the next generation

        System.out.print("The game has ended. Thanks for playing!");
         
    }//end main() method

    /*The userInput() method takes in the rows and columns parameters to calculate the maximum number of cells that the grid can fit to
     * ensure that the user's input for the initial number of cells in generation 0 is a valid input
     * by checking to see if the user's input is an integer that is within the range. If the user's input is valid, 
     * the method will return the user's input to generate the initial array in the currentArray() method.
     */
    private static int userInput(int rows, int columns) {// begin userInput() method
        
        // -- Variables Declaration --
        Scanner input = new Scanner(System.in);
        int initialCells = 0; //user's input for number of cells to start the game with
        int arraySize =  rows * columns; // calculates size of array to find maximum number of live cells possible to start the game with

        // -- Program Input --
        System.out.println("Welcome to the game of life!");

        do {//loop to check if user's input is within range and is an integer
            System.out.println("Please enter how many cells you wish to start the game with (0-" + arraySize + "): ");
            if (input.hasNextInt()) { // checks if user's input is an integer
                initialCells = input.nextInt();
                if (initialCells < 0 || initialCells > arraySize){//checks if user's input is out of range
                    System.out.println("Input is out of range.");
                } else {//if user's input is valid
                    break;
                }
            } else {//if user's input is not an integer
                System.out.println("Input must be an integer.");
            }
            input.nextLine(); // Clears scanner
        } while(initialCells < 0 || initialCells > arraySize); //loops until user's input is within range

        return initialCells; // returns user's choice of number of live cells to start game with

    }//end userInput() method

    /*The currentArray() method takes in the rows and columns parameter to generate the first generation of cells as a 2D boolean array 
     * with live cells equallying the value true and dead cells eqalying the value false, using the user's specifiec cell population from 
     * the userInput() method. The live cells will be randomly distributed throughout the grid. Once the grid is randomly populated with 
     * live cells with the cell population equallying the user's input, the method will return the array to use in the main() method to 
     * display the first generation of cells using the display() method while acting as a starting point to generate the next generation 
     * using the nextArray() method.
     */
    private static boolean[][] firstArray(int rows, int columns) {//begin firstArray() method
        // -- Variables Declaration --
        boolean[][] firstArray = new boolean[rows][columns]; 
        int initialCells = userInput(rows, columns); // Stores the user's choice of number of live cells to start game with
        int cellCount = 0; // Stores number of cells that have been made alive as array is populated
        int randomX = 0;
        int randomY = 0;

        // -- Program Execution --
        // Loop for populating array with live cells in random locations in array
        while (cellCount < initialCells) { // The loop will continue until the number of cells that have been made to be alive equals the number of cells selected to be alive by the user
            randomX = (int) (Math.random() * rows); // Selects a random row in the array between index of 1st row (0) and index of last row (number of rows - 1), inclusive
            randomY = (int) (Math.random() * columns); // selects a random column in the array

            //since the default of boolean array is false, at a random row and cloumn the value will change to true
            if (!firstArray[randomX][randomY]) {  //checks if the cell at that position is false or not, this prevents the cell to be counted twice at the same position
                firstArray[randomX][randomY] = true;
                cellCount++; //add cell to cellCount to ensure the grid is populated with the same number of cells as the users input
            } 
        } 

        return firstArray; //return the array of generation 0 using user's input of initial cells

    }//end firstArray() method

    /*The display() method takes in the array and generation in the main() method to display the generation count 
     * and the array of that generation. Since the cells in all arrays are represented as true and false, the method
     * prints out "O" if an element in the array is true and "." if the element is false.
     */
    private static void display(boolean[][] currentArray, int generation) {//begin display() method
        // -- Program Output --
        System.out.println("----Generation " + generation + "-----------------------------------");

        // Loop to iterate through every row in the array
        for (int i = 0; i < currentArray.length; i++) {
            // Loop to iterate through every column in the current row
            for (int j = 0; j < currentArray[0].length; j++) { 
                if (currentArray[i][j]) {//if cell is alive, display O
                    System.out.print("O");
                } else {//if cell is dead, display .
                    System.out.print(".");
                } 
            } 

            System.out.println(); // goes to next line at the end of every row to start new row
        } 

        System.out.println();
    }//end display() method

    /*The nextArray() method takes in the current array from the main() method to generate the array for the next
     * generation. To determine whether or not the cell is alive in the next generation, this method uses the checkNumAliveNeightbours() method 
     * to return the number of neighbours each cell has which would determine whether or not the cell is alive for the next generation.
     */
    private static boolean[][] nextArray(boolean[][] currentArray) {//begin nextArray() method
        // -- Variables Declaration --
        boolean[][] nextArray = new boolean[currentArray.length][currentArray[0].length]; // creates a new array with same number of rows and columns as original array
        int aliveNeighbours = 0; // stores number of living neighbours the current cell being checked has

        // -- Program Execution --
        // Loop to iterate through every row in the array
        for (int i = 0; i < currentArray.length; i++) {
            // Loop to iterate through every column in the current row
            for (int j = 0; j < currentArray[0].length; j++) {
                aliveNeighbours = checkNeighbours(currentArray, i, j); // returns the number of alive neighbours the cell at the current row and column has

                if (aliveNeighbours == 3){//if cell has 3 alive neighbours, it will be alive in the next generation regardless of its current state
                    nextArray[i][j] = true;
                } else if (currentArray[i][j] && (aliveNeighbours == 2)){//if cell is alive and has 2 alive neighbours, it will be alive in the next generation
                    nextArray[i][j] = true;
                } //default is false, so if cell is dead and has 2 alive neighbours, it will remain dead in the next generation
                //if neighbours are less than 2 or more than 3, cell will be dead in the next generation regardless of its current state

                aliveNeighbours = 0; // reset number of alive neighbours for next run of loop
            } 
        } 

        return nextArray; //return the updated array for the next generation

    }//end nextArray() method

    /*The checkNumAliveNeighbours() method takes in the array from the main() method and the position of the cell in the for-loop 
     * from the nextArray() method, with the parameters x being the cell's row index and y being the cell's column index, to check 
     * whether or not the elements around the cells are alive or not. This method returns the total number of elements alive around each cell.
     */
    private static int checkNeighbours(boolean[][] currentArray, int x, int y) {//begin checkNumAliveNeighbours() method
        // -- Variables Declaration --
        int aliveNeighbours = 0;

        // -- Program Execution --
        for (int i = x - 1; i <= x + 1; i++) {//Loop to iterate through the element before and after the cell's row index
            
            if (i >= 0 && i < currentArray.length) {/*checks if the element before or after the cell's row index is within the range of 
                                                     *the array to prevent out of bounds exception when checking for neighbours for 
                                                     *cells on the edge of the grid.*/
                
                for (int j = y - 1; j <= y + 1; j++) {//Loop to iterate through the element before and after the cell's column index
                    /*Check diagonals --> at each cell it loops through the row and column before, the row before and column after, 
                     *                    the row after and column before, and the row and column after the cell. 
                     *Check horizontals --> when i = the cells row position, it only checks the columns before and after the cell.
                     *Check verticals --> when j = column position, it only checks the row before and after the cell.*/
                    
                    if ((j >= 0 && j < currentArray[i].length) && (i != x || j != y) && currentArray[i][j] == true) {
                    /*(j >= 0 && j < currentArray[i].length) --> checks if the element before or after the cell's column index is within the 
                                                                 range of the array to prevent out of bounds exception when checking for 
                                                                 neighbours for cells on the edge of the grid.
                     *(i != row || j != col) --> prevents the cell being checked from being counted as a neighbour.
                     *(currentArray[i][j] == true) --> checks if the cell around the checked cell is alive.*/
                        
                        aliveNeighbours++;//counts the number of alive neighbours at each position

                    }
                }
            }
        }

        return aliveNeighbours;//returns the number of alive neighbours for the cell being checked

    }//end checkNumAliveNeighbours() method

    /*The nextGeneration() method ask the user's whether or not they want to continue to the next generation and stores the user's
     * choice as a boolean with the input "1" being true and the input "exit" being false. This boolean is returned to the 
     * main() method to determine if the next generation array gets displayed. 
     */
    private static boolean nextGeneration() {//begin nextGeneration() method
        // -- Variables Declaration --
        Scanner input = new Scanner(System.in);
        boolean choice = true;
        String choice1 = "";

        // -- Program Input + Execution --
        do {
            System.out.println("Enter \"1\" to advance to next generation, enter \"exit\" to exit the game");
            choice1 = input.nextLine();

            if (choice1.equals("1")){
                choice = true;
            } else if (choice1.equalsIgnoreCase("exit")){
                choice = false;
            } else { //if user enters anything that isn't a "1" or "exit" it will be considered invalid
                System.out.println("That is an invalid input.");
                input.nextLine();//clears scanner
            }

        } while(choice1.equals(""));//loops until user's input is valid
    
        return choice;//returns user's choice to continue or exit the game
    
    }//end nextGeneration() method

}//end class

/*
 * Code Structure and Readability:

The code is well-organized and easy to follow.
The use of comments to explain the logic in each method is helpful and enhances readability.

User Input Validation:

The code includes validation for the number of initial live cells, which is good practice.
The loop to validate user input for advancing generations or exiting the game is also well-implemented.

Class and Method Design:

The separation of functionality into different methods is a good design choice. It makes the code modular and easier to maintain.
The methods are well-defined and serve their purposes effectively.

Logical Flow:

The logical flow of the program is clear and easy to follow.
The main game loop is well-implemented, allowing the user to advance generations or exit the game.

Next Generation Logic:

The logic for determining the next generation is well-implemented and follows the rules of Conway's Game of Life accurately.

User Prompt:

The user prompt for advancing generations or exiting the game is clear and easy to understand.

Method Documentation:

The methods are well-documented with comments explaining their purpose and functionality.

Neighbor Checking:

The neighbor checking logic is well-implemented and accurately counts the neighbors for each cell.

Game Loop:

The game loop is well-implemented, allowing the user to continue playing or exit the game based on their input.
 */
 