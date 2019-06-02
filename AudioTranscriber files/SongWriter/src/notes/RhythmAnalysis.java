package notes;
import constants.Constants;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;


public class RhythmAnalysis {
	
	private ArrayList<String> rhythms = new ArrayList<String>();
	private int[] noteColumn;
	
	public RhythmAnalysis(int[] noteColumn) {
		this.noteColumn = noteColumn;
		
		findRhythms();
		
	}
	
	public void findRhythms() {
		boolean note = noteColumn[0] == 1;
		int score = 1;
		
		for (int i = 1; i < noteColumn.length; i++) {
			if (noteColumn[i] == noteColumn[i - 1]) {
				score++;
			}
			else {
				rhythms.add((note ? "N" : "R") + score);
				note = noteColumn[i] == 1;
				score = 1;
			}
		}
		rhythms.add((note ? "N" : "R") + score);
	}
	
	public String[] getRhythms() {
		String[] strRhythms = new String[rhythms.size()];
		
		for (int i = 0; i < rhythms.size(); i++) strRhythms[i] = rhythms.get(i);
		
		return strRhythms;
	}
	
	public static void main(String[] args) {
		
		int[] noteColumn = {1,1,1,0,0,1,0,1,1,1,1,1,0};
		RhythmAnalysis testAnalysis = new RhythmAnalysis(noteColumn);
		
		for (String rhythm : testAnalysis.getRhythms()) System.out.print(rhythm + " ");
		
		JFrame f= new JFrame("Panel Example");
		JPanel jp = new JPanel(new GridLayout(1, testAnalysis.getRhythms().length));
		
		ArrayList<JLabel> music = new ArrayList<JLabel>();
		//JLabel note = new JLabel();
		//JLabel rest = new JLabel();
		//ImageIcon noteIcon = new ImageIcon(new ImageIcon("C:\\Users\\pawlakj4700\\Downloads\\16th_note.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));	
		//ImageIcon restIcon = new ImageIcon(new ImageIcon("C:\\Users\\pawlakj4700\\Downloads\\16th_rest.png").getImage().getScaledInstance(20, 40, Image.SCALE_DEFAULT));
		
		//note.setIcon(noteIcon);
		//rest.setIcon(restIcon);

		
		for (String rhythm : testAnalysis.getRhythms()) {
			if (rhythm.charAt(0) == 'N') {
				JLabel note = new JLabel();
				ImageIcon noteIcon = new ImageIcon(new ImageIcon("C:\\Users\\pawlakj4700\\Downloads\\16th_note.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));	
				note.setIcon(noteIcon);

				music.add(note);
			}
			else {
				JLabel rest = new JLabel();
				ImageIcon restIcon = new ImageIcon(new ImageIcon("C:\\Users\\pawlakj4700\\Downloads\\16th_rest.png").getImage().getScaledInstance(20, 40, Image.SCALE_DEFAULT));
				rest.setIcon(restIcon);

				music.add(rest);
			}
		}
		
		for (JLabel beat : music) jp.add(beat);
		f.add(jp);
		f.setSize(400,400);
		f.setVisible(true);
	}
	
}
