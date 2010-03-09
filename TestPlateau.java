
package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;


public class TestPlateau
	{

	public static void main(String[] args)
		{
		Plateau plateau = new Plateau(0, 2, 2, 0);
		plateau.afficherPlateau();
		plateau.miseAJourGrille(new Move(2,3));
		System.out.println();
		plateau.afficherPlateau();

		Plateau plateau2 = new Plateau(plateau.getProchainPlateau());
		plateau2.miseAJourGrille(new Move(2,2));
		System.out.println();
		plateau2.afficherPlateau();

		Plateau plateau3 = new Plateau(plateau2.getProchainPlateau());
		plateau3.miseAJourGrille(new Move(5,4));
		System.out.println();
		plateau3.afficherPlateau();

		Plateau plateau4 = new Plateau(plateau3.getProchainPlateau());
		plateau4.miseAJourGrille(new Move(2,4));
		System.out.println();
		plateau4.afficherPlateau();

		Plateau plateau5 = new Plateau(plateau4.getProchainPlateau());
		plateau5.miseAJourGrille(new Move(1,1));
		System.out.println();
		plateau5.afficherPlateau();

		}
	}
