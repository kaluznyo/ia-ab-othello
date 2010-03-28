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

		if (move != null)
			{
			Position tmp = new Position(move);
			// Mise à jour du jeu en fonction du coup joué par l'adversaire
			rootBoard = rootBoard.applyOp(tmp);
			}
		else
			{
			// Si ce n'est pas le premier coup et que c'est à nous de jouer
			if (rootBoard.movesPlayed > 0)
				{
				// Applique un mouvement nul si l'adversaire ne peut pas jouer
				rootBoard = rootBoard.applyOp(null);
				}
			}

		// Trouve un bon prochain coup
		AlphaBetaReturnValues tmp2 = alphabeta(rootBoard, depth, 1, Integer.MAX_VALUE);

		// Récupère la position
		Position tmp1 = tmp2.getPosition();

		// Mise à jour du jeu en appliquant le coup trouvé
		rootBoard = rootBoard.applyOp(tmp1);

		// Renvoie le coup à jouer
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
			return new AlphaBetaReturnValues(null, root.evalBoard());
			}
		int optVal = minormax * Integer.MIN_VALUE;
		Position optOp = null;

		for(Position op:root.findAvailableMoves())
			{
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
