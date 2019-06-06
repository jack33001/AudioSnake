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
	static int baseSnakeWidth = 5;
	static int baseSnakeLength = 5;
	int snakeWidth;
	int snakeLength;
	double averageEnergy;
	
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
	int red = 125;
	int green = 125;
	int blue = 125;
	BPMDetector myDetector;
	long start;
	int loopCounter = 0;
	
	public Snake(BPMDetector myDetector) {
		this.myDetector = myDetector;
		
		//set averageEnergy equal to energy of whole song
	}
	
	
	public void paintComponent(Graphics g) 
	{
		Color mySnake = new Color(red, green, blue);
		g.setColor(mySnake);
		g.fillRect((int) x, (int) y, snakeWidth, snakeLength);
		
		tm.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		double currentTime = (System.currentTimeMillis() - start)/1000.0;
		
		myDetector.getLocalInstantEnergy(currentTime);
		int currentTempo = myDetector.getTempoAt(currentTime);
		speed = Math.pow(currentTempo / 100.0, 2);
		
		//update snake size values based on energy values. Don't update averageEnergy loop iteration because getLocalAverageEnergy() is a slow function.
		if (loopCounter % 10 == 0) averageEnergy = myDetector.getLocalAverageEnergy(currentTime);
		double energyProp = myDetector.getLocalInstantEnergy(currentTime) / averageEnergy;
		snakeWidth = (int)Math.round(baseSnakeWidth * Math.pow(energyProp, Constants.PULSE_FACTOR));
		snakeLength = snakeWidth;
		
		accY = Math.random() * 0.4 - 0.2;
		velY = velY + accY;
		
		accX = Math.random() * 0.4 - 0.2;
		velX = velX + accX;
		
		//randomly change color
		/*double redRando = Math.random() * 11.0 - 5.0;
		if (redRando < 0) {
			red += 0.01;
		}
		if (redRando > 0) {
			red -= 0.01;
		}
		if (red < 10) {
			red += 0.02;
		}
		if (red > 240) {
			red -= 0.02;
		}
		
		double greenRando = Math.random() - 0.5;
		if (greenRando > 0) {
			green += 0.01;
		}
		if (greenRando < 0) {
			green -= 0.01;
		}
		if (green < 10) {
			green += 0.02;
		}
		if (green > 240) {
			green -= 0.02;
		}
		
		double blueRando = Math.random() - 0.5;
		if (blueRando > 0) {
			blue += 0.01;
		}
		if (blueRando < 0) {
			blue -= 0.01;
		}
		if (blue < 10) {
			blue += 0.02;
		}
		if (blue > 240) {
			blue -= 0.02;
		}*/
		
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
		System.out.println("Current size: " + snakeWidth + ", " + (myDetector.getLocalInstantEnergy(currentTime) + " " + averageEnergy));
		System.out.println("Current tempo: " + currentTempo);
		System.out.println("Current speed: " + speed + "\n");
		
	
		//System.out.println((System.currentTimeMillis() - start)/1000.0);
		//System.out.println("adder:" + greenRando);
		//System.out.println("red:" + redRando);
		//System.out.println("green:" + greenRando);
		//System.out.println("blue:" + blueRando);
		
		
		y = y + velY;
		x = x + velX;
			
		repaint();
	
		}
	
		
	public void startSnake()
	{
		JFrame jf = new JFrame();
		jf.setTitle("Snake");
		jf.setSize(boardSizex, boardSizey);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(this);
		jf.setVisible(true);
		start = (long)System.currentTimeMillis();
		}
	}

	


