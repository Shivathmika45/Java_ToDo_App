
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoApp extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> todoList;
    private JTextField inputField;
    private JButton addButton, removeButton, clearButton;

    public ToDoApp() {
        super("To-Do App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane listScroll = new JScrollPane(todoList);

        inputField = new JTextField();
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        clearButton = new JButton("Clear All");

        // Top panel for input and add button
        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        topPanel.add(inputField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));

        // Bottom panel for control buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        bottomPanel.add(removeButton);
        bottomPanel.add(clearButton);

        // Main layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(listScroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Event handlers
        addButton.addActionListener(e -> addTask());
        inputField.addActionListener(e -> addTask()); // Enter to add
        removeButton.addActionListener(e -> removeSelectedTask());
        clearButton.addActionListener(e -> clearAllTasks());

        // Double-click to toggle completed (simple strike-through simulation)
        todoList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int idx = todoList.locationToIndex(e.getPoint());
                    if (idx >= 0) toggleTask(idx);
                }
            }
        });
    }

    private void addTask() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            listModel.addElement(text);
            inputField.setText("");
            inputField.requestFocusInWindow();
        }
    }

    private void removeSelectedTask() {
        int idx = todoList.getSelectedIndex();
        if (idx >= 0) {
            listModel.remove(idx);
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to remove.", "No selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearAllTasks() {
        if (listModel.isEmpty()) return;
        int ans = JOptionPane.showConfirmDialog(this, "Clear all tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) listModel.clear();
    }

    private void toggleTask(int idx) {
        String item = listModel.get(idx);
        if (item.startsWith("\u2713 ")) {
            // unmark
            listModel.set(idx, item.substring(2));
        } else {
            // mark as done with a checkmark prefix
            listModel.set(idx, "\u2713 " + item);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoApp app = new ToDoApp();
            app.setVisible(true);
        });
    }
}
