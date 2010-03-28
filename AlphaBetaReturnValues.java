
package Participants.FroidevauxKaluznyNeuhaus;


/*
 * Cette classe permet de stocker les valeurs de retour de la fonction alpha-beta
 *
 * */
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
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private final Position move;
	private final int evalValue;
	}
