import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

 public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField userIDField;

    public SignUpPage() {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 📸 إضافة صورة الخلفية
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png"); // تأكد من أن الصورة في المسار الصحيح
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel); // تعيين الخلفية كـ content pane

        // 🎨 لوحة العنوان في الأعلى
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 35));
        topPanel.setPreferredSize(new Dimension(400, 100));
        topPanel.setBackground(new Color(0, 102, 204, 150)); // تأثير شفاف قليلاً
        JLabel titleLabel = new JLabel("Sign Up Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // 🎨 لوحة المحتوى الرئيسية
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setOpaque(false); // مهم حتى تبقى الخلفية مرئية

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 100, 300, 25);
        userLabel.setForeground(Color.WHITE);
        centerPanel.add(userLabel);
        usernameField = new JTextField();
        usernameField.setBounds(50, 130, 300, 35);
        centerPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 180, 300, 25);
        passLabel.setForeground(Color.WHITE);
        centerPanel.add(passLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(50, 210, 300, 35);
        centerPanel.add(passwordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(50, 270, 300, 40);
        signUpButton.setBackground(new Color(0, 102, 204));
        signUpButton.setForeground(Color.WHITE);
        centerPanel.add(signUpButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(50, 330, 300, 40);
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        centerPanel.add(cancelButton);

        add(centerPanel, BorderLayout.CENTER);

        // 🌟 الأحداث
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ingregenius", "root", "Heba-5252");

                    // أولاً: ندخل المستخدم في جدول Users ونحصل على الـ UserID اللي تولّد تلقائيًا
                    String insertUserSQL = "INSERT INTO Users (Name) VALUES (?)";
                    PreparedStatement psUser = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
                    psUser.setString(1, usernameField.getText());
                    psUser.executeUpdate();

                    ResultSet rs = psUser.getGeneratedKeys();
                    int userID = -1;
                    if (rs.next()) {
                        userID = rs.getInt(1);
                    }

                    // ثانيًا: ندخله في جدول Login باستخدام الـ UserID
                    String insertLoginSQL = "INSERT INTO Login (UserID, Username, Password) VALUES (?, ?, ?)";
                    PreparedStatement psLogin = conn.prepareStatement(insertLoginSQL);
                    psLogin.setInt(1, userID);
                    psLogin.setString(2, usernameField.getText());
                    psLogin.setString(3, new String(passwordField.getPassword()));
                    psLogin.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Sign up successful!");
                    new Home();

                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionsPage().setVisible(true);
                setVisible(false);
            }
        });
    }
}
