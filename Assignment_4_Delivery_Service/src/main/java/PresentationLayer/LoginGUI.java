package PresentationLayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Array;
import java.util.Arrays;

public class LoginGUI {
    public JFrame startUpFrame;
    public Dimension startDimension;
    public JPanel startUpPanel;

    private Color darkBlue = new Color(45, 49, 66);
    private Color lightOrange = new Color(239, 131, 84);
    private Color white = new Color(255, 255, 255);
    private Color lightGray = new Color(191, 192, 192);
    private Color blueGray = new Color(79, 93, 117);

    public JButton createAccount, loginButton, submitCreateButton, submitLoginButton, backButton;
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JComboBox roleBox;

    public LoginGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        startDimension = new Dimension(screenSize.width/4, screenSize.height/2);

        startUpFrame = new JFrame("Welcome!");
        startUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startUpFrame.setSize(startDimension);
        startUpFrame.setLocationRelativeTo(null);
        startUpFrame.setResizable(true);
        startUpFrame.setLayout(new BoxLayout(startUpFrame.getContentPane(), BoxLayout.Y_AXIS));

        startUpPanel = new JPanel();
        startUpPanel.setPreferredSize(startDimension);
        startUpPanel.setLayout(new GridBagLayout());
        startUpPanel.setBackground(darkBlue);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 0, 15, 0);

        constraints.gridx = constraints.gridy = 0;
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        welcomeLabel.setForeground(white);
        startUpPanel.add(welcomeLabel, constraints);

        constraints.gridy++;
        createAccount = new JButton("Create a new Account");
        createAccount.setFont(new Font("Courier New", Font.PLAIN, 20));
        createAccount.setForeground(darkBlue);
        createAccount.setBackground(lightGray);
        createAccount.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(createAccount, constraints);

        constraints.gridy++;
        loginButton = new JButton("Login into an Account");
        loginButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        loginButton.setForeground(darkBlue);
        loginButton.setBackground(lightOrange);
        loginButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(loginButton, constraints);

        backButton = new JButton("Back to the main page");
        submitCreateButton = new JButton("Submit");
        submitLoginButton = new JButton("Submit");

        String[] roles = new String[3];
        roles[0] = "Administrator";
        roles[1] = "Employee";
        roles[2] = "Client";
        roleBox = new JComboBox(roles);
        roleBox.setFont(new Font("Courier New", Font.PLAIN, 20));
        roleBox.setForeground(white);
        roleBox.setBackground(blueGray);

        startUpFrame.add(startUpPanel);
        startUpFrame.getContentPane();
        startUpFrame.setVisible(true);
    }

    public void reset() {
        startUpFrame.remove(startUpPanel);
        startUpPanel.removeAll();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 0, 15, 0);

        constraints.gridx = constraints.gridy = 0;
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        welcomeLabel.setForeground(white);
        startUpPanel.add(welcomeLabel, constraints);

        constraints.gridy++;
        startUpPanel.add(createAccount, constraints);

        constraints.gridy++;
        startUpPanel.add(loginButton, constraints);

        startUpPanel.repaint();
        startUpPanel.revalidate();

        startUpFrame.add(startUpPanel);
        startUpFrame.repaint();
        startUpFrame.revalidate();
    }

    public void createNewAccount() {
        startUpFrame.remove(startUpPanel);

        startUpPanel.removeAll();
        startUpPanel.setPreferredSize(startDimension);
        startUpPanel.setLayout(new GridBagLayout());
        startUpPanel.setBackground(darkBlue);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 10, 15, 10);

        constraints.gridx = constraints.gridy = 0;
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Courier New", Font.PLAIN, 20));
        username.setForeground(lightOrange);
        startUpPanel.add(username, constraints);

        constraints.gridx++;
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameField.setBackground(blueGray);
        usernameField.setForeground(white);
        usernameField.setFont(new Font("Courier New", Font.PLAIN, 20));
        usernameField.setEditable(true);
        startUpPanel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy ++;
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Courier New", Font.PLAIN, 20));
        password.setForeground(lightOrange);
        startUpPanel.add(password, constraints);

        constraints.gridx++;
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setBackground(blueGray);
        passwordField.setForeground(white);
        passwordField.setFont(new Font("Courier New", Font.PLAIN, 20));
        passwordField.setEditable(true);
        startUpPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel role = new JLabel("Role:");
        role.setFont(new Font("Courier New", Font.PLAIN, 20));
        role.setForeground(lightOrange);
        startUpPanel.add(role, constraints);

        constraints.gridx++;
        startUpPanel.add(roleBox, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy++;
        submitCreateButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        submitCreateButton.setForeground(darkBlue);
        submitCreateButton.setBackground(lightOrange);
        submitCreateButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(submitCreateButton, constraints);

        constraints.gridy++;
        backButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        backButton.setForeground(darkBlue);
        backButton.setBackground(lightGray);
        backButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(backButton, constraints);

        startUpPanel.repaint();
        startUpPanel.revalidate();

        startUpFrame.add(startUpPanel);
        startUpFrame.repaint();
        startUpFrame.revalidate();
    }

    public void loginAccount() {
        startUpFrame.remove(startUpPanel);

        startUpPanel.removeAll();
        startUpPanel.setPreferredSize(startDimension);
        startUpPanel.setLayout(new GridBagLayout());
        startUpPanel.setBackground(darkBlue);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 10, 15, 10);

        constraints.gridx = constraints.gridy = 0;
        JLabel username = new JLabel("Username:");
        username.setFont(new Font("Courier New", Font.PLAIN, 20));
        username.setForeground(lightOrange);
        startUpPanel.add(username, constraints);

        constraints.gridx++;
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameField.setBackground(blueGray);
        usernameField.setForeground(white);
        usernameField.setFont(new Font("Courier New", Font.PLAIN, 20));
        usernameField.setEditable(true);
        startUpPanel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy ++;
        JLabel password = new JLabel("Password:");
        password.setFont(new Font("Courier New", Font.PLAIN, 20));
        password.setForeground(lightOrange);
        startUpPanel.add(password, constraints);

        constraints.gridx++;
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setBackground(blueGray);
        passwordField.setForeground(white);
        passwordField.setFont(new Font("Courier New", Font.PLAIN, 20));
        passwordField.setEditable(true);
        startUpPanel.add(passwordField, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy++;
        submitLoginButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        submitLoginButton.setForeground(darkBlue);
        submitLoginButton.setBackground(lightOrange);
        submitLoginButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(submitLoginButton, constraints);

        constraints.gridy++;
        backButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        backButton.setForeground(darkBlue);
        backButton.setBackground(lightGray);
        backButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startUpPanel.add(backButton, constraints);

        startUpPanel.repaint();
        startUpPanel.revalidate();

        startUpFrame.add(startUpPanel);
        startUpFrame.repaint();
        startUpFrame.revalidate();
    }
}
