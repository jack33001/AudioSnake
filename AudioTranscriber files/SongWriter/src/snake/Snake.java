package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import constants.Constants;
import notes.BPMDetector;
import notes.Song;

@SuppressWarnings("serial")
public class Snake extends JPanel implements ActionListener {
	
	static int boardSizex = 1000;
	static int boardSizey = 1000;
	static int baseSnakeWidth = Constants.baseSnakeSize;
	static int baseSnakeLength = Constants.baseSnakeSize;
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
	int colorChange = 1;
	int noteCounter = 0;
	Song mySong;
	
	public Snake(BPMDetector myDetector, Song mySong) {
		this.myDetector = myDetector;
		this.mySong = mySong;
		
		int sum = 0;
		int count = 0;
		
		//calculate average energy for song, ignoring first few and last few seconds
		for (int i = Constants.FADE_CONST; i < myDetector.getSongLength() - Constants.FADE_CONST; i++) {
			sum += myDetector.getLocalAverageEnergy(i);
			count++;
		}
		averageEnergy = sum / count;
		
		//set averageEnergy equal to energy of whole song
	}
	
	
	public void paintComponent(Graphics g) 
	{
		Color mySnake = new Color(red, green, blue);
		g.setColor(mySnake);
		
		//g.fillRect((int) (x - snakeWidth/2), (int) (y - snakeLength/2), snakeWidth, snakeLength);
		g.fillOval((int) (x - snakeWidth/2), (int) (y - snakeLength/2), snakeWidth, snakeLength);
		
		tm.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		double currentTime = (System.currentTimeMillis() - start)/1000.0;
		
		myDetector.getLocalInstantEnergy(currentTime);
		int currentTempo = myDetector.getTempoAt(currentTime);
		//speed = Constants.SPEED_CONSTANT*Math.pow(currentTempo / 100.0, 2)*(snakeWidth/baseSnakeWidth);
		speed = Constants.SPEED_CONSTANT*Math.pow(currentTempo / 100.0, 2)*((double)snakeWidth/baseSnakeWidth);
		//speed = (currentTempo / 100.0) * (Constants.SPEED_CONSTANT);
		
		//update snake size values based on energy values.
		double energyProp = myDetector.getLocalInstantEnergy(currentTime) / averageEnergy;
		snakeWidth = (int)Math.round(baseSnakeWidth * Math.pow(energyProp, Constants.PULSE_FACTOR));
		snakeLength = snakeWidth;
		
		accY = Math.random() * 0.4 - 0.2;
		velY = velY + accY;
		
		accX = Math.random() * 0.4 - 0.2;
		velX = velX + accX;
		
		
		//COLOR//
		for (int j = 0; j < mySong.getNotesAt(currentTime).length; j++) {
			if (mySong.getNotesAt(currentTime)[j] == 1) {
			noteCounter = j;
			  
			if (noteCounter % 12 == 0) {	//c is yellow (255-255-0)
				if (red < 254) {
					red += colorChange;
				}
				if (green < 254) {	
					green += colorChange;
				}
				if (blue > 0) {
					blue -= colorChange;
				}
			}
				
			if (noteCounter % 12 == 1) {	//c# is yellow-green (102 - 255 - 102)
				if (red < 102) {
					red += colorChange;
				}
				if (red > 102) {
					red -= colorChange;
				}
				if (green < 254) {
					green += colorChange;
				}
				if (blue < 102) {
					blue += colorChange;
				}
				if (blue > 102) {
					blue -= colorChange;
				}
			}
				
			if (noteCounter % 12 == 2) {	//d is green (0 - 204 - 0)
				if (red > 0) {
					red -= colorChange;
				}
				if (blue < 204) {
					blue += colorChange;
				}
				if (green > 204) {
					green -= colorChange;
				}
				if (green < 204) {
					green += colorChange;
				}
				if (blue > 0) {
					blue -= colorChange;
				}
			}
			if (noteCounter % 12 == 3) {	//d# is green-blue (51 - 153 - 255)
				if (red < 51) {
					red += colorChange;
				}
				if (red > 51) {
					red -= colorChange;
				}
				if (green < 153) {
					green += colorChange;
				}
				if (green > 153) {
					green -= colorChange;
				}
				if (blue < 254) {
					blue += colorChange;
				}
			}
			if (noteCounter % 12 == 4) {	//e is blue (0 - 0 - 255)
				/*if (red > 0) {
					red -= colorChange;
				}*/
				if (green > 0) {			//COMMENT THIS IN FOR SUPERSTITION
					green -= colorChange;
				}
				if (blue < 254) {
					blue += colorChange;
				}
			}
			/*if (noteCounter % 12 == 5) {	//f is indigo (0 - 0 -153)
				if (red > 0) {
					red -= colorChange;
				}
				if (green > 0) {			//COMMENT THIS IN FOR SUPERSTITION
					green -= colorChange;
				}
				if (blue < 153) {
					blue += colorChange;
				}
				if (blue > 153) {
					blue -= colorChange;
				}
			}*/
			if (noteCounter % 12 == 6) {	//f# is dark dark blue (0 - 0 - 200)
				if (red > 0) {
					red -= colorChange;
				}
				if (green > 0) {
					green -= colorChange;
				}
				if (blue < 200) {
					blue += colorChange;
				}
				if (blue > 200) {
					blue -= colorChange;
				}
			}
			if (noteCounter % 12 == 7) { //g is violet (102 - 0 - 153)
				if (red < 102) {
					red += colorChange;
				}
				if (red > 102) {
					red -= colorChange;
				}
				if (green > 1) {
					green -= colorChange;
				}
				if (blue < 153) {
					blue += colorChange;
				}
				if (blue > 153) {
					blue -= colorChange;
				}
			}
				
			if (noteCounter % 12 == 8) {	//g# (209 - 0 - 190)
				if (red < 209) {
					red += colorChange;
				}
				if (red > 209) {
					red -= colorChange;
				}
				if (green > 0) {
					green -= colorChange;
				}
				if (blue > 190) {
					blue -= colorChange;
				}
				if (blue < 190) {
					blue += colorChange;
				}
			}
			if (noteCounter % 12 == 9) {		//a is red (255-0-0)
				if (red < 255) {
					red += colorChange;
				}
				if (green > 0) {
					green -= colorChange;
				}
				if (blue > 0) {
					blue -= colorChange;
				}
			}
			if (noteCounter % 12 == 10) {		//a# (255 - 153 - 51)
				if (red < 255) {
					red += colorChange;
				}
				if (green < 153) {
					green += colorChange;
				}
				if (green > 153) {
					green -= colorChange;
				}
				if (blue < 51) {
					blue += colorChange;
				}
				if (blue > 51) {
					blue -= colorChange;
				}
			}
			if (noteCounter % 12 == 11) {		//b is orange (255-102-0)
				if (red < 255) {
					red += colorChange;
				}
				if (green < 102) {
					red += colorChange;
				}
				if (green > 102) {
					red -= colorChange;
				}
				if (blue > 0) {
					blue += colorChange;
				}	
			}
			if (red > 255) red = 255;
			if (blue > 255) blue = 255;
			if (green > 255) green = 255;
			}

		}

		//AVOID EDGES
		
		
		
		if (x < 0) {
			x += boardSizex;
		}
		if (x > boardSizex - Constants.baseSnakeSize) {
			x -= boardSizex;
		}
		if (y < 0) {
			y += boardSizey;
		}
		if (y > boardSizey - Constants.baseSnakeSize) {
			y -= boardSizey;
		}
		
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
		
		
		System.out.println("real speed:" + Math.sqrt(xSqr + ySqr));
		//System.out.println("velY:" + velY);
		//System.out.println("velX:" + velX);
		//System.out.println("Current size: " + snakeWidth);
		//System.out.println("Current tempo: " + currentTempo);
		System.out.println("Speed I want: " + speed);
		//System.out.println("uh j: " + j);
		//System.out.println("velY:" + velY);
		//System.out.println("velX:" + velX);
		//System.out.println("Current color: " + red + " " + green + " " + blue + "\n");
		
	
		//System.out.println((System.currentTimeMillis() - start)/1000.0);
		//System.out.println("adder:" + greenRando);
		//System.out.println("red:" + red);
		//System.out.println("green:" + green);
		//System.out.println("blue:" + blue);
		
		
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

	


