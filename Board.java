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
			System.arraycopy(plateau.stableGrid[i], 0, this.stableGrid[i], 0, 8);
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

	public void displayStableGrid()
		{
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (this.stableGrid[i][j] == K_EMPTY)
					{
					System.out.print("x ");
					}
				else
					{
					System.out.print(this.stableGrid[i][j] + " ");
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
		return new Board(this, move);
		}

	public boolean isFinal()
		{
		return (this.findAvailableMoves().isEmpty() || this.emptyCaseNb() == 0);
		}

	public int evalBoard()
		{
		// TODO: stabilité des pièces > très efficace

		// RECHERCHE DES PIONS DEFINITIFS
		// Pour chaque pions sur le plateau
		/*
		coupsPossibles = findAvailableMoves();
		if (movesPlayed > 0)
			{
			if (coupsPossibles != null)
				{
				for(Position pos:coupsPossibles)
					{
					int posI = pos.i;
					int posJ = pos.j;

					int stable[] = new int[4];

					// Si le coup est déjà stable, on ne fait rien
					if (stableGrid[pos.i][pos.j] == K_EMPTY)
						{
						// Si le pion se trouve dans un coin
						if (posI == 0 && posJ == 0 || posI == 7 && posJ == 0 || posI == 0 && posJ == 7 || posI == 7 && posJ == 7)
							{
							if (grid[posI][posJ] == owner)
								{
								stableGrid[posI][posJ] = 1;
								}
							else
								{
								stableGrid[posI][posJ] = -1;
								}
							}
						else
							{
							// Variation de i
							//for(int angleI = -1; angleI <= 1; angleI++)
								{
								// variation de j
								//for(int angleJ = -1; angleJ <= 1; angleJ++)
									{
									// On ne prend pas en compte le cas ou i et j sont nuls
									//if (!(angleI == 0 && angleJ == 0))
										{

										/******* HORIZONTAL ******** /
										int cpt = 0;
										// Parcours droit
										while(posI + cpt <= 7 && posI + cpt > 0 && grid[posI + cpt][posJ] == owner)
											{
											//
											cpt++;
											}

										if (posI + cpt >= 8)
											{
											stable[0] = 1;
											}
										else
											{
											int tmp = grid[posI + cpt][posJ];

											// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
											// Parcours gauche
											cpt = 0;
											while(posI + cpt < 8 && posI + cpt > 0 && grid[posI + cpt][posJ] == owner)
												{
												cpt--;
												}
											if (posI + cpt < 0)
												{
												// PION DEFINITIF sur l'horizontal
												stable[0] = 1;
												}
											else
												{
												if (tmp == K_EMPTY && (grid[posI + cpt][posJ] != 1 - owner || grid[posI + cpt][posJ] != K_EMPTY) || tmp == 1 - owner && grid[posI + cpt][posJ] != K_EMPTY)
													{
													stable[0] = 1;
													}
												}
											}





										/********* VERTICAL ********** /
										cpt = 0;
										// Parcours droit
										while(posJ + cpt < 8 && posJ + cpt >= 0 && grid[posI][posJ + cpt] == owner)
											{
											//
											cpt++;
											}
										if (posJ + cpt >= 8)
											{
											stable[1] = 1;
											}
										else
											{
											int tmp = grid[posI][posJ + cpt];

											// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
											// Parcours gauche
											cpt = 0;
											while(posJ + cpt < 8 && posJ + cpt >= 0 && grid[posI][posJ + cpt] == owner)
												{
												cpt--;
												}
											if (posJ + cpt < 0)
												{
												// PION DEFINITIF sur l'horizontal
												stable[1] = 1;
												}
											else
												{
												if (tmp == K_EMPTY && grid[posI][posJ + cpt] != 1 - owner || tmp == 1 - owner && grid[posI][posJ + cpt] != K_EMPTY)
													{
													stable[1] = 1;
													}
												}
											}




										/********* DIAGO BAS ********** /
										cpt = 0;
										// Parcours droit
										while(posI + cpt < 8 && posI + cpt >= 0 && posJ + cpt < 8 && posJ + cpt >= 0 && grid[posI + cpt][posJ + cpt] == owner)
											{
											//
											cpt++;
											}
										if (posJ + cpt >= 8 || posI + cpt >= 8)
											{
											stable[2] = 1;
											}
										else
											{
											int tmp = grid[posI + cpt][posJ + cpt];

											// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
											// Parcours gauche
											cpt = 0;
											while(posI + cpt < 8 && posI + cpt >= 0 && posJ + cpt < 8 && posJ + cpt >= 0 && grid[posI + cpt][posJ + cpt] == owner)
												{
												cpt--;
												}
											if (posJ + cpt < 0 && posI + cpt < 0)
												{
												// PION DEFINITIF sur l'horizontal
												stable[2] = 1;
												}
											else
												{
												if (tmp == K_EMPTY && grid[posI+cpt][posJ + cpt] != 1 - owner || tmp == 1 - owner && grid[posI+cpt][posJ + cpt] != K_EMPTY)
													{
													stable[2] = 1;
													}
												}
											}



										/********* DIAGO HAUT ********** /
										cpt = 0;
										// Parcours droit
										while(posI + cpt < 7 && posI + cpt > 0 && posJ - cpt < 7 && posJ - cpt > 0 && grid[posI + cpt][posJ - cpt] == owner)
											{
											//
											cpt++;
											}
										if (posJ + cpt >= 8 || posI + cpt < 0)
											{
											stable[3] = 1;
											}
										else
											{
											//System.out.println("POS DH ("+ posI +","+posJ+") +cpt="+cpt);
											int tmp = grid[posI + cpt][posJ - cpt];

											// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
											// Parcours gauche
											cpt = 0;
											while(posI + cpt < 8 && posI + cpt >= 0 && posJ - cpt < 8 && posJ - cpt >= 0 && grid[posI + cpt][posJ - cpt] == owner)
												{
												cpt--;
												}
											if (posJ - cpt >= 8 && posI + cpt < 0)
												{
												// PION DEFINITIF sur l'horizontal
												stable[3] = 1;
												}
											else
												{
												if (tmp == K_EMPTY && grid[posI+cpt][posJ - cpt] != 1 - owner || tmp == 1 - owner && grid[posI+cpt][posJ - cpt] != K_EMPTY)
													{
													stable[3] = 1;
													}
												}
											}



										if (stable[0] == 1 && stable[1] == 1 && stable[2] == 1 && stable[3] == 1 )
											{
											if (grid[posI][posJ] == owner)
												{
													stableGrid[posI][posJ] = 1;
													}
											else
												{
													stableGrid[posI][posJ] = -1;
													}
											}
										}
									}
								}
							}
						}
					}
				}
			}*/

		int scoreEval = 0;
		int adversaire = 1 - owner;
		int nbPiecesIA, nbPiecesJoueur;

		for(int i = 0; i < 64; i++)
			{
				scoreEval += stableGrid[i / 8][i % 8]*250;
			}
		System.out.println("STABLE" + scoreEval);

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

		//Si partie terminée
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

		//Si début de partie
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

		//Si owner possède des pions dans les bords
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

		//Si owner possède des cases centrales
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

		//Si adversaire possède des cases centrales
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

		//Si owner possède des cases en coins
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

		//Si adversaire possède des cases en coins
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

		//Si owner possède 3 cases dans les coins
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

		//Si Joueur possède 3 cases dans les coins
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

		//Si owner possède 4 cases dans les coins
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

		//Si Joueur possède 4 cases dans les coins
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
		//scoreEval += 10*this.findAvailableMoves().size();
		//scoreEval *= this.findAvailableMoves().size();
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
		this.stableGrid = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grid[i][j] = K_EMPTY;
				this.stableGrid[i][j] = K_EMPTY;
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
	private int stableGrid[][];
	private int activePlayer;
	private ArrayList<Position> coupsPossibles;
	private int bluePiecesNb;
	private int redPiecesNb;
	private int movesPlayed;
	private int eval;

	private static final int K_EMPTY = -1;
	private static final int K_STABLE = 0;
	private static final int K_RED = 0;
	private static final int K_BLUE = 1;
	}
