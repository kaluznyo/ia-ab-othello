
package Participants.FroidevauxKaluznyNeuhaus;

public final class AlphaBetaReturnValues
	{
	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public AlphaBetaReturnValues(Position move, int evalValue)
		{
		super();
		this.move = move;
		this.evalValue = evalValue;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
//	public void setMove(Move move)
//		{
//		this.move = move;
//		}
//
//	public void setEvalValue(int evalValue)
//		{
//		this.evalValue = evalValue;
//		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	public Position getPosition()
		{
		return this.move;
		}

	public int getEvalValue()
		{
		return this.evalValue;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private final Position move;
	private final int evalValue;
	}