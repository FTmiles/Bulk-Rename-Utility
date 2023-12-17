import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

public class Edits {
    Panel mainPanel;
    JTextField prefix,suffix;
    JCheckBox checkBoxNumbering, checkBoxLocalNumb;

    long numberingFrom = 1;
    boolean numberingEnabled = false;
    boolean numberingLocalEnabled = false;
    JPanel delGroup = new JPanel();

    public Edits(Panel mainPanel){
        //state set up
        this.mainPanel = mainPanel;

        //GUI set up
        prefix();
        suffix();

        startDel();
        endDel();

        delGroup.setMaximumSize(new Dimension(280, 50));
            JLabel label = new JLabel("Delete characters");
            label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        delGroup.setOpaque(false);

        mainPanel.editPanel.add(label);
        mainPanel.editPanel.add(delGroup);





        numbering();

    }

    public void numbering(){
        checkBoxNumbering = new JCheckBox("Enable Numbering");
        checkBoxNumbering.setBackground(Color.GRAY);

        checkBoxNumbering.addActionListener(e -> {
            numberingEnabled = checkBoxNumbering.isSelected();
            mainPanel.updateFilters();
        });

        //local in folders
        checkBoxLocalNumb = new JCheckBox("only local");
        checkBoxLocalNumb.setBackground(Color.GRAY);

        checkBoxLocalNumb.addActionListener(e -> {
            numberingLocalEnabled = checkBoxLocalNumb.isSelected();
            if (numberingEnabled)
                mainPanel.updateFilters();
        });

        //starting from - spinner

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 0, 1999_999_999, 1);
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(50, spinner.getPreferredSize().height));

        spinner.addChangeListener(e -> {
            numberingFrom = (int) spinner.getValue();
            if (numberingEnabled)
                mainPanel.updateFilesLabel(false);

        });



        JPanel group = new JPanel();
        group.setOpaque(false);
        group.add(checkBoxNumbering);
        group.add(checkBoxLocalNumb);
        group.add(spinner);
        group.setMaximumSize(new Dimension(400, 50));

        mainPanel.editPanel.add(group);

    }

    public void endDel(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

        JSpinner spinner = new JSpinner(spinnerModel);


        spinner.addChangeListener(e -> {
            mainPanel.delEnd = (int) spinner.getValue();
            mainPanel.updateFilesLabel(false);

//            System.out.println("Selected value: " + mainPanel.delStart);

        });





        delGroup.add(new JLabel("End: "));
        delGroup.add(spinner);

//        mainPanel.editPanel.add(delGroup);


    }

    public void startDel(){
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);

        JSpinner spinner = new JSpinner(spinnerModel);


        spinner.addChangeListener(e -> {
            mainPanel.delStart = (int) spinner.getValue();
            mainPanel.updateFilesLabel(false);

//            System.out.println("Selected value: " + mainPanel.delStart);

        });




        delGroup.add(new JLabel("Start: "));
        delGroup.add(spinner);

    }

    public void prefix(){

        JLabel prefixLbl = new JLabel("Prefix");
        prefix = new JTextField();
        prefix.setColumns(20);

        JPanel group = new JPanel();
        group.setOpaque(false);
        group.add(prefixLbl);
        group.add(prefix);
        group.setMaximumSize(new Dimension(400, 50));
        mainPanel.editPanel.add(group);


        prefix.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.prefixBlock = prefix.getText();
                mainPanel.updateFilesLabel(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.prefixBlock = prefix.getText();
                mainPanel.updateFilesLabel(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println(LocalTime.now() + "changed");
            }
        });
    }


    public void suffix(){
        JLabel suffixLbl = new JLabel("Suffix");
        suffix = new JTextField();
        suffix.setColumns(20);

        JPanel group = new JPanel();
        group.setOpaque(false);
        group.add(suffixLbl);
        group.add(suffix);
        group.setMaximumSize(new Dimension(400, 50));
        mainPanel.editPanel.add(group);


        suffix.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.suffixBlock = suffix.getText();
                mainPanel.updateFilesLabel(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.suffixBlock = suffix.getText();
                mainPanel.updateFilesLabel(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

}
