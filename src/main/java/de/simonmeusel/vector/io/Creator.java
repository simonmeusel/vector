package de.simonmeusel.vector.io;

import java.io.OutputStream;

import de.simonmeusel.vector.board.Board;

public interface Creator {

	public void create(OutputStream out, Board board) throws Exception;
	
}
