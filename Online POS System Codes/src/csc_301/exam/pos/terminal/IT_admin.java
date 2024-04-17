/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package csc_301.exam.pos.terminal;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abdul
 */
public class IT_admin extends javax.swing.JFrame {

    public String Hash(String c) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("MD5");
            msgDigest.update((new String(c)).getBytes("UTF8"));
            String passHash = new String(msgDigest.digest());
            return passHash;
        } catch (Exception ex) {

            return c;
        }
    }
    public String filename = null;
    static byte photo[] = null;
    ArrayList<String> birthdayCelebrants = new ArrayList<>();
    private String userFirstname;
    private String userEmail;

    /**
     * Creates new form IT_admin
     */
    public IT_admin(String userFirstname, String userEmail) {
        initComponents();
        displaydate();
//        runBirthdayPopUp p = new runBirthdayPopUp();
//        Thread t = new Thread(p);
//        t.start();
        this.userFirstname = userFirstname;
        this.userEmail = userEmail;
        jLabel12.setText(userFirstname);
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
                        jLabel14.setText(formattedDateTime);
                    }
                });
            }
        });
        timer.start();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UserLastName = new javax.swing.JLabel();
        UserDOB = new javax.swing.JLabel();
        UserPhoneNo = new javax.swing.JLabel();
        UserGender = new javax.swing.JLabel();
        UserEmail = new javax.swing.JLabel();
        UserUsername = new javax.swing.JLabel();
        UserPassword = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        UserPhoneNo1 = new javax.swing.JLabel();
        UserGender1 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        UserEmail1 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jRadioButton3 = new javax.swing.JRadioButton();
        UserUsername1 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jRadioButton4 = new javax.swing.JRadioButton();
        UserPassword1 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        UserLastName1 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        UserDOB1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jTextField11 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton13 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N
        jLabel1.setText("Welcome");

        jLabel2.setText("First Name:");

        jLabel3.setText("Middle Name:");

        UserLastName.setText("Last Name:");

        UserDOB.setText("Date Of Birth:");

        UserPhoneNo.setText("Phone Number:");

        UserGender.setText("Gender:");

        UserEmail.setText("Email:");

        UserUsername.setText("Username:");

        UserPassword.setText("Password:");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Male");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Female");

        jButton1.setText("Browse Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Take Webcam Image");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Auto-Generate Password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Create User");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Department:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Departmet", "Sales Person/Cashier", "Inventory/Stock Manager", "IT Administrator/Manager", "Sales Manager" }));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField7.setText("jTextField7");

        jLabel5.setText("Picture Location:");

        jCheckBox1.setText("Show Password");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(389, 389, 389)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(UserEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(UserUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(UserPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(57, 57, 57)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserLastName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserPhoneNo, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                .addComponent(UserGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(18, 18, 18)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                    .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(8, 8, 8)
                                                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(175, 175, 175)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jButton3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserLastName)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserDOB)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserPhoneNo)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserGender)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserEmail)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserUsername)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserPassword)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Register ", jPanel2);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UserPhoneNo1.setText("Phone Number:");

        UserGender1.setText("Gender:");

        UserEmail1.setText("Email:");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Male");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        UserUsername1.setText("Username:");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Female");

        UserPassword1.setText("Password:");

        jLabel8.setText("Picture Location:");

        jButton6.setText("Search");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Clear");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel9.setText("First Name:");

        jLabel10.setText("Middle Name:");

        jLabel11.setText("Department:");

        UserLastName1.setText("Last Name:");

        jButton8.setText("Browse Image");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        UserDOB1.setText("Date Of Birth:");

        jButton9.setText("Take Webcam Image");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Departmet", "Sales Person/Cashier", "Inventory/Stock Manager", "IT Administrator/Manager", "Sales Manager" }));

        jButton10.setText("Re Auto-Generate Password");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField11KeyTyped(evt);
            }
        });

        jButton11.setText("Update");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Delete");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Show Password");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(UserEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(819, 819, 819))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(UserUsername1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserLastName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserDOB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserPhoneNo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(UserGender1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(8, 8, 8)
                                                .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField10, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField11, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(128, 128, 128)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(UserPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton10)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserUsername1)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserLastName1)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserDOB1)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserPhoneNo1)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserGender1)
                            .addComponent(jRadioButton4)
                            .addComponent(jRadioButton3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(32, 32, 32)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserEmail1)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserPassword1)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton12)
                    .addComponent(jButton11)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Search/Modify/Delete", jPanel3);

        jButton13.setText("Notifications");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SimSun-ExtB", 1, 24)); // NOI18N

        jLabel14.setFont(new java.awt.Font("SimSun-ExtB", 0, 24)); // NOI18N

        jButton14.setText("Sign Out");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 868, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton13)
                            .addComponent(jButton14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jTabbedPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        if (jCheckBox2.isSelected()) {
            jPasswordField2.setEchoChar((char) 0);
        } else {
            jPasswordField2.setEchoChar('*');
        }  // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        try {
            String username = jTextField13.getText();
            String department = jComboBox2.getSelectedItem().toString();
            if (department.equals("Sales Person/Cashier")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("delete from sales_department where Username=? ");
                    ps.setString(1, username);
                    int rs = ps.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, username + "'s record succesfully deleted");

                    jTextField8.setText("");
                    jTextField9.setText("");
                    jTextField10.setText("");
                    jDateChooser2.setDateFormatString("");
                    jTextField11.setText("");
                    buttonGroup1.clearSelection();
                    jComboBox2.setSelectedIndex(0);
                    jTextField12.setText("");
                    jPasswordField2.setText("");
                    jTextField13.setText("");
                    jLabel7.setText("");
                    jTextField14.setText("");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("Inventory/Stock Manager")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("delete from inventory_department where Username=? ");
                    ps.setString(1, username);
                    int rs = ps.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, username + "'s record succesfully deleted");

                    jTextField8.setText("");
                    jTextField9.setText("");
                    jTextField10.setText("");
                    jDateChooser2.setDateFormatString("");
                    jTextField11.setText("");
                    buttonGroup1.clearSelection();
                    jComboBox2.setSelectedIndex(0);
                    jTextField12.setText("");
                    jPasswordField2.setText("");
                    jTextField13.setText("");
                    jLabel7.setText("");
                    jTextField14.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("IT Administrator/Manager")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("delete from it_department where Username=? ");
                    ps.setString(1, username);
                    int rs = ps.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, username + "'s record succesfully deleted");

                    jTextField8.setText("");
                    jTextField9.setText("");
                    jTextField10.setText("");
                    jDateChooser2.setDateFormatString("");
                    jTextField11.setText("");
                    buttonGroup1.clearSelection();
                    jComboBox2.setSelectedIndex(0);
                    jTextField12.setText("");
                    jPasswordField2.setText("");
                    jTextField13.setText("");
                    jLabel7.setText("");
                    jTextField14.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }  else if (department.equals("sales_manager")) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("delete from sales_manager where Username=? ");
                    ps.setString(1, username);
                    int rs = ps.executeUpdate();
                    JOptionPane.showMessageDialog(rootPane, username + "'s record succesfully deleted");

                    jTextField8.setText("");
                    jTextField9.setText("");
                    jTextField10.setText("");
                    jDateChooser2.setDateFormatString("");
                    jTextField11.setText("");
                    buttonGroup1.clearSelection();
                    jComboBox2.setSelectedIndex(0);
                    jTextField12.setText("");
                    jPasswordField2.setText("");
                    jTextField13.setText("");
                    jLabel7.setText("");
                    jTextField14.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            String firstname = jTextField8.getText();
            String middlename = jTextField9.getText();
            String lastname = jTextField10.getText();
            SimpleDateFormat dat = new SimpleDateFormat("dd/MM/yyyy");
            String dob = dat.format(jDateChooser2.getDate());
            String phoneno = jTextField11.getText();
            String gender = null;
            if (jRadioButton3.isSelected()) {
                gender = "Male";
            } else if (jRadioButton4.isSelected()) {
                gender = "Female";
            }
            String department = jComboBox2.getSelectedItem().toString();
            String email = jTextField12.getText();
            String username = jTextField13.getText();

            if (department.equals("Sales Person/Cashier")) {
                try {
                    String password1 = jPasswordField2.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("update sales_department set FirstName=?, MiddleName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Gender=?, Department=?, Email=?, Password=?, Photo=? where Username=? ");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, enpass);
                    ps.setBytes(10, photo);
                    ps.setString(11, username);
                    JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully updated. Please check your email for confirmation.");
                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel7.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("Inventory/Stock Manager")) {
                try {
                    String password1 = jPasswordField2.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("update inventory_department set FirstName=?, MiddleName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Gender=?, Department=?, Email=?, Password=?, Photo=? where Username=? ");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, enpass);
                    ps.setBytes(10, photo);
                    ps.setString(11, username);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully updated. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel7.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("IT Administrator/Manager")) {
                try {
                    String password1 = jPasswordField2.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("update it_department set FirstName=?, MiddleName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Gender=?, Department=?, Email=?, Password=?, Photo=? where Username=? ");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, enpass);
                    ps.setBytes(10, photo);
                    ps.setString(11, username);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully updated. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel7.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("Sales Manager")) {
                try {
                    String password1 = jPasswordField2.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("update sales_manager set FirstName=?, MiddleName=?, LastName=?, DateOfBirth=?, PhoneNumber=?, Gender=?, Department=?, Email=?, Password=?, Photo=? where Username=? ");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, enpass);
                    ps.setBytes(10, photo);
                    ps.setString(11, username);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully updated. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel7.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a department.");
            }
        
        }catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
}
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        //Auto-generated random password
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String specialChars = "<>,.?/}]{[+_-)(!@#$%^&*=";
        String combination = upper + lower + num + specialChars;
        int len = 8;
        char[] ppassword = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            ppassword[i] = combination.charAt(r.nextInt(combination.length()));
        }
        jPasswordField2.setText(new String(ppassword));        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Webcam2 newWebcam2 = new Webcam2();
        newWebcam2.show();
        newWebcam2.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "jpg");
            jFileChooser1.setAcceptAllFileFilterUsed(false);
            jFileChooser1.addChoosableFileFilter(filter);
            jFileChooser1.showOpenDialog(null);

            File f = jFileChooser1.getSelectedFile();
            filename = f.getAbsolutePath();
            String path = f.getAbsolutePath();
            jTextField7.setText(path);
            Image im = Toolkit.getDefaultToolkit().createImage(path);
            im = im.getScaledInstance(jLabel6.getWidth(), jLabel6.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon ic = new ImageIcon(im);
            jLabel6.setIcon(ic);

            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] Byte = new byte[1024];

            for (int i; (i = fis.read(Byte)) != -1;) {
                baos.write(Byte, 0, i);
            }
            photo = baos.toByteArray();
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jDateChooser2.setDateFormatString("");
        jTextField11.setText("");
        buttonGroup1.clearSelection();
        jComboBox2.setSelectedIndex(0);
        jTextField12.setText("");
        jPasswordField2.setText("");
        jTextField13.setText("");
        jLabel7.setText("");
        jTextField14.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            String username = jTextField13.getText();
            String getUsername = null;
            Class.forName("com.mysql.cj.jdbc.Driver");   // loading the driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");

            // Check in sales_department
            PreparedStatement ps = con.prepareStatement("select * from sales_department where Username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                getUsername = rs.getString(9);

                // Set values for the sales_department
                jTextField8.setText(rs.getString(1));
                jTextField9.setText(rs.getString(2));
                jTextField10.setText(rs.getString(3));
                String dateOfBirthStr = rs.getString("DateOfBirth");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);
                jDateChooser2.setDate(dateOfBirth);
                jTextField11.setText(rs.getString(5));
                String gender = rs.getString(6);
                if (gender.equalsIgnoreCase("Male")) {
                    jRadioButton3.setSelected(true);
                } else {
                    jRadioButton4.setSelected(true);
                }
                jComboBox2.setSelectedItem(rs.getString(7));
                jTextField12.setText(rs.getString(8));
                jPasswordField2.setText(rs.getString(10));

                // Set the image for sales_department
                photo = rs.getBytes(11);
                ImageIcon ic = new ImageIcon(photo);
                Image im = ic.getImage();
                Image scaled = im.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIc = new ImageIcon(scaled);
                jLabel7.setIcon(scaledIc);

            } else {
                // If not found in sales_department, check in inventory_department
                PreparedStatement psInventory = con.prepareStatement("select * from inventory_department where Username=?");
                psInventory.setString(1, username);
                ResultSet rsInventory = psInventory.executeQuery();

                if (rsInventory.next()) {
                    getUsername = rsInventory.getString(9);

                    // Set values for inventory_department
                    jTextField8.setText(rsInventory.getString(1));
                    jTextField9.setText(rsInventory.getString(2));
                    jTextField10.setText(rsInventory.getString(3));
                    String dateOfBirthStrInventory = rsInventory.getString("DateOfBirth");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dateOfBirthInventory = dateFormat.parse(dateOfBirthStrInventory);
                    jDateChooser2.setDate(dateOfBirthInventory);
                    jTextField11.setText(rsInventory.getString(5));
                    String gender = rsInventory.getString(6);
                    if (gender.equalsIgnoreCase("Male")) {
                        jRadioButton3.setSelected(true);
                    } else {
                        jRadioButton4.setSelected(true);
                    }
                    jComboBox2.setSelectedItem(rsInventory.getString(7));
                    jTextField12.setText(rsInventory.getString(8));
                    jPasswordField2.setText(rsInventory.getString(10));

                    // Set the image for inventory_department
                    photo = rsInventory.getBytes(11);
                    ImageIcon ic = new ImageIcon(photo);
                    Image im = ic.getImage();
                    Image scaled = im.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon scaledIc = new ImageIcon(scaled);
                    jLabel7.setIcon(scaledIc);

                } else {
                    // If not found in inventory_department, check in it_department
                    PreparedStatement psIT = con.prepareStatement("select * from it_department where Username=?");
                    psIT.setString(1, username);
                    ResultSet rsIT = psIT.executeQuery();

                    if (rsIT.next()) {
                        getUsername = rsIT.getString(9);

                        // Set values for it_department
                        jTextField8.setText(rsIT.getString(1));
                        jTextField9.setText(rsIT.getString(2));
                        jTextField10.setText(rsIT.getString(3));
                        String dateOfBirthStrIT = rsIT.getString("DateOfBirth");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateOfBirthIT = dateFormat.parse(dateOfBirthStrIT);
                        jDateChooser2.setDate(dateOfBirthIT);
                        jTextField11.setText(rsIT.getString(5));
                        String gender = rsIT.getString(6);
                        if (gender.equalsIgnoreCase("Male")) {
                            jRadioButton3.setSelected(true);
                        } else {
                            jRadioButton4.setSelected(true);
                        }
                        jComboBox2.setSelectedItem(rsIT.getString(7));
                        jTextField12.setText(rsIT.getString(8));
                        jPasswordField2.setText(rsIT.getString(10));

                        // Set the image for it_department
                        photo = rsIT.getBytes(11);
                        ImageIcon ic = new ImageIcon(photo);
                        Image im = ic.getImage();
                        Image scaled = im.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon scaledIc = new ImageIcon(scaled);
                        jLabel7.setIcon(scaledIc);
                    } else {
                        // If not found in inventory_department, check in it_department
                        PreparedStatement sm = con.prepareStatement("select * from sales_manager where Username=?");
                        psIT.setString(1, username);
                        ResultSet ns = sm.executeQuery();

                        if (rsIT.next()) {
                            getUsername = ns.getString(9);

                            // Set values for it_department
                            jTextField8.setText(ns.getString(1));
                            jTextField9.setText(ns.getString(2));
                            jTextField10.setText(ns.getString(3));
                            String dateOfBirthSns = ns.getString("DateOfBirth");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date dateOfBirthK = dateFormat.parse(dateOfBirthSns);
                            jDateChooser2.setDate(dateOfBirthK);
                            jTextField11.setText(ns.getString(5));
                            String gender = ns.getString(6);
                            if (gender.equalsIgnoreCase("Male")) {
                                jRadioButton3.setSelected(true);
                            } else {
                                jRadioButton4.setSelected(true);
                            }
                            jComboBox2.setSelectedItem(ns.getString(7));
                            jTextField12.setText(ns.getString(8));
                            jPasswordField2.setText(ns.getString(10));

                            // Set the image for it_department
                            photo = ns.getBytes(11);
                            ImageIcon ic = new ImageIcon(photo);
                            Image im = ic.getImage();
                            Image scaled = im.getScaledInstance(jLabel7.getWidth(), jLabel7.getHeight(), Image.SCALE_SMOOTH);
                            ImageIcon scaledIc = new ImageIcon(scaled);
                            jLabel7.setIcon(scaledIc);
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Could Not Find User!");
                        }
                    }
                }
            }

            con.close(); // Close the connection when done

        } catch (Exception e) {
            e.printStackTrace(); // Print the exception details for debugging purposes
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jPasswordField1.setEchoChar((char) 0);
        } else {
            jPasswordField1.setEchoChar('*');
        }  // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jDateChooser1.setDate(null);
        jTextField4.setText("");
        buttonGroup1.clearSelection();
        jComboBox1.setSelectedIndex(0);
        jTextField5.setText("");
        jPasswordField1.setText("");
        jTextField6.setText("");
        jLabel6.setText("");
        jTextField7.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            String firstname = jTextField1.getText();
            String middlename = jTextField2.getText();
            String lastname = jTextField3.getText();
            SimpleDateFormat dat = new SimpleDateFormat("dd/MM/yyyy");
            String dob = dat.format(jDateChooser1.getDate());
            String phoneno = jTextField4.getText();
            String gender = null;
            if (jRadioButton1.isSelected()) {
                gender = "Male";
            } else if (jRadioButton2.isSelected()) {
                gender = "Female";
            }
            String department = jComboBox1.getSelectedItem().toString();
            String email = jTextField5.getText();
            String username = jTextField6.getText();

            if (department.equals("Sales Person/Cashier")) {
                try {
                    String password1 = jPasswordField1.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("insert into sales_department values(?,?,?,?,?,?,?,?,?,?,?)");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, username);
                    ps.setString(10, enpass);
                    ps.setBytes(11, photo);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully created. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel6.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("Inventory/Stock Manager")) {
                try {
                    String password1 = jPasswordField1.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("insert into inventory_department values(?,?,?,?,?,?,?,?,?,?,?)");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, username);
                    ps.setString(10, enpass);
                    ps.setBytes(11, photo);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully created. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel6.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("IT Administrator/Manager")) {
                try {

                    String password1 = jPasswordField1.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("insert into it_department values(?,?,?,?,?,?,?,?,?,?,?)");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, username);
                    ps.setString(10, enpass);
                    ps.setBytes(11, photo);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully created. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel6.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else if (department.equals("Sales Manager")) {
                try {

                    String password1 = jPasswordField1.getText();
                    String enpass = Hash(password1);

                    Class.forName("com.mysql.cj.jdbc.Driver");   //loading the drive
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/exam-csc_301", "root", "password999...");
                    PreparedStatement ps = con.prepareStatement("insert into sales_manager values(?,?,?,?,?,?,?,?,?,?,?)");

                    ps.setString(1, firstname);
                    ps.setString(2, middlename);
                    ps.setString(3, lastname);
                    ps.setString(4, dob);
                    ps.setString(5, phoneno);
                    ps.setString(6, gender);
                    ps.setString(7, department);
                    ps.setString(8, email);
                    ps.setString(9, username);
                    ps.setString(10, enpass);
                    ps.setBytes(11, photo);

                    int rs = ps.executeUpdate();

                    //sending email section
                    String subject = "Username and Password for the new POS system\n";        // TODO add your handling code here:
                    String receiver = email;
                    String body = "Hello " + firstname + ",\nPlease find your username and password for the new POS system attached in the email below.\n"
                            + "Your Username: " + username + "\n"
                            + "Your Password: " + password1 + "\n"
                            + "Have a good day!";
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
                        JOptionPane.showMessageDialog(rootPane, "User " + firstname + " has been successfully created. Please check your email for confirmation.");

                    } catch (MessagingException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }

                    jTextField1.setText("");
                    jTextField2.setText("");
                    jTextField3.setText("");
                    jTextField4.setText("");
                    jTextField5.setText("");
                    jTextField6.setText("");
                    jDateChooser1.setDate(null);
                    buttonGroup1.clearSelection();
                    jComboBox1.setSelectedIndex(0);
                    jPasswordField1.setText("");
                    jLabel6.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Please select a department");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Auto-generated random password
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String specialChars = "<>,.?/}]{[+_-)(!@#$%^&*=";
        String combination = upper + lower + num + specialChars;
        int len = 8;
        char[] ppassword = new char[len];
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            ppassword[i] = combination.charAt(r.nextInt(combination.length()));
        }
        jPasswordField1.setText(new String(ppassword));        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Webcam newWebcam = new Webcam();
        newWebcam.show();
        newWebcam.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image", "jpg");
            jFileChooser1.setAcceptAllFileFilterUsed(false);
            jFileChooser1.addChoosableFileFilter(filter);
            jFileChooser1.showOpenDialog(null);

            File f = jFileChooser1.getSelectedFile();
            filename = f.getAbsolutePath();
            String path = f.getAbsolutePath();
            jTextField7.setText(path);
            Image im = Toolkit.getDefaultToolkit().createImage(path);
            im = im.getScaledInstance(jLabel6.getWidth(), jLabel6.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon ic = new ImageIcon(im);
            jLabel6.setIcon(ic);

            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] Byte = new byte[1024];

            for (int i; (i = fis.read(Byte)) != -1;) {
                baos.write(Byte, 0, i);
            }
            photo = baos.toByteArray();
        } catch (Exception e) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        BirthdayPopUp();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            Home c = new Home();
            c.setVisible(true);
            c.pack();
            c.setLocationRelativeTo(null);
            c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }  // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTextField11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c) && !evt.isAltDown()) {
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Phone Number can only contain digits.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c) && !evt.isAltDown()) {
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Phone Number can only contain digits.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4KeyTyped

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
            java.util.logging.Logger.getLogger(IT_admin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IT_admin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IT_admin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IT_admin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IT_admin("John Doe", "john.doe@example.com").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel UserDOB;
    private javax.swing.JLabel UserDOB1;
    private javax.swing.JLabel UserEmail;
    private javax.swing.JLabel UserEmail1;
    private javax.swing.JLabel UserGender;
    private javax.swing.JLabel UserGender1;
    private javax.swing.JLabel UserLastName;
    private javax.swing.JLabel UserLastName1;
    private javax.swing.JLabel UserPassword;
    private javax.swing.JLabel UserPassword1;
    private javax.swing.JLabel UserPhoneNo;
    private javax.swing.JLabel UserPhoneNo1;
    private javax.swing.JLabel UserUsername;
    private javax.swing.JLabel UserUsername1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public static javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    public static javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
