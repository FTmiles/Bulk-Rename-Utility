import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalTime;

public class Filters {

Panel mainPanel;

public Filters(Panel mainPanel){
    //state set up
    this.mainPanel = mainPanel;

    //GUI set up
    filterName();
    ignore();
    }


    public void filterName(){

    JLabel fltNameLbl = new JLabel("By file name");
    JTextField fltName = new JTextField(10);

        JPanel group = new JPanel();
        group.setOpaque(false);
        group.add(fltNameLbl);
        group.add(fltName);
        group.setMaximumSize(new Dimension(400, 50));
        mainPanel.filterPanel.add(group);

//        mainPanel.filterPanel.add(fltNameLbl);
//        mainPanel.filterPanel.add(fltName);
//=====================================================================
    JLabel fltExtLbl = new JLabel("By file extension");
    JTextField fltExt = new JTextField(10);


        JPanel group1 = new JPanel();
            group1.setOpaque(false);
            group1.add(fltExtLbl);
            group1.add(fltExt);
            group1.setMaximumSize(new Dimension(400, 50));
            mainPanel.filterPanel.add(group1);
//        mainPanel.filterPanel.add(fltExtLbl);
//        mainPanel.filterPanel.add(fltExt);

        fltExt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.fltExt = fltExt.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.fltExt = fltExt.getText();
                mainPanel.updateFilters();            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        fltName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.fltName = fltName.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.fltName = fltName.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

    }
    public void ignore(){

    JLabel ignoreFileLbl = new JLabel("Ignore files");
    JTextField ignoreFile = new JTextField(10);

        JPanel group1 = new JPanel();
            group1.setOpaque(false);
            group1.add(ignoreFileLbl);
            group1.add(ignoreFile);
            group1.setMaximumSize(new Dimension(400, 50));
            mainPanel.filterPanel.add(group1);

//        mainPanel.filterPanel.add(ignoreFileLbl);
//        mainPanel.filterPanel.add(ignoreFile);

    JLabel ignoreFolderLbl = new JLabel("Ignore folders");
    JTextField ignoreFolder = new JTextField(10);

        JPanel group2 = new JPanel();
            group2.setOpaque(false);
            group2.add(ignoreFolderLbl);
            group2.add(ignoreFolder);
            group2.setMaximumSize(new Dimension(400, 50));
            mainPanel.filterPanel.add(group2);

//        mainPanel.filterPanel.add(ignoreFolderLbl);
//        mainPanel.filterPanel.add(ignoreFolder);

        ignoreFile.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.ignoreFile = ignoreFile.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.ignoreFile = ignoreFile.getText();
                mainPanel.updateFilters();            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });


        ignoreFolder.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mainPanel.ignoreFolder = ignoreFolder.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mainPanel.ignoreFolder = ignoreFolder.getText();
                mainPanel.updateFilters();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

    }

}
