package piece_puzzle.model;

import java.util.ArrayList;
import java.util.List;
import piece_puzzle.actions.ActionPiecePlace;
import piece_puzzle.observer.IPlateauListener;

public class Plateau implements IEnsembleBlocs {

	private List<IPlateauListener> m_listeners;

	private List<AbstractPiece> m_pieces;
	private int m_width;
	private int m_height;

	public Plateau() {
		this(10, 10);
	}
	
	public Plateau(int w, int h) {
		m_width = w;
		m_height = h;
		
		m_pieces = new ArrayList<>();
		m_listeners = new ArrayList<>();
	}

	public void addPiece(AbstractPiece p) {
		addPiece(p, m_pieces.size());
	}
	public void addPiece(AbstractPiece p, int index) {
		ActionPiecePlace placement = new ActionPiecePlace(this, p, index);
		
		if(placement.isValid()) {
			placement.apply();
		}
	}

	public int removePiece(AbstractPiece p) {
		int index = m_pieces.indexOf(p);
		m_pieces.remove(p);

		return index;
	}

	public boolean isCaseFilledAt(int x, int y) {
		if(x < 0 || x >= m_width ||
			y < 0 || y >= m_height) {
			return true;
		}
		
		for(AbstractPiece p : m_pieces) {
			if(p.getPosition().getX() <= x 
					&& p.getPosition().getX() + p.getWidth() >= x
					&& p.getPosition().getY() <= y
					&& p.getPosition().getY() + p.getHeight() >= y) {
				if(p.isCaseFilledAt(x - p.getPosition().getX(),
						y - p.getPosition().getY())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public AbstractPiece getPieceAt(int x, int y) {
		if(x < 0 || x >= m_width ||
			y < 0 || y >= m_height) {
			return null;
		}
		
		for(AbstractPiece p : m_pieces) {
			if(p.getPosition().getX() <= x 
					&& p.getPosition().getX() + p.getWidth() >= x
					&& p.getPosition().getY() <= y
					&& p.getPosition().getY() + p.getHeight() >= y) {
				if(p.isCaseFilledAt(x - p.getPosition().getX(),
						y - p.getPosition().getY())) {
					return p;
				}
			}
		}
		
		return null;
	}

	public void addListener(IPlateauListener listener) {
		m_listeners.add(listener);
	}

	public void removeListener(IPlateauListener listener) {
		m_listeners.remove(listener);
	}

	public void firePieceAdded(AbstractPiece p) {
		for(IPlateauListener l : m_listeners) {
			l.pieceAdded(p);
		}
	}

	public void firePieceRemoved(AbstractPiece p) {
		for(IPlateauListener l : m_listeners) {
			l.pieceRemoved(p);
		}
	}

	public void firePieceMoved(AbstractPiece p) {
		for(IPlateauListener l : m_listeners) {
			l.pieceMoved(p);
		}
	}

	public void firePieceRotated(AbstractPiece p) {
		for(IPlateauListener l : m_listeners) {
			l.pieceRotated(p);
		}
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		
		for(int y = 0 ; y < m_height ; y++) {
			for(int x = 0 ; x < m_width ; x++) {
				char c = '.';
				for(AbstractPiece p : m_pieces) {
					if(p.isCaseFilledAt(x - p.getPosition().getX(), y - p.getPosition().getY())) {
						c = 'X';
						break;
					}
					
				}
				buffer.append(c);
			}
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}

	public List<AbstractPiece> getPieces() {
		return m_pieces;
	}

	public void setPieces(List<AbstractPiece> pieces) {
		this.m_pieces = pieces;
	}

	@Override
	public int getWidth() {
		return m_width;
	}

	@Override
	public int getHeight() {
		return m_height;
	}

}
