import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.MouseInfo;

public class window extends JFrame implements MouseListener
{
	private map partie;
	private static final long serialVersionUID = 1L;

	public window(int x, int y, String name)
	{
		partie=new map(); //initialisation de la fenetre
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	    this.setSize(x, y);
	    this.setTitle(name);
	    this.setBounds(0,0,613,630);
	    this.getContentPane().setBackground(Color.WHITE);
	    this.getContentPane().add(partie);
	    
	    this.addMouseListener(this); //initialisation de la souris
        this.setFocusable(true);
	}
	
	
	public void mouseReleased(MouseEvent e)
	{
		int x,y;
		x=e.getPoint().x;
        y=e.getPoint().y;
		x=(x-10)/66;
		y=(y-30)/66;
    	if(e.getButton() == MouseEvent.BUTTON1) //clic gauche
			partie.addcarre(x, y);
    	else if(e.getButton()==MouseEvent.BUTTON3)
    		partie.subcarre(x, y);
    	partie.repaint();
    	if(partie.check())
    	{
    		this.removeMouseListener(this);
    		partie.setwin();
    	}
	}
	
	
	
	public void mouseExited(MouseEvent me)
	{
	}
	
	public void mouseEntered(MouseEvent me)
	{
	}
	
	public void mouseClicked(MouseEvent me)
	{
	}
	
	public void mousePressed(MouseEvent me)
	{
	}
}