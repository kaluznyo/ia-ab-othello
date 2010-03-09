
package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;

public class TestPlateau
	{

	public static void main(String[] args)
		{
		Plateau plateau = new Plateau(0, 2, 2, 0);
		plateau.afficherPlateau();
		plateau.miseAJourGrille(new Move(1,7));
		System.out.println();
		plateau.afficherPlateau();
		}
	}
