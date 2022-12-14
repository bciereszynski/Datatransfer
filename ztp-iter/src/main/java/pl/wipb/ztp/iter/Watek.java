package pl.wipb.ztp.iter;

import java.util.Iterator;
import java.util.Random;

// ten wątek nie wykorzystuje iteratora
class Watek implements Runnable {

	private Kafelki p;
	private int x, y;

	// x, y to początkowa pozycja do iteracji
	public Watek(Kafelki k, int x, int y) {
		this.p = k;
		this.x = x;
		this.y = y;
	}

	public void run() {
		
		 Random r = new Random();
	     int wylosowana = r.nextInt(4);
	     Iterator<Tile> i;
	       
	     if (wylosowana % 4 == 0) {
	    	 i = p.iterator(x, y);
	     }
	     else if (wylosowana % 4 == 1) {
	    	 i = p.reverseIterator(x, y);
	     }
	     else if (wylosowana % 4 == 2){
	    	 i = p.diagonalIterator(x, y);
	     }
	     else{
	    	 i = p.snakeIterator(x, y);
	     }
		 
		while(i.hasNext()) {
			Tile t = i.next();
            t.flip();
            p.repaint();
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
            }
		}
		// klasyczna podwójna pętla do iteracji
		// tutaj kontrolujemy kolejność odwiedzin
		// zostanie to zastąpione pętlą z użyciem iteratora
		/*for (int i = y; i < p.getRows(); ++i) {
			int j;
			if (i == y) {
				j = x;
			} else {
				j = 0;
			}
			for (; j < p.getCols(); ++j) {
				// a w środku - obracamy, odświeżamy i czekamy
				p.getAt(i, j).flip();
				p.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}*/
	}
}