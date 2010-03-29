/*
Cette classe permet de gÈrer le plateau de jeu.
On y trouve les mÈthodes pour mettre ‡ jour le plateau ainsi que celles pour Èvaluer un Ètat de jeu.
*/

package Participants.FroidevauxKaluznyNeuhaus;

import java.util.ArrayList;

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
		}


	public Board(int activePlayer)
		{
		this(0, 2, 2, 0, activePlayer);
		}


	public Board(Board plateau)
		{
		this(plateau.activePlayer, plateau.bluePiecesNb, plateau.redPiecesNb, plateau.movesPlayed, plateau.owner);
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grid[i][j] = plateau.grid[i][j];
				this.pointsGrid[i][j] = plateau.pointsGrid[i][j];
				this.stableGrid[i][j] = plateau.stableGrid[i][j];
				}
			}
		}


	public Board(Board oldState, Position move)
		{
		this(oldState);
		// Applique le coup au plateau
		if (move != null)
			{
			this.updateBoard(move);
			}

		// Met ‡ jour la valeur de stabilitÈ du plateau
		ArrayList<Position> availableMoves = findPlayerPieces();
		if (movesPlayed > 0 && availableMoves != null)
			{
			for(Position pos:availableMoves)
				{
				updateStability(pos);
				}
			}

		// Change le joueur actif
		this.activePlayer = 1 - this.activePlayer;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	// Renvoie le nombre de case vide
	public int emptyCaseNb()
		{
		return 64 - bluePiecesNb - redPiecesNb;
		}

	// Retourne les pions suite au coup passÈ en paramËtre
	public void updateBoard(Position move)
		{
		int i = move.i;
		int j = move.j;

		// Attribue le coup jouÈ au joueur actid
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
					// Initialisation de la position de recherche initiale (case à‡ cÙôÈté de la position initiale)
					i = move.i + angleI;
					j = move.j + angleJ;
					// parcours pour chercher les cases que l'on peut retourner
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - this.activePlayer))
						{
						i += angleI;
						j += angleJ;
						}

					// Traitement pour retourner les piËces
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == this.activePlayer)
						{
						int cpt = 1;
						// Retournement des pièËces
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
		}


	// Recherche tous les coups possibles ‡ partir de la position passÈ en paramËtre
	// POUR LE JOUEUR ACTIF
	public ArrayList<Position> findAvailableMoves(Position move)
		{
		int i = move.i;
		int j = move.j;

		ArrayList<Position> availableMoves = new ArrayList<Position>();

		for(int angleI = -1; angleI <= 1; angleI++)
			{
			for(int angleJ = -1; angleJ <= 1; angleJ++)
				{
				if (angleI != 0 || angleJ != 0)
					{
					i = move.i + angleI;
					j = move.j + angleJ;
					// Tant que l'on peut sauter par dessus le(s) pion(s) de l'autre joueur, on continue
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - this.activePlayer))
						{
						i += angleI;
						j += angleJ;
						}

					// Prochaine case vide aprËès des cases blanches
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == K_EMPTY && (i != move.i + angleI || j != move.j + angleJ))
						{
						// Stockage du pion dans un arraylist
						availableMoves.add(new Position(i, j));
						}
					}
				}
			}

		// Renvoie tous les coups trouvÈs
		return availableMoves;
		}


	// Recherche tous les coups possibles ‡ partir de la position passÈ en paramËtre
	// POUR L'IA (crÈateur du plateau)
	public ArrayList<Position> rechercheCoupsPossiblesOwner(Position move)
		{
		int i = move.i;
		int j = move.j;

		ArrayList<Position> availableMoves = new ArrayList<Position>();

		for(int angleI = -1; angleI <= 1; angleI++)
			{
			for(int angleJ = -1; angleJ <= 1; angleJ++)
				{
				if (angleI != 0 || angleJ != 0)
					{
					i = move.i + angleI;
					j = move.j + angleJ;
					// Tant que l'on peut sauter par dessus le(s) pion(s) de l'autre joueur, on continue
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == (1 - owner))
						{
						i += angleI;
						j += angleJ;
						}

					// Prochaine case vide aprËs des cases blanches
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grid[i][j] == K_EMPTY && (i != move.i + angleI || j != move.j + angleJ))
						{
						// Stockage du pion dans un arraylist
						availableMoves.add(new Position(i, j));
						}
					}
				}
			}

		// Renvoie tous les coups trouvÈs
		return availableMoves;
		}


	// Renvoie la liste de tous les pions de l'IA (crÈateur du plateau)
	public ArrayList<Position> findOwnerPieces()
		{
		ArrayList<Position> ownerPieces = new ArrayList<Position>();

		// Récupère les pions du joueur actif
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grid[i][j] == owner)
					{
					// Ajouter les pions du joueur actif
					ownerPieces.add(new Position(i, j));
					}
				}
			}
		return ownerPieces;
		}


	// Renvoie la liste de tous les pions du joueur actif
	public ArrayList<Position> findPlayerPieces()
		{
		ArrayList<Position> playerPieces = new ArrayList<Position>();

		// RéÈcupËère les pions du joueur actif
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


	// Recherche tous les coups possibles ‡ partir des pions de l'IA
	// POUR L'IA (crÈateur du plateau)
	public ArrayList<Position> findOwnerAvailableMoves()
		{
		ArrayList<Position> ownerPieces = findOwnerPieces();
		ArrayList<Position> operatorsAvailable = new ArrayList<Position>();
		for(Position move:ownerPieces)
			{
			// Recherche coups possibles
			for(Position move1:rechercheCoupsPossiblesOwner(move))
				{
				if (!operatorsAvailable.contains(move1))
					{
					operatorsAvailable.add(move1);
					}
				}
			}
		return operatorsAvailable;
		}


	// Recherche tous les coups possibles ‡ partir des pions du joueur actif
	// POUR LE JOUEUR ACTIF
	public ArrayList<Position> findAvailableMoves()
		{
		ArrayList<Position> playerPieces = findPlayerPieces();
		ArrayList<Position> operatorsAvailable = new ArrayList<Position>();

		for(Position move:playerPieces)
			{
			// Recherche coups possibles
			for(Position move1:findAvailableMoves(move))
				{
				if (!operatorsAvailable.contains(move1))
					{
					operatorsAvailable.add(move1);
					}
				}
			}
		return operatorsAvailable;
		}


	// Met ‡ jour la stabilitÈ d'un pion dont la position est passÈe en paramËtre
	public void updateStability(Position pos)
		{
		if (pos != null)
			{
			int posI = pos.i;
			int posJ = pos.j;

			// Tableau pour stocker le rÈsultat de tous les test
			boolean stable[] = new boolean[4];

			// Si le coup est déÈj‡à stable, on ne fait rien
			if (stableGrid[pos.i][pos.j] == K_EMPTY)
				{
				// Si le pion se trouve dans un coin
				if (posI == 0 && posJ == 0 || posI == 7 && posJ == 0 || posI == 0 && posJ == 7 || posI == 7 && posJ == 7)
					{
					if (grid[posI][posJ] == owner)
						{
						stableGrid[posI][posJ] = owner;

						}
					else
						{
						stableGrid[posI][posJ] = 1-owner;
						}
					}
				else
					{
					/******* PARCOURS HORIZONTAL ********/
					int cpt = 0;
					// Parcours droit
					while(posI + cpt <= 7 && grid[posI + cpt][posJ] == activePlayer)
						{
						cpt++;
						}

					if (posI + cpt >= 8)
						{
						stable[0] = true;
						}
					else
						{
						// Parcours dans l'autre sens en cherchant le compléÈmentaire (vide ou case adverse)
						// Parcours gauche
						cpt = 0;
						while(posI + cpt >= 0 && grid[posI + cpt][posJ] == activePlayer)
							{
							cpt--;
							}

						if (posI + cpt < 0)
							{
							// PION DEFINITIF sur l'horizontal
							stable[0] = true;
							}
						else
							{
							// VÈrification que la ligne ne soit pas pleine
							cpt = 0;
							while(cpt <= 7 && grid[cpt][posJ] != K_EMPTY)
								{
								cpt++;
								}

							if (cpt >= 8)
								{
								stable[0] = true;
								}
							else
								{
								return;
								}
							}
						}

					/********* PARCOURS VERTICAL **********/
					cpt = 0;
					// Parcours droit
					while(posJ + cpt <= 7 && grid[posI][posJ + cpt] == activePlayer)
						{
						//
						cpt++;
						}
					if (posJ + cpt >= 8)
						{
						stable[1] = true;
						}
					else
						{
						// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
						// Parcours gauche
						cpt = 0;
						while(posJ + cpt >= 0 && grid[posI][posJ + cpt] == activePlayer)
							{
							cpt--;
							}

						if (posJ + cpt < 0)
							{
							// PION DEFINITIF sur l'horizontal
							stable[1] = true;
							}
						else
							{
							// VÈrification que la ligne ne soit pas pleine
							cpt = 0;
							while(cpt <= 7 && grid[posI][cpt] != K_EMPTY)
								{
								cpt++;
								}

							if (cpt >= 8)
								{
								stable[1] = true;
								}
							else
								{
								return;
								}
							}
						}

					/********* PARCOURS DIAGO BAS **********/
					cpt = 0;
					// Parcours droit
					while(posI + cpt <= 7 && posJ + cpt <= 7 && grid[posI + cpt][posJ + cpt] == activePlayer)
						{
						cpt++;
						}

					if (posI + cpt >= 8 || posJ + cpt >= 8)
						{
						stable[2] = true;
						}
					else
						{
						// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
						// Parcours gauche
						cpt = 0;
						while(posI + cpt >= 0 && posJ + cpt >= 0 && grid[posI + cpt][posJ + cpt] == activePlayer)
							{
							cpt--;
							}
						if (posI + cpt < 0 || posJ + cpt < 0)
							{
							// PION DEFINITIF sur la diagonale
							stable[2] = true;
							}
						else
							{
							// VÈrification que la ligne ne soit pas pleine
							cpt = 0;
							while(posI + cpt >= 0 && posJ + cpt >= 0 && grid[posI + cpt][posJ + cpt] != K_EMPTY)
								{
								cpt--;
								}
							if (posI + cpt < 0 || posJ + cpt < 0)
								{
								cpt = 0;
								while(posI + cpt <= 7 && posJ + cpt <= 7 && grid[posI + cpt][posJ + cpt] != K_EMPTY)
									{
									cpt++;
									}

								if (posI + cpt >= 8 || posJ + cpt >= 8)
									{
									stable[2] = true;
									}
								else
									{
									return;
									}
								}
							else
								{
								return;
								}
							}

						}

					/********* PARCOURS DIAGO HAUT **********/
					cpt = 0;
					// Parcours droit
					while(posI + cpt <= 7 && posJ - cpt >= 0 && grid[posI + cpt][posJ - cpt] == activePlayer)
						{
						cpt++;
						}

					if (posI + cpt >= 8 || posJ - cpt < 0)
						{
						stable[3] = true;
						}
					else
						{
						// Parcours dans l'autre sens en cherchant le complémentaire (vide ou case adverse)
						// Parcours gauche
						cpt = 0;
						while(posI + cpt >= 0 && posJ - cpt <= 7 && grid[posI + cpt][posJ - cpt] == activePlayer)
							{
							cpt--;
							}
						if (posI + cpt < 0 || posJ - cpt >= 8)
							{
							// PION DEFINITIF sur l'horizontal
							stable[3] = true;
							}
						else
							{
							// VÈrification que la ligne ne soit pas pleine
							cpt = 0;
							while(posI + cpt >= 0 && posJ - cpt <= 7 && grid[posI + cpt][posJ - cpt] != K_EMPTY)
								{
								cpt--;
								}
							if (posI + cpt < 0 || posJ - cpt >= 8)
								{
								cpt = 0;
								while(posI + cpt <= 7 && posJ - cpt >= 0 && grid[posI + cpt][posJ - cpt] != K_EMPTY)
									{
									cpt++;
									}

								if (posI + cpt >= 8 || posJ - cpt < 0)
									{
									stable[3] = true;
									}
								else
									{
									return;
									}
								}
							else
								{
								return;
								}
							}
						}

					// Si tous les tests ont passÈs, on rend la case stable
					if (stable[0] && stable[1] && stable[2] && stable[3])
						{
						if (activePlayer == owner)
							{
							stableGrid[posI][posJ] = owner;
							}
						else if (activePlayer == 1 - owner)
							{
							stableGrid[posI][posJ] = 1-owner;
							}
						}
					}
				}
			}
		}


	// Affichage du plateau de jeu normal
	public void displayBoard()
		{
		for(int j = 0; j < 8; j++)
			{
			for(int i = 0; i < 8; i++)
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


	// Affichage du plateau de jeu de rÈpartition des points
	public void displayPointsGrid()
		{
		for(int j = 0; j < 8; j++)
			{
			for(int i = 0; i < 8; i++)
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


	// Affichage du plateau de jeu donnant les cases stables
	public void displayStableGrid()
		{
		for(int j = 0; j < 8; j++)
			{
			for(int i = 0; i < 8; i++)
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


	// Applique le coup fourni en paramËtre et renvoie le plateau rÈsultant
	public Board applyOp(Position move)
		{
		// Met ‡ jour le nombre de coups jouÈs
		movesPlayed++;

		return new Board(this, move);
		}


	// DÈtermine si le plateau est jouable ou non
	public boolean isFinal()
		{
		return (this.findAvailableMoves().isEmpty() || this.emptyCaseNb() == 0);
		}


	// Evalue la stabilitÈ du plateau de jeu
	public int evalStability()
		{
		int val = 0;
		for(int i = 0; i <= 7; i++)
			{
			for(int j = 0; j <= 7; j++)
				{
				if (stableGrid[i][j] == owner)
					{
					val += 1;
					}
				else if (stableGrid[i][j] == 1 - owner)
					{
					val -= 1;
					}
				}
			}

		if (val == 64)
			{
			val = 100;
			}
		else if (val == -64)
			{
			val = -100;
			}
		return val;
		}


	// Evalue l'Ètat d'un plateau de jeu
	public int evalBoard()
		{
		// DÈfinition des coefficients pour les diffÈrents critËres
		int coefMobility = 0;
		int coefPosition = 0;
		int coefMaterial = 0;
		int coefStability = 0;

		int adversaire = 1 - owner;

		// Variation des coefficients au long de la partie:
		// Debut de jeu
		if (bluePiecesNb + redPiecesNb <= 35)
			{
			coefMobility = 1000;
			coefPosition = 300;
			coefMaterial = 1;

			}
		// Milieu de jeu
		else if (bluePiecesNb + redPiecesNb <= 55)
			{
			coefMobility = 300;
			coefPosition = 120;
			coefMaterial = 3;
			}
		// Fin de jeu
		else
			{
			coefMobility = 0;
			coefPosition = 7;
			coefMaterial = 100;
			}

		// On augmente la stabilitÈ ‡ partir du moment o˘ on a les coins
		//if (grid[0][0] == owner || grid[0][7] == owner || grid[7][0] == owner || grid[7][7] == owner)
		//	{
		//	coefStability = 100;
		//	}

		// POUR DES RAISONS DE PERFORMANCE, NOUS AVONS CHOISI DE PRENDRE UN COEFFICIENT DE STABILITE FIXE
		coefStability = 1000;

		// Initialisation des Èvaluations des diffÈrents critËres
		scoreMobility = this.findOwnerAvailableMoves().size();
		scoreMaterial = 0;
		scorePosition = 0;
		scoreStability = evalStability();

		// MISE A JOUR DE LA GRILLE DES POINTS (VÈérification des coins et leurs voisins)
		if (grid[0][0] == owner)
			{
			pointsGrid[0][1] = 200;
			pointsGrid[1][0] = 200;
			}

		if (grid[7][0] == owner)
			{
			pointsGrid[6][0] = 200;
			pointsGrid[7][1] = 200;
			}

		if (grid[0][7] == owner)
			{
			pointsGrid[0][6] = 200;
			pointsGrid[1][7] = 200;
			}

		if (grid[7][7] == owner)
			{
			pointsGrid[6][7] = 200;
			pointsGrid[7][6] = 200;
			}

		if (grid[0][0] == adversaire)
			{
			pointsGrid[0][1] = -200;
			pointsGrid[1][0] = -200;
			}

		if (grid[7][0] == adversaire)
			{
			pointsGrid[6][0] = -200;
			pointsGrid[7][1] = -200;
			}

		if (grid[0][7] == adversaire)
			{
			pointsGrid[0][6] = -200;
			pointsGrid[1][7] = -200;
			}

		if (grid[7][7] == adversaire)
			{
			pointsGrid[6][7] = -200;
			pointsGrid[7][6] = -200;
			}

		// Calcul du scorePosition et scoreMaterial
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grid[i][j] == owner)
					{
					// Mise à jour du nbre de pions
					scoreMaterial += 1;

					// score en fonction de la position
					scorePosition += pointsGrid[i][j];
					}
				else if (grid[i][j] == adversaire)
					{
					// Mise à jour du nbre de pions
					scoreMaterial -= 1;

					// score en fonction de la position
					scorePosition -= pointsGrid[i][j];
					}
				}
			}

		// scoreMobility varie de ±20 (environ)
		// scorePosition varie de ±1000
		// scoreMaterial varie de ±64
		// scoreStability varie de ±64

		// Uniformisation de tous les facteurs (±1000)
		return coefMobility * 50 * scoreMobility + coefPosition * scorePosition + coefMaterial * 15 * scoreMaterial + coefStability * 15 * scoreStability;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initBoard()
		{
		// Initialise la grille avec toutes les cases vides
		this.grid = new int[8][8];
		this.pointsGrid = new int[8][8];
		this.stableGrid = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grid[i][j] = K_EMPTY;
				this.pointsGrid[i][j] = 0;
				this.stableGrid[i][j] = K_EMPTY;
				}
			}

		// Initialise les 4 pions de base
		this.grid[3][3] = K_BLUE;
		this.grid[4][4] = K_BLUE;
		this.grid[3][4] = K_RED;
		this.grid[4][3] = K_RED;

		initPointsGrid();
		}


	// Initialisation de la grille des points
	private void initPointsGrid()
		{
		//4 cases centre
		this.pointsGrid[3][3] = 16;
		this.pointsGrid[4][4] = 16;
		this.pointsGrid[3][4] = 16;
		this.pointsGrid[4][3] = 16;

		//4Coins
		this.pointsGrid[0][0] = 5000;
		this.pointsGrid[0][7] = 5000;
		this.pointsGrid[7][0] = 5000;
		this.pointsGrid[7][7] = 5000;

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
		this.pointsGrid[2][2] = 3;
		this.pointsGrid[5][5] = 3;
		this.pointsGrid[5][2] = 3;
		this.pointsGrid[2][5] = 3;

		this.pointsGrid[2][3] = 2;
		this.pointsGrid[2][4] = 2;
		this.pointsGrid[5][3] = 2;
		this.pointsGrid[5][4] = 2;

		this.pointsGrid[3][2] = 2;
		this.pointsGrid[4][2] = 2;
		this.pointsGrid[3][5] = 2;
		this.pointsGrid[4][5] = 2;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private int grid[][];
	private int pointsGrid[][];
	private int stableGrid[][];
	private int activePlayer;
	private int bluePiecesNb;
	private int redPiecesNb;

	public int scoreMobility;
	public int scoreMaterial;
	public int scorePosition;
	public int scoreStability;
	public int movesPlayed;
	public int owner;

	private static final int K_EMPTY = -1;
	private static final int K_RED = 0;
	private static final int K_BLUE = 1;
	}
