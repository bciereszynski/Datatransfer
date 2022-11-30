//MUCHY METODA SZABLONOWA
/*http://blogprogramisty.net/przydatne-wzorce-projektowe-w-c/*/
package javaapplication2;
 
import javax.swing.*;
 
import java.awt.*;
import java.awt.event.*;
import java.util.*;
 
abstract class Mucha {
 
    private final double k = 0.01;
    double x, y; // pozycja muchy
    double vx, vy; // predkosc muchy
 
    public Mucha() {
        x = Math.random();
        y = Math.random();
        vx = k * (Math.random() - Math.random());
        vy = k * (Math.random() - Math.random());
    }
 
    protected abstract void setColor(Graphics g);
    
    protected abstract void fillOval(Graphics g, int x, int y, int width, int height);
 
    public void draw(Graphics g) {
        setColor(g);
        Rectangle rc = g.getClipBounds();
        int a = (int) (x * rc.getWidth());
        int b = (int) (y * rc.getHeight());
        fillOval(g,a,b,5,5);
    }
 
    protected abstract void move();
}
 
class PijanaMucha extends Mucha {
 
    private Random random = new Random();
 
    protected void move() {
        double lenght = Math.sqrt(vx*vx + vy*vy);
        double alfa = Math.acos(vx / lenght);
        double rangeMax = 2 * Math.PI;
        double rangeMin = 0;
        alfa = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
        vx = lenght * Math.cos(alfa);
        vy = lenght * Math.sin(alfa);
        
        x += vx;
        y += vy;
        if (x < 0) {
            x = -x;
            vx = -vx;
        }
        if (x > 1) {
            x = 2 - x;
            vx = -vx;
        }
        if (y < 0) {
            y = -y;
            vy = -vy;
        }
        if (y > 1) {
            y = 2 - y;
            vy = -vy;
        }
    }
 
    @Override
    protected void setColor(Graphics g) {
        g.setColor(Color.red);
    }

    @Override
    protected void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x,y,width,height);
    }
}
 
class MuchaPunkt extends Mucha {
 
    private Random random = new Random();
    double r ,Ox, Oy, alfa;
 
    public MuchaPunkt() {
        super();
        r = random.nextDouble() / 10;
        Ox = x;
        Oy = y;
        x = x + r;
        y = y + r;
    }
 
    protected void move() {
        x = Ox + r * Math.sin(alfa);
        y = Oy + r * Math.cos(alfa);
        alfa = alfa + 0.1;
        if (alfa >= 2 * Math.PI)
        {
            alfa = 0;
        }
    }
 
    @Override
    protected void setColor(Graphics g) {
        g.setColor(Color.blue);
    }

    @Override
    protected void fillOval(Graphics g, int x, int y, int width, int height) {
        g.fillOval(x,y,width,height);
    }
}
 
public class JavaApplication2 extends JPanel implements Runnable {
 
    private Mucha[] ar;
    private Random random = new Random();
 
    public JavaApplication2() {
        this.setPreferredSize(new Dimension(640, 480));
        ar = new Mucha[30];
        for (int i = 0; i < ar.length; ++i) {
            if (random.nextBoolean()) {
                ar[i] = new MuchaPunkt();
            } else {
                ar[i] = new PijanaMucha();
            }
        }
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < ar.length; ++i) {
            ar[i].draw(g);
        }
    }
 
    public void run() {
        while (true) {
            for (int i = 0; i < ar.length; ++i) {
                ar[i].move();
            }
            repaint();
            try {
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
    public static void main(String[] args) {
        JFrame frame = new JFrame("Muchy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JavaApplication2 m = new JavaApplication2();
        frame.getContentPane().add(m);
        frame.pack();
        frame.setVisible(true);
        new Thread(m).start();
    }
}