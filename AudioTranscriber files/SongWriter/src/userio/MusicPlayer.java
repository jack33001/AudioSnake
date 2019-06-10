package userio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import constants.Constants;
import jackstuff.Fourier;
import notes.BPMDetector;
import notes.Song;
import snake.Snake;
import util.AePlayWave;

public class MusicPlayer extends JPanel implements ActionListener {
	JFrame window = new JFrame("Selecta nice song pl0x =) ");
	JLabel info = new JLabel("pl0x don't play the next song until the first song is over.");
	JButton addButton = new JButton("Add Music");
	JButton playButton = new JButton("Play");
	// JFrame stopButton = new JFrame("Stop");
	Font customFont = new Font("", Font.BOLD, 20);
	JComboBox list = new JComboBox();
	JFileChooser browser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Sound", "wav");
	int returnValue;
	String[] musics = new String[10];
	int index = 0;
	File selectedFile;
	File sound;
	AudioInputStream ais;
	Clip clip;

	public MusicPlayer() {
		this.setBackground(Color.BLACK);
		window.add(this);

		addButton.addActionListener(this);
		playButton.addActionListener(this);
		// stopButton.addActionListener(this);

		info.setFont(new Font("", Font.ITALIC, 15));
		window.add(info, BorderLayout.PAGE_END);

		addButton.setFont(customFont);
		window.add(addButton, BorderLayout.LINE_START);

		playButton.setFont(customFont);
		window.add(playButton, BorderLayout.CENTER);

		// stopButton.setFont(customFont);
		// window.add(image,BorderLayout.LINE_END);

		window.add(list, BorderLayout.PAGE_START);

		browser.setFileFilter(filter);

		window.setSize(400, 200);
		window.setLocation(750, 350);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==addButton)
		{
			returnValue = browser.showOpenDialog(window);
			
			if(returnValue == browser.APPROVE_OPTION)
			{
				selectedFile = browser.getSelectedFile();
				musics[index] = selectedFile.toString();
				list.addItem("SpAgHeTti PiZzamAn  #" + index);
				index++;
			}
			
		}
		
		else if(ae.getSource() == playButton )
		{
			try {
			
				{
					Song music = new Song(Fourier.FFTFile(selectedFile.toString()));
					BPMDetector detector = new BPMDetector(selectedFile.toString()); //create BPMDetector object (automatically finds all beats)
					Snake snake = new Snake(detector, music); //create snake object
				    					
					sound = new File(musics[list.getSelectedIndex()]);
					ais = AudioSystem.getAudioInputStream(sound);
					clip = AudioSystem.getClip();
					clip.open(ais);
					clip.start(); //start playing music
					
					snake.startSnake(); //start the snake rendering

				}
				
			
			}catch(Exception e) {JOptionPane.showMessageDialog(null, e);}
			
			
		}
		
	}

}