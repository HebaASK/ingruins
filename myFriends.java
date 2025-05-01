import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class myFriends extends JFrame {

    public myFriends() {
        setTitle("My Friends");
        setSize(400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
JLabel background = new JLabel(new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png"));
background.setBounds(0, 0, 400, 600);
setContentPane(new JPanel(null));
getContentPane().add(background);


        JLabel titleLabel = new JLabel("My Friends 👥", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setBounds(0, 20, 400, 30);
        add(titleLabel);

        // Panel أفقي للصور
        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.X_AXIS));
        friendsPanel.setBackground(Color.WHITE);

        // مثال لإضافة أصدقاء
        String[] names = {"Lama", "Noor", "Sara", "Joud", "Dana", "Mona"};
        String[] imagePaths = {
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person1.jpg",
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person2.jpg",
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person3.jpg",
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person4.jpg",
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person5.jpg",
            "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\person6.jpg"
        };

        for (int i = 0; i < names.length; i++) {
            JPanel friend = createFriendItem(imagePaths[i], names[i]);
            friendsPanel.add(friend);
        }

        // Scroll داخل panel
        JScrollPane scrollPane = new JScrollPane(friendsPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 70, 360, 140);
        scrollPane.setBorder(null);
        add(scrollPane);

        // ✅ شريط التنقل السفلي
        JPanel navBar = new JPanel();
        navBar.setLayout(null);
        navBar.setBackground(Color.WHITE);
        navBar.setBounds(0, 500, 400, 80); // x, y, width, height

        JButton addFriendBtn = createNavButton("➕", "Add", 0);
        addFriendBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "إضافة صديق جديدة"));

        JButton newChatBtn = createNavButton("💬", "Chat", 133);
        newChatBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "بدء محادثة جديدة"));

        JButton requestsBtn = createNavButton("👥", "Requests", 266);
        requestsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "طلبات الصداقة"));

        navBar.add(addFriendBtn);
        navBar.add(newChatBtn);
        navBar.add(requestsBtn);
        add(navBar);
        background.setBounds(0, 0, getWidth(), getHeight());
        getContentPane().add(titleLabel);
        getContentPane().add(scrollPane);
        getContentPane().add(navBar);
        


        setVisible(true);
    }

    private JButton createNavButton(String icon, String text, int x) {
        JButton button = new JButton("<html><div style='text-align: center;'>" + icon + "<br>" + text + "</div></html>");
        button.setBounds(x, 0, 133, 80); // x, y, width, height
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;
    }

    private JPanel createFriendItem(String imagePath, String name) {
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(100, 130));
        panel.setBackground(Color.WHITE);

        JLabel imageLabel = circleLabel(imagePath, 80);
        imageLabel.setBounds(10, 0, 80, 80);

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setBounds(0, 90, 100, 20);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(imageLabel);
        panel.add(nameLabel);
        return panel;
    }

    private JLabel circleLabel(String imagePath, int size) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            BufferedImage circleImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circleImage.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
            g2.drawImage(originalImage, 0, 0, size, size, null);
            g2.dispose();

            return new JLabel(new ImageIcon(circleImage));
        } catch (IOException e) {
            e.printStackTrace();
            return new JLabel("❌");
        }
    }

   
}
