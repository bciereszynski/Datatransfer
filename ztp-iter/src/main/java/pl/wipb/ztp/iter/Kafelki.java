package pl.wipb.ztp.iter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;


//macierz kafelków
class Kafelki extends JPanel {

	private Tile[][] matrix;
	private int tilesize;
	// kafelek podświetlony (myszką)
	private int hx = -1, hy = -1;

	// inicjalizacja macierzy
	public Kafelki(int cols, int rows, int tilesize) {
		this.setPreferredSize(new Dimension(cols * tilesize, rows * tilesize));
		this.tilesize = tilesize;
		matrix = new Tile[rows][cols];
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[i].length; ++j) {
				matrix[i][j] = new Tile();
			}
		}
	}

	// rysowanie macierzy (oraz jednego podświetlonego)
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[i].length; ++j) {
				if (i == hy && j == hx) {
					g.setColor(matrix[i][j].getColor().brighter());
				} else {
					g.setColor(matrix[i][j].getColor());
				}
				g.fillRect(j * tilesize, i * tilesize + 1, tilesize - 1, tilesize - 1);
			}
		}
	}

	// podświetl
	public void highlight(int x, int y) {
		hx = x;
		hy = y;
		repaint();
	}
	
	// metoda pobierająca iterator
	//ITERATOR ZWYKLY
	public Iterator<Tile> iterator(int x, int y){
		return new BasicIterator(x,y);
	}
	class BasicIterator implements Iterator<Tile> {
		 
        private int x,y;
 
        public BasicIterator(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        @Override
        public boolean hasNext() {
            if (y == matrix.length-1 && x == matrix[y].length)
                return false;
            return true;
        }
 
        @Override
        public Tile next() {
            if (matrix[y].length == x) {
                x = 0;
                y++;
            }
            return matrix[y][x++];
        }
    }
	
	//ITERATOR Odwrotny
	public Iterator<Tile> reverseIterator(int x, int y){
		return new ReverseIterator(x,y);
	}
	class ReverseIterator implements Iterator<Tile> {
		 
        private int x,y;
 
        public ReverseIterator(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        @Override
        public boolean hasNext() {
            if (y == 0 && x == -1) {
                return false;
            }
            return true;
        }
 
       @Override
        public Tile next() {
            if (x == -1){
                x = matrix[y].length - 1;
                y--;
            }
         
            return matrix[y][x--];
        }
    }
	//ITERATOR Ukosny
	public Iterator<Tile> diagonalIterator(int x, int y){
		return new DiagonalIterator(x,y);
	}
	class DiagonalIterator implements Iterator<Tile> {
		 
        private int x = 0, y = 0;
 
        public DiagonalIterator(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        @Override
        public boolean hasNext() {
        	//y can be out of limit after last incrementation
        	if (y == matrix.length)
        		y=0;
            if (matrix[y].length == x) {
                return false;
            }
            return true;
        }
 
        @Override
        public Tile next()
        {
            if (y == matrix.length)
                y=0;
            return matrix[y++][x++];
        }
     }
	
	//ITERATOR snake
	public Iterator<Tile> snakeIterator(int x, int y){
		return new SnakeIterator(x,y);
	}
	class SnakeIterator implements Iterator<Tile> {
		 
        private int x,y;
        private int state = 3; //0-1-2-3
 
        public SnakeIterator(int x, int y) {
            this.x = x;
            this.y = y;
        }
 
        @Override
        public boolean hasNext() {
            if (matrix.length == y || (matrix[y].length == x&&matrix.length <= y+2)) {
                return false;
            }
            return true;
        }
 
        @Override
        public Tile next()
        {
        	state = (state+1)%4;
        	if (x == matrix[y].length){
            	y+=2;
            	x=0;
            } 
        	switch(state) {
        	case 0:
        		return matrix[y++][x];
        	case 1:
        		return matrix[y][x++];
        	case 2:
        		return matrix[y--][x];
        	case 3:
        		return matrix[y][x++];
        	default:
        		//to avoid syntax error
        		return matrix[y][x];
        	}           
        }
     }
	
	/*
	// trzy poniższe metody znikną w finalnej wersji
	public int getRows() {
		return matrix.length;
	}

	public int getCols() {
		return matrix[0].length;
	}

	public Tile getAt(int row, int col) {
		return matrix[row][col];
	}
	*/
	
}