/*
Exemple d'implÈmentation d'un joueur d'Othello. Cette implÈmentation sert uniquement
‡ dÈmontrer le principe, mais n'implÈmente aucune intelligence: les coups ‡ jouer sont
simplement lus ‡ la console!
*/

// Votre version sera dans Participants.<VosNoms>

package Participants.FroidevauxKaluznyNeuhaus;

import java.util.ArrayList;
import java.util.HashSet;

import Othello.Move;

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

	public Board(Board board, int activePlayer)
		{
		this(activePlayer, board.bluePiecesNb, board.redPiecesNb, board.movesPlayed);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(board.grid[i], 0, this.grid[i], 0, 8);
			}
		}

	public Board(Board plateau)
		{
		this(plateau.activePlayer, plateau.bluePiecesNb, plateau.redPiecesNb, plateau.movesPlayed);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grid[i], 0, this.grid[i], 0, 8);
			}
		}

	public Board(Board oldState, Move move)
		{
		this(oldState);
		this.updateBoard(move);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public int emptyCaseNb()
		{
		return 64 - bluePiecesNb - redPiecesNb;
		}

	public void updateBoard(Move move1)
		{
		Move move = new Move(move1.j, move1.i);
		int i = move.i;
		int j = move.j;
		System.out.println("Move Pos = (" + i + "," + j + ")");
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
						int cpt = 1;
						// Retournement des pièces
						while((move.i + cpt * angleI) * angleI < i * angleI || (move.j + cpt * angleJ) * angleJ < j * angleJ)
							{
							grid[move.i + angleI * cpt][move.j + angleJ * cpt] = this.activePlayer;
							cpt++;
							flippedPiecesNb++;
							}
						}
					}
				}
			}

		System.out.println("Pions retournés:" + flippedPiecesNb);

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

		System.out.println("Pions bleu=" + bluePiecesNb + "/ rouge =" + redPiecesNb);
		}

	public ArrayList<Move> rechercheCoupsPossibles(Move move1)
		{
		Move move = new Move(move1.j, move1.i);
		int i = move.i;
		int j = move.j;
		System.out.println("Pos Coup = (" + i + "," + j + ")");
		grid[i][j] = this.activePlayer;
		ArrayList<Move> availableMoves = new ArrayList<Move>();

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
						availableMoves.add(new Move(i, j));
						}
					}
				}
			}
		return availableMoves;
		}

	/*
		// Met à jour la grille à partir du coup joué
		public void miseAJourGrille(Move coup1)
			{
			Move coup = new Move(coup1.j, coup1.i);
			int i = coup.i;
			int j = coup.j;
			System.out.println("Pos Coup = (" + i + "," + j + ")");
			grille[i][j] = this.joueurActif;
			int nbPiecesRetournees = 0;
			//Traitement vertical (Bas)
			j++;
			while(j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				System.out.println("Test" + j);
				j++;
				}

			if (j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				for(int j2 = coup.j + 1; j2 < j; j2++)
					{
					grille[i][j2] = this.joueurActif;
					System.out.println("VB> (" + i + "," + j2 + ")");
					nbPiecesRetournees++;
					//				if (this.joueurActif == K_BLEU)
					//
					}
				}

			//Traitement vertical (Haut)
			j = coup.j - 1;
			while(j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				j--;
				}

			if (j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				for(int j2 = coup.j - 1; j2 > j; j2--)
					{
					grille[i][j2] = this.joueurActif;
					System.out.println("VH> (" + i + "," + j2 + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement horizontal (Gauche)
			j = coup.j;
			i = coup.i - 1;
			while(i < 8 && i >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i--;
				}

			if (i < 8 && i >= 0 && grille[i][j] == this.joueurActif)
				{
				for(int i2 = coup.i - 1; i2 > i; i2--)
					{
					grille[i2][j] = this.joueurActif;
					System.out.println("HG> (" + i2 + "," + j + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement horizontal (Droite)
			i = coup.i + 1;
			while(i < 8 && i >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i++;
				}

			if (i < 8 && i >= 0 && grille[i][j] == this.joueurActif)
				{
				for(int i2 = coup.i + 1; i2 < i; i2++)
					{
					grille[i2][j] = this.joueurActif;
					System.out.println("HD> (" + i2 + "," + j + ")");
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Bas,Droite)
			i = coup.i + 1;
			j = coup.j + 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i++;
				j++;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt < i)
					{
					grille[coup.i + cpt][coup.j + cpt] = this.joueurActif;
					System.out.println("DBD> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt++;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Haut,Gauche)
			i = coup.i - 1;
			j = coup.j - 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i--;
				j--;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grille[coup.i + cpt][coup.j + cpt] = this.joueurActif;
					System.out.println("DHG> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt--;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Haut,Droite)
			i = coup.i + 1;
			j = coup.j - 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i++;
				j--;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grille[coup.i + cpt][coup.j - cpt] = this.joueurActif;
					System.out.println("DHD> (" + (coup.i + cpt) + "," + (coup.i + cpt) + ")");
					cpt++;
					nbPiecesRetournees++;
					}
				}

			//Traitement Diagonal (Bas,Gauche)
			i = coup.i - 1;
			j = coup.j + 1;
			while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif)
				{
				i--;
				j++;
				}

			if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
				{
				nbPiecesRetournees--;
				int cpt = 0;
				while(coup.i + cpt > i)
					{
					grille[coup.i + cpt][coup.j - cpt] = this.joueurActif;
					System.out.println("DBD> (" + (coup.i + cpt) + "," + (coup.i - cpt) + ")");
					nbPiecesRetournees++;
					cpt--;
					}
				}

			System.out.println("Pions retournés:" + nbPiecesRetournees);

			// Mise à jour du nombre de pions de chaque joueur
			if (this.joueurActif == K_BLEU)
				{
				nbPionsBleu += nbPiecesRetournees + 1;
				nbPionsRouge -= nbPiecesRetournees;
				}
			else
				{
				nbPionsBleu -= nbPiecesRetournees;
				nbPionsRouge += nbPiecesRetournees + 1;
				}
			System.out.println("Pions bleu=" + nbPionsBleu + "/ rouge =" + nbPionsRouge);
			}
		*/

	public ArrayList<Move> findPlayerPieces()
		{
		ArrayList<Move> playerPieces = new ArrayList<Move>();
		// Récupère les pions du joueur actif
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grid[i][j] == activePlayer)
					{
					// Trouver les coups possibles
					playerPieces.add(new Move(i, j));
					}
				}
			}
		return playerPieces;
		}

	public HashSet<Move> findAvailableMoves()
		{
		ArrayList<Move> playerPieces = findPlayerPieces();
		HashSet<Move> operatorsAvailable = new HashSet<Move>();

		for(Move move:playerPieces)
			{
			// Recherche coups possibles
			for(Move move1:rechercheCoupsPossibles(move))
				{
				operatorsAvailable.add(move1);
				}
			}

		return operatorsAvailable;
		}

	public Board getNextBoard()
		{
		return new Board(this, 1 - this.activePlayer);
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
		}



	// IMPORTANT METHODS
	public Board applyOp(Move move)
		{
		// return a new board with the move applied
		return new Board(this, move);
		}

	public boolean isFinal()
		{
		return (this.findAvailableMoves().isEmpty() || this.emptyCaseNb()==0);
		}

	public int evalBoard()
		{
		// TODO Auto-generated method stub
		// Evaluate the current state of the board
		return 0;
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

	public Move[] getAvailableMoves()
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
	private Move coupsPossibles[];
	private int bluePiecesNb;
	private int redPiecesNb;
	private int movesPlayed;
	private int eval;

	private static final int K_EMPTY = -1;
	private static final int K_RED = 0;
	private static final int K_BLUE = 1;
	}
