import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class swing extends JFrame{

	JPanel p= new JPanel();
	JButton b=new JButton("Yoo buddy");
	JButton y=new JButton("check");
	JTextField t= new JTextField("Hi",20);                           //for the inputs
	JTextArea ta =new JTextArea("HOw\nare \nyou?",5,20);
	JLabel l=new JLabel("What's up..?");//say what you want to say..
	String choices[]={"1","2","3"};
	JComboBox cb=new JComboBox(choices);


	public static void main (String[] args){
	new swing();
	}

	public swing(){

	super("APP");

	setSize(400,300);
	setResizable(true);

	setVisible(true);

	p.add(b);
	p.add(t);
	p.add(ta);
	p.add(y);
	p.add(l); 
	p.add(cb);
	add(p);
	

	}

}