import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Date;
import java.sql.Timestamp;

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

    private String emailPassword = "";
    private String bankPassword = "";
    private String shopPassword = "";

    private static String FINAL_EMAIL_PASSWORD;
    private static String FINAL_BANK_PASSWORD;
    private static String FINAL_SHOP_PASSWORD;

    private int attempts;

    private boolean isEmail = false;
    private boolean isBank = false;
    private boolean isShop = false;

    Random rand = new Random();
    int num = rand.nextInt(100000);
    private String user = "user" + Integer.toString(num);

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
                                        "-", "~", "/", "{", "}", "*",
                                        "(", "<",">", "|", ")", "^"};

    private Date date = new Date();
    private Timestamp ts = new Timestamp(date.getTime());

    String directory = "/Users/mattah/Desktop";
    //String directory = "C:\\Users\\micha\\Documents\\COMP3008\\PasswordScheme";
    String fileName = "sample.txt";
    String absolutePath = directory + File.separator + fileName;



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

    private void fileWriter(String event){
        String scheme = "";
        String fileContent = "";
        if(isEmail){
            scheme+= "Email";
        }
        if(isBank){
            scheme+= "Bank";
        }
        if(isShop){
            scheme+= "Shop";
        }

        if(event.equals("practiceGood")){
            fileContent = ts + " " + user + " " + scheme + " " + "practice good" + " ";
        }
        if(event.equals("practiceBad")){
            fileContent = ts + " " + user + " " + scheme + " " + "practice bad" + " ";
        }
        if(event.equals("createStart")){
            fileContent = ts + " " + user + " " + scheme + " " + "create start" + " ";
        }
        if(event.equals("createSubmit")){
            fileContent = ts + " " + user + " " + scheme + " " + "create submit" + " ";
        }
        if(event.equals("pwSave")){
            fileContent = ts + " " + user + " " + scheme + " " + "password save" + " ";
        }
        if(event.equals("loginSuccess")){
            fileContent = ts + " " + user + " " + scheme + " " + "login success" + " ";
        }
        if(event.equals("loginFailure")){
            fileContent = ts + " " + user + " " + scheme + " " + "login failure" + " ";
        }

        try(FileWriter fileWriter = new FileWriter(absolutePath, true)) {
            fileWriter.write(fileContent);
            fileWriter.write("\r\n");
        } catch (IOException err) {
            // exception handling
            System.out.print("Error in writing file: " + err);
        }

    }

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
                fileWriter("createStart");
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
                        //write timestamp, user, password submit
                        testFrame.dispose();
                        fileWriter("createSubmit");
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
                            fileWriter("practiceGood");
                            JOptionPane.showMessageDialog(testFrame, "Correct Password");
                            System.out.println("password match!");
                        }
                        else {
                            JOptionPane.showMessageDialog(testFrame, "Wrong Password try again");
                            //log password practice bad
                            fileWriter("practiceBad");
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

                attempts = 3;

                JFrame frame = new JFrame("Test Password");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);

                JLabel infoLabel = new JLabel("Enter your password: ");

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
                                    //write to file with timestamp, user, password submitted
                                    fileWriter("loginSuccess");
                                    frame.dispose();
                                } else {
                                    attempts--;
                                    if(attempts != 0) {
                                        JOptionPane.showMessageDialog(frame, "Wrong Password try again " + attempts + " attempts left");
                                        System.out.println("wrong try again");
                                        //write to file with timestamp, user, practice bad
                                        input.setText("");
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(frame,"Failed to enter password within 3 attempts");
                                    }
                                }

                            }

                            if(attempts == 0) {
                                frame.dispose();
                                if (isEmail) {
                                    enterEmailButton.setEnabled(false);
                                }
                                if (isBank) {
                                    enterBankButton.setEnabled(false);
                                }
                                if (isShop) {
                                    enterShopButton.setEnabled(false);
                                }
                                fileWriter("loginFailure");
                            }
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
                isEmail = true;
                isBank = false;
                isShop = false;
                openTestWindow(emailPassword);

                System.out.println(emailPassword);
            }
        });
        saveEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //final String
                if (!emailPassword.isEmpty()) {
                    isEmail = true;
                    isBank = false;
                    isShop = false;
                    fileWriter("pwSave");
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
                isEmail = false;
                isBank = true;
                isShop = false;
                openTestWindow(bankPassword);
            }
        });

        saveBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bankPassword.isEmpty()) {
                    isEmail = false;
                    isBank = true;
                    isShop = false;
                    fileWriter("pwSave");
                    FINAL_BANK_PASSWORD = bankPassword;
                    System.out.println("Bank password saved: " + FINAL_BANK_PASSWORD);
                    createBankButton.setEnabled(false);
                }
            }
        });

        createShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPassword = makePassword();
                isEmail = false;
                isBank = false;
                isShop = true;
                openTestWindow(shopPassword);
            }
        });

        saveShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!shopPassword.isEmpty()) {
                    isEmail = false;
                    isBank = false;
                    isShop = true;
                    fileWriter("pwSave");
                    FINAL_SHOP_PASSWORD = shopPassword;
                    System.out.println("Shop password saved: " + FINAL_SHOP_PASSWORD);
                    createShopButton.setEnabled(false);
                }
            }
        });

        enterEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_EMAIL_PASSWORD.equals(null)) {
                    isEmail = true;
                    isBank = false;
                    isShop = false;
                    openEnterWindow(FINAL_EMAIL_PASSWORD);
                }

            }
        });

        doneEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEmail = true;
                isBank = false;
                isShop = false;
                enterEmailButton.setEnabled(false);
            }
        });

        enterBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_BANK_PASSWORD.isEmpty()) {
                    isEmail = false;
                    isBank = true;
                    isShop = false;
                    openEnterWindow(FINAL_BANK_PASSWORD);
                }
            }
        });
        doneBankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEmail = false;
                isBank = true;
                isShop = false;
                enterBankButton.setEnabled(false);
            }
        });

        enterShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!FINAL_SHOP_PASSWORD.isEmpty())
                    isEmail = false;
                    isBank = false;
                    isShop = true;
                    openEnterWindow(FINAL_SHOP_PASSWORD);
            }
        });
        doneShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEmail = false;
                isBank = false;
                isShop = true;
                enterShopButton.setEnabled(false);
            }
        });
    }
}
