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

	public Board(int activePlayer, int bluePiecesNb, int redPiecesNb, int movesPlayed)
		{
		this.activePlayer = activePlayer;
		this.bluePiecesNb = bluePiecesNb;
		this.redPiecesNb = redPiecesNb;
		this.movesPlayed = movesPlayed;
		initBoard();
		this.eval = evalBoard();
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
		this(plateau.activePlayer, plateau.bluePiecesNb, plateau.redPiecesNb, plateau.movesPlayed);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grid[i], 0, this.grid[i], 0, 8);
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


		// Met à jour la grille à partir du coup joué
		/*public void updateBoard(Position coup)
			{
			//Move coup = new Move(coup1.j, coup1.i);
			int i = coup.i;
			int j = coup.j;
			System.out.println("Pos Coup = (" + i + "," + j + ")");
			grid[i][j] = this.activePlayer;
			int nbPiecesRetournees = 0;
			//Traitement vertical (Bas)
			j++;
			while(j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				//System.out.println("Test" + j);
				j++;
				}

			if (j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				for(int j2 = coup.j + 1; j2 < j; j2++)
					{
					grid[i][j2] = this.activePlayer;
					System.out.println("VB> (" + i + "," + j2 + ")");
					nbPiecesRetournees++;
					//				if (this.joueurActif == K_BLEU)
					//
					}
				}

			//Traitement vertical (Haut)
			j = coup.j - 1;
			while(j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				j--;
				}

			if (j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				for(int j2 = coup.j - 1; j2 > j; j2--)
					{
					grid[i][j2] = this.activePlayer;
					System.out.println("VH> (" + i + "," + j2 + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement horizontal (Gauche)
			j = coup.j;
			i = coup.i - 1;
			while(i < 8 && i >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i--;
				}

			if (i < 8 && i >= 0 && grid[i][j] == this.activePlayer)
				{
				for(int i2 = coup.i - 1; i2 > i; i2--)
					{
					grid[i2][j] = this.activePlayer;
					System.out.println("HG> (" + i2 + "," + j + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement horizontal (Droite)
			i = coup.i + 1;
			while(i < 8 && i >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i++;
				}

			if (i < 8 && i >= 0 && grid[i][j] == this.activePlayer)
				{
				for(int i2 = coup.i + 1; i2 < i; i2++)
					{
					grid[i2][j] = this.activePlayer;
					System.out.println("HD> (" + i2 + "," + j + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Bas,Droite)
			i = coup.i + 1;
			j = coup.j + 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i++;
				j++;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt < i)
					{
					grid[coup.i + cpt][coup.j + cpt] = this.activePlayer;
					System.out.println("DBD> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt++;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Haut,Gauche)
			i = coup.i - 1;
			j = coup.j - 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i--;
				j--;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grid[coup.i + cpt][coup.j + cpt] = this.activePlayer;
					System.out.println("DHG> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt--;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Haut,Droite)
			i = coup.i + 1;
			j = coup.j - 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i++;
				j--;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grid[coup.i + cpt][coup.j - cpt] = this.activePlayer;
					System.out.println("DHD> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt++;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Bas,Gauche)
			i = coup.i - 1;
			j = coup.j + 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] != K_EMPTY && grid[i][j] != this.activePlayer)
				{
				i--;
				j++;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grid[coup.i + cpt][coup.j - cpt] = this.activePlayer;
					System.out.println("DBD> (" + (coup.i + cpt) + "," + (coup.i - cpt) + ")");
					nbPiecesRetournees++;
					cpt--;
					}
				}

			//System.out.println("Pions retournés:" + nbPiecesRetournees);

			// Mise à jour du nombre de pions de chaque joueur
			if (this.activePlayer == K_BLUE)
				{
				bluePiecesNb += nbPiecesRetournees + 1;
				redPiecesNb -= nbPiecesRetournees;
				}
			else
				{
				bluePiecesNb -= nbPiecesRetournees;
				redPiecesNb += nbPiecesRetournees + 1;
				}
			//System.out.println("Pions bleu=" + bluePiecesNb + "/ rouge =" + redPiecesNb);
			System.out.println("********");
			}*/


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


	// IMPORTANT METHODS
	public Board applyOp(Position move)
		{
		// return a new board with the move applied
		return new Board(this, move);
		}


	public boolean isFinal()
		{
		return (this.findAvailableMoves().isEmpty() || this.emptyCaseNb() == 0);
		}


	public int evalBoard()
		{
		// TODO Auto-generated method stub
		// Evaluate the current state of the board
		return 1;//(int)(Math.random() * 100);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initBoard()
		{
		// Initialise la grille avec toutes les cases vides
		this.grid = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grid[i][j] = K_EMPTY;
				}
			}

		// Initialise les 4 pions de base
		this.grid[3][3] = K_BLUE;
		this.grid[4][4] = K_BLUE;
		this.grid[3][4] = K_RED;
		this.grid[4][3] = K_RED;
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

	public Position[] getAvailableMoves()
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
	private int activePlayer;
	private Position coupsPossibles[];
	private int bluePiecesNb;
	private int redPiecesNb;
	private int movesPlayed;
	private int eval;

	private static final int K_EMPTY = -1;
	private static final int K_RED = 0;
	private static final int K_BLUE = 1;
	}
