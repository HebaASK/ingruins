import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class MyPlan extends JFrame {

    private JPanel contentPane;
    private Connection conn;
    private JScrollPane breakFastScroll, lunchScroll, dinnerScroll;
    private JLabel breakfast, lunch, dinner, idealW, duration, calories, weeklyLoss, title;
    private JTextArea breakfastText, lunchText, dinnerText;
    private JButton homeButton;

    public MyPlan(int userId) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 400, 800);

        contentPane = new JPanel(null); // layout null
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

       
        ImageIcon backgroundIcon = new ImageIcon("C:\\Users\\DELL\\OneDrive\\Desktop\\GUI\\src\\gui\\iPhone 16 - 5.png");
        Image bgImage = backgroundIcon.getImage().getScaledInstance(400, 800, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, 400, 800);
        contentPane.add(backgroundLabel);
       

        title = new JLabel("Weight Loss Planner");
        title.setBounds(100, 10, 250, 40);
        title.setFont(new java.awt.Font("Sylfaen", 0, 24));
        title.setForeground(new Color(0, 0, 102));
        contentPane.add(title);

        homeButton = new JButton("Home");
        homeButton.setBounds(10, 10, 70, 30);
        homeButton.setBackground(Color.WHITE);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Home().setVisible(true);
            }
        });
        contentPane.add(homeButton);

        idealW = new JLabel("Ideal Weight:");
        idealW.setBounds(20, 70, 300, 30);
        contentPane.add(idealW);

        duration = new JLabel("Estimated Duration:");
        duration.setBounds(20, 100, 300, 30);
        contentPane.add(duration);

        calories = new JLabel("Recommended Daily Calories: 1500 cal");
        calories.setBounds(20, 130, 300, 30);
        contentPane.add(calories);

        weeklyLoss = new JLabel("Expected Weekly Loss: 1 Kg");
        weeklyLoss.setBounds(20, 160, 300, 30);
        contentPane.add(weeklyLoss);

        breakfast = new JLabel("Breakfast:");
        breakfast.setBounds(20, 210, 200, 30);
        contentPane.add(breakfast);

        breakfastText = new JTextArea();
        breakfastText.setBackground(new Color(246, 246, 246));
        breakFastScroll = new JScrollPane(breakfastText);
        breakFastScroll.setBounds(20, 240, 340, 100);
        contentPane.add(breakFastScroll);

        lunch = new JLabel("Lunch:");
        lunch.setBounds(20, 350, 200, 30);
        contentPane.add(lunch);

        lunchText = new JTextArea();
        lunchText.setBackground(new Color(246, 246, 246));
        lunchScroll = new JScrollPane(lunchText);
        lunchScroll.setBounds(20, 380, 340, 100);
        contentPane.add(lunchScroll);

        dinner = new JLabel("Dinner:");
        dinner.setBounds(20, 490, 200, 30);
        contentPane.add(dinner);

        dinnerText = new JTextArea();
        dinnerText.setBackground(new Color(246, 246, 246));
        dinnerScroll = new JScrollPane(dinnerText);
        dinnerScroll.setBounds(20, 520, 340, 100);
        contentPane.add(dinnerScroll);

        // ضع الخلفية في الخلف
        contentPane.setComponentZOrder(backgroundLabel, contentPane.getComponentCount() - 1);

        // العمليات الحسابية
        calculateIdealWeight(userId);
        showEstimatedDuration(userId);
        displayMeals(userId, java.time.LocalDate.now().toString());
    }

    private void connectDatabase() {
        conn = DatabaseConnection.getConnection();
    }

    private void calculateIdealWeight(int userId) {
        connectDatabase();
        try {
            String query = "SELECT Height, Gender FROM Users WHERE UserID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double height = rs.getDouble("Height");
                String gender = rs.getString("Gender");

                double idealWeight = gender.equalsIgnoreCase("Male")
                        ? 50 + 0.91 * (height - 152.4)
                        : 45.5 + 0.91 * (height - 152.4);

                idealW.setText("Ideal Weight: " + String.format("%.2f kg", idealWeight));
            } else {
                idealW.setText("User not found.");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            idealW.setText("Error occurred.");
        }
    }

    private void showEstimatedDuration(int userId) {
        connectDatabase();
        try {
            String query = "SELECT Weight FROM users WHERE UserID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double currentWeight = rs.getDouble("Weight");
                String[] parts = idealW.getText().split(":");

                if (parts.length > 1) {
                    double idealWeight = Double.parseDouble(parts[1].replace("kg", "").trim());
                    double difference = currentWeight - idealWeight;
                    int weeks = (int) Math.ceil(Math.max(difference, 0));

                    duration.setText("Estimated Duration: " + weeks + " week(s)");
                }
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            duration.setText("Error estimating duration.");
        }
    }

    private void displayMeals(int userID, String date) {
        connectDatabase();
        try {
            String query = """
                SELECT 
                    r1.Name AS Breakfast,
                    r2.Name AS Lunch,
                    r3.Name AS Dinner
                FROM UserMealPlan u
                JOIN Recipe r1 ON u.BreakfastID = r1.RecipeID
                JOIN Recipe r2 ON u.LunchID = r2.RecipeID
                JOIN Recipe r3 ON u.DinnerID = r3.RecipeID
                WHERE u.UserID = ? AND u.Date = ?
            """;

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                breakfastText.setText(rs.getString("Breakfast"));
                lunchText.setText(rs.getString("Lunch"));
                dinnerText.setText(rs.getString("Dinner"));
            } else {
                breakfastText.setText("لا توجد وصفة فطور.");
                lunchText.setText("لا توجد وصفة غداء.");
                dinnerText.setText("لا توجد وصفة عشاء.");
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            breakfastText.setText("خطأ في تحميل الفطور.");
            lunchText.setText("خطأ في تحميل الغداء.");
            dinnerText.setText("خطأ في تحميل العشاء.");
            e.printStackTrace();
        }
    }
}

