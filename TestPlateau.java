
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
		System.out.println();
		plateau2.afficherPlateau();

		plateau2.miseAJourGrille(new Move(4,2));
		System.out.println();
		plateau2.afficherPlateau();
		}
	}
