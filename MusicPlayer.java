package GreasyMusic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MusicPlayer extends JPanel implements ActionListener {
	JFrame window = new JFrame("Selecta nice song pl0x =) ");
	JLabel info = new JLabel("pl0x don't play the next song until the first song is over.");
	JButton addButton = new JButton("Add Music");
	JButton playButton = new JButton("Play");
	//JFrame stopButton = new JFrame("Stop");
	Font customFont = new Font("",Font.BOLD,20);
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
	
	MusicPlayer()
	{
		this.setBackground(Color.BLACK);
		window.add(this);
		
		addButton.addActionListener(this);
		playButton.addActionListener(this);
		//stopButton.addActionListener(this);
		
		info.setFont(new Font("",Font.ITALIC,15));
		window.add(info,BorderLayout.PAGE_END);
		
		addButton.setFont(customFont);
		window.add(addButton,BorderLayout.LINE_START);
		
		playButton.setFont(customFont);
		window.add(playButton,BorderLayout.CENTER);
		
		//stopButton.setFont(customFont);
		//window.add(image,BorderLayout.LINE_END);
		
		window.add(list,BorderLayout.PAGE_START);
		
		browser.setFileFilter(filter);
		
		window.setSize(400,200);
		window.setLocation(750,350);
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
				if(list.getSelectedIndex()==0)
				{
					sound = new File(musics[list.getSelectedIndex()]);
					ais = AudioSystem.getAudioInputStream(sound);
					clip = AudioSystem.getClip();
					clip.open(ais);
					clip.start();
				}
				
				else if(list.getSelectedIndex()==2)
				{
					sound = new File(musics[list.getSelectedIndex()]);
					ais = AudioSystem.getAudioInputStream(sound);
					clip = AudioSystem.getClip();
					clip.open(ais);
					clip.start();
				}
			
			}catch(Exception e) {JOptionPane.showMessageDialog(null, e);}
			
			
		}
		
	}

}