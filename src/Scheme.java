import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class Scheme extends JFrame {

    private JPanel rootPanel;
    private JButton createEmailButton;
    private JButton saveEmailButton;
    private JButton createShopButton;
    private JButton saveShopButton;
    private JButton createBankButton;
    private JButton saveBankButton;
    private JLabel createBankLabel;
    private JLabel createShopLabel;
    private JLabel createEmailLabel;
    private JPanel createPasswordsPanel;

    private JButton enterShopButton;
    private JButton doneShopButton;
    private JButton enterBankButton;
    private JButton doneBankButton;
    private JButton enterEmailButton;
    private JButton doneEmailButton;

    private String emailPassword;
    private String bankPassword;
    private String shopPassword;

    private static String FINAL_EMAIL_PASSWORD;
    private static String FINAL_BANK_PASSWORD;
    private static String FINAL_SHOP_PASSWORD;

    private int attempts;

    private static final String [] WORDS = {"tie", "nose", "my", "ever", "how", "plan",
            "in", "cage", "did", "bell", "each", "fort",
            "bark", "cell", "late", "with", "pull", "draw",
            "blue", "ask", "rest", "fast", "sing", "rice",
            "hat", "walk", "mood", "hay", "pool", "hope",
            "dig", "ring", "map", "tape", "such", "main"};

    private static final String [] NUMBER_AND_SYMBOLS = {"!", "@", "#", "$", "%", "&",
                                        "1", "2", "3", "4", "5", "6",
                                        "7", "8", "9", "0", ";", ":",
                                        "]", "[", "?", "=", "+", "_",
                                        "-", "~", "/", "{", "}", "*",};

    //open file to write to (append to save updates)

    /* create random 4 word password form WORDS array
    private String makePassword(){
        String password = "";
        //System.out.println("Random number: " + (int)(Math.random()*35+1));
        for (int i = 0; i < 4; i++) {
            password += WORDS[(int)(Math.random()*35+1)];
        }

        return password;
    }
    */

//    create random 3 word 1 symbol password form WORDS & NUMBER_AND_SYMBOLS array
    private String makePassword(){
        String password = "";
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            password += WORDS[random.nextInt(36)];
        }
        password += NUMBER_AND_SYMBOLS[random.nextInt(30)];

        return password;
    }

    private void init(){
        setTitle("Password Scheme");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(rootPanel);

        setVisible(true);
    }

    //opens a new window for testing a password
    private void openTestWindow(String password){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                //write to file current user, timestamp, create event
                JFrame testFrame = new JFrame("Test Password");
                testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);

                JLabel infoLabel = new JLabel("Your passowrd is: " + password);
                Font f = infoLabel.getFont();

                infoLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                JLabel infoLabel2 = new JLabel("Please practice inputing your password." );
                JLabel infoLabel3 = new JLabel("Close window when you can remember your password.");

                JPanel inputpanel = new JPanel();
                JButton acceptPasswordButton = new JButton("Accept Password");
                acceptPasswordButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        testFrame.dispose();
                    }
                });

                inputpanel.setLayout(new FlowLayout());
                JPasswordField input = new JPasswordField(20);
                JButton enterButton = new JButton("Enter");
                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (Arrays.equals(input.getPassword(), password.toCharArray())) {
                            // password match! log practice success
                            JOptionPane.showMessageDialog(testFrame, "Correct Password");
                            System.out.println("password match!");
                        }
                        else {
                            JOptionPane.showMessageDialog(testFrame, "Wrong Password try again");
                            System.out.println("wrong try again");
                        }
                        input.setText("");
                    }
                });

                panel.add(infoLabel);
                panel.add(infoLabel2);
                panel.add(infoLabel3);
                panel.add(acceptPasswordButton);
                inputpanel.add(input);
                inputpanel.add(enterButton);
                panel.add(inputpanel);

                testFrame.getContentPane().add(BorderLayout.CENTER, panel);
                testFrame.pack();
                testFrame.setLocationByPlatform(true);
                testFrame.setVisible(true);
                testFrame.setResizable(false);
            }

        });
    }



    //main applet window
    private void openEnterWindow(String password){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {

                attempts = 2;

                JFrame frame = new JFrame("Test Password");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);

                JLabel infoLabel = new JLabel("Enter your passowrd: ");

                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JPasswordField input = new JPasswordField(20);
                JButton enterButton = new JButton("Enter");


                    enterButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (attempts != 0) {
                                if (Arrays.equals(input.getPassword(), password.toCharArray())) {
                                    // password match!
                                    JOptionPane.showMessageDialog(frame, "Success! " + attempts + " attempts left");
                                    System.out.println("password match!");
                                    frame.dispose();
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Wrong Password try again " + attempts + " attempts left");
                                    System.out.println("wrong try again");
                                    input.setText("");
                                }
                            }
                            attempts--;
                        }

                    });

                panel.add(infoLabel);
                inputpanel.add(input);
                inputpanel.add(enterButton);
                panel.add(inputpanel);

                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);

                frame.setVisible(true);
                frame.setResizable(false);
            }

        });
    }

    public Scheme() {

        init();

        createEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //write to file create start event
                emailPassword = makePassword();
                openTestWindow(emailPassword);

                System.out.println(emailPassword);
            }
        });
        saveEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //final String
                if (!emailPassword.isEmpty()) {
                    
                    FINAL_EMAIL_PASSWORD = emailPassword;
                    System.out.println("Email password saved: " + FINAL_EMAIL_PASSWORD);
                    createEmailButton.setEnabled(false);
                }

            }
        });

        createBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankPassword = makePassword();
                openTestWindow(bankPassword);
            }
        });

        saveBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bankPassword.isEmpty()) {
                    FINAL_BANK_PASSWORD = bankPassword;
                    System.out.println("Bank password saved: " + FINAL_EMAIL_PASSWORD);
                    createBankButton.setEnabled(false);
                }
            }
        });

        createShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPassword = makePassword();
                openTestWindow(shopPassword);
            }
        });

        saveShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shopPassword.isEmpty()) {
                    FINAL_SHOP_PASSWORD = shopPassword;
                    System.out.println("Shop password saved: " + FINAL_EMAIL_PASSWORD);
                    createShopButton.setEnabled(false);
                }
            }
        });

        enterEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_EMAIL_PASSWORD.equals(null))
                    openEnterWindow(FINAL_EMAIL_PASSWORD);

            }
        });

        doneEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterEmailButton.setEnabled(false);
            }
        });

        enterBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_BANK_PASSWORD.isEmpty())
                    openEnterWindow(FINAL_BANK_PASSWORD);

            }
        });
        doneBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterBankButton.setEnabled(false);
            }
        });

        enterShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_SHOP_PASSWORD.isEmpty())
                    openEnterWindow(FINAL_SHOP_PASSWORD);

            }
        });
        doneShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doneShopButton.setEnabled(false);
            }
        });
    }
}
