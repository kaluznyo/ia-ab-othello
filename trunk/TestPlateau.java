
package Participants.FroidevauxKaluznyNeuhaus;

import java.util.ArrayList;

import Othello.Move;


public class TestPlateau
	{

	public static void main(String[] args)
		{
		Board plateau = new Board(0, 2, 2, 0);
		plateau.displayBoard();
		plateau.updateBoard(new Move(2,3));
		System.out.println();
		plateau.displayBoard();

		Board plateau2 = new Board(plateau.getNextBoard());
		plateau2.updateBoard(new Move(2,2));
		System.out.println();
		plateau2.displayBoard();

		ArrayList<Move> tmp = plateau2.rechercheCoupsPossibles(new Move(3,3));
		for(Move move:tmp)
			{
			System.out.println("MovePossible= (" + move.i +","+move.j+")");
			}

		Board plateau3 = new Board(plateau2.getNextBoard());
		plateau3.updateBoard(new Move(5,4));
		System.out.println();
		plateau3.displayBoard();

		Board plateau4 = new Board(plateau3.getNextBoard());
		plateau4.updateBoard(new Move(2,4));
		System.out.println();
		plateau4.displayBoard();

		Board plateau5 = new Board(plateau4.getNextBoard());
		plateau5.updateBoard(new Move(1,1));
		System.out.println();
		plateau5.displayBoard();

		}
	}
