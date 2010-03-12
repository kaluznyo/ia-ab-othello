/*
Exemple d'implémentation d'un joueur d'Othello. Cette implémentation sert uniquement
à démontrer le principe, mais n'implémente aucune intelligence: les coups à jouer sont
simplement lus à la console!
*/

// Votre version sera dans Participants.<VosNoms>

package Participants.FroidevauxKaluznyNeuhaus;

// Pour l'interopérabilité: il faut une représentation commune des coups!
import java.util.Scanner;

import Othello.Move;

// Vous devrez étendre Othello.Joueur pour implémenter votre propre joueur...
public class Joueur extends Othello.Joueur
	{

	// depth: profondeur alpha-beta
	// playerID: 0 = rouge, 1 = bleu
	public Joueur(int depth, int playerID)
		{
		super();
		}

	Scanner stdin = new Scanner(System.in);

	// Méthode appelée à chaque fois que vous devez jouer un coup.
	@Override public Move nextPlay(Move move)
		{
		// Ici, vous devrez
		// - Mettre à jour votre représentation du jeu en fonction du coup joué par l'adversaire
		// - Décider quel coup jouer (alpha-beta!!)
		// - Remettre à jour votre représentation du jeu
		// - Retourner le coup choisi
		// Mais ici, on se contente de lire à la console:
		Move result = null;
		if (move != null)
			{
			System.out.println("Coup adverse: " + move.i + ", " + move.j);
			}
		System.out.println("Votre coup: ");
		System.out.print("Colonne (-1 si aucun coup possible): ");
		int i = stdin.nextInt();
		if (i != -1)
			{
			System.out.print("Ligne: ");
			int j = stdin.nextInt();
			result = new Move(i, j);
			}
		return result;
		}

	// minormax = 1: maximize
	// minormax = -1: minimize
	// TODO: must return a couple (BoardEval, Move)
	public void alphabeta(Board root, int depth, int minormax, int parentValue)
		{
		if (depth == 0 || root.isFinal())
			{
			// Retourne l'Žvaluation de la grille et il n'y a plus de coups possibles
			//return root.evalGrille(), None
			}
		int optVal = minormax * -Integer.MIN_VALUE;
		Move optOp = null;

		/*for(Move op:root.findAvailableMoves())
			{
			Board newState = new Board(root, op);
			val, dummy = alphabeta(newState, depth-1, -minormax, optVal);
			if (val*minormax > optVal * minormax)
				{
				optVal = val;
				optOp = op;
				if (optVal*minormax > parentValue * minormax)
					{
					break;
					}
				}
			}
		return optVal, optOp;*/
		}
	}
