import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.nio.file.Path;

import java.util.*;
import java.util.List;

public class Panel extends JPanel {
Path myPath;
JLabel filesLabel;
JPanel filterPanel, editPanel, fileTreePanel;

String prefixBlock = "";
String suffixBlock = "";

String fltName = "";
String fltExt = "";

String ignoreFolder = "";
String ignoreFile = "";

int delStart = 0;
int delEnd = 0;

    GroupLayout layout;

ArrayList<File> files = new ArrayList<>();
List<File> filesFltred;// = new ArrayList<>();
    HashMap<Path, ArrayList<File>> fileHash = new HashMap<>();



JTextField workDirInput;

Filters filtersObj;
Edits editsObj;

    public Panel(Path myPath, JTextField workDirInput){
    //set state
        this.myPath = myPath;
        this.workDirInput = workDirInput;

//set up the JPanel - panel
        this.setBackground(Color.pink);
//        this.setLayout(new GridLayout(2, 1));
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        this.setVisible(false);



        filterPanel = new JPanel();
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        JLabel filterTitle = new JLabel("Filter");
            filterTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            filterTitle.setFont(new Font("Calibri", Font.BOLD,30));
            filterPanel.add(filterTitle);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0; // 50% width
        gbc.weighty = 0.5; // Grow vertically based on content
        gbc.fill = GridBagConstraints.BOTH;
        this.add(filterPanel, gbc);
        filterPanel.setBackground(Color.PINK);
        filterPanel.setVisible(true);

        //EDIT PANEL TRYING TO GROUP

        editPanel = new JPanel();
        editPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

        JLabel editTitle = new JLabel("Edit");
            editTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            editTitle.setFont(new Font("Calibri", Font.BOLD,30));
            editPanel.add(editTitle);





        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0; // 50% width
        gbc.weighty = 0.5; // Grow vertically based on content
        gbc.fill = GridBagConstraints.BOTH;
        editPanel.setBackground(Color.GRAY);
        editPanel.setVisible(true);
        add(editPanel, gbc);


        fileTreePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        fileTreePanel.setLayout(new BoxLayout(fileTreePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(fileTreePanel);
        //increase scroll speed
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setUnitIncrement(50));


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER; // Span all columns
        gbc.weightx = 0.7; // Do not grow vertically
        gbc.fill = GridBagConstraints.BOTH;
//        fileTreePanel.setBackground(Color.cyan);
        fileTreePanel.setVisible(true);
        add(scrollPane, gbc);



        filesLabel = new JLabel("here files label o");
        filesLabel.setFont(new Font("Calibri", Font.PLAIN, 15));

//        listFiles();

        //        filterPanel.setBackground(Color.gray);
//        filterPanel.setVisible(true);
        filtersObj = new Filters(this);
        editsObj = new Edits(this);

        //submit button
        JButton submitBtn = new JButton(" VAMOS! ");
        submitBtn.addActionListener(e -> {
            vamosSubmit();
        });
        submitBtn.setBackground(new Color(96, 214, 126));
        submitBtn.setPreferredSize(new Dimension(200, 70));
        submitBtn.setToolTipText("Submit, rename the files");
        submitBtn.setBorder(new LineBorder(Color.DARK_GRAY, 1));
        submitBtn.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        editPanel.add(submitBtn);


        System.out.println("files in the file");
        digFiles(new File(myPath.toString()));
        updateFilters();


        fileTreePanel.add(filesLabel);


    }



    public void digFiles(File folder){
    File[] fileArr = folder.listFiles();

//    ArrayList<File> files = new ArrayList<>();

    for (File file : fileArr) {
        if (file.isDirectory())
            digFiles(file);
        else
            files.add(file);
    }
//    if (!files.isEmpty())
//        fileHash.put(folder.toPath(), files);


}

    public void listFiles(){
        File fileObj = new File(myPath.toString());
        File[] fileArr = fileObj.listFiles();

        files.addAll(Arrays.asList(fileArr));
        updateFilters();
//        updateFilesLabel();

    }

    public void vamosSubmit(){
        long numberFrom = editsObj.numberingFrom;
        int filesRenamed = 0;
        int filesFailed = 0;
        for (File file : filesFltred) {
            StringBuilder sb = new StringBuilder();

            String[] nameParts = getNameParts(file.getName());

            String name0delStartEnd = "";
            if (delStart + delEnd < nameParts[0].length())
                name0delStartEnd = nameParts[0].substring(delStart, nameParts[0].length() - delEnd);

            sb.append(file.getParent())
                    .append("\\")
                    .append(prefixBlock)
                .append(name0delStartEnd)
                .append(suffixBlock)
                .append(editsObj.numberingEnabled? numberFrom++ : ""  ) //numbering
                .append(nameParts[1].isEmpty() ? "" : ".")
                .append(nameParts[1]);


            if (   file.renameTo(new File(sb.toString()))   ) {
                filesRenamed++;
                System.out.println("Success -- " + sb);
            }
            else
                filesFailed++;


        }
        String failedMsg = (filesFailed > 0) ? "\nFailed renaming "+filesFailed+" files" : "";
        String msg = "Successfully renamed " + filesRenamed +"/" +filesFltred.size() +" files" + failedMsg;

        JOptionPane.showMessageDialog(null, msg, "Action completed", JOptionPane.PLAIN_MESSAGE);

        workDirInput.postActionEvent();


    }

    public void updateFilesLabel(boolean submit){
        StringBuilder sb = new StringBuilder("<html><body  style='text-align: left'>");
        String previous = "";

        long numberFrom = editsObj.numberingFrom;

        for (File file : filesFltred) {

            //nameparts[0] = Base Name, nameparts[1] = extension
        String[] nameParts = getNameParts(file.getName());
        String name0delStartEnd = "";
        if (delStart + delEnd < nameParts[0].length())
            name0delStartEnd = nameParts[0].substring(delStart, nameParts[0].length() - delEnd);

        int subfolderDepth =(int) file.getParentFile().toString().substring(myPath.toString().length()).chars().filter(ch -> ch == '\\').count();

            //if folder changed
            String folderJump="";
            if (!previous.equals(file.getParentFile().toString())) {
                folderJump = "<br>📁" + file.getParentFile().toString().substring(myPath.toString().length()) + "\\<br>";
                if (editsObj.numberingLocalEnabled) numberFrom = editsObj.numberingFrom;
            }
            sb.append (folderJump)

            .append("<span style='color: #fff'>"+ "___".repeat(subfolderDepth) + "</span>")
//                    .append("<span style='color: #00ffff'>"+ file.getParentFile().toString().substring(myPath.toString().length()) + "</span>")
//          sb.append(file.getParentFile().toString().substring(myPath.toString().length()))
//          sb.append(file.getParentFile().toString().substring(myPath.toString().length()))
            .append("<b>")
            .append(prefixBlock)
            .append(name0delStartEnd)
            .append(suffixBlock)
            .append(editsObj.numberingEnabled? numberFrom++ : ""  ) //numbering
            .append("</b>")
            .append(nameParts[1].isEmpty() ? "" : ".")
            .append(nameParts[1])
            .append("<br>");

            previous = file.getParentFile().toString();
        }


        sb.append("</html>");
        filesLabel.setText(sb.toString());

        System.out.println("updating files labels");
    }

    public String[] getNameParts(String name){
        if (name.contains(".")){
            String baseName = name.substring(0, name.lastIndexOf('.'));
            String extension = name.substring(name.lastIndexOf('.') + 1);
            return new String[]{baseName, extension};
        }
        return new String[]{name, ""};
    }

    public void updateFilters(){

        String[] ignoreFolderArr = ignoreFolder.split(" ");
        String[] ignoreFileArr = ignoreFile.split(" ");



        filesFltred = files.stream().filter(file -> {
            String[] nameParts = getNameParts(file.getName());

                    Path relativPath = myPath.relativize(file.toPath());
                    Path onlyRelFolders = relativPath.getParent();
                    String relFold = onlyRelFolders != null? onlyRelFolders.toString() : "";

                    //ignore folder
                    boolean NOTignoredFolder;
                    if (!ignoreFolder.isEmpty())
                    NOTignoredFolder = Arrays.stream(ignoreFolderArr)
                            .noneMatch(ignor -> relFold.contains(ignor));
                    else NOTignoredFolder = true;

                    //ignore file
                    boolean NOTignoredFile;

                    if (!ignoreFile.isEmpty())
                        NOTignoredFile = Arrays.stream(ignoreFileArr)
                                .noneMatch(ignor -> file.getName().contains(ignor));
                    else NOTignoredFile = true;

                    //filter out or keep this file-- output
                    return nameParts[0].contains(fltName) && nameParts[1].contains(fltExt) &&
                            NOTignoredFolder && NOTignoredFile;


        })
                .sorted((file1, file2) -> {

                    int compared = file1.getParent().toLowerCase().compareTo(
                            file2.getParent().toLowerCase());

                    if (compared < 0)
                        return -1;
                    else if(compared > 0)
                        return 1;
                    else
                        return 0;

                        }







//                        Integer.compare(
//                       (int) file1.getParentFile().toString().chars().filter(ch -> ch == '\\').count()  ,
//                       (int) file2.getParentFile().toString().chars().filter(ch -> ch == '\\').count()
//                        )

                )

                .toList();

        updateFilesLabel(false);
    }

}
