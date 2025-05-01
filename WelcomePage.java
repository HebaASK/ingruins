import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame {
    public WelcomePage() {
        setTitle("Welcome");
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // تحميل صورة الخلفية
        ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png");
        Image scaled = backgroundIcon.getImage().getScaledInstance(400, 800, Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(scaled);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);  // جعل الصورة هي خلفية الفريم

        // شريط علوي
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);  // جعل الخلفية شفافة
        topPanel.setPreferredSize(new Dimension(400, 60));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Welcome Page");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(30, 30, 60));
        topPanel.add(titleLabel);

        backgroundLabel.add(topPanel, BorderLayout.NORTH);

        // محتوى في الوسط
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // شفاف
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); // مسافة علوية

        // صورة الشعار
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\logo.jpg");
        Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setPreferredSize(new Dimension(200, 200));
        logoLabel.setMaximumSize(new Dimension(200, 200));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // نص الترحيب
        JLabel welcomeText = new JLabel("Welcome to IngreGenius");
        welcomeText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        welcomeText.setForeground(new Color(30, 30, 60)); // لون النص
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeText.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(logoLabel);
        centerPanel.add(welcomeText);

        backgroundLabel.add(centerPanel, BorderLayout.CENTER);

        // زر الاستمرار في الأسفل
        JButton continueButton = new JButton("Next ");
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        continueButton.setBackground(new Color(0, 102, 204));
        continueButton.setForeground(Color.WHITE);
        continueButton.setPreferredSize(new Dimension(300, 60));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false); // شفاف
        bottomPanel.setPreferredSize(new Dimension(400, 100));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(continueButton);

        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        // الحدث عند الضغط على الزر
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OptionsPage().setVisible(true);
                setVisible(false);
            }
        });
    }
}
