/*
Exemple d'implémentation d'un joueur d'Othello. Cette implémentation sert uniquement
à démontrer le principe, mais n'implémente aucune intelligence: les coups à jouer sont
simplement lus à la console!
*/

// Votre version sera dans Participants.<VosNoms>

package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;

// Pour l'interopérabilité: il faut une représentation commune des coups!

// Vous devrez étendre Othello.Joueur pour implémenter votre propre joueur...
public class Plateau
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Plateau(int joueurActif, int nbPionsBlancs, int nbPionsNoirs, int nbCoupsJoues)
		{
		this.joueurActif = joueurActif;
		this.nbPionsBlancs = nbPionsBlancs;
		this.nbPionsNoirs = nbPionsNoirs;
		this.nbCoupsJoues = nbCoupsJoues;
		initGrille();
		this.eval = evalPlateau();
		}

	public Plateau(Plateau plateau, int joueurActif)
		{
		this(joueurActif, plateau.nbPionsBlancs, plateau.nbPionsNoirs, plateau.nbCoupsJoues);
		for(int i = 0; i < 8; i++)
			{
			System.arraycopy(plateau.grille[i], 0, this.grille[i], 0, 8);
			}
		}

	public Plateau(Plateau plateau)
		{
		this(plateau.joueurActif, plateau.nbPionsBlancs, plateau.nbPionsNoirs, plateau.nbCoupsJoues);
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
		return 64 - nbPionsBlancs - nbPionsNoirs;
		}

	public int evalGrille()
		{
		return 0;
		}

	public void miseAJourGrille(Move coup)
		{
		int i = coup.i;
		int j = coup.j;

		grille[i][j] = this.joueurActif;

		//Traitement vertical (Bas)
		j++;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && j < 8 && j >= 0)
			{
			j++;
			}

		if(grille[i][j] == this.joueurActif)
			{
			for(int j2 = coup.j; j2 < j; j2++)
				{
				grille[i][j2] = this.joueurActif;
				}
			}

		//Traitement vertical (Haut)
		j = coup.j-1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && j < 8 && j >= 0)
			{
			j--;
			}

		if(grille[i][j] == this.joueurActif)
			{
			for(int j2 = coup.j; j2 > j; j2--)
				{
				grille[i][j2] = this.joueurActif;
				}
			}

		//Traitement horizontal (Gauche)
		i = coup.i-1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0)
			{
			i--;
			}

		if(grille[i][j] == this.joueurActif)
			{
			for(int i2 = coup.i; i2 > i; i2--)
				{
				grille[i2][j] = this.joueurActif;
				}
			}

		//Traitement horizontal (Droite)
		i = coup.i+1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0)
			{
			i++;
			}

		if(grille[i][j] == this.joueurActif)
			{
			for(int i2 = coup.i; i2 < i; i2++)
				{
				grille[i2][j] = this.joueurActif;
				}
			}

		//Traitement Diagonal (Bas,Droite)
		i = coup.i+1;
		j = coup.j+1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0 && j < 8 && j >= 0)
			{
			i++;
			j++;
			}

		if(grille[i][j] == this.joueurActif)
			{
			int cpt = 0;
			while(coup.i + cpt < i)
				{
				grille[coup.i + cpt][coup.j + cpt] = this.joueurActif;
				cpt++;
				}
			}

		//Traitement Diagonal (Haut,Gauche)
		i = coup.i-1;
		j = coup.j-1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0 && j < 8 && j >= 0)
			{
			i--;
			j--;
			}

		if(grille[i][j] == this.joueurActif)
			{
			int cpt = 0;
			while(coup.i + cpt > i)
				{
				grille[coup.i + cpt][coup.j + cpt] = this.joueurActif;
				cpt--;
				}
			}

		//Traitement Diagonal (Haut,Droite)
		i = coup.i+1;
		j = coup.j-1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0 && j < 8 && j >= 0)
			{
			i++;
			j--;
			}

		if(grille[i][j] == this.joueurActif)
			{
			int cpt = 0;
			while(coup.i + cpt > i)
				{
				grille[coup.i + cpt][coup.j - cpt] = this.joueurActif;
				cpt++;
				}
			}

		//Traitement Diagonal (Haut,Droite)
		i = coup.i-1;
		j = coup.j+1;
		while(grille[i][j] != K_VIDE && grille[i][j] != this.joueurActif && i < 8 && i >= 0 && j < 8 && j >= 0)
			{
			i--;
			j++;
			}

		if(grille[i][j] == this.joueurActif)
			{
			int cpt = 0;
			while(coup.i + cpt > i)
				{
				grille[coup.i + cpt][coup.j - cpt] = this.joueurActif;
				cpt--;
				}
			}

		}

	public Plateau getProchainPlateau()
		{
		return new Plateau(this, 1 - this.joueurActif);
		}

	public void  afficherPlateau()
		{
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				System.out.print(this.grille[i][j] + ", ");
				}
			System.out.println();
			}
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void initGrille()
		{
		this.grille = new int[8][8];
		for(int i = 0; i < 8; i++)
			{
			for(int j = 0; j < 8; j++)
				{
				this.grille[i][j] = K_VIDE;
				}
			}
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
	private int nbPionsBlancs;
	private int nbPionsNoirs;
	private int nbCoupsJoues;
	private int eval;

	private static final int K_VIDE = -1;
	private static final int K_ROUGE = 0;
	private static final int K_BLEU = 1;

	}
