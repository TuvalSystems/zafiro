package Librerias.ZafDisSeg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * @web 
 * @author Tony
 */
public class CirclePanel extends JPanel implements MouseListener,MouseMotionListener{
    
    private final Dimension d = new Dimension(400,400);
    private Point initialClick;
    private JFrame parent;
     
    /**
     * Constructor de clase
     */
    public CirclePanel(){
        super();
        CirclePanel.this.setSize(d);
        CirclePanel.this.setPreferredSize(d);
        CirclePanel.this.setVisible(true);     
        CirclePanel.this.addMouseListener(CirclePanel.this);
        CirclePanel.this.addMouseMotionListener(CirclePanel.this);
    }
    
     @Override
    public void paintComponent(Graphics g){    
        Graphics2D g2 =(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);    
        
        //fondo        
        g2.setStroke(new BasicStroke( 0f) );
        g2.setColor( new Color(228,228,228) );        
        g2.fill( new Ellipse2D.Double(0, 0 , getWidth(), getHeight() ) );
        
        g2.setColor( new Color(240,240,240) );
        g2.fill( new Ellipse2D.Double(10, 10 , getWidth()-20, getHeight()-20 ) );
        
       //borde
       g2.setStroke(new BasicStroke( 1f) );
       g2.setColor( new Color(174,174,174) );
       g2.draw( new Ellipse2D.Double(0, 0 , getWidth()-1, getHeight()-1 ) );
       g2.draw( new Ellipse2D.Double(10, 10 , getWidth()-21, getHeight()-21 ) );
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        initialClick = e.getPoint();
        getComponentAt(initialClick);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        //obtiene posicion de la ventana
        int thisX = parent.getLocation().x;
        int thisY = parent.getLocation().y;

        //determina el desplazamiento
        int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
        int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

        //mueve la ventana a su nueva posicion
        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        parent.setLocation(X, Y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public void setParent(JFrame parent) {
        this.parent = parent;
    }
    
    
}//End:class
