/*
Exemple d'impl�mentation d'un joueur d'Othello. Cette impl�mentation sert uniquement
� d�montrer le principe, mais n'impl�mente aucune intelligence: les coups � jouer sont
simplement lus � la console!
*/

// Votre version sera dans Participants.<VosNoms>

package Participants.FroidevauxKaluznyNeuhaus;

import java.util.ArrayList;

// Pour l'interop�rabilit�: il faut une repr�sentation commune des coups!

// Vous devrez �tendre Othello.Joueur pour impl�menter votre propre joueur...
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
		this(activePlayer, 2, 2, 0, 1-activePlayer);
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
					// Initialisation de la position de recherche initiale (case � c�t� de la position intiale)
					i = move.i + angleI;
					j = move.j + angleJ;
					// parcours pour chercher les cases que l'on peut retourner
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - this.activePlayer))
						{
						i += angleI;
						j += angleJ;
						}

					// Traitement pour retourner les pi�ces
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
						{
						//System.out.println("PTS: ("+i+","+j+")");
						int cpt = 1;
						// Retournement des pi�ces
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

		//System.out.println("Pions retourn�s:" + flippedPiecesNb);

		// Mise � jour du nombre de pions de chaque joueur
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

					// Prochaine case vide apr�s des cases blanches
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

	// Met � jour la grille � partir du coup jou�
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

		//System.out.println("Pions retourn�s:" + nbPiecesRetournees);

		// Mise � jour du nombre de pions de chaque joueur
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
		// R�cup�re les pions du joueur actif
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
		// TODO: stabilit� des pi�ces > tr�s efficace
		int scoreEval = 0;
		int adversaire = 1 - owner;
		int nbPiecesIA, nbPiecesJoueur;
		//System.out.println("OWNER = " + owner);
		if (owner == K_BLUE)
			{
			nbPiecesIA = bluePiecesNb;
			nbPiecesJoueur = redPiecesNb;
			}
		else
			{
			nbPiecesIA = redPiecesNb;
			nbPiecesJoueur = bluePiecesNb;
			}

		//Si partie termin�e
		if (bluePiecesNb + redPiecesNb >= 64)
			{
			if (nbPiecesIA > nbPiecesJoueur)
				{
				scoreEval += 1000;
				}
			else
				{
				scoreEval -= 1000;
				}
			}

		//Si d�but de partie
		if (bluePiecesNb + redPiecesNb <= 15)
			{
			if (nbPiecesIA < nbPiecesJoueur)
				{
				scoreEval += 5;
				}
			else
				{
				scoreEval -= 5;
				}
			}

		//Si owner poss�de des pions dans les bords
		for(int i = 0; i < 8; i++)
			{
			if (this.grid[i][0] == owner)
				{
				scoreEval += 10;
				}
			else if (this.grid[i][0] == adversaire)
				{
				scoreEval -= 10;
				}
			}

		for(int i = 0; i < 8; i++)
			{
			if (this.grid[0][i] == owner)
				{
				scoreEval += 10;
				}
			else if (this.grid[i][0] == adversaire)
				{
				scoreEval -= 10;
				}
			}

		for(int i = 0; i < 8; i++)
			{
			if (this.grid[i][7] == owner)
				{
				scoreEval += 10;
				}
			else if (this.grid[i][0] == adversaire)
				{
				scoreEval -= 10;
				}
			}

		for(int i = 0; i < 8; i++)
			{
			if (this.grid[7][i] == owner)
				{
				scoreEval += 10;
				}
			else if (this.grid[i][0] == adversaire)
				{
				scoreEval -= 10;
				}
			}

		//Si owner poss�de des cases centrales
		if (this.grid[3][3] == owner)
			{
			scoreEval += 25;
			}
		if (this.grid[3][4] == owner)
			{
			scoreEval += 25;
			}
		if (this.grid[4][3] == owner)
			{
			scoreEval += 25;
			}
		if (this.grid[4][4] == owner)
			{
			scoreEval += 25;
			}

		//Si adversaire poss�de des cases centrales
		if (this.grid[3][3] == adversaire)
			{
			scoreEval -= 25;
			}
		if (this.grid[3][4] == adversaire)
			{
			scoreEval -= 25;
			}
		if (this.grid[4][3] == adversaire)
			{
			scoreEval -= 25;
			}
		if (this.grid[4][4] == adversaire)
			{
			scoreEval -= 25;
			}

		//Si owner poss�de des cases en coins
		if (this.grid[0][0] == owner)
			{
			scoreEval += 50;
			}
		if (this.grid[0][7] == owner)
			{
			scoreEval += 50;
			}
		if (this.grid[7][0] == owner)
			{
			scoreEval += 50;
			}
		if (this.grid[7][7] == owner)
			{
			scoreEval += 50;
			}

		//Si adversaire poss�de des cases en coins
		if (this.grid[0][0] == adversaire)
			{
			scoreEval -= 50;
			}
		if (this.grid[0][7] == adversaire)
			{
			scoreEval -= 50;
			}
		if (this.grid[7][0] == adversaire)
			{
			scoreEval -= 50;
			}
		if (this.grid[7][7] == adversaire)
			{
			scoreEval -= 50;
			}

		//Si owner poss�de 3 cases dans les coins
		if (this.grid[0][0] == owner && this.grid[1][0] == owner && this.grid[0][1] == owner)
			{
			scoreEval += 100;
			}
		if (this.grid[0][7] == owner && this.grid[1][7] == owner && this.grid[0][6] == owner)
			{
			scoreEval += 100;
			}
		if (this.grid[7][0] == owner && this.grid[7][1] == owner && this.grid[6][0] == owner)
			{
			scoreEval += 100;
			}
		if (this.grid[7][7] == owner && this.grid[7][6] == owner && this.grid[6][7] == owner)
			{
			scoreEval += 100;
			}

		//Si Joueur poss�de 3 cases dans les coins
		if (this.grid[0][0] == adversaire && this.grid[1][0] == adversaire && this.grid[0][1] == adversaire)
			{
			scoreEval -= 100;
			}

		if (this.grid[0][7] == adversaire && this.grid[1][7] == adversaire && this.grid[0][6] == adversaire)
			{
			scoreEval -= 100;
			}

		if (this.grid[7][0] == adversaire && this.grid[7][1] == adversaire && this.grid[6][0] == adversaire)
			{
			scoreEval -= 100;
			}

		if (this.grid[7][7] == adversaire && this.grid[7][6] == adversaire && this.grid[6][7] == adversaire)
			{
			scoreEval -= 100;
			}

		//Si owner poss�de 4 cases dans les coins
		if (this.grid[0][0] == owner && this.grid[1][0] == owner && this.grid[0][1] == owner && this.grid[1][1] == owner)
			{
			scoreEval += 150;
			}

		if (this.grid[0][7] == owner && this.grid[1][7] == owner && this.grid[0][6] == owner && this.grid[1][6] == owner)
			{
			scoreEval += 150;
			}

		if (this.grid[7][0] == owner && this.grid[7][1] == owner && this.grid[6][0] == owner && this.grid[6][1] == owner)
			{
			scoreEval += 150;
			}

		if (this.grid[7][7] == owner && this.grid[7][6] == owner && this.grid[6][7] == owner && this.grid[6][6] == owner)
			{
			scoreEval += 150;
			}

		//Si Joueur poss�de 4 cases dans les coins
		if (this.grid[0][0] == adversaire && this.grid[1][0] == adversaire && this.grid[0][1] == adversaire && this.grid[1][1] == adversaire)
			{
			scoreEval -= 150;
			}

		if (this.grid[0][7] == adversaire && this.grid[1][7] == adversaire && this.grid[0][6] == adversaire && this.grid[1][6] == adversaire)
			{
			scoreEval -= 150;
			}

		if (this.grid[7][0] == adversaire && this.grid[7][1] == adversaire && this.grid[6][0] == adversaire && this.grid[6][1] == adversaire)
			{
			scoreEval -= 150;
			}

		if (this.grid[7][7] == adversaire && this.grid[7][6] == adversaire && this.grid[6][7] == adversaire && this.grid[6][6] == adversaire)
			{
			scoreEval -= 150;
			}

		//System.out.println("VAL="+scoreEval);
		return scoreEval;

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
