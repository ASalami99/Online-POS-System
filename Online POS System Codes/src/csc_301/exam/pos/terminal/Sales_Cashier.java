/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package csc_301.exam.pos.terminal;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Sales_Cashier extends javax.swing.JFrame {

    public Double Stock_qty = 0.0;
    ArrayList<String> birthdayCelebrants = new ArrayList<>();
    private String userFirstname;
    private String userEmail;
    Double bHeight = 0.0;
    private Home homeInstance;

    public Sales_Cashier(String userFirstname, String userEmail, Home homeInstance) {
        //  public Sales_Cashier(String userFirstname, String userEmail) {
        initComponents();
        total();
        this.userFirstname = userFirstname;
        this.userEmail = userEmail;
        jLabel18.setText(userFirstname);
//       this.homeInstance = homeInstance;
        this.homeInstance = new Home();
        displaydate();

        //   amount_due();
        jTextField3.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                amount_due();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                amount_due();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This is usually not needed for plain text fields
            }
        });
    }

    private void total() {
        int numrow = jTable1.getRowCount();
        double tot = 0;
        for (int i = 0; i < numrow; i++) {
            double val = Double.valueOf(jTable1.getValueAt(i, 4).toString());
            tot += val;
        }
        jLabel15.setText(Double.toString(tot));
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

    private String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(currentDate);
    }

    private void amount_due() {
        String tamount = jLabel15.getText();
        Double tamnt = Double.valueOf(tamount);

        String pamount = jTextField3.getText();
        Double pamnt = Double.valueOf(pamount);

        Double amountdue = tamnt - pamnt;
        jLabel16.setText(Double.toString(amountdue));

    }

    private void checkStockStatus() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            PreparedStatement ps = con.prepareStatement("SELECT ProductName, Quantity FROM inventory");
            ResultSet rs = ps.executeQuery();

            // Additional lists to store low stock and out of stock products for email
            List<String> lowStockProductsForEmail = new ArrayList<>();
            List<String> outOfStockProductsForEmail = new ArrayList<>();

            while (rs.next()) {
                String productName = rs.getString("ProductName");
                int quantity = rs.getInt("Quantity");

                if (quantity <= 10 && quantity > 0) {
                    // Low stock
                    JOptionPane.showMessageDialog(rootPane, "Only " + quantity + " " + productName + " left in stock. Please restock");

                    // Add to email list
                    lowStockProductsForEmail.add(productName + " - " + quantity);
                } else if (quantity == 0) {
                    // Out of stock
                    JOptionPane.showMessageDialog(rootPane, "We are out of stock for " + productName + ". Please restock");

                    // Add to email list
                    outOfStockProductsForEmail.add(productName + " - " + quantity);
                }
            }

            if (!lowStockProductsForEmail.isEmpty() || !outOfStockProductsForEmail.isEmpty()) {
                // Send email to the user
                String subject = "Stock Status\n";
                String receiver = userEmail;
                String body = "Hello " + userFirstname + ",\n\n";

                // Add low stock products to email body
                if (!lowStockProductsForEmail.isEmpty()) {
                    body += "The following products are low in stock:\n";
                    for (String lowStockProduct : lowStockProductsForEmail) {
                        body += lowStockProduct + "\n";
                    }
                }

                // Add out of stock products to email body
                if (!outOfStockProductsForEmail.isEmpty()) {
                    body += "\nThe following products are out of stock:\n";
                    for (String outOfStockProduct : outOfStockProductsForEmail) {
                        body += outOfStockProduct + "\n";
                    }
                }

                body += "\nPlease restock to ensure availability. Thank you.";

                String senderEmail = "abdullahsalami909@gmail.com";
                String senderPassword = "twjtowmgmsnfitml";
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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
                    JOptionPane.showMessageDialog(rootPane, "Stock status email sent. Please check your email for details.");

                } catch (MessagingException e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
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

    private void updateStockQuantity() {
        try {
            // Iterate through each row in jTable1 and update stock quantity
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String productName = (String) tableModel.getValueAt(i, 0);
                int soldQuantity = Integer.parseInt(tableModel.getValueAt(i, 2).toString());

                // Update stock quantity in the database
                updateStockInDatabase(productName, soldQuantity);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
            e.printStackTrace();
        }
    }

    private void updateStockInDatabase(String productName, int soldQuantity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            // Retrieve current stock quantity from the database
            PreparedStatement selectPs = con.prepareStatement("SELECT Quantity FROM inventory WHERE ProductName = ?");
            selectPs.setString(1, productName);
            ResultSet selectRs = selectPs.executeQuery();

            if (selectRs.next()) {
                int currentQuantity = selectRs.getInt("Quantity");

                // Calculate and update new stock quantity
                int newQuantity = currentQuantity - soldQuantity;

                // Update the 'inventory' table with the new quantity
                PreparedStatement updatePs = con.prepareStatement("UPDATE inventory SET Quantity = ? WHERE ProductName = ?");
                updatePs.setInt(1, newQuantity);
                updatePs.setString(2, productName);
                int rowsUpdated = updatePs.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Stock updated for product: " + productName);
                } else {
                    System.out.println("Failed to update stock for product: " + productName);
                }
            }

            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
            e.printStackTrace();
        }
    }

