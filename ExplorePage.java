import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import javax.swing.*;

public class ExplorePage extends JFrame {

    public ExplorePage() {
        setTitle("Explore Page");
        setSize(400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // خلفية الصورة
        JLabel background = new JLabel();
        background.setBounds(0, 0, 400, 800);
        ImageIcon icon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png");
        Image img = icon.getImage().getScaledInstance(400, 800, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));

        add(background);

        // البانل العلوي
        JPanel topBar = createTopBar();
        topBar.setBounds(0, 0, 400, 60);
        add(topBar);

        // شريط البحث
        JPanel searchBar = createSearchBar();
        searchBar.setBounds(20, 70, 360, 40);
        add(searchBar);

        // المحتوى الرئيسي
        JPanel mainContent = createMainContent();
        mainContent.setBounds(20, 120, 360, 600);
        add(mainContent);

        // شريط التنقل السفلي
        JPanel navBar = new JPanel(null);
        navBar.setBackground(Color.WHITE);
        navBar.setBounds(0, 720, 400, 80);

        JButton homeBtn = createNavButton("🏠", "Home", 0);
        homeBtn.addActionListener(e -> {
            new Home().setVisible(true);
            dispose();
        });
        navBar.add(homeBtn);

        JButton exploreBtn = createNavButton("🔍", "Explore", 133);
        exploreBtn.setEnabled(false);
        navBar.add(exploreBtn);

        JButton profileBtn = createNavButton("👤", "Profile", 266);
        profileBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "My Account");
        });
        navBar.add(profileBtn);

        add(navBar);

        // جعل الخلفية في الخلف
        getContentPane().setComponentZOrder(background, getContentPane().getComponentCount() - 1);

        setVisible(true);
    }

    private JButton createNavButton(String icon, String text, int x) {
        JButton button = new JButton("<html><div style='text-align: center;'>" + icon + "<br>" + text + "</div></html>");
        button.setBounds(x, 0, 133, 80);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(null);
        topBar.setBackground(new Color(60, 90, 120));

        JLabel menuIcon = new JLabel("≡");
        menuIcon.setForeground(Color.WHITE);
        menuIcon.setBounds(10, 10, 30, 40);

        JLabel title = new JLabel("Explore", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 10, 100, 40);

        JLabel profileIcon = new JLabel("👤");
        profileIcon.setForeground(Color.WHITE);
        profileIcon.setBounds(360, 10, 30, 40);

        topBar.add(menuIcon);
        topBar.add(title);
        topBar.add(profileIcon);
        return topBar;
    }

    private JPanel createSearchBar() {
        JPanel searchBar = new JPanel(null);
        searchBar.setBackground(Color.WHITE);

        JTextField searchField = new JTextField("What are you looking for?");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBounds(0, 0, 360, 40);

        searchBar.add(searchField);
        return searchBar;
    }

    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        JLabel articleLabel = new JLabel("Newest Articles", SwingConstants.CENTER);
        articleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        articleLabel.setForeground(Color.DARK_GRAY);
        articleLabel.setBounds(0, 0, 360, 30);
        mainPanel.add(articleLabel);

        JButton art = new JButton("");
        art.setBounds(0, 0, 360, 30);
        art.setContentAreaFilled(false);
        art.setBorderPainted(false);
        art.setFocusPainted(false);
        art.addActionListener(e -> {
            new Articles().setVisible(true);
            dispose();
        });
        mainPanel.add(art);

        JLabel articleImage = scaleImage("/food1.png", 350, 150);
        articleImage.setBounds(5, 30, 350, 150);
        mainPanel.add(articleImage);

        JLabel sweetLabel = new JLabel("Guilt-Free Sweets?", SwingConstants.CENTER);
        sweetLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        sweetLabel.setBounds(0, 190, 360, 30);
        mainPanel.add(sweetLabel);

        JLabel sweet1 = scaleImage("/gui/Frame 2.png", 80, 100);
        sweet1.setBounds(50, 220, 80, 100);
        JLabel sweet2 = scaleImage("/gui/sweet2.png", 80, 100);
        sweet2.setBounds(150, 220, 80, 100);
        JLabel sweet3 = scaleImage("/gui/sweet3.png", 80, 100);
        sweet3.setBounds(250, 220, 80, 100);

        mainPanel.add(sweet1);
        mainPanel.add(sweet2);
        mainPanel.add(sweet3);

        JButton openChatBotBtn = new JButton("Open ChatBot");
        openChatBotBtn.setBounds(100, 450, 150, 40);
        openChatBotBtn.addActionListener(e -> {
            new ChatBotMain().setVisible(true);
          
        });
        mainPanel.add(openChatBotBtn);

        JLabel friendsLabel = new JLabel("My Friends", SwingConstants.CENTER);
        friendsLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        friendsLabel.setBounds(0, 330, 360, 30);
        mainPanel.add(friendsLabel);

        JButton openFriendsBtn = new JButton();
        openFriendsBtn.setIcon(new ImageIcon(getClass().getResource("/gui/Avatar Group.png")));
        openFriendsBtn.setBounds(20, 360,480, 80);
        openFriendsBtn.setBorder(null);
        openFriendsBtn.setContentAreaFilled(false);
        openFriendsBtn.addActionListener(e -> {
            new myFriends().setVisible(true);
            
        });
        mainPanel.add(openFriendsBtn);

        return mainPanel;
    }

    private JLabel scaleImage(String path, int width, int height) {
        URL imageURL = getClass().getResource(path);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } else {
            System.err.println("Image not found: " + path);
            return new JLabel("Image not found");
        }
    }

}

