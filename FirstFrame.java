package practice;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javafx.application.Application.launch;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class FirstFrame extends BattleshipMain
    {
        public static String PlayerName;
        public FirstFrame()
            {
                JFrame firstframe = new JFrame();
                JLabel InsertName = new JLabel("Please insert your name and press ''OK'' ");
                
                JTextField NameField = new JTextField(15);
                
                JButton OK = new JButton("ΟΚ");
                
                JPanel panelTop = new JPanel();
                JPanel panelCenter = new JPanel();
                JPanel panelBottom = new JPanel();
                
                panelTop.add(InsertName);
                panelCenter.add(NameField);

                panelBottom.add(OK);
                
                
                firstframe.setSize(350, 150);
                firstframe.setLayout(new BorderLayout(0,12));
                firstframe.setTitle("BattleShip - Player`s Details");

                firstframe.add(panelTop, BorderLayout.NORTH);
                firstframe.add(panelCenter, BorderLayout.CENTER);
                firstframe.add(panelBottom, BorderLayout.SOUTH);
                
                
                firstframe.setLocationRelativeTo(null);
                firstframe.setVisible(true);
                firstframe.setResizable(false);
                firstframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
                
                OK.addActionListener(new ActionListener() 
                    {
			@Override
			public void actionPerformed(ActionEvent arg0) 
                            {
                                firstframe.setVisible(false);
                                PlayerName = NameField.getText();
                                launch(BattleshipMain.class);
                            }
                    });
            }

        public static void main(String[] args) {
        new FirstFrame();
    }

}
    