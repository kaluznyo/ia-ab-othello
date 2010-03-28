
package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;

/*
 * Cette classe permet d'étendre la classe Move en lui ajoutant la méthode equals
 *
 * */
public class Position extends Move
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public Position(int i, int j)
		{
		super(i, j);
		}

	public Position(Move move)
		{
		super(move.i, move.j);
		}


	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override public boolean equals(Object obj)
		{
		Position tmp = (Position)obj;
		return (tmp.i == this.i) && (tmp.j == this.j);
		}

	public Move toMove()
		{
		return new Move(this.i, this.j);
		}
	}
