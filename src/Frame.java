import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

public class Frame extends JFrame {
Panel panel;
JLabel instructions;
    JTextField workDirInput;
    public Frame (){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setTitle("Bulk rename utility");

        workDirInput = new JTextField("C:\\Users\\Due\\Desktop\\del");
        workDirInput.setBackground(new Color(96, 214, 126));

        this.add(workDirInput, BorderLayout.NORTH);
        this.setVisible(true);


//working directory input field action listener
        workDirInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isAncestorOf(panel)) {
                    remove(panel);
                    revalidate();
                    repaint();
                }
//                instructions.setVisible(false);
                remove(instructions);
                System.out.println(workDirInput.getText());
                activatePanel();
                workDirInput.setBackground(Color.white);

            }
        });

        instructions = new JLabel("^ Paste in a working directory ^");
        instructions.setFont(new Font("Calibri", Font.BOLD, 40));
        instructions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        instructions.setHorizontalTextPosition(SwingConstants.RIGHT);
        add(instructions);


    }

    public void activatePanel(){
        panel = new Panel(Paths.get(workDirInput.getText()), workDirInput);
        this.add(panel);
        panel.setVisible(true);
    }

}
