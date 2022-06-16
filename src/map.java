import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.Vector;


public class map extends JComponent
{
	private Vector<Shape> base_grille= new Vector<Shape>();
	private int [][] chiffres=new int [9][9]; //tableau contenant la grille a remplir
	private boolean [][] fixed=new boolean [9][9]; //tableau qui indique si une case peu etre modifiee
	private boolean win;
	
	public map() //constructeur
	{
		win=false;
		
		this.base_grille.addElement(new Line2D.Double(0, 0, 600, 0));
		this.base_grille.addElement(new Line2D.Double(0, 66, 600, 66));
		this.base_grille.addElement(new Line2D.Double(0, 132, 600, 132));
		this.base_grille.addElement(new Line2D.Double(0, 198, 600, 198));
		this.base_grille.addElement(new Line2D.Double(0, 264, 600, 264));
		this.base_grille.addElement(new Line2D.Double(0, 330, 600, 330));
		this.base_grille.addElement(new Line2D.Double(0, 396, 600, 396));
		this.base_grille.addElement(new Line2D.Double(0, 462, 600, 462));
		this.base_grille.addElement(new Line2D.Double(0, 528, 600, 528));
		this.base_grille.addElement(new Line2D.Double(0, 600, 600, 600));
		
		this.base_grille.addElement(new Line2D.Double(66, 0, 66, 600));
		this.base_grille.addElement(new Line2D.Double(132, 0, 132, 600));
		this.base_grille.addElement(new Line2D.Double(198, 0, 198, 600));
		this.base_grille.addElement(new Line2D.Double(264, 0, 264, 600));
		this.base_grille.addElement(new Line2D.Double(330, 0, 330, 600));
		this.base_grille.addElement(new Line2D.Double(396, 0, 396, 600));
		this.base_grille.addElement(new Line2D.Double(462, 0, 462, 600));
		this.base_grille.addElement(new Line2D.Double(528, 0, 528, 600));
		this.base_grille.addElement(new Line2D.Double(600, 0, 600, 600));

		for(int y=0; y<9; y++)
		{
			for(int x=0; x<9; x++)
			{
				chiffres[x][y]=0;
			}
		}
		
		int value; //partie pour générer un sodoku entier aléatoirement
		int fail=0; //compteur pour verifier si mon miserable programme n'a pas genere un sudoku impossible 
		Vector <Integer> tries=new Vector<Integer>();
		for(int y=0; y<9; y++)
		{
			for(int x=0; x<9; x++)
			{
				do 
				{
					value=(int)Math.floor(Math.random()*(9-1+1)+1);
					if(!tries.contains(value))
					{
						tries.add(value);
					}
					else if(tries.size()==9) //si on a essayé les 9 valeurs pour une case et que aucune ne va
					{
						if(fail>50)
						{
							y=0;
							fail=0;
							for(int j=0; j<9; j++)
							{
								for(int i=0; i<9; i++)
								{
									chiffres[i][j]=0;
								}
							}
						}
						else
						{
							for(int i=0; i<9; i++)
							{
								chiffres[i][y]=0; //on efface toute la ligne
								fail++;
							}
						}
						x=0;
						tries=new Vector<Integer>();
						value=(int)Math.floor(Math.random()*(9-1+1)+1); //et on recommence
					}
				}while(ligneok(y, value)==false || coloneok(x, value)==false || carreok(x, y, value)==false);
				chiffres[x][y]=value;
				tries=new Vector<Integer>(); //on reinitialise le tableau
			}
			fail=0;
		}
		
		System.out.println("solution :");
		for(int y=0; y<9; y++)
		{
			for(int x=0; x<9; x++)
			{
				System.out.print(chiffres[x][y]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
		int x1,y1;
		int difficulte=(int)Math.floor(Math.random()*(46-37+1)+37); //on retire ensuite un certain nombre de cases a la solution
		//int difficulte=3;
		for(int i=0; i<difficulte; i++)
		{
			do
			{
				x1=(int)Math.floor(Math.random()*(8-0+1)+0);
				y1=(int)Math.floor(Math.random()*(8-0+1)+0);
			}while(chiffres[x1][y1]==0);
			chiffres[x1][y1]=0;
		}
		
		for(int y=0; y<9; y++) //on rempli le tableau des cases a ne pas toucher
		{
			for(int x=0; x<9; x++)
			{
				if(chiffres[x][y]==0)
					fixed[x][y]=false;
				else
					fixed[x][y]=true;
			}
		}
	}
	
	private boolean ligneok(int _x, int valeure)
	{
		for(int i=0; i<9; i++)
		{
			if(chiffres[i][_x]==valeure)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean coloneok(int _y, int valeure)
	{
		for(int i=0; i<9; i++)
		{
			if(chiffres[_y][i]==valeure)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean carreok(int _x, int _y, int valeure)
	{
		int carre[]=new int [9];
		int compt=0;
		
		if(_y<3)
		{
			if(_x<3)
			{
				for(int y=0; y<3; y++)
				{
					for(int x=0; x<3; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else if(_x<6)
			{
				for(int y=0; y<3; y++)
				{
					for(int x=3; x<6; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else 
			{
				for(int y=0; y<3; y++)
				{
					for(int x=6; x<9; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
		}
		else if (_y<6)
		{
			if(_x<3)
			{
				for(int y=3; y<6; y++)
				{
					for(int x=0; x<3; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else if(_x<6)
			{
				for(int y=3; y<6; y++)
				{
					for(int x=3; x<6; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else 
			{
				for(int y=3; y<6; y++)
				{
					for(int x=6; x<9; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
		}
		else 
		{
			if(_x<3)
			{
				for(int y=6; y<9; y++)
				{
					for(int x=0; x<3; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else if(_x<6)
			{
				for(int y=6; y<9; y++)
				{
					for(int x=3; x<6; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
			else 
			{
				for(int y=6; y<9; y++)
				{
					for(int x=6; x<9; x++)
					{
						carre[compt]=chiffres[x][y];
						compt++;
					}
				}
			}
		}
		
		for(int i=0; i<9; i++)
		{
			if(carre[i]==valeure)
			{
				return false;
			}
		}
		return true;
	}
	
	public void addcarre(int _x, int _y)
	{
		if(fixed[_x][_y]==false)
		{
			if(chiffres[_x][_y]!=9)
			{
				chiffres[_x][_y]++;
			}
			else
				chiffres[_x][_y]=0;
		}
	}
	
	public void subcarre(int _x, int _y)
	{
		if(fixed[_x][_y]==false)
		{
			if(chiffres[_x][_y]==0)
				chiffres[_x][_y]=9;
			else
				chiffres[_x][_y]--;
		}
	}
	
	public boolean check()
	{
		int [][] copy=new int [9][9];
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				copy[j][i]=chiffres[j][i];
			}
		}
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				chiffres[j][i]=0;
				if(!(ligneok(i, copy[j][i]) && coloneok(j, copy[j][i]) && carreok(j, i, copy[j][i])))
				{
					chiffres[j][i]=copy[j][i];
					return false;
				}
				else
					chiffres[j][i]=copy[j][i];
			}
		}
		return true;
	}
	
	public void setwin()
	{
		win=true;
	}
	
	protected void paintComponent(Graphics g)
	{
		Graphics2D surface= (Graphics2D)g;
		surface.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //antialiasing
		
		for (int i=0; i<this.base_grille.size(); i++) //dessin de la grille de base
		{
			if(i%3==0)
			{
				surface.setStroke(new BasicStroke(4));
			}
			else
				surface.setStroke(new BasicStroke(2));
			surface.setColor(new Color(120, 120, 120));
			surface.draw(this.base_grille.get(i));
		}
		
		
		surface.setFont(new Font("font_numbers", Font.PLAIN, 40));
		for(int y=0; y<9; y++) //écriture des chiffres
		{
			for(int x=0; x<9; x++)
			{
				if(chiffres[x][y]!=0)
				{
					if(fixed[x][y]==true)
						surface.setColor(new Color(0, 0, 0));
					else
						surface.setColor(new Color(7, 20, 163));
					surface.drawString(Integer.toString(chiffres[x][y]), x*66+20, y*66+47);
				}
			}
		}
		
		if(win)
		{
			surface.setFont(new Font("wincondition", Font.BOLD, 160));
			surface.setColor(new Color(232, 59, 39));
			surface.drawString("Bravo !", 30, 360);
		}
		/*try 
		{
			Image image=ImageIO.read(new File("C:/Users/Pierre/Downloads/bn.jpg"));
			surface.drawImage(image, 1, 1, 350, 350, null);
			surface.drawImage(image, 400, 1, 350, 350, null);
			
		} 
		catch (IOException e) 
		{
			System.out.print("erreur chargement image");
		}
		surface.setColor(Color.red);
		surface.setFont(new Font("Serif", Font.PLAIN, 25));
		surface.drawString("Placez vous bateaux !", 20, 400);*/
	}
}