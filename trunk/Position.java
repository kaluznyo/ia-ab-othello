
package Participants.FroidevauxKaluznyNeuhaus;

import Othello.Move;

public class Position
	{

	public int i, j;

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public Position(int i, int j)
		{
		super();
		this.i = i;
		this.j = j;
		}

	public Position(Move move)
		{
		super();
		this.i = move.i;
		this.j = move.j;
		}

	@Override public boolean equals(Object obj)
		{
		Position tmp = (Position)obj;
		return (tmp.i == this.i) && (tmp.j == this.j);
		}

	private boolean isEqual(Position pos)
		{
		return (pos.i == this.i) && (pos.j == this.j);
		}


	public Move toMove()
		{
		return new Move(this.i, this.j);
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	}
