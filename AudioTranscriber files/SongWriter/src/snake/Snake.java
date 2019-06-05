package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import constants.Constants;
import notes.BPMDetector;

public class Snake extends JPanel implements ActionListener {
	
	static int boardSizex = 1000;
	static int boardSizey = 1000;
	static int snakeWidth = 5;
	static int snakeLength = 5;
	
	Timer tm = new Timer(10, this);		//5 milliseconds
	double x = 500;
	double y = 500;
	double velX = 0;
	double velY = 0;
	double accX = 0;
	double accY = 0;
	double speed = 0;
	double randomYAcc = 0;
	double randomYVel = 0;
	int negate = 0;
	int tempo = 250;
	int tempoSize = 50;
	double[] tempos = new double[tempoSize];
	int red = 125;
	int green = 125;
	int blue = 125;
	private BPMDetector myDetector = new BPMDetector(Constants.WAVFILE_LOCATION);
	long start = (long)System.currentTimeMillis();
	
	
	public void paintComponent(Graphics g) 
	{
		Color mySnake = new Color(red, green, blue);
		g.setColor(mySnake);
		g.fillRect((int) x, (int) y, snakeWidth, snakeLength);
		
		tm.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		accY = Math.random() * 0.4 - 0.2;
		velY = velY + accY;
		
		accX = Math.random() * 0.4 - 0.2;
		velX = velX + accX;
		
		
		
		int currentTempo = myDetector.getTempoAt((System.currentTimeMillis() - start)/1000.0);
		speed = currentTempo / 100.0;
		
		
		if (y < 200) {
			accY += 0.01;
			if (y < 200) {
				velY += 0.05;
			}
		}
		if (y > 800) {
			accY -= 0.01;
			if (y > 800) {
				velY -= 0.05;
			}
		}
		if (x < 200) {
			accX += 0.01;
			if (x < 200) {
				velX += 0.05;
			}
		}
		if (x > 800) {
			accX -= 0.01;
			if (x > 800) {
				velX -= 0.05;
			}
		}
		
			
		if (x < 0||x > boardSizex - 40)
			velX = -velX;
		if (y < 0||y > boardSizey - 40)
			velY = -velY;
		
		double xSqr = velX * velX;
		double ySqr = velY * velY;
		
		if (xSqr + ySqr < speed * speed) {
			if (velY > 0) {
				velY += 0.03;
			}
			if (velY < 0) {
				velY -= 0.03;
			}
			if (velX > 0) {
				velX += 0.03;
			}
			if (velX < 0) {
				velX -= 0.03;
			}
			
		}

		
		if (xSqr + ySqr > speed * speed) {
			if (velY > 0) {
				velY -= 0.03;
			}
			if (velY < 0) {
				velY += 0.03;
			}
			if (velX > 0) {
				velX -= 0.03;
			}
			if (velX < 0) {
				velX += 0.03;
			}
			
			
		}
		
		
		//System.out.println(Math.sqrt(xSqr + ySqr));
		//System.out.println("velY:" + velY);
		//System.out.println("velX:" + velX);
		System.out.println("current tempo:" + currentTempo);
		System.out.println("current speed:" + speed);
		
	
		System.out.println((System.currentTimeMillis() - start)/1000.0);
		//System.out.println("adder:" + greenRando);
		//System.out.println(red);
		
		y = y + velY;
		x = x + velX;
			
		repaint();
	
		}
	
		
	public static void main(String[] args)
	{
		
		Snake r = new Snake();
		JFrame jf = new JFrame();
		jf.setTitle("Snake");
		jf.setSize(boardSizex, boardSizey);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(r);
		jf.setVisible(true);
		}
	}

	

