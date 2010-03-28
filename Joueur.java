/*
Implémentation d'un joueur d'Othello.
Cette implémentation utilise l'algorithme alpha-neta afin de déterminer le meilleur coup possible.
*/

package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;

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
		System.out.println("activePlayer = " + playerID + " / MIN = " + Integer.MIN_VALUE);
		}

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
			System.out.println(">> BEFORE UPDATE/ Move = (" + move.i + "," + move.j + ")");

			Position tmp = new Position(move);
			// System.out.println(">> BEFORE UPDATE/ tmp = (" + tmp.i + "," + tmp.j + ")");
			// rootBoard.displayBoard();

			// Mise à jour du jeu en fonction du coup joué par l'adversaire
			rootBoard = rootBoard.applyOp(tmp);

			// System.out.println(">> AFTER UPDATE");
			// rootBoard.displayBoard();

			//rootBoard.displayBoard();
			}
		else
			{
			if (rootBoard.movesPlayed > 0)
				{
				rootBoard = rootBoard.applyOp(null);
				}
			}

		// Trouve un coup qui maximise notre prochain coup
		AlphaBetaReturnValues tmp2 = alphabeta(rootBoard, depth, 1, Integer.MAX_VALUE);

		// Récupère la position
		Position tmp1 = tmp2.getPosition();
		// Mise à jour du jeu en appliquant le coup trouvé
		rootBoard = rootBoard.applyOp(tmp1);
//		System.out.println("STAB = " + rootBoard.evalStability());
		rootBoard.displayStableGrid();
		rootBoard.evalBoard();
		System.out.println("*** MOB = " + rootBoard.scoreMobility + " / POS = " + rootBoard.scorePosition + " / MAT = " + rootBoard.scoreMaterial + " / "+rootBoard.scoreStability);

		//		System.out.println("Eval=" + rootBoard.evalBoard() +" / AB eval="+ tmp2.getEvalValue());

		//		rootBoard.displayBoard();
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
			return tmp1.toMove();
			}
		}

	// minormax = 1: maximize
	// minormax = -1: minimize
	public AlphaBetaReturnValues alphabeta(Board root, int depth, int minormax, int parentValue)
		{
		if (depth == 0 || root.isFinal())
			{
			// Retourne l'éŽvaluation de la grille et il n'y a plus de coups possibles
			return new AlphaBetaReturnValues(null, root.evalBoard());
			}
		int optVal = minormax * Integer.MIN_VALUE;
		Position optOp = null;

		for(Position op:root.findAvailableMoves()) //int i=0; i<root.findAvailableMoves().size(); i++)
			{
			//Position op = root.findAvailableMoves().get(i);
			Board newState = root.applyOp(op);

			int val = alphabeta(newState, depth - 1, -minormax, optVal).getEvalValue();
			if (val * minormax > optVal * minormax)
				{
				optVal = val;
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
