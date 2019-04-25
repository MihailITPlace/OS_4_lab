import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends JFrame {

    PhysicalMemoryTable inst = PhysicalMemoryTable.getInstance();

    public MainWindow() {
        super("ОС Лаб 4");

        JPanel jPanel = new JPanel(new BorderLayout());
        setContentPane(jPanel);

        jPanel.setLayout(null);

        DefaultListModel listModel = new DefaultListModel();

        TablePanel tablePanel = new TablePanel(32, 32);
        tablePanel.setBounds(0, 0, 15 * 32, 15 * 32);
        jPanel.add(tablePanel);

        JPanel panel = new JPanel();
        panel.setBounds(15 * 32 + 50, 0, 800 - 15 * 32, 800);
        jPanel.add(panel);
        panel.setLayout(null);

        JTextField textField = new JTextField();
        textField.setBounds(0, 15 * 32 - 50, 800 - 15 * 32 - 50, 23);
        panel.add(textField);

        JTextField textFieldSize = new JTextField();
        textFieldSize.setBounds(800 - 15 * 32 - 45, 15 * 32 - 50, 45, 23);
        panel.add(textFieldSize);

        JList list = new JList(listModel);
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String fileName = (String) list.getSelectedValue();
                if (fileName == null) {
                    return;
                }
                inst.selectFile(fileName);
                tablePanel.repaint();
            }
        });
        list.setBounds(0, 11, 800 - 15 * 32, 15 * 32 - 100);
        panel.add(list);

        JButton button = new JButton("Add");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int sizeFile = Integer.parseInt(textFieldSize.getText());
                    String fileName = textField.getText();
                    inst.addFile(fileName, sizeFile);
                    listModel.addElement(textField.getText());
                    tablePanel.repaint();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.out.println(ex.getMessage());
                }
            }
        });
        button.setBounds(0, 15 * 32 - 20, 800 - 15 * 32, 23);
        panel.add(button);

        JButton button_1 = new JButton("Delete");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String fileName = (String) list.getSelectedValue();
                    listModel.removeElement(list.getSelectedValue());
                    inst.deleteFile(fileName);
                    tablePanel.repaint();
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    System.out.println(ex.getMessage());
                }
            }
        });
        button_1.setBounds(0, 15 * 32 + 20, 800 - 15 * 32, 23);
        panel.add(button_1);

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MainWindow gr = new MainWindow();
        gr.setVisible(true);
    }
}
