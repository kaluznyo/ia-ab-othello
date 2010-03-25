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
		rootBoard = new Board(playerID);
		this.depth = depth;
		System.out.println("activePlayer = "+ playerID+" / MIN = "+ Integer.MIN_VALUE);
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
			{//System.out.println(">> BEFORE UPDATE/ Move = (" + move.i + "," + move.j + ")");

			//System.out.println("MOVE" + move);
			Position tmp = new Position(move);
			//			System.out.println(">> BEFORE UPDATE/ tmp = (" + tmp.i + "," + tmp.j + ")");
			//rootBoard.displayBoard();

			rootBoard = rootBoard.applyOp(tmp);

			//System.out.println(">> AFTER UPDATE");
			//rootBoard.displayBoard();


			//rootBoard.displayBoard();
			//System.out.println(">> Stable Grid");
			//rootBoard.displayPointsGrid();
			}

		// find a move that maximize our chances
		AlphaBetaReturnValues tmp2 = alphabeta(rootBoard, depth, 1, Integer.MAX_VALUE);
		Position tmp1 = tmp2.getPosition();
		rootBoard = rootBoard.applyOp(tmp1);
		//System.out.println("Eval=" + rootBoard.evalBoard());
		//System.out.println("*** MOB = " + rootBoard.scoreMobility + " / POS = " + rootBoard.scorePosition + " / MAT = " + rootBoard.scoreMaterial);
		rootBoard.displayBoard();
//		ArrayList<Position> tmp = rootBoard.findAvailableMoves();// rechercheCoupsPossibles(new Move(3,3));
//		for(Position t:tmp)
//			{
//			System.out.println("MovePossible= (" + t.i +","+t.j+")");
//			}


		if (tmp1 == null)
			{
			return null;
			}
		else
			{
			//System.out.println(">> NEXT MOVE = (" + tmp1.i + "," + tmp1.j + ")");
			//rootBoard.displayBoard();
			//System.out.println("Eval=" + rootBoard.evalBoard());
			return tmp1.toMove();
			}
		}

	// minormax = 1: maximize
	// minormax = -1: minimize
	public AlphaBetaReturnValues alphabeta(Board root, int depth, int minormax, int parentValue)
		{
		if (depth == 0 || root.isFinal())
			{
			// Retourne l'Žvaluation de la grille et il n'y a plus de coups possibles
			return new AlphaBetaReturnValues(null, root.evalBoard());
			}
		int optVal = minormax * Integer.MIN_VALUE;
		Position optOp = null;

//		System.out.println("Moves = "+root.findAvailableMoves().toString());

		for(int i=0; i<root.findAvailableMoves().size(); i++)//Position op:root.findAvailableMoves())
			{
			Position op = root.findAvailableMoves().get(i);
//			System.out.println(" >Move = "+op.toString() );
			Board newState = root.applyOp(op);
//			System.out.println(" > optVal"+ optVal+"/minormax"+minormax);

			int val = alphabeta(newState, depth - 1, -minormax, optVal).getEvalValue();
//			System.out.println(" > val"+ val);
//			System.out.println(">>AB<<");
//			newState.displayBoard();
//			System.out.println("val = " +val+" / optVal"+optVal);
			if (val * minormax > optVal * minormax)
				{
				optVal = val;
				optOp = op;
//				System.out.println("val = " +val+" / parent = "+parentValue);
				if (optVal * minormax > parentValue * minormax)
					{
//					System.out.println(">>>>>>PASS");
					break;
					}
				}
			}
		return new AlphaBetaReturnValues(optOp, optVal);
		}
	}
