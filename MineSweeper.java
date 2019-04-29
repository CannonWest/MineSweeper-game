public class MineSweeper {
	//FIELDS
	private int numMines;
	private int boardSize;
	private int[][] board;
	private int[][] playerBoard;
	
	//CONSTRUCTORS
	public MineSweeper()	{
		super();
	}
	
	public MineSweeper(int boardSize, int numMines)	{
		super();
		
		if (numMines > boardSize*boardSize)	
			throw new IllegalArgumentException("Number of mines exceeds grid spaces");
		
		this.numMines = numMines;
		this.boardSize = boardSize;
		board = new int[boardSize][boardSize];
		playerBoard = new int[boardSize][boardSize];
		
		generateBoard();
		
		printBoard();
	}
	
	//PUBLIC METHODS
	public int getBoardSize()	{
		return boardSize;
	}
	
	public void selectSpace(String playerInp)	{
		char playerXChar = Character.toUpperCase(playerInp.charAt(0));
		
		if(Character.isDigit(playerXChar))
			throw new IllegalArgumentException("Please enter the letter/symbol, followed by the digit");
		
		int playerX = playerXChar - 'A';
		
		int playerY = Integer.parseInt(playerInp.substring(1, playerInp.length())) - 1;
		
		if (board[playerX][playerY] == -1)
			throw new RuntimeException("Explosion! Game Over");
		
		clearSpace(playerX, playerY);
		printBoard();
	}
	
	public boolean hasWon()	{
		int spacesHidden = 0;
		for(int i = 0; i < boardSize; i++)	{
			for(int j = 0; j < boardSize; j++)	{
				if (playerBoard[i][j] != 1)	{
					spacesHidden++;
				}
			}
		}
		return (spacesHidden == numMines);
	}

	//PRIVATE METHODS
	private void generateBoard()	{
		for (int i = 0; i < numMines; i++)	{
			int x = (int) (Math.random() * boardSize);
			int y = (int) (Math.random() * boardSize);
			
			if (board[x][y] == -1)
				i--;
			
			board[x][y] = -1;
		}
		setHints();
	}
	
	private void clearSpace(int x, int y)	{
		if(board[x][y] == 0)	{
			for (int i = -1; i <= 1; i++)	{
				for (int j = -1; j <= 1; j++)	{
					int xIndex = x + i;
					int yIndex = y + j;
					if (isCellValid(xIndex, yIndex) && playerBoard[xIndex][yIndex] != 1)	{
						if(board[xIndex][yIndex] == 0)	{
							playerBoard[xIndex][yIndex] = 1;
							clearSpace(xIndex, yIndex);
						}
						playerBoard[xIndex][yIndex] = 1;
					}
				}
			}
		}
		playerBoard[x][y] = 1;
	}
	
	private void setHints()	{
		for (int i = 0; i < boardSize; i++)	{				//goes through each row
			for (int j = 0; j < boardSize; j++)	{  			//goes through each column
				
				if (board[i][j] == -1)	{ 					//if there is a mine
					
					for (int k = -1; k <= 1; k++)	{		//creates a variable cycling from -1 to 1 that will be added to the index
						for (int l = -1; l <= 1; l++)	{	//same as above
							int xIndex = i + k;
							int yIndex = j + l;
							
							if (isCellValid(xIndex, yIndex))	{
								if (board[xIndex][yIndex] != -1)	{
									board[xIndex][yIndex]++;
								}
							}
						}	
					}
				}
			}
		}
	}	
			
	private boolean isCellValid(int x, int y)	{
		if (x < 0 || x > boardSize - 1)	{
			return false;
		}
		if (y < 0 || y > boardSize -1)	{
			return false;
		}
		return true;
	}

	//PRINT METHODS
	public void printBoardWithMines()	{
		for (int i = 0; i < boardSize; i++)	{
			for (int j = 0; j < boardSize; j++)	{
				System.out.printf("%3d",board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printBoard()	{
		System.out.print("    ");
		for (int i = 1; i <= boardSize; i++)	{
			System.out.printf("%-3d", i);
		}
		System.out.println();
		System.out.println();
		
		for (int i = 0; i < boardSize; i++)	{
			char[] rows = Character.toChars('A' + i);
			char row = rows[0];
			System.out.print(row + " ");
			for (int j = 0; j < boardSize; j++)	{
				if (playerBoard[i][j] == 1)
					System.out.printf("%3d", board[i][j]);
				else
					System.out.printf("%3c", 'X');
			}
			System.out.println();
		}
		System.out.println();
	}
}