//    public void stockup() {
//        DefaultTableModel td = (DefaultTableModel) jTable1.getModel();
//        int rc = td.getRowCount();
//
//        for (int i = 0; i < rc; i++) {
//            String pcode = td.getValueAt(i, 2).toString();
//            String sold_qty = td.getValueAt(i, 3).toString();
//
//            System.out.println(pcode);
//            System.out.println(sold_qty);
//
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//                PreparedStatement ps = con.prepareStatement("select Quantity from inventory where ProductCode= '" + pcode + "' ");
//                ResultSet rs = ps.executeQuery();
//
//                if (rs.next()) {
//                    Stock_qty = Double.valueOf(rs.getString("Quantity"));
//                }
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(rootPane, e);
//            }
//
//            Double st_qty = Stock_qty;
//            Double sell_qty = Double.valueOf(sold_qty);
//
//            Double new_qty = st_qty - sell_qty;
//
//            String nqty = String.valueOf(new_qty);
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//                PreparedStatement ps = con.prepareStatement("update inventory set Quantity = '" + nqty + "' where ProductCode= '" + pcode + "' ");
//                ResultSet rs = ps.executeQuery();
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(rootPane, e);
//            }
//
//        }
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N
        jLabel1.setText("Welcome,");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Product Code", "Quantity", "Unit Price", "Total Price"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Search For Product:");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Quantity:");

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField2KeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Unit Price(₦)");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("0.00");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Stock Quantity:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("0");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Product Code:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jButton2.setText("Add To Cart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Remove From Cart");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Clear All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Pay And Print Reciept");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel13.setText("Amount Due(₦):");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setText("Total Amount(₦):");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setText("0.00");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setText("0.00");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setText("Amount Paid(₦):");

        jTextField3.setText("0.00");

        jButton13.setText("Notifications");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N
        jLabel18.setText("jLabel18");

        jButton6.setText("Sign Out");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SimSun-ExtB", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton13)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13))
                                .addGap(72, 72, 72)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jButton5))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(61, 61, 61)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(76, 76, 76)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addGap(26, 26, 26))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String ProductName = jTextField1.getText();
            Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999..."); //establishing connec
            PreparedStatement ps = con.prepareStatement("select * from inventory where ProductName=?");
            ps.setString(1, ProductName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jLabel6.setText(rs.getString(7));
                jLabel9.setText(rs.getString(6));
                jLabel12.setText(rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Could Not Find Product.");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        int quantityy = Integer.parseInt(jTextField2.getText());
//        int stk_quantity = Integer.parseInt(jLabel9.getText());
//
//        if (quantityy < stk_quantity) {
//
//            try {
//                String pname = jTextField1.getText();
//                String qnty = jTextField2.getText();
//                Double quantity = Double.valueOf(qnty);
//                String upric = jLabel6.getText();
//                Double uprice = Double.valueOf(upric);
//
//                Double tprice = quantity * uprice;
//
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
//                PreparedStatement ps = con.prepareStatement("select ProductName, ProductCode from inventory where ProductName=?");
//                ps.setString(1, pname);
//                ResultSet rs = ps.executeQuery();
//
//                while (rs.next()) {
//                    //String productName = rs.getString(1);
//                    String productCode = rs.getString(2);
//                    String data[] = {rs.getString(1), rs.getString(2), qnty, upric, String.valueOf(tprice)};
//                    DefaultTableModel td = (DefaultTableModel) jTable1.getModel();
//                    td.addRow(data);
//                    total();
//                    amount_due();
//                }
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(rootPane, e);
//                e.printStackTrace();
//            }
//
//        } else {
//            JOptionPane.showMessageDialog(rootPane, "This item is low on stock. Only " + stk_quantity + " left in stock");
//        }

        try {
            String pname = jTextField1.getText();
            String qnty = jTextField2.getText();
            int quantityy = Integer.parseInt(qnty);

            // Check if the product exists in the inventory
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
            PreparedStatement ps = con.prepareStatement("SELECT ProductName, ProductCode, ExpiryDate FROM inventory WHERE ProductName = ?");
            ps.setString(1, pname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String productName = rs.getString("ProductName");
                String productCode = rs.getString("ProductCode");
                String expiryDateStr = rs.getString("ExpiryDate");
                Date expiryDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiryDateStr);

                // Get the current date
                Date currentDate = new Date();

                // Compare expiry date with current date
                if (expiryDate.before(currentDate)) {
                    // Product has expired
                    JOptionPane.showMessageDialog(rootPane, "Sorry, this item cannot be sold. It has expired");
                } else {
                    // Product has not expired, check stock quantity
                    int stk_quantity = Integer.parseInt(jLabel9.getText());

                    if (quantityy < stk_quantity) {
                        // Proceed to add the product to the table
                        String upric = jLabel6.getText();
                        Double uprice = Double.valueOf(upric);
                        Double tprice = quantityy * uprice;

                        // Add the product to the table
                        String data[] = {productName, productCode, qnty, upric, String.valueOf(tprice)};
                        DefaultTableModel td = (DefaultTableModel) jTable1.getModel();
                        td.addRow(data);
                        total();
                        amount_due();
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "This item is low on stock. Only " + stk_quantity + " left in stock");
                    }
                }
            } else {
                // Product not found in the inventory
                JOptionPane.showMessageDialog(rootPane, "Product not found in inventory");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String cashierName = jLabel18.getText();
        int numrow = jTable1.getRowCount();
        int tot = 0;
        for (int i = 0; i < numrow; i++) {
//            double val = Double.valueOf(jTable1.getValueAt(i, 2).toString());
            int val = Integer.valueOf(jTable1.getValueAt(i, 2).toString());
            tot += val;
        }
        String totalQuantity = (Integer.toString(tot));
        String totalAmount = jLabel15.getText();
        String currentDate = getCurrentDate();
        String username = homeInstance.jTextField1.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            // Prepare the SQL statement
            String sql = "INSERT INTO cashier_sales (Username, Quantity, Amount, sale_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            // Set parameters
            ps.setString(1, username);
            ps.setString(2, totalQuantity);
            ps.setString(3, totalAmount);
            ps.setString(4, currentDate);

            // Execute the SQL statement
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                updateStockQuantity();
                JOptionPane.showMessageDialog(rootPane, "Payment Successful!");
            } else {
                JOptionPane.showMessageDialog(rootPane, "Failed to record sale data.");
            }

            // Close the connection
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        try {
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            String insertQuery = "INSERT INTO sales (ProductName, Quantity, Price, Date) VALUES (?, ?, ?, ?)";
            Class.forName("com.mysql.cj.jdbc.Driver");
            String path = "jdbc:mysql://localhost:3306/exam-csc_301";
            String user = "root";
            String pass = "password999...";
            Connection con = DriverManager.getConnection(path, user, pass);
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            for (int row = 0; row < jTable1.getRowCount(); row++) {
                Object column1Value = jTable1.getValueAt(row, 0); // Assuming column 1 is at index 0
                Object column3Value = jTable1.getValueAt(row, 2); // Assuming column 3 is at index 2
                Object column4Value = jTable1.getValueAt(row, 3); // Assuming column 4 is at index 3

                Date today = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String cDate = dateFormat.format(today);

                preparedStatement.setObject(1, column1Value);
                preparedStatement.setObject(2, column3Value);
                preparedStatement.setObject(3, column4Value);
                preparedStatement.setObject(4, cDate);

                preparedStatement.executeUpdate();
            }

            JOptionPane.showMessageDialog(rootPane, "Records Inserted");
            tableModel.setRowCount(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        try {
            // Initialize the PrinterJob
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(new ReceiptPrintable(), getPageFormat(pj));

            // Print the receipt
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
            updateStockQuantity();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel td = (DefaultTableModel) jTable1.getModel();
        td.setRowCount(0);
        jTextField1.setText("");
        jTextField2.setText("");
        jLabel9.setText("0");
        jLabel6.setText("0.00");
        jLabel12.setText("");
        jLabel16.setText("0.00");
        total();
        amount_due();// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel td = (DefaultTableModel) jTable1.getModel();

        // Check if there are any rows to remove
        if (td.getRowCount() > 0) {
            td.removeRow(td.getRowCount() - 1);
            total();
        } else {
            JOptionPane.showMessageDialog(rootPane, "No rows to remove.");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        BirthdayPopUp();
        checkProductExpiry();
        checkStockStatus();// TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c) && !evt.isAltDown()) {
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Please enter digits only.");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2KeyTyped

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            Home c = new Home();
            c.setVisible(true);
            c.pack();
            c.setLocationRelativeTo(null);
            c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /**
     * @param args the command line arguments
     */
    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(20);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;

    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public class ReceiptPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            ImageIcon icon = new ImageIcon("\"C:\\Users\\abdul\\OneDrive\\Pictures\\PAU Logo - Pius2.png\"");
            int result = NO_SUCH_PAGE;

            // Only one page, index 0
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();

                // Set the font and size
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));

                // Initial position
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;

                // Print the receipt header
                g2d.drawString("************************** RECEIPT **************************", 10, y);
                y += yShift;
                g2d.drawString(" Item Name      Quantity      Unit Price      Total Price", 10, y);
                y += headerRectHeight;

                // Loop through each row in jTable1 and print the details
                DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
                int rowCount = tableModel.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    String itemName = (String) tableModel.getValueAt(i, 0);
                    String quantity = tableModel.getValueAt(i, 2).toString();
                    String unitPrice = tableModel.getValueAt(i, 3).toString();
                    String totalPrice = tableModel.getValueAt(i, 4).toString();

                    g2d.drawString(" " + itemName + "             " + quantity + "           " + unitPrice + "           " + totalPrice, 10, y);
                    y += yShift;
                }

                // Print the total amount, amount due, and amount paid
                g2d.drawString("*************************************************************", 10, y);
                y += yShift;
                g2d.drawString(" Total Amount:       " + jLabel15.getText(), 10, y);
                y += yShift;
                g2d.drawString(" Amount Due:         " + jLabel16.getText(), 10, y);
                y += yShift;
                g2d.drawString(" Amount Paid:        " + jTextField3.getText(), 10, y);
                y += yShift;
                g2d.drawString("*************************************************************", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("                pau.edu.ng        ", 12, y);
                y += yShift;
                g2d.drawString("         KM 52 Lekki - Epe Expressway ", 12, y);
                y += yShift;
                g2d.drawString("           Lagos State, NIGERIA ", 12, y);
                y += yShift;
                g2d.drawString("            www.facebook.com/PAU ", 12, y);
                y += yShift;
                g2d.drawString("             +234 902 712 3425      ", 12, y);
                y += yShift;
                g2d.drawString("*************************************************************", 10, y);
                y += yShift;
                g2d.drawString("           THANK YOU. PLEASE COME AGAIN            ", 10, y);
                y += yShift;
                g2d.drawString("*************************************************************", 10, y);
                y += yShift;

                // Print any other information you want on the receipt
                result = PAGE_EXISTS;
            }

            return result;
        }
    }

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
            java.util.logging.Logger.getLogger(Sales_Cashier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales_Cashier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales_Cashier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales_Cashier.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        Home homeInstance = new Home();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Sales_Cashier().setVisible(true);
                new Sales_Cashier("John Doe", "john.doe@example.com", homeInstance).setVisible(true);
                // new Sales_Cashier("John Doe", "john.doe@example.com").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
