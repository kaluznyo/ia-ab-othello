
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
		this.i = move.j;
		this.j = move.i;
		}

	@Override public boolean equals(Object obj)
		{
		// TODO Auto-generated method stub
		Position tmp = (Position)obj;
		return (tmp.i == this.i) && (tmp.j == this.j);
		}

	private boolean isEqual(Position pos)
		{
		return (pos.i == this.i) && (pos.j == this.j);
		}


	public Move toMove()
		{
		return new Move(this.j, this.i);
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
