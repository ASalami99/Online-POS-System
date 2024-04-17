/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package csc_301.exam.pos.terminal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abdul
 */
public class SalesManager extends javax.swing.JFrame {

    ArrayList<String> birthdayCelebrants = new ArrayList<>();
    private String userFirstname;
    private String userEmail;

    /**
     * Creates new form SalesManager
     */
    public SalesManager(String userFirstname, String userEmail) {
        initComponents();
        this.userFirstname = userFirstname;
        this.userEmail = userEmail;
        jLabel2.setText(userFirstname);
        displaydate();
    }

    private void displaydate() {
        Timer timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        // Get the current date and time
                        Date currentDate = new Date();

                        // Format the date using SimpleDateFormat with the desired pattern
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy  HH:mm:ss", Locale.ENGLISH);
                        String formattedDateTime = dateFormat.format(currentDate);

                        // Update the JLabel with the formatted date and time
                        jLabel19.setText(formattedDateTime);
                    }
                });
            }
        });
        timer.start();
    }

    private void checkProductExpiry() {
        try {
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = dateFormat.format(today);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            PreparedStatement ps = con.prepareStatement("SELECT ProductName, ExpiryDate FROM inventory");
            ResultSet rs = ps.executeQuery();

            StringBuilder expiringNextWeek = new StringBuilder("The following items are expiring next week:\n");
            StringBuilder expiredProducts = new StringBuilder("The following items have expired:\n");

            // Additional lists to store products for email
            List<String> expiredProductsForEmail = new ArrayList<>();
            List<String> expiringProductsNextWeekForEmail = new ArrayList<>();

            while (rs.next()) {
                String productName = rs.getString("ProductName");
                String expiryDateStr = rs.getString("ExpiryDate");
                Date expiryDate = dateFormat.parse(expiryDateStr);

                Calendar nextWeek = Calendar.getInstance();
                nextWeek.setTime(today);
                nextWeek.add(Calendar.DATE, 7);

                if (expiryDate.before(today)) {
                    expiredProducts.append(productName).append(" (Expired on ").append(expiryDateStr).append(")\n");
                    expiredProductsForEmail.add(productName + " - Expired on " + expiryDateStr);
                } else if (expiryDate.before(nextWeek.getTime())) {
                    expiringNextWeek.append(productName).append(" (Expiring on ").append(expiryDateStr).append(")\n");
                    expiringProductsNextWeekForEmail.add(productName + " - Expiring on " + expiryDateStr);
                }
            }

            if (expiringNextWeek.length() > "The following items are expiring next week:\n".length()) {
                JOptionPane.showMessageDialog(rootPane, expiringNextWeek.toString());
            }

            if (expiredProducts.length() > "The following items have expired:\n".length()) {
                JOptionPane.showMessageDialog(rootPane, expiredProducts.toString());
            }

            // Send email to the user
            String subject = "Product Expiry Dates\n";
            String receiver = userEmail;
            String body = "Hello " + userFirstname + ",\n\n"
                    + "Please find below the list of products which have expired or are about to expire. "
                    + "We should restock them:\n\n";

            // Add expired products to email body
            body += "Expired Products:\n";
            for (String expiredProduct : expiredProductsForEmail) {
                body += expiredProduct + "\n";
            }

            // Add products about to expire next week to email body
            body += "\nProducts Expiring Next Week:\n";
            for (String expiringProduct : expiringProductsNextWeekForEmail) {
                body += expiringProduct + "\n";
            }

            body += "\nWe should restock these products to ensure availability. Thank you.";

            String senderEmail = "abdullahsalami909@gmail.com";
            String senderPassword = "twjtowmgmsnfitml";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
                JOptionPane.showMessageDialog(rootPane, "Please check your email for the list of expired itmes.");

            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
            // Remaining code for sending email
            // ...
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    private void BirthdayPopUp() {
        Date da = new Date();
        SimpleDateFormat dat = new SimpleDateFormat("dd/MM");
        String currentDateInYear = dat.format(da);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            // Check sales_department
            PreparedStatement psSales = con.prepareStatement("select FirstName, LastName, DateOfBirth from sales_department");
            ResultSet rsSales = psSales.executeQuery();
            checkAndAddBirthdayCelebrants(rsSales, currentDateInYear);

            // Check inventory_department
            PreparedStatement psInventory = con.prepareStatement("select FirstName, LastName, DateOfBirth from inventory_department");
            ResultSet rsInventory = psInventory.executeQuery();
            checkAndAddBirthdayCelebrants(rsInventory, currentDateInYear);

            // Check IT_department
            PreparedStatement psIT = con.prepareStatement("select FirstName, LastName, DateOfBirth from IT_department");
            ResultSet rsIT = psIT.executeQuery();
            checkAndAddBirthdayCelebrants(rsIT, currentDateInYear);

            // The rest of your code remains unchanged
            String namesOfBirthdayCelebrants = "";
            for (int i = 0; i < birthdayCelebrants.size(); i++) {
                if (i == birthdayCelebrants.size() - 1) {
                    namesOfBirthdayCelebrants += " and";
                }
                namesOfBirthdayCelebrants += "\n" + birthdayCelebrants.get(i);
            }

            if (birthdayCelebrants.size() == 1) {
                JOptionPane.showMessageDialog(rootPane,
                        "Today is " + birthdayCelebrants.get(0) + "'s birthday!\nDon't forget to wish them a happy birthday!");
            } else if (birthdayCelebrants.size() > 1) {
                JOptionPane.showMessageDialog(rootPane,
                        "Happy Birthday to:" + namesOfBirthdayCelebrants);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }

    private void checkAndAddBirthdayCelebrants(ResultSet resultSet, String currentDateInYear) throws SQLException {
        while (resultSet.next()) {
            String FirstName = resultSet.getString(1);
            String LastName = resultSet.getString(2);
            String dob = resultSet.getString(3);
            String[] dateArr = dob.split("/");
            String dateInYear = dateArr[0] + "/" + dateArr[1];
            if (currentDateInYear.equals(dateInYear)) {
                birthdayCelebrants.add(FirstName + " " + LastName);
            }
        }
    }

    private String fetchUnitPriceForProduct(String productName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            String query = "SELECT Price FROM sales WHERE ProductName = ?";
            try ( PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, productName);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getString("Price");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        // Return a default value or handle the case when the unit price is not found
        return "0"; // You may adjust this based on your needs
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton13.setText("Notifications");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N
        jLabel1.setText("Welcome");

        jLabel2.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "First Name", "Middle Name", "Last Name", "No. Of Items Sold", "Total Amount Sold", "Date"
            }
        ));
        jScrollPane5.setViewportView(jTable1);

        jLabel5.setText("Enter Username of Cashier whose sales performacne you want to see");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jButton23.setText("View Sales Perfomance");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton23)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cashier Sales Performance", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity Sold", "Unit Price", "Total Price Sold"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(jButton1)))
                .addContainerGap(232, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton1)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Top Selling Items", jPanel3);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Option", "Daily", "Weekly", "Monthly", "Yearly" }));

        jLabel3.setText("Search sales report:");

        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Total Quantity Sold", "Total Amount Sold"
            }
        ));
        jScrollPane2.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(jButton2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(242, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sales Reports", jPanel4);

        jButton3.setText("Sign Out");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SimSun-ExtB", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(417, 417, 417))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(661, 661, 661)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        BirthdayPopUp();
        checkProductExpiry();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        try {
            String username = jTextField1.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            PreparedStatement ps = con.prepareStatement("SELECT s.Username, s.Quantity, s.Amount, s.sale_date, d.FirstName, d.MiddleName, d.LastName FROM cashier_sales s JOIN sales_department d ON s.Username = d.Username WHERE s.Username = ?");
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String data[] = {rs.getString("Username"), rs.getString("FirstName"), rs.getString("MiddleName"), rs.getString("LastName"), rs.getString("Quantity"), rs.getString("Amount"), rs.getString("sale_date")};
                DefaultTableModel td = (DefaultTableModel) jTable1.getModel();
                td.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//
//            PreparedStatement ps = con.prepareStatement("SELECT ProductName, Quantity, Price FROM sales");
//            ResultSet rs = ps.executeQuery();
//
//            List<String[]> rows = new ArrayList<>(); // List to store rows of data
//
//            while (rs.next()) {
//                String productname = rs.getString("ProductName");
//                String quantity = rs.getString("Quantity");
//                int quantAsInt = Integer.parseInt(quantity);
//                String unitprice = rs.getString("Price");
//                int priceAsInt = Integer.parseInt(unitprice);
//
//                int total = quantAsInt * priceAsInt;
//                String totalAsString = String.valueOf(total);
//
//                String data[] = {productname, quantity, unitprice, totalAsString};
//                rows.add(data); // Add each row to the list
//            }
//
//            // Sort the list based on descending order of quantity
//            rows.sort(Comparator.comparingInt(row -> -Integer.parseInt(row[1])));
//
//            // Add sorted rows to the table
//            DefaultTableModel td = (DefaultTableModel) jTable2.getModel();
//            for (String[] row : rows) {
//                td.addRow(row);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
//        }

//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//
//            PreparedStatement ps = con.prepareStatement("SELECT ProductName, Quantity, Price FROM sales");
//            ResultSet rs = ps.executeQuery();
//
//            Map<String, Integer> productSums = new HashMap<>(); // Map to store product sums
//
//            while (rs.next()) {
//                String productname = rs.getString("ProductName");
//                String quantity = rs.getString("Quantity");
//                int quantAsInt = Integer.parseInt(quantity);
//                String unitprice = rs.getString("Price");
//                int priceAsInt = Integer.parseInt(unitprice);
//
//                int total = quantAsInt * priceAsInt;
//                String totalAsString = String.valueOf(total);
//
//                // Check if the product name is already in the map
//                if (productSums.containsKey(productname)) {
//                    // If yes, update the sum by adding the current quantity
//                    int currentSum = productSums.get(productname);
//                    productSums.put(productname, currentSum + quantAsInt);
//                } else {
//                    // If no, add the product name to the map with the current quantity
//                    productSums.put(productname, quantAsInt);
//                }
//            }
//
//            // Create a list of rows from the map entries
//            List<String[]> rows = new ArrayList<>();
//            for (Map.Entry<String, Integer> entry : productSums.entrySet()) {
//                String productName = entry.getKey();
//                String totalQuantity = String.valueOf(entry.getValue());
//                // You may fetch unit price and calculate total price here if needed
//
//                String data[] = {productName, totalQuantity};
//                rows.add(data);
//            }
//
//            // Sort the list based on descending order of total quantity
//            rows.sort(Comparator.comparingInt(row -> -Integer.parseInt(row[1])));
//
//            // Add sorted rows to the table
//            DefaultTableModel td = (DefaultTableModel) jTable2.getModel();
//            for (String[] row : rows) {
//                td.addRow(row);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            PreparedStatement ps = con.prepareStatement("SELECT ProductName, Quantity, Price FROM sales");
            ResultSet rs = ps.executeQuery();

            Map<String, Integer> productSums = new HashMap<>(); // Map to store product sums

            while (rs.next()) {
                String productname = rs.getString("ProductName");
                String quantity = rs.getString("Quantity");
                int quantAsInt = Integer.parseInt(quantity);

                // Fetch unit price from the database using your method
                String unitPrice = fetchUnitPriceForProduct(productname);
                int priceAsInt = Integer.parseInt(unitPrice);

                int total = quantAsInt * priceAsInt;

                // Check if the product name is already in the map
                if (productSums.containsKey(productname)) {
                    // If yes, update the sum by adding the current quantity
                    int currentSum = productSums.get(productname);
                    productSums.put(productname, currentSum + quantAsInt);
                } else {
                    // If no, add the product name to the map with the current quantity
                    productSums.put(productname, quantAsInt);
                }
            }

            // Create a list of rows from the map entries
            List<String[]> rows = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : productSums.entrySet()) {
                String productName = entry.getKey();
                int totalQuantity = entry.getValue();

                // Fetch unit price from the database using your method
                String unitPrice = fetchUnitPriceForProduct(productName);
                int totalPrice = totalQuantity * Integer.parseInt(unitPrice);

                String data[] = {productName, String.valueOf(totalQuantity), unitPrice, String.valueOf(totalPrice)};
                rows.add(data);
            }

            // Sort the list based on descending order of total quantity
            rows.sort(Comparator.comparingInt(row -> -Integer.parseInt(row[1])));

            // Add sorted rows to the table
            DefaultTableModel td = (DefaultTableModel) jTable2.getModel();
            for (String[] row : rows) {
                td.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        String type = jComboBox1.getSelectedItem().toString();
//
//        if (type.equals("Daily")) {
//            try {
//
//                Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//                PreparedStatement ps = con.prepareStatement("SELECT sale_date, Quantity AS TotalQuantity, Amount AS TotalAmount FROM cashier_sales WHERE Date = ? GROUP BY Date");
//
//                Date today = new Date();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String currentDate = dateFormat.format(today);
//                ps.setString(1, currentDate);
//
//                int rs = ps.executeUpdate();
//
//               
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(rootPane, e);
//            }
//        } else if (type.equals("Weekly")) {
//        } 

        String type = jComboBox1.getSelectedItem().toString();

        if (type.equals("Daily")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                PreparedStatement ps = con.prepareStatement("SELECT sale_date, SUM(Quantity) AS TotalQuantity, SUM(Amount) AS TotalAmount FROM cashier_sales GROUP BY sale_date");

                try ( ResultSet rs = ps.executeQuery()) {
                    // Assuming jTable2 is your target table
                    DefaultTableModel jTable2Model = new DefaultTableModel();
                    jTable2Model.addColumn("Date");
                    jTable2Model.addColumn("Total Quantity Sold");
                    jTable2Model.addColumn("Total Amount Sold");

                    while (rs.next()) {
                        Object[] rowData = {
                            rs.getString("sale_date"),
                            rs.getInt("TotalQuantity"),
                            rs.getDouble("TotalAmount")
                        };
                        jTable2Model.addRow(rowData);
                    }

                    jTable2.setModel(jTable2Model);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            Home c = new Home();
            c.setVisible(true);
            c.pack();
            c.setLocationRelativeTo(null);
            c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SalesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalesManager("John Doe", "john.doe@example.com").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
