
package Participants.FroidevauxKaluznyNeuhaus;



public class TestPlateau
	{

	public static void main(String[] args)
		{
//		Joueur j1 = new Joueur(5, 0);
//		j1.rootBoard.displayBoard();
//		j1.nextPlay(new Move(3,2));
//		j1.rootBoard.displayBoard();

		Board plateau = new Board(0, 2, 2, 0, 1);
		plateau.displayBoard();
		plateau.updateBoard(new Position(2,3));
		System.out.println();
		plateau.displayBoard();
		System.out.println("Eval = "+plateau.evalBoard());

//		Position p1 = new Position(0,1);
//		Position p2 = new Position(0,1);
//		System.out.println("Comparaison="+ p1.equals(p2));
//		System.out.println("Player="+plateau.getActivePlayer());
//		ArrayList<Position> tmp1 = plateau.rechercheCoupsPossibles(new Position(3,3));
//		for(Position move:tmp1)
//			{
//			System.out.println("MovePossible= (" + move.i +","+move.j+")");
//			}
//
//		Board plateau2 = new Board(plateau.getNextBoard());
//		plateau2.updateBoard(new Position(2,2));
//		System.out.println();
//		plateau2.displayBoard();
//		System.out.println("Eval = "+plateau.evalBoard());
		//System.out.println("Player="+plateau2.getActivePlayer());
//
//		ArrayList<Position> tmp = plateau2.findAvailableMoves();// rechercheCoupsPossibles(new Move(3,3));
//		for(Position move:tmp)
//			{
//			System.out.println("MovePossible= (" + move.i +","+move.j+")");
//			}
//
//		Board plateau3 = new Board(plateau2.getNextBoard());
//		plateau3.updateBoard(new Position(5,4));
//		System.out.println();
//		plateau3.displayBoard();
//		System.out.println("Eval = "+plateau.evalBoard());
//
//		Board plateau4 = new Board(plateau3.getNextBoard());
//		plateau4.updateBoard(new Position(2,4));
//		System.out.println();
//		plateau4.displayBoard();
//		System.out.println("Eval = "+plateau.evalBoard());
//
//		Board plateau5 = new Board(plateau4.getNextBoard());
//		plateau5.updateBoard(new Position(1,1));
//		System.out.println();
//		plateau5.displayBoard();
//		System.out.println("Eval = "+plateau.evalBoard());

		}
	}
