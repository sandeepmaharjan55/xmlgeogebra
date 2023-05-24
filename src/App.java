package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JOptionPane;
public class App extends JFrame{
    private boolean expand = false;
    public boolean midPoint = false;
    public AppPanel frame;

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
        menu = new JMenu("Menu");
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
                   frame.readData(j.getSelectedFile().getAbsolutePath());
                }
            }
        });
        menu.add(menuItem);
        menu.add(new JMenuItem(new AbstractAction("Save File") {
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
                 frame.saveData(j.getSelectedFile().getName());
                    JOptionPane.showMessageDialog(null,
                            "Saved in Geogebra/savedFile/geogebra.xml",
                            "File saved",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        setJMenuBar(menuBar);
//
        ControlPanel cp = new ControlPanel();
        cp.setBounds(525, 10, 80, 30);
        add(cp, BorderLayout.EAST);
        System.out.println("value of cp "+cp);

//this helps to add polygons
        frame = new AppPanel();
        frame.cp = cp;
        frame.setBorder(BorderFactory.createCompoundBorder());
        frame.setBounds(0,0,500,500);
        add(frame, BorderLayout.CENTER);

        setVisible(true);
    }
    public class ControlPanel extends JPanel {
        public TextField textbox = new TextField();
        public TextField expSize = new TextField();
        public JRadioButton drawPoint = new JRadioButton("Draw Point");
        public JRadioButton drawLine = new JRadioButton("Draw Line");
        public JRadioButton drawPolygon = new JRadioButton("Draw Polygon");

        public ControlPanel() {
            JPanel upPanel = new JPanel();
            upPanel.setPreferredSize(new Dimension(200,500));
            upPanel.setMaximumSize(new Dimension(200,500));
            upPanel.setLayout(new GridLayout(0,1));
            setLayout(new GridLayout(0,1));
            setMaximumSize(new Dimension(200,700));
            textbox.setText("1");
            textbox.setPreferredSize(new Dimension(30,20));
            upPanel.add(new JLabel("Select Element"));
            upPanel.add(textbox);
            upPanel.add(Box.createRigidArea(new Dimension(0,55)));

            // expansion size
            expSize.setText("1");
            expSize.setPreferredSize(new Dimension(30,20));
            ButtonGroup group = new ButtonGroup();
            group.add(drawPoint);
            group.add(drawLine);
            group.add(drawPolygon);
            drawPolygon.setSelected(true);
            upPanel.add(drawPoint);
            upPanel.add(drawLine);
            upPanel.add(drawPolygon);
            upPanel.add(new JSeparator());
            add(upPanel);
            setVisible(true);
        }
    }
    public static void main(String[] args) {
        new App();
    }
}
