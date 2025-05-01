import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MyRecipesPage extends JFrame {
    private JPanel recipesPanel;
    private DatabaseConnection db;
    private int userId;

    public MyRecipesPage(int userId) {
        this.userId = userId;
        db = new DatabaseConnection();

        setTitle("My Recipes");
        setSize(400, 800);  // تعديل الحجم
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // --- Background Image ---
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png"); // تأكدي من مسار الصورة
        Image img = backgroundImage.getImage().getScaledInstance(400, 800, Image.SCALE_SMOOTH); // تعديل الأبعاد
        backgroundImage = new ImageIcon(img);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 800);  // تعديل الإحداثيات
        setContentPane(backgroundLabel);//
        backgroundLabel.setLayout(null); // حتى نقدر نضيف العناصر فوقه

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBounds(0, 0, 400, 80);  // تعديل الحجم

        JLabel titleLabel = new JLabel("  My Recipes");
       titleLabel.setForeground(new Color(30, 30, 60));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));  // تعديل الخط

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(new Color(70, 130, 180));
        addButton.addActionListener(e -> showAddDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);
        backgroundLabel.add(headerPanel);

        // --- Recipes Panel ---
        recipesPanel = new JPanel();
        recipesPanel.setLayout(new BoxLayout(recipesPanel, BoxLayout.Y_AXIS));
        recipesPanel.setOpaque(false); 

        JScrollPane scrollPane = new JScrollPane(recipesPanel);
        scrollPane.setBounds(0, 80, 400, 600);  
        backgroundLabel.add(scrollPane);

        // --- Bottom Navigation Bar ---
        JPanel navBar = new JPanel(null);
        navBar.setBackground(Color.WHITE);
        navBar.setBounds(0, 700, 400, 80);  
        JButton homeBtn = createNavButton("🏠", "Home", 0);
        homeBtn.addActionListener(e -> {
            new Home().setVisible(true);
            dispose();
        });
        navBar.add(homeBtn);

        JButton exploreBtn = createNavButton("🔍", "Explore", 100);
        exploreBtn.addActionListener(e -> new ExplorePage());
        navBar.add(exploreBtn);

        JButton profileBtn = createNavButton("👤", "Profile", 200);  // 
        profileBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "My Account"));
        navBar.add(profileBtn);

        JButton updatesBtn = createNavButton("✏️", "Updates", 300);  
        updatesBtn.addActionListener(e -> openUpdateDialog());
        navBar.add(updatesBtn);

        backgroundLabel.add(navBar);

        loadRecipes();
        setVisible(true);
        headerPanel.setOpaque(false); 
        recipesPanel.setOpaque(false);
    }

    private void showAddDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter recipe name:");
        if (name == null || name.isEmpty()) return;

        String ingredients = JOptionPane.showInputDialog(this, "Enter ingredients:");
        if (ingredients == null || ingredients.isEmpty()) return;

        String calories = JOptionPane.showInputDialog(this, "Enter calories:");
        if (calories == null || calories.isEmpty()) return;

        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
                "INSERT INTO myrecipes (name, ingredients, calories, user_id) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, ingredients);
            stmt.setString(3, calories);
            stmt.setInt(4, userId);
            stmt.executeUpdate();
            loadRecipes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving recipe.");
        }
    }

    private void loadRecipes() {
        recipesPanel.removeAll();

        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT * FROM myrecipes WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String ingredients = rs.getString("ingredients");
                String calories = rs.getString("calories");

                JButton recipeButton = new JButton(name);
                recipeButton.setFont(new Font("Arial", Font.PLAIN, 16));
                recipeButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                        "Ingredients: " + ingredients + "\nCalories: " + calories,
                        name,
                        JOptionPane.INFORMATION_MESSAGE));

                recipesPanel.add(Box.createVerticalStrut(5));
                recipesPanel.add(recipeButton);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error while loading recipes.");
        }

        recipesPanel.revalidate();
        recipesPanel.repaint();
    }

    private void openUpdateDialog() {
        JFrame updateFrame = new JFrame("Manage Recipes");
        updateFrame.setSize(350, 400);  // تعديل الحجم
        updateFrame.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);

        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT * FROM myrecipes WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                JPanel item = new JPanel(new BorderLayout());
                JLabel nameLabel = new JLabel(name);
                JButton deleteBtn = new JButton("Delete");
                deleteBtn.setBackground(Color.RED);
                deleteBtn.setForeground(Color.WHITE);

                deleteBtn.addActionListener(e -> {
                    try {
                        PreparedStatement del = db.getConnection().prepareStatement(
                            "DELETE FROM myrecipes WHERE id = ? AND user_id = ?");
                        del.setInt(1, id);
                        del.setInt(2, userId);
                        del.executeUpdate();
                        updateFrame.dispose();
                        loadRecipes();
                        openUpdateDialog();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting recipe.");
                    }
                });

                item.add(nameLabel, BorderLayout.CENTER);
                item.add(deleteBtn, BorderLayout.EAST);
                listPanel.add(item);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error opening update window.");
        }

        updateFrame.add(scroll, BorderLayout.CENTER);
        updateFrame.setVisible(true);
    }

    private JButton createNavButton(String emoji, String text, int x) {
        JButton button = new JButton("<html><center>" + emoji + "<br>" + text + "</center></html>");
        button.setBounds(x, 0, 100, 80);  // تعديل الأبعاد
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 230, 230));
                button.setOpaque(true);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setOpaque(false);
            }
        });

        return button;
    }
}
