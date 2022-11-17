package pl.wipb.ztp.chess;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class MovementDecorator extends Piece{
	int x, y;
	private Piece prior;
	
	public MovementDecorator(Piece dragged) {
		super(dragged);
		prior = dragged;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform temp = g.getTransform();
		AffineTransform tr = g.getTransform();
		tr.setToTranslation(x,y);
		g.setTransform(tr);
		prior.draw(g);
		g.setTransform(temp);
	}

	@Override
	public void moveTo(int xx, int yy) {
		x = xx;
		y = yy;
	}
	
	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}
}
