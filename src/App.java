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

            ButtonGroup group = new ButtonGroup();
            group.add(drawPoint);
            group.add(drawLine);
            group.add(drawPolygon);
            drawPolygon.setSelected(true);
            upPanel.add(drawPoint);
            upPanel.add(drawLine);
            upPanel.add(drawPolygon);
            upPanel.add(new JSeparator());

            //clear button
            JButton clearBut = new JButton("Clear");
            clearBut.setSize(60, 20);
            clearBut.addActionListener(this::actionClear);
            upPanel.add(clearBut);
            add(upPanel, BorderLayout.SOUTH);
            add(upPanel);
            setVisible(true);
        }
        public void actionClear(ActionEvent e) {
            Graphics g = getGraphics();
            super.paint(g);
            panel.actionPerformed(e);
        }

    }


    public static void main(String[] args) {
        new App();
    }
}
