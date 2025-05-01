import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WeightTrackerGUI extends JFrame {

    private JTextField startWeightField;
    private JTextField goalWeightField;
    private JTextField currentWeightField;
    private JLabel resultLabel;
    private double startWeight;
    private double goalWeight;

    public WeightTrackerGUI() {
        setTitle("Progress Tracker");
        setSize(400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 📸 خلفية الصورة
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png"); // تأكدي من الاسم والمسار
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel); // تعيين الخلفية كـ content pane

        // 🎨 لوحة المحتوى الشفافة فوق الخلفية
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // مهم حتى تظهر الخلفية
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        Color textColor = new Color(30, 30, 60);;
        Color fieldBg = new Color(30, 30, 60);
        Color inupt = Color.WHITE;

        JLabel startLabel = new JLabel("Your starting weight:");
        startLabel.setForeground(textColor);
        startLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(startLabel);

        startWeightField = new JTextField(20);
        styleField(startWeightField, fieldBg, inupt);
        contentPanel.add(startWeightField);

        JLabel goalLabel = new JLabel("Your goal weight:");
        goalLabel.setForeground(textColor);
        goalLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(goalLabel);

        goalWeightField = new JTextField(20);
        styleField(goalWeightField, fieldBg, inupt);
        contentPanel.add(goalWeightField);

        JButton setGoalsButton = new JButton("Save Goals");
        setGoalsButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(setGoalsButton);
        addVerticalSpace(contentPanel, 10);

        JLabel updateLabel = new JLabel("Update current weight:");
        updateLabel.setForeground(textColor);
        updateLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(updateLabel);

        currentWeightField = new JTextField(20);
        styleField(currentWeightField, fieldBg, inupt);
        contentPanel.add(currentWeightField);

        JButton checkProgressButton = new JButton("Check Progress");
        checkProgressButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(checkProgressButton);
        addVerticalSpace(contentPanel, 10);

        resultLabel = new JLabel("");
        resultLabel.setForeground(Color.YELLOW);
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        contentPanel.add(resultLabel);

        backgroundLabel.add(contentPanel, BorderLayout.CENTER); 

        // 🚩 الشريط السفلي
        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(240, 240, 240));
        navBar.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton homeBtn = createNavButton("🏠", "Home");
        homeBtn.addActionListener(e -> {
            new Home();
            dispose();
        });
        navBar.add(homeBtn);

        JButton exploreBtn = createNavButton("🔍", "Explore");
        exploreBtn.addActionListener(e -> new ExplorePage());
        navBar.add(exploreBtn);

        JButton profileBtn = createNavButton("👤", "Profile");
        profileBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "My Account clicked!");
        });
        navBar.add(profileBtn);

        backgroundLabel.add(navBar, BorderLayout.SOUTH); // إضافة الشريط السفلي للخلفية

        // 🧠 الأحداث
        setGoalsButton.addActionListener(e -> {
            try {
                startWeight = Double.parseDouble(startWeightField.getText());
                goalWeight = Double.parseDouble(goalWeightField.getText());
                if (goalWeight >= startWeight) {
                    JOptionPane.showMessageDialog(null, "Goal weight must be less than starting weight!");
                } else {
                    JOptionPane.showMessageDialog(null, "Goals saved successfully!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
            }
        });

        checkProgressButton.addActionListener(e -> {
            try {
                double currentWeight = Double.parseDouble(currentWeightField.getText());
                double totalLoss = startWeight - goalWeight;
                double lostSoFar = startWeight - currentWeight;

                if (lostSoFar < 0) {
                    resultLabel.setText("Great job! You've lost %.2f%% of your goal 🎉");
                } else if (lostSoFar == 0) {
                    resultLabel.setText("No change yet, stay consistent ✅");
                } else {
                    double progress = (lostSoFar / totalLoss) * 100;
                    resultLabel.setText(String.format("You've gained some weight. It's okay, keep trying 🌟", progress));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
            }
        });

        setVisible(true);
    }

    private JButton createNavButton(String icon, String text) {
        JButton button = new JButton("<html><div style='text-align: center;'>" + icon + "<br>" + text + "</div></html>");
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;
    }

    private void addVerticalSpace(JPanel panel, int pixels) {
        panel.add(Box.createRigidArea(new Dimension(0, pixels)));
    }

    private void styleField(JTextField field, Color bg, Color fg) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(fg);
    }
}
