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
public class Plateau
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Plateau(int joueurActif, int nbPionsBleu, int nbPionsRouge, int nbCoupsJoues)
		{
		this.joueurActif = joueurActif;
		this.nbPionsBleu = nbPionsBleu;
		this.nbPionsRouge = nbPionsRouge;
		this.nbCoupsJoues = nbCoupsJoues;
		initGrille();
		this.eval = evalPlateau();
		}

	public Plateau(Plateau plateau, int joueurActif)
		{
		this(joueurActif, plateau.nbPionsBleu, plateau.nbPionsRouge, plateau.nbCoupsJoues);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grille[i], 0, this.grille[i], 0, 8);
			}
		}

	public Plateau(Plateau plateau)
		{
		this(plateau.joueurActif, plateau.nbPionsBleu, plateau.nbPionsRouge, plateau.nbCoupsJoues);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grille[i], 0, this.grille[i], 0, 8);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public int nbCasesVides()
		{
		return 64 - nbPionsBleu - nbPionsRouge;
		}

	public int evalGrille()
		{
		return 0;
		}

	public void miseAJourGrille(Move coup1)
		{
		Move coup = new Move(coup1.j, coup1.i);
		int i = coup.i;
		int j = coup.j;
		System.out.println("Pos Coup = (" + i + "," + j + ")");
		grille[i][j] = this.joueurActif;
		int nbPiecesRetournees = 0;

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
					i = coup.i + angleI;
					j = coup.j + angleJ;
					// parcours pour chercher les cases que l'on peut retourner
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == (1 - this.joueurActif))
						{
						i += angleI;
						j += angleJ;
						}

					// Traitement pour retourner les pièces
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == this.joueurActif)
						{
						int cpt = 1;
						// Retournement des pièces
						while((coup.i + cpt * angleI) * angleI < i * angleI || (coup.j + cpt * angleJ) * angleJ < j * angleJ)
							{
							grille[coup.i + angleI * cpt][coup.j + angleJ * cpt] = this.joueurActif;
							cpt++;
							nbPiecesRetournees++;
							}
						}
					}
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

	public ArrayList<Move> rechercheCoupsPossibles(Move coup1)
		{
		Move coup = new Move(coup1.j, coup1.i);
		int i = coup.i;
		int j = coup.j;
		System.out.println("Pos Coup = (" + i + "," + j + ")");
		grille[i][j] = this.joueurActif;
		ArrayList<Move> tabCoupsPossibles = new ArrayList<Move>();

		for(int angleI = -1; angleI <= 1; angleI++)
			{
			for(int angleJ = -1; angleJ <= 1; angleJ++)
				{
				if (angleI != 0 || angleJ != 0)
					{
					i = coup.i + angleI;
					j = coup.j + angleJ;
					while(i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == (1 - this.joueurActif))
						{
						i += angleI;
						j += angleJ;
						}

					// Prochaine case vide après des cases blanches
					if (i < 8 && i >= 0 && j < 8 && j >= 0 && grille[i][j] == K_VIDE && (i != coup.i + angleI || j != coup.j + angleJ))
						{
						// Stockage du pion dans un arraylist
						tabCoupsPossibles.add(new Move(i, j));
						}
					}
				}
			}
		return tabCoupsPossibles;
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

	public ArrayList<Move> getPionsJoueurs()
		{
		ArrayList<Move> tabPionsJoueurs = new ArrayList<Move>();
		// Récupère les pions du joueur actif
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (grille[i][j] == joueurActif)
					{
					// Trouver les coups possibles
					tabPionsJoueurs.add(new Move(i, j));
					}
				}
			}
		return tabPionsJoueurs;
		}

	public HashSet<Move> calculerCoupsPossibles()
		{
		ArrayList<Move> tabPionsJoueurs = getPionsJoueurs();

		for(Move move:tabPionsJoueurs)
			{
			// Recherche coups possibles
			rechercheCoupsPossibles(move);
			}

		return null;

		}

	public Plateau getProchainPlateau()
		{
		return new Plateau(this, 1 - this.joueurActif);
		}

	public void afficherPlateau()
		{
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				if (this.grille[i][j] == K_VIDE)
					{
					System.out.print("x ");
					}
				else
					{
					System.out.print(this.grille[i][j] + " ");
					}
				}
			System.out.println();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initGrille()
		{
		// Initialise la grille avec toutes les cases vides
		this.grille = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grille[i][j] = K_VIDE;
				}
			}

		// Initialise les 4 pions de base
		this.grille[3][3] = K_BLEU;
		this.grille[4][4] = K_BLEU;
		this.grille[3][4] = K_ROUGE;
		this.grille[4][3] = K_ROUGE;
		}

	private int evalPlateau()
		{
		// TODO Auto-generated method stub
		return 0;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setJoueurActif(int joueurActif)
		{
		this.joueurActif = joueurActif;
		}

	public void setGrille(int[][] grille)
		{
		this.grille = grille;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public int[][] getGrille()
		{
		return this.grille;
		}

	public int getJoueurActif()
		{
		return this.joueurActif;
		}

	public Move[] getCoupsPossibles()
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

	private int grille[][];
	private int joueurActif;
	private Move coupsPossibles[];
	private int nbPionsBleu;
	private int nbPionsRouge;
	private int nbCoupsJoues;
	private int nbPionsRetournes;
	private int eval;

	private static final int K_VIDE = -1;
	private static final int K_ROUGE = 0;
	private static final int K_BLEU = 1;

	}
