package piece_puzzle.actions;

import piece_puzzle.actions.validator.ActionPiecePlaceValidator;
import piece_puzzle.actions.validator.IValidator;
import piece_puzzle.model.Plateau;
import piece_puzzle.model.AbstractPiece;

public class ActionPiecePlace implements IAction {

	private AbstractPiece m_piece;
	private Plateau m_plateau;
	private int m_index;

	public ActionPiecePlace(Plateau plateau, AbstractPiece piece, int index) {
		m_piece = piece;
		m_plateau = plateau;
		m_index = index;
	}
	
	@Override
	public boolean isValid() {
		IValidator validator = new ActionPiecePlaceValidator(m_plateau, m_piece);
		
		return validator.isValid();
	}

	@Override
	public void apply() {
		m_plateau.getPieces().add(m_index, m_piece);
	}
	
}
