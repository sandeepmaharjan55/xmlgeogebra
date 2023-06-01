package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;

public class App extends JFrame{
    private boolean expand = false;
    public boolean midPoint = false;
    public AppPanel panel;

    public App() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("xml Geogebra");

        // Menu bar
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        menuBar = new JMenuBar();
        //This shows menu.
        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem = new JMenuItem(new AbstractAction("Read File") {
            public void actionPerformed(ActionEvent e) {
                // Button pressed logic goes here
                File f = new File(System.getProperty("user.dir")+"/Geogebra/");
                JFileChooser j = new JFileChooser(f, FileSystemView.getFileSystemView());
                // restrict the user to select files of all types
                j.setAcceptAllFileFilterUsed(false);
                // only allow files of .xml extension
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .xml files", "xml");
                j.addChoosableFileFilter(restrict);
                int r = j.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                   panel.readData(j.getSelectedFile().getAbsolutePath());
                }
            }
        });
        menu.add(menuItem);
        menu.add(new JMenuItem(new AbstractAction("Write File") {
            public void actionPerformed(ActionEvent e) {
                // Button pressed logic goes here
                File f = new File(System.getProperty("user.dir")+"/Geogebra/templateFile/");
                JFileChooser j = new JFileChooser(f, FileSystemView.getFileSystemView());
                // restrict the user to select files of all types
                j.setAcceptAllFileFilterUsed(false);
                // set a title for the dialog
                j.setDialogTitle("Choose a .xml template file");
                // only allow files of .xml extension
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .xml files", "xml");
                j.addChoosableFileFilter(restrict);
                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
//                   j.getSelectedFile().getName();
                 panel.saveData(j.getSelectedFile().getName());
                    JOptionPane.showMessageDialog(null,
                            "Saved in Geogebra/savedFile/geogebra.xml",
                            "File saved",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        setJMenuBar(menuBar);
// side control panel - left side
        ControlPanel cp = new ControlPanel();
        cp.setBounds(525, 10, 80, 30);
        add(cp, BorderLayout.WEST);
        //System.out.println("value of cp "+cp);

//add polygons points, line area
        panel = new AppPanel();
        panel.cp = cp;
        panel.setBorder(BorderFactory.createCompoundBorder());
        panel.setBounds(0,0,500,500);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
    public class ControlPanel extends JPanel {
        public JTextField textbox = new JTextField();
        public JTextField textboxUnclick = new JTextField();
        public JRadioButton drawPoint = new JRadioButton("Point");
        public JRadioButton drawLine = new JRadioButton("Line");
        public JRadioButton drawPolygon = new JRadioButton("Polygon");



        public ControlPanel() {
            JPanel upPanel = new JPanel();
            upPanel.setPreferredSize(new Dimension(200,500));
            upPanel.setMaximumSize(new Dimension(200,500));
            upPanel.setLayout(new GridLayout(0,1));
            setLayout(new GridLayout(0,1)); // explore other layouts
            setMaximumSize(new Dimension(200,700));
            textbox.setText("1");
            textbox.setPreferredSize(new Dimension(30,20));
            upPanel.add(new JLabel("Select Element"));
            upPanel.add(textbox);
            upPanel.add(textboxUnclick);
            textboxUnclick.setEditable(false);
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            ButtonGroup group = new ButtonGroup();
            group.add(drawPoint);
            group.add(drawLine);
            group.add(drawPolygon);
            //drawPolygon.setSelected(true);

            //deselect button here
            JButton deselectButton = new JButton("Deselect");
//            deselectButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    group.clearSelection();
//                }
//            });
            deselectButton.addActionListener(this::actionClear);
            upPanel.add(deselectButton);
            upPanel.add(new JLabel("Select Geometry Collection "));
            upPanel.add(drawPoint);
            upPanel.add(drawLine);
            upPanel.add(drawPolygon);
            upPanel.add(new JSeparator());

            //delete button here Select element and Geometry
            upPanel.add(new JLabel("Select element and Geometry"));
            JButton deleteButton = new JButton("Delete");
//            deselectButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    group.clearSelection();
//                }
//            });
            deleteButton.addActionListener(this::actionDelete);
            upPanel.add(deleteButton);
            // Add spaces in between fields
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));

            //clear button
            JButton clearBut = new JButton("Clear");
            clearBut.setSize(60, 20);
            clearBut.addActionListener(this::actionClear);
            upPanel.add(clearBut);
            add(upPanel, BorderLayout.SOUTH);
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));
            add(upPanel);
            setVisible(true);
        }

        private void actionDelete(ActionEvent e) {
          panel.actionDeletePerformed(e);

        }


//        private void actionDeselect(ActionEvent actionEvent) {
//            System.out.println("m hahar");
//            group.clearSelection();
//
//        }

        public void actionClear(ActionEvent e) {
//            Graphics g = getGraphics();
//            super.paint(g);
            panel.actionPerformed(e);
        }

    }


    public static void main(String[] args) {
        new App();
    }
}


// May 25
// 1. Convert line to objects like polygons and points
// 2. Add draw methods in all objects to draw the item, and also add labels (A,L,P) respectively
// 3. Add select radio button to do nothing or any other option to do so
// 4. While drawing lines, when first point is drawn, the mouse cursor should behave like end of that line and draw the line. Use mouse motion event


//May 26
// Work on line drawing from mouse movement - done
// Add draw method in polygon
// add vertices list(points) and edges list(segments) in the polygon class - done


//May 31
//polygon move, set index null after deletion array
//display the length of th line
// add button to find mid point of line
//polygon perimeter and area
// if line intersect represent it with red