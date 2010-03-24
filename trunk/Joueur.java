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

	public Board rootBoard;
	private int depth;

	// depth: profondeur alpha-beta
	// playerID: 0 = rouge, 1 = bleu
	public Joueur(int depth, int playerID)
		{
		super();
		rootBoard = new Board(1-playerID);

		this.depth = depth;
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

		if (move != null)
			{
			System.out.println("MOVE" + move);
			Position tmp = new Position(move);
			System.out.println(">> BEFORE UPDATE/ Move = (" + move.i + "," + move.j + ")");
			rootBoard.displayBoard();
			rootBoard = rootBoard.applyOp(tmp);
			System.out.println(">> AFTER UPDATE");
			rootBoard.displayBoard();
			System.out.println(">> Stable Grid");
			rootBoard.displayPointsGrid();
			System.out.println("*** MOB = " + rootBoard.scoreMobility + " / POS = " + rootBoard.scorePosition + " / MAT = " + rootBoard.scoreMaterial);
	}
		else
			{
			System.out.println("******No move available");
			rootBoard = rootBoard.applyOp(null);
			}
		//int das = stdin.nextInt();

		//		if (move != null)
		//			{
		//			System.out.println("Coup adverse: " + move.i + ", " + move.j);
		//			}
		//		System.out.println("Votre coup: ");
		//		System.out.print("Colonne (-1 si aucun coup possible): ");
		/*int i = stdin.nextInt();
		if (i != -1)
			{
			System.out.print("Ligne: ");
			int j = stdin.nextInt();
			result = new Move(i, j);
			}*/
		AlphaBetaReturnValues tmp2 = alphabeta(rootBoard, depth, 1, rootBoard.evalBoard());
		Position tmp1 = tmp2.getPosition();
		System.out.println("Eval="+tmp2.getEvalValue());
		rootBoard = rootBoard.applyOp(tmp1);
		//rootBoard.displayPointsGrid();

		if (tmp1 == null)
			{
			return null;
			}
		else
			{
			System.out.println(">> NEXT MOVE = (" + tmp1.i + "," + tmp1.j + ")");
			rootBoard.displayBoard();
			return tmp1.toMove();
			}
		}

	// minormax = 1: maximize
	// minormax = -1: minimize
	// TODO: must return a couple (BoardEval, Move)
	public AlphaBetaReturnValues alphabeta(Board root, int depth, int minormax, int parentValue)
		{
		if (depth == 0 || root.isFinal())
			{
			// Retourne l'Žvaluation de la grille et il n'y a plus de coups possibles
			return new AlphaBetaReturnValues(null, root.evalBoard());
			}
		int optVal = minormax * -Integer.MIN_VALUE;
		Position optOp = null;

		for(Position op:root.findAvailableMoves())
			{
			Board newState = root.applyOp(op);
			AlphaBetaReturnValues returnValues;
			returnValues = alphabeta(newState, depth - 1, -minormax, optVal);

			if (returnValues.getEvalValue() * minormax > optVal * minormax)
				{
				optVal = returnValues.getEvalValue();
				optOp = op;
				if (optVal * minormax > parentValue * minormax)
					{
					break;
					}
				}
			}
		return new AlphaBetaReturnValues(optOp, optVal);
		}
	}
