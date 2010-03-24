/*
Exemple d'implÈmentation d'un joueur d'Othello. Cette implÈmentation sert uniquement
‡ dÈmontrer le principe, mais n'implÈmente aucune intelligence: les coups ‡ jouer sont
simplement lus ‡ la console!
*/

// Votre version sera dans Participants.<VosNoms>

package Participants.FroidevauxKaluznyNeuhaus;

import java.util.ArrayList;

// Pour l'interopÈrabilitÈ: il faut une reprÈsentation commune des coups!

// Vous devrez Ètendre Othello.Joueur pour implÈmenter votre propre joueur...
public class Board
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Board(int activePlayer, int bluePiecesNb, int redPiecesNb, int movesPlayed, int owner)
		{
		this.activePlayer = activePlayer;
		this.bluePiecesNb = bluePiecesNb;
		this.redPiecesNb = redPiecesNb;
		this.movesPlayed = movesPlayed;
		this.owner = owner;
		initBoard();
		this.eval = evalBoard();
		}

	public int owner;

	public Board(int activePlayer)
		{
		this(activePlayer, 2, 2, 0, 1 - activePlayer);
		}

	/*public Board(Board board, int activePlayer)
		{
		this(activePlayer, board.bluePiecesNb, board.redPiecesNb, board.movesPlayed);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(board.grid[i], 0, this.grid[i], 0, 8);
			}
		}
	*/
	public Board(Board plateau)
		{
		this(plateau.activePlayer, plateau.bluePiecesNb, plateau.redPiecesNb, plateau.movesPlayed, plateau.owner);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grid[i], 0, this.grid[i], 0, 8);
			}
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.pointsGrid[i], 0, this.pointsGrid[i], 0, 8);
			}
		}

	public Board(Board oldState, Position move)
		{
		this(oldState);
		if (move != null)
			{
			this.updateBoard(move);
			}

		this.activePlayer = 1 - this.activePlayer;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public int emptyCaseNb()
		{
		return 64 - bluePiecesNb - redPiecesNb;
		}

	public void updateBoard(Position move)
		{
		int i = move.i;
		int j = move.j;
		//System.out.println("Move Pos = (" + i + "," + j + ")");
		grid[i][j] = this.activePlayer;
		int flippedPiecesNb = 0;

		// Variation de i
		for(int angleI = -1; angleI <= 1; angleI++)
			{
			// variation de j
			for(int angleJ = -1; angleJ <= 1; angleJ++)
				{
				// On ne prend pas en compte le cas ou i et j sont nuls
				if (!(angleI == 0 && angleJ == 0))
					{
					// Initialisation de la position de recherche initiale (case à côté de la position intiale)
					i = move.i + angleI;
					j = move.j + angleJ;
					// parcours pour chercher les cases que l'on peut retourner
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - this.activePlayer))
						{
						i += angleI;
						j += angleJ;
						}

					// Traitement pour retourner les pièces
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
						{
						//System.out.println("PTS: ("+i+","+j+")");
						int cpt = 1;
						// Retournement des pièces
						while((move.i + cpt * angleI) * angleI < i * angleI || (move.j + cpt * angleJ) * angleJ < j * angleJ)
							{
							grid[move.i + angleI * cpt][move.j + angleJ * cpt] = this.activePlayer;
							//System.out.println("FLIP: ("+(move.i + angleI * cpt)+","+(move.j + angleJ * cpt)+")");
							cpt++;
							flippedPiecesNb++;
							}
						}
					}
				}
			}

		//System.out.println("Pions retournés:" + flippedPiecesNb);

		// Mise à jour du nombre de pions de chaque joueur
		if (this.activePlayer == K_BLUE)
			{
			bluePiecesNb += flippedPiecesNb + 1;
			redPiecesNb -= flippedPiecesNb;
			}
		else
			{
			bluePiecesNb -= flippedPiecesNb;
			redPiecesNb += flippedPiecesNb + 1;
			}

		//System.out.println("Pions bleu=" + bluePiecesNb + "/ rouge =" + redPiecesNb);
		}

	public ArrayList<Position> rechercheCoupsPossibles(Position move)
		{
		int i = move.i;
		int j = move.j;
		//System.out.println("Pos Coup = (" + i + "," + j + ")");
		grid[i][j] = this.activePlayer;
		ArrayList<Position> availableMoves = new ArrayList<Position>();

		for(int angleI = -1; angleI <= 1; angleI++)
			{
			for(int angleJ = -1; angleJ <= 1; angleJ++)
				{
				if (angleI != 0 || angleJ != 0)
					{
					i = move.i + angleI;
					j = move.j + angleJ;
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - this.activePlayer))
						{
						i += angleI;
						j += angleJ;
						}

					// Prochaine case vide après des cases blanches
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == K_EMPTY && (i != move.i + angleI || j != move.j + angleJ))
						{
						// Stockage du pion dans un arraylist
						availableMoves.add(new Position(i, j));
						}
					}
				}
			}
		return availableMoves;
		}

	public ArrayList<Position> findPlayerPieces()
		{
		ArrayList<Position> playerPieces = new ArrayList<Position>();
		// Récupère les pions du joueur actif
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grid[i][j] == activePlayer)
					{
					// Trouver les pions du joueur actif
					playerPieces.add(new Position(i, j));
					}
				}
			}
		return playerPieces;
		}

	public ArrayList<Position> findAvailableMoves()
		{
		ArrayList<Position> playerPieces = findPlayerPieces();
		ArrayList<Position> operatorsAvailable = new ArrayList<Position>();

		for(Position move:playerPieces)
			{
			// Recherche coups possibles
			for(Position move1:rechercheCoupsPossibles(move))
				{
				if (!operatorsAvailable.contains(move1))
					{
					operatorsAvailable.add(move1);
					}
				}
			}
		//coupsPossibles = new ArrayList<Position>(operatorsAvailable);
		return operatorsAvailable;
		}

	public Board getNextBoard()
		{
		return new Board(this, null);//1 - this.activePlayer);
		}

	public void displayBoard()
		{
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (this.grid[i][j] == K_EMPTY)
					{
					System.out.print("x ");
					}
				else
					{
					System.out.print(this.grid[i][j] + " ");
					}
				}
			System.out.println();
			}
		System.out.println();
		}

	public void displayPointsGrid()
		{
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (this.pointsGrid[i][j] == K_EMPTY)
					{
					System.out.print("x ");
					}
				else
					{
					System.out.print(this.pointsGrid[i][j] + " ");
					}
				}
			System.out.println();
			}
		System.out.println();
		}

	// IMPORTANT METHODS
	public Board applyOp(Position move)
		{
		// return a new board with the move applied
		movesPlayed++;
		if (move != null)
			{
			if (move.i == 0 && move.j == 0)
				{
				pointsGrid[0][1] = 200;
				pointsGrid[1][0] = 200;
				}

			if (move.i == 7 && move.j == 0)
				{
				pointsGrid[6][0] = 200;
				pointsGrid[7][1] = 200;
				}

			if (move.i == 0 && move.j == 7)
				{
				pointsGrid[0][6] = 200;
				pointsGrid[1][7] = 200;
				}

			if (move.i == 7 && move.j == 7)
				{
				pointsGrid[6][7] = 200;
				pointsGrid[7][6] = 200;
				}
			}

		return new Board(this, move);
		}

	public boolean isFinal()
		{
		return (this.findAvailableMoves().isEmpty() || this.emptyCaseNb() == 0);
		}

	public int evalBoard()
		{
		int coefMobility = 10;
		int coefPosition = 7;
		int coefMaterial = 1;

		scoreMobility = this.findAvailableMoves().size();
		scoreMaterial = 0;
		scorePosition = 0;

		if (owner == K_BLUE)
			{
			scoreMaterial = bluePiecesNb;
			}
		else
			{
			scoreMaterial = redPiecesNb;
			}

		//Calcul du scorePosition
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grid[i][j] == owner)
					{
//					System.out.println("***********");
//					this.displayBoard();
//					System.out.println("***********");
//					this.displayPointsGrid();
//					System.out.println("***********");

					scorePosition += pointsGrid[i][j];
					System.out.println("PASS" + pointsGrid[i][j]);
					}
				else if (grid[i][j] == 1 - owner)
					{
					scorePosition -= pointsGrid[i][j];
				//	System.out.println("PASS1");
					}
				}
			}
		//System.out.println(">> POS"+ scorePosition);
		return coefMobility * scoreMobility + coefPosition * scorePosition + coefMaterial * scoreMaterial;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initBoard()
		{
		// Initialise la grille avec toutes les cases vides
		this.grid = new int[8][8];
		this.pointsGrid = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grid[i][j] = K_EMPTY;
				this.pointsGrid[i][j] = 0;
				}
			}

		// Initialise les 4 pions de base
		this.grid[3][3] = K_BLUE;
		this.grid[4][4] = K_BLUE;
		this.grid[3][4] = K_RED;
		this.grid[4][3] = K_RED;

		initPointsGrid();
		}

	private void initPointsGrid()
		{
		//4 cases centre
		this.pointsGrid[3][3] = 16;
		this.pointsGrid[4][4] = 16;
		this.pointsGrid[3][4] = 16;
		this.pointsGrid[4][3] = 16;

		//4Coins
		this.pointsGrid[0][0] = 500;
		this.pointsGrid[0][7] = 500;
		this.pointsGrid[7][0] = 500;
		this.pointsGrid[7][7] = 500;

		//4Coins +/- 1
		this.pointsGrid[0][1] = -150;
		this.pointsGrid[1][0] = -150;
		this.pointsGrid[1][7] = -150;
		this.pointsGrid[0][6] = -150;

		this.pointsGrid[7][1] = -150;
		this.pointsGrid[6][0] = -150;
		this.pointsGrid[7][6] = -150;
		this.pointsGrid[6][7] = -150;

		this.pointsGrid[7][1] = -150;
		this.pointsGrid[6][0] = -150;
		this.pointsGrid[7][6] = -150;
		this.pointsGrid[6][7] = -150;

		//
		this.pointsGrid[1][1] = -250;
		this.pointsGrid[6][1] = -250;
		this.pointsGrid[1][6] = -250;
		this.pointsGrid[6][6] = -250;

		//Bords
		this.pointsGrid[2][0] = 30;
		this.pointsGrid[0][2] = 30;
		this.pointsGrid[5][0] = 30;
		this.pointsGrid[0][5] = 30;

		this.pointsGrid[2][7] = 30;
		this.pointsGrid[7][2] = 30;
		this.pointsGrid[5][7] = 30;
		this.pointsGrid[7][5] = 30;

		this.pointsGrid[3][0] = 10;
		this.pointsGrid[0][3] = 10;
		this.pointsGrid[4][0] = 10;
		this.pointsGrid[0][4] = 10;

		this.pointsGrid[3][7] = 10;
		this.pointsGrid[7][3] = 10;
		this.pointsGrid[4][7] = 10;
		this.pointsGrid[7][4] = 10;

		//Bords centre
		this.pointsGrid[2][2] = 1;
		this.pointsGrid[5][5] = 1;
		this.pointsGrid[5][2] = 1;
		this.pointsGrid[2][5] = 1;

		this.pointsGrid[2][3] = 2;
		this.pointsGrid[2][4] = 2;
		this.pointsGrid[5][3] = 2;
		this.pointsGrid[5][4] = 2;

		this.pointsGrid[3][2] = 2;
		this.pointsGrid[4][2] = 2;
		this.pointsGrid[3][5] = 2;
		this.pointsGrid[4][5] = 2;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setActivePlayer(int activePlayer)
		{
		this.activePlayer = activePlayer;
		}

	public void setGrid(int[][] grid)
		{
		this.grid = grid;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int[][] getGrid()
		{
		return this.grid;
		}

	public int getActivePlayer()
		{
		return this.activePlayer;
		}

	public ArrayList<Position> getAvailableMoves()
		{
		return this.coupsPossibles;
		}

	public int getEval()
		{
		return this.eval;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private int grid[][];
	private int pointsGrid[][];
	private int activePlayer;
	private ArrayList<Position> coupsPossibles;
	private int bluePiecesNb;
	private int redPiecesNb;
	private int movesPlayed;
	private int eval;

	public int scoreMobility;
	public int scoreMaterial;
	public int scorePosition;

	private static final int K_EMPTY = -1;
	private static final int K_STABLE = 0;
	private static final int K_RED = 0;
	private static final int K_BLUE = 1;
	}
