import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Articles extends JFrame {

    public Articles() {
        setTitle("News Articles");
        setSize(400, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
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
        exploreBtn.setEnabled(true); 
        exploreBtn.addActionListener(e->{
            new ExplorePage();

        });
        navBar.add(exploreBtn);

        JButton profileBtn = createNavButton("👤", "Profile", 266);
        profileBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "My Account");
        });
        navBar.add(profileBtn);

        add(navBar);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10)); // شبكة 4 مقالات
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createArticlePanel("The Bitter Truth About Sugar", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\truth_about_sugar.jpg", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\The Bitter Truth About Sugar.txt"));
        mainPanel.add(createArticlePanel("Vitamin D", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\Vitamin_D.jpg", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\Vitamin D.txt"));
        mainPanel.add(createArticlePanel("Tips from a Certified Nutritionist!", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\shutterstock.jpg", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\Tips from a Certified Nutritionist!.txt"));
        mainPanel.add(createArticlePanel("Sometimes, More May Become Less!", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\increase-weight-loss.jpg", "C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\Sometimes, More May Become Less!.txt"));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
        

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
    


    private JPanel createArticlePanel(String title, String imageName, String fileName) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        ImageIcon originalIcon = new ImageIcon(imageName);
        Image resizedImage = originalIcon.getImage().getScaledInstance(450, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel imageLabel = new JLabel(resizedIcon);
        panel.add(imageLabel, BorderLayout.CENTER);

        JButton button = new JButton(title);
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openArticleWindow(title, fileName);
            }
        });

        panel.add(button, BorderLayout.SOUTH);
        return panel;
    }

    private void openArticleWindow(String title, String fileName) {
        JFrame articleFrame = new JFrame(title);
        articleFrame.setSize(400, 900);
        articleFrame.setLocationRelativeTo(null);
        articleFrame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(textArea);
        articleFrame.add(scrollPane, BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            textArea.read(reader, null);
        } catch (IOException ex) {
            textArea.setText("Error loading article: " + ex.getMessage());
        }

        articleFrame.setVisible(true);
    }

    
}
