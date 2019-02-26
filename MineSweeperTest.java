import java.util.InputMismatchException;
import java.util.Scanner;

public class MineSweeperTest {
	public static void main(String args[])	{
		//Create a Scanner to take user input
		Scanner userInpS = new Scanner(System.in);
		String userInp = "";
		
		//Create a loop that goes until the player inputs a valid number for the grid size
		boolean loop = true;
		do	{
			System.out.println("Please give a side length for how big you would like your grid to be (For example, type '5' for a 5x5 grid)");
			System.out.println("Note that 32x32 is the maximum grid size");
			try	{
				userInp = userInpS.next();
				int mines = Integer.parseInt(userInp);
				if (mines > 32)
					throw new NumberFormatException();
				loop = false;
			}	catch(NumberFormatException e)	{
				System.out.println("Invalid number/input");
				System.out.println();
			}
		} while (loop);
		
		int boardSize = Integer.parseInt(userInp);
		
		//Create a loop that goes until the user inputs a valid number for the mines (or a difficulty setting)
		loop = true;
		do	{
			System.out.println("How many mines would you like in your game?");
			System.out.println("If you're not sure, type 'easy', 'medium', or 'hard'");
			try {
				userInp = userInpS.next();
				
				//determines if the player entered something that is not a digit and assigns the corresponding difficulty (throws if it is invalid)
				if(!Character.isDigit(userInp.charAt(0)))	{
					char diff = userInp.charAt(0);
					switch (diff)	{
					case 'e':
						userInp = "" + (boardSize * boardSize)/7;
						break;
					case 'm':
						userInp = "" + (boardSize * boardSize)/5;
						break;
					case 'h':
						userInp = "" + (boardSize * boardSize)/3;
						break;
					default:
						throw new InputMismatchException("For a predetermined difficulty, type 'easy', 'medium', or 'hard'\n");
					}
				}
				
				int numMines = Integer.parseInt(userInp);
				if (numMines > boardSize*boardSize)	
					throw new IllegalArgumentException("Number of mines exceeds grid spaces");
				loop = false;
			} catch(InputMismatchException f)	{
				System.out.println(f.getMessage());
				System.out.println();
			} catch(IllegalArgumentException i)	{
				System.out.println(i.getMessage());
				System.out.println();
			}
		} while(loop);
		int numMines = Integer.parseInt(userInp);
		
		MineSweeper game = new MineSweeper(boardSize, numMines);
		
		loop = true;
		do {
			System.out.println("Select a space by entering the corresponding letter/symbol and number (i.e. 'C5')");
			try	{
				userInp = userInpS.next();
				game.selectSpace(userInp);
				loop = !(game.hasWon());
			} catch (IllegalArgumentException e)	{
				System.out.println(e.getMessage());
				System.out.println();
			} catch (IndexOutOfBoundsException i)	{
				System.out.println("Space is out of bounds/invalid");
				System.out.println();
			} catch (RuntimeException f)	{
				System.out.println(f.getMessage());
				userInpS.close();
				return;
			}
		} while (loop);
		
		System.out.println("Congratulations! You won!");
		userInpS.close();
	}
}
