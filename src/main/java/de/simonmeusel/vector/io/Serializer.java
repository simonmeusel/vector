package de.simonmeusel.vector.io;

import java.io.InputStream;
import java.io.OutputStream;

import de.simonmeusel.vector.board.Board;

public interface Serializer {

	public void serialize(OutputStream out, Board board) throws Exception;

	public void deserialize(InputStream in, Board board) throws Exception;
	
}
