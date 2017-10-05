import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PuzzlePiece{
	private int index; 
	private int val;
	private JButton button;

	public PuzzlePiece(int index, int val){
		this.index = index;
		this.val = val;
		this.button = new JButton(Integer.toString(val));
	}

	public PuzzlePiece(){
		this.index = 16;
		this.val = 16;
		this.button=new JButton();
	}

	public int getIndex() {
		return index;
	}

	public int getVal(){
		return val;
	}

	public JButton getButton() {
		return button;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setVal(int val){
		this.val = val;
	}
}

