package pl.wipb.ztp.chess;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class PlacementDecorator extends Piece{
	private int ZEROX=23;
	private int ZEROY=7;
	public PlacementDecorator(Piece piece) {
		super(piece);
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform temp = g.getTransform();
		AffineTransform tr = g.getTransform();
		tr.translate(ZEROX,ZEROY);
		tr.scale(Piece.TILESIZE, Piece.TILESIZE);
		g.setTransform(tr);
		super.draw(g);
		g.setTransform(temp);
	}

	@Override
	public void moveTo(int xx, int yy) {
		super.moveTo(xx, yy);
	}
	@Override
	public int getX() {
		return super.getX();
	}
	@Override
	public int getY() {
		return super.getY();
	}
	
}
