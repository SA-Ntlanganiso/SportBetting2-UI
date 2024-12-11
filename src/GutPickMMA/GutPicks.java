
package GutPickMMA;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import static java.lang.ProcessBuilder.Redirect.Type.READ;
import static java.nio.file.AccessMode.EXECUTE;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

public class GutPicks extends javax.swing.JFrame {

    private ArrayList<RosterMetrics> ufcRoster = new ArrayList<RosterMetrics>();
    private Set<String> appendedFighterDetails = new HashSet<>();
    private ArrayList<String> token = new ArrayList<String>();
    private Set<JButton> alreadyClicked = new HashSet();
    private JButton[] buttons = new JButton[22];
    private JButton[] oddsButtons = new JButton[22];
    private boolean[] buttonClicked = new boolean[22]; 
    private double oddsReference = 0.0;
    private double wagerReference = 0.0;
    private double potentialWinReference = 0.0;
    private static int toggleCount = 0;
    private static MMADatabase mmaDb;
    
    public GutPicks() {
        initComponents();
        showFights = new JButton("Show Fights");
        loadConfig(); // Ensure mmaDb is initialized before calling loadData
        loadData();
        setupButtonListeners();
    }
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties.txt")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            mmaDb = new MMADatabase(username,password,url);
            System.out.println("Database iinstantiated successfully");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private synchronized void loadData() {
    try (BufferedReader br = new BufferedReader(new FileReader("UFCRosterStats.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("#");
            if (parts.length >= 16) {
                RosterMetrics fighter = new RosterMetrics(
                    
                    Integer.parseInt(parts[0]), // Ranking
                    parts[1], // Name
                    parts[2], // weightclass
                    parts[3].charAt(0), // Gender
                    parts[4], // record
                    parts[5], // nickname
                    Double.parseDouble(parts[6]), // odds
                    parts[7], // nationality
                    parts[8], // birthYear
                    Integer.parseInt(parts[9]), // age
                    parts[10], // height
                    parts[11], // weight
                    parts[12], // sartdate
                    parts[13], // win vai ko
                    parts[14], // win via decisons
                    parts[15] // Submissions
                );
                ufcRoster.add(fighter);
                
                // populating the database
                String sql = "INSERT INTO FIGHTERS(ranking, fighterName, weightDivison, record, nickName, odds, nationality, DOB, age, height, weight, debute, winsViaKO, winsViaDec, winsViaSub) \n" +
"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                if(mmaDb.addFighter(fighter, sql)){
                    System.out.println("New figher add successfully.");
                }
            }
        }       
            btnForfighter1.setText("loading..");
            btnForfighter2.setText("loading...");
            btnForfighter2.setText("Loading...");
            btnForfighter3.setText("Loading...");
            btnForfighter4.setText("Loading...");
            btnForfighter5.setText("Loading...");
            btnForfighter6.setText("Loading...");
            btnForfighter7.setText("Loading...");
            btnForfighter8.setText("Loading...");
            btnForfighter9.setText("Loading...");
            btnForfighter10.setText("Loading...");
            btnForfighter11.setText("Loading...");
            btnForfighter12.setText("Loading...");
            btnForfighter13.setText("Loading...");
            btnForfighter14.setText("Loading...");
            btnForfighter15.setText("Loading...");
            btnForfighter16.setText("Loading...");
            btnForfighter17.setText("Loading...");
            btnForfighter18.setText("Loading...");
            btnForfighter19.setText("Loading...");
            btnForfighter20.setText("Loading...");
            btnForfighter21.setText("Loading...");
            btnForfighter22.setText("Loading...");
            var1.setText("...");
            var11.setText("...");
            var12.setText("...");
            var13.setText("...");
            var14.setText("...");
            var15.setText("...");
            var16.setText("...");
            var10.setText("...");
            var18.setText("...");
            var19.setText("...");
            var2.setText("...");
            var20.setText("...");
            var17.setText("...");
            var22.setText("...");
            var23.setText("...");
            var3.setText("...");
            var4.setText("...");
            var5.setText("...");
            var6.setText("...");
            var7.setText("...");
            var8.setText("...");
            var9.setText("...");
        
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Failed to read the fighters data file.\n" + e.getMessage(), "File Reading Error", JOptionPane.ERROR_MESSAGE);
        btnForfighter1.setText("Error loading data");
        btnForfighter2.setText("Error loading data");
    }
}
    private void setupButtonListeners() {
        JButton[] buttons = {
            btnForfighter1, btnForfighter2, btnForfighter3, btnForfighter4, btnForfighter5,
            btnForfighter6, btnForfighter7, btnForfighter8, btnForfighter9, btnForfighter10,
            btnForfighter11, btnForfighter12, btnForfighter13, btnForfighter14, btnForfighter15,
            btnForfighter16, btnForfighter17, btnForfighter18, btnForfighter19, btnForfighter20,
            btnForfighter21, btnForfighter22
        };
         
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    toggleFighterSelection(index);
                    alreadyClicked.add(buttons[index]);
                    toggleCount++;
                    if(toggleCount == 1 ){
                        buttons[index].setBackground(Color.green);
                        
                    }else if(alreadyClicked.contains(buttons[index]) && toggleCount > 1 ){
                       buttons[index].setBackground(Color.LIGHT_GRAY);
                       toggleCount = 0;
                    }
                    updateTextArea(index);
                    totalOddsActionPerformed(e);
                }
            });
        }
    }
    private synchronized void updateTextArea(int index) {
        if (index >= ufcRoster.size()) {
            jTextArea1.append("Fighter data unavailable.\n\n");
            return;
        }
        RosterMetrics fighter = ufcRoster.get(index);
        String fighterDetails = "Fighter Details:\n" +
                                "Name: " + fighter.getFighterName() + "\t" +
                                "Odds: " + fighter.getOdds() + "\n" +
                                "Record: " + fighter.getFighterRecord() + "\n" +
                                "Date of birth: " + fighter.getDateOfBirth() + "\n" +
                                "Age : " + fighter.getAge() + "\n" +
                                "Nationality: " + fighter.getPlaceOfBirth() + "\n\n";

        // Check if the fighter details are already appended
        if (!appendedFighterDetails.contains(fighterDetails)) {
            jTextArea1.append(fighterDetails);
            appendedFighterDetails.add(fighterDetails);
        } else {
            System.out.println("Fighter details already added.");
        }

        try {
            // Tokenize the string if it's not empty
            if (!jTextArea1.getText().isEmpty()) {
                String str = jTextArea1.getText();
                ArrayList<String> chunk = tokenization(str);
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> tokenization(String str) {
        ArrayList<String> tokens = new ArrayList<>();
        String[] words = str.split("\\s+"); // Split the string into words based on whitespace
        for (String word : words) {
            tokens.add(word);
        }
        return tokens;
    }
/*
    public void removeduplicateSelections(String str ,ArrayList<String> chunk){
        String[] parts = str.split("");
        for(String textfieldContent :parts){
            if(){
            }
        } 
    }*/
    private void toggleFighterSelection(int index) {
        if (index < buttonClicked.length) {
            buttonClicked[index] = !buttonClicked[index] ;// Toggle the selection state
            
            
        }
    }
    private void filelAccess(){
        Path path = Paths.get("C:\\Users\\asina\\Documents\\NetBeansProjects\\MMATestUnit\\build\\classes\\GutPickMMA");
        System.out.println("Path is "+ path.toString());
        
        try{
            path.getFileSystem().provider().checkAccess(path, AccessMode.READ,AccessMode.EXECUTE);
        }catch(IOException ex){
            System.out.println("This file is not accessable!");
        }
    }
    /**-+ 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        picksBoard = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnForfighter1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnForfighter2 = new javax.swing.JButton();
        showFights = new javax.swing.JButton();
        btnForfighter3 = new javax.swing.JButton();
        btnForfighter4 = new javax.swing.JButton();
        btnForfighter5 = new javax.swing.JButton();
        btnForfighter6 = new javax.swing.JButton();
        btnForfighter7 = new javax.swing.JButton();
        btnForfighter8 = new javax.swing.JButton();
        btnForfighter9 = new javax.swing.JButton();
        btnForfighter10 = new javax.swing.JButton();
        btnForfighter11 = new javax.swing.JButton();
        btnForfighter12 = new javax.swing.JButton();
        btnForfighter13 = new javax.swing.JButton();
        btnForfighter14 = new javax.swing.JButton();
        btnForfighter15 = new javax.swing.JButton();
        btnForfighter16 = new javax.swing.JButton();
        btnForfighter17 = new javax.swing.JButton();
        btnForfighter18 = new javax.swing.JButton();
        btnForfighter19 = new javax.swing.JButton();
        btnForfighter20 = new javax.swing.JButton();
        btnForfighter21 = new javax.swing.JButton();
        btnForfighter22 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalOdds = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        wagerAmount = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnclear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        var1 = new javax.swing.JButton();
        var2 = new javax.swing.JButton();
        var3 = new javax.swing.JButton();
        var4 = new javax.swing.JButton();
        var5 = new javax.swing.JButton();
        var6 = new javax.swing.JButton();
        var7 = new javax.swing.JButton();
        var8 = new javax.swing.JButton();
        var9 = new javax.swing.JButton();
        var11 = new javax.swing.JButton();
        var12 = new javax.swing.JButton();
        var13 = new javax.swing.JButton();
        var14 = new javax.swing.JButton();
        var15 = new javax.swing.JButton();
        var16 = new javax.swing.JButton();
        var10 = new javax.swing.JButton();
        var18 = new javax.swing.JButton();
        var19 = new javax.swing.JButton();
        var20 = new javax.swing.JButton();
        var17 = new javax.swing.JButton();
        var22 = new javax.swing.JButton();
        var23 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        picksBoard.setOpaque(true);

        jLabel2.setFont(new java.awt.Font("Franklin Gothic Medium Cond", 3, 36)); // NOI18N
        jLabel2.setText("GutPICKS.bet");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial Narrow", 3, 14)); // NOI18N
        jLabel3.setText("MMA");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 0));
        jLabel9.setText("UFC 305 FIGHT CARD");

        btnForfighter1.setText("loading...");
        btnForfighter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 0));
        jLabel13.setText("vs");

        btnForfighter2.setText("loading...");
        btnForfighter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter2ActionPerformed(evt);
            }
        });

        showFights.setBackground(new java.awt.Color(51, 153, 255));
        showFights.setText("Show Fights");
        showFights.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showFightsActionPerformed(evt);
            }
        });

        btnForfighter3.setText("loading...");
        btnForfighter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter3ActionPerformed(evt);
            }
        });

        btnForfighter4.setText("loading...");
        btnForfighter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter4ActionPerformed(evt);
            }
        });

        btnForfighter5.setText("loading...");
        btnForfighter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter5ActionPerformed(evt);
            }
        });

        btnForfighter6.setText("loading...");
        btnForfighter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter6ActionPerformed(evt);
            }
        });

        btnForfighter7.setText("loading...");
        btnForfighter7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter7ActionPerformed(evt);
            }
        });

        btnForfighter8.setText("loading...");
        btnForfighter8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter8ActionPerformed(evt);
            }
        });

        btnForfighter9.setText("loading...");
        btnForfighter9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter9ActionPerformed(evt);
            }
        });

        btnForfighter10.setText("loading...");
        btnForfighter10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter10ActionPerformed(evt);
            }
        });

        btnForfighter11.setText("loading...");
        btnForfighter11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter11ActionPerformed(evt);
            }
        });

        btnForfighter12.setText("loading...");
        btnForfighter12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter12ActionPerformed(evt);
            }
        });

        btnForfighter13.setText("loading...");
        btnForfighter13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter13ActionPerformed(evt);
            }
        });

        btnForfighter14.setText("loading...");
        btnForfighter14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter14ActionPerformed(evt);
            }
        });

        btnForfighter15.setText("loading...");
        btnForfighter15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter15ActionPerformed(evt);
            }
        });

        btnForfighter16.setText("loading...");
        btnForfighter16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter16ActionPerformed(evt);
            }
        });

        btnForfighter17.setText("loading...");
        btnForfighter17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter17ActionPerformed(evt);
            }
        });

        btnForfighter18.setText("loading...");
        btnForfighter18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter18ActionPerformed(evt);
            }
        });

        btnForfighter19.setText("loading...");
        btnForfighter19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter19ActionPerformed(evt);
            }
        });

        btnForfighter20.setText("loading...");
        btnForfighter20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter20ActionPerformed(evt);
            }
        });

        btnForfighter21.setText("loading...");
        btnForfighter21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter21ActionPerformed(evt);
            }
        });

        btnForfighter22.setText("loading...");
        btnForfighter22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForfighter22ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("  BETTING SLIP");
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 0), new java.awt.Color(204, 204, 204)), "bet"));

        totalOdds.setText("Odds:");
        totalOdds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalOddsActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("ACTUAL WIN RETURN");

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("ENTER WAGER AMOUNT");

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("TOTAL ODDS");

        wagerAmount.setText("R 0.00");
        wagerAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wagerAmountActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(102, 102, 102));
        btnSave.setText("BET");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnclear.setBackground(new java.awt.Color(102, 102, 102));
        btnclear.setText("CLEAR");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Cash Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(90, 90, 90)
                        .addComponent(totalOdds, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnclear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(118, 118, 118)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 14, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(90, 90, 90)
                        .addComponent(wagerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wagerAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave))
                .addGap(18, 18, 18)
                .addComponent(btnclear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalOdds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(24, 24, 24))
        );

        jLabel14.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 0));
        jLabel14.setText("vs");

        jLabel15.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 0));
        jLabel15.setText("vs");

        jLabel16.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 0));
        jLabel16.setText("vs");

        jLabel17.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 0));
        jLabel17.setText("vs");

        jLabel18.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 153, 0));
        jLabel18.setText("vs");

        jLabel19.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 0));
        jLabel19.setText("vs");

        jLabel20.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 0));
        jLabel20.setText("vs");

        jLabel21.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 0));
        jLabel21.setText("vs");

        jLabel22.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 0));
        jLabel22.setText("vs");

        jLabel23.setFont(new java.awt.Font("MV Boli", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 0));
        jLabel23.setText("vs");

        var1.setText("odds...");
        var1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                var1ActionPerformed(evt);
            }
        });

        var2.setText("odds...");

        var3.setText("odds...");

        var4.setText("odds...");

        var5.setText("odds...");

        var6.setText("odds...");

        var7.setText("odds...");

        var8.setText("odds...");

        var9.setText("odds...");

        var11.setText("odds...");
        var11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                var11ActionPerformed(evt);
            }
        });

        var12.setText("odds...");

        var13.setText("odds...");

        var14.setText("odds...");

        var15.setText("odds...");

        var16.setText("odds...");

        var10.setText("odds...");

        var18.setText("odds...");

        var19.setText("odds...");

        var20.setText("odds...");

        var17.setText("odds...");

        var22.setText("odds...");

        var23.setText("odds...");

        picksBoard.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(showFights, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(btnForfighter22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel21, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(jLabel23, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var10, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var19, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var20, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var22, javax.swing.JLayeredPane.DEFAULT_LAYER);
        picksBoard.setLayer(var23, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picksBoardLayout = new javax.swing.GroupLayout(picksBoard);
        picksBoard.setLayout(picksBoardLayout);
        picksBoardLayout.setHorizontalGroup(
            picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picksBoardLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(var11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(var2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(var1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(var12, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(picksBoardLayout.createSequentialGroup()
                        .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(picksBoardLayout.createSequentialGroup()
                                .addComponent(showFights, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(picksBoardLayout.createSequentialGroup()
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter21, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter22, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter19, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter20, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter17, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter18, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter15, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter16, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter13, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter14, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter11, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnForfighter12, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter9, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter10, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter7, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter8, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter6, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnForfighter4, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(picksBoardLayout.createSequentialGroup()
                                        .addComponent(btnForfighter1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnForfighter2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(var22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(var13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(var23, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(93, 93, 93)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(picksBoardLayout.createSequentialGroup()
                .addGap(388, 388, 388)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        picksBoardLayout.setVerticalGroup(
            picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picksBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(picksBoardLayout.createSequentialGroup()
                        .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(picksBoardLayout.createSequentialGroup()
                                .addComponent(var13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(var14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(var15)
                                .addGap(18, 18, 18)
                                .addComponent(var16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(var10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(var18)
                                .addGap(18, 18, 18)
                                .addComponent(var19)
                                .addGap(18, 18, 18)
                                .addComponent(var20)
                                .addGap(18, 18, 18)
                                .addComponent(var17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(var22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(var23))
                            .addGroup(picksBoardLayout.createSequentialGroup()
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter1)
                                    .addComponent(jLabel13)
                                    .addComponent(btnForfighter2)
                                    .addComponent(var1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnForfighter3)
                                        .addComponent(btnForfighter4)
                                        .addComponent(jLabel14))
                                    .addComponent(var2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter5)
                                    .addComponent(btnForfighter6)
                                    .addComponent(jLabel15)
                                    .addComponent(var3))
                                .addGap(18, 18, 18)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnForfighter7)
                                        .addComponent(var4))
                                    .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnForfighter8)
                                        .addComponent(jLabel16)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter9)
                                    .addComponent(btnForfighter10)
                                    .addComponent(jLabel17)
                                    .addComponent(var5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter11)
                                    .addComponent(btnForfighter12)
                                    .addComponent(jLabel18)
                                    .addComponent(var6))
                                .addGap(18, 18, 18)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter13)
                                    .addComponent(btnForfighter14)
                                    .addComponent(jLabel19)
                                    .addComponent(var7))
                                .addGap(18, 18, 18)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter15)
                                    .addComponent(btnForfighter16)
                                    .addComponent(jLabel20)
                                    .addComponent(var8))
                                .addGap(18, 18, 18)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter17)
                                    .addComponent(btnForfighter18)
                                    .addComponent(jLabel21)
                                    .addComponent(var9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter19)
                                    .addComponent(btnForfighter20)
                                    .addComponent(jLabel22)
                                    .addComponent(var11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(picksBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnForfighter21)
                                    .addComponent(btnForfighter22)
                                    .addComponent(jLabel23)
                                    .addComponent(var12))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(showFights))
                    .addGroup(picksBoardLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(picksBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(picksBoard)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnForfighter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter1ActionPerformed
        
    }//GEN-LAST:event_btnForfighter1ActionPerformed

    private void btnForfighter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter2ActionPerformed

    private void showFightsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showFightsActionPerformed
        
        try{
            JButton[] oddsButtons = {
                var1,var13,var2,var14,var3,var15,
                var4,var16,var5,var10,var6,var18,
                var7,var19,var8,var20,var9,
                var17,var11,var22,var12,var23
            };
            
            for (int j = 0; j < oddsButtons.length - 1; j++) { // Prevent j + 1 from exceeding bounds
                try {
                    oddsButtons[j].setText(String.valueOf(ufcRoster.get(j).getOdds()));
                    oddsButtons[j + 1].setText(String.valueOf(ufcRoster.get(j + 1).getOdds()));

                    if (ufcRoster.get(j).getOdds() > ufcRoster.get(j + 1).getOdds()) {
                        oddsButtons[j].setBackground(Color.GRAY);
                        oddsButtons[j + 1].setBackground(Color.BLUE);
                    } else if (ufcRoster.get(j).getOdds() == ufcRoster.get(j + 1).getOdds()) {
                        oddsButtons[j].setBackground(Color.BLUE);
                        oddsButtons[j + 1].setBackground(Color.BLUE);
                    } else {
                        oddsButtons[j].setBackground(Color.BLUE);
                        oddsButtons[j + 1].setBackground(Color.RED);
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Index out of bounds: " + e.getMessage());
                }
            }

        }catch(IndexOutOfBoundsException e){ //Still don't know why this is out of bound??
            System.out.println(e.getMessage());
        } 
        catch(ExceptionInInitializerError e){
            System.out.println(e.getMessage());
        }
        if (ufcRoster.size() >= 2) {
        // Set text to fighter names
        btnForfighter1.setText(ufcRoster.get(0).getFighterName());
        btnForfighter2.setText(ufcRoster.get(1).getFighterName());
        btnForfighter3.setText(ufcRoster.get(2).getFighterName());
        btnForfighter4.setText(ufcRoster.get(3).getFighterName());
        btnForfighter5.setText(ufcRoster.get(4).getFighterName());
        btnForfighter6.setText(ufcRoster.get(5).getFighterName());
        btnForfighter7.setText(ufcRoster.get(6).getFighterName());
        btnForfighter8.setText(ufcRoster.get(7).getFighterName());
        btnForfighter9.setText(ufcRoster.get(8).getFighterName());
        btnForfighter10.setText(ufcRoster.get(9).getFighterName());
        btnForfighter11.setText(ufcRoster.get(10).getFighterName());
        btnForfighter12.setText(ufcRoster.get(11).getFighterName());
        btnForfighter13.setText(ufcRoster.get(12).getFighterName());
        btnForfighter14.setText(ufcRoster.get(13).getFighterName());
        btnForfighter15.setText(ufcRoster.get(14).getFighterName());
        btnForfighter16.setText(ufcRoster.get(15).getFighterName());
        btnForfighter17.setText(ufcRoster.get(16).getFighterName());
        btnForfighter18.setText(ufcRoster.get(17).getFighterName());
        btnForfighter19.setText(ufcRoster.get(18).getFighterName());
        btnForfighter20.setText(ufcRoster.get(19).getFighterName());
        btnForfighter21.setText(ufcRoster.get(20).getFighterName());
        btnForfighter22.setText(ufcRoster.get(21).getFighterName());
        
        
              
        } else {
            // Handle cases where not enough fighters are loaded
            if (ufcRoster.isEmpty()) {
                btnForfighter1.setText("No fighters loaded");
                btnForfighter2.setText("No fighters loaded");
            } else if (ufcRoster.size() == 1) {
                btnForfighter1.setText(ufcRoster.get(0).getFighterName());
                btnForfighter2.setText("Missing fighter data");
            }
    }

    }//GEN-LAST:event_showFightsActionPerformed

    private void btnForfighter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter3ActionPerformed

    private void btnForfighter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter4ActionPerformed

    private void btnForfighter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter5ActionPerformed

    private void btnForfighter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter6ActionPerformed

    private void btnForfighter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter7ActionPerformed

    private void btnForfighter8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter8ActionPerformed

    private void btnForfighter9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter9ActionPerformed

    private void btnForfighter10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter10ActionPerformed

    private void btnForfighter11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter11ActionPerformed

    private void btnForfighter12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter12ActionPerformed

    private void btnForfighter13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter13ActionPerformed

    private void btnForfighter14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter14ActionPerformed

    private void btnForfighter15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter15ActionPerformed

    private void btnForfighter16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter16ActionPerformed

    private void btnForfighter17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter17ActionPerformed

    private void btnForfighter18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter18ActionPerformed

    private void btnForfighter19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter19ActionPerformed

    private void btnForfighter20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter20ActionPerformed

    private void btnForfighter21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter21ActionPerformed

    private void btnForfighter22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForfighter22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnForfighter22ActionPerformed

    private void totalOddsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalOddsActionPerformed
        oddsReference = 0.0; 
        for (int i = 0; i < buttonClicked.length; i++) {
            if (buttonClicked[i] && i < ufcRoster.size()) {
                oddsReference += ufcRoster.get(i).getOdds(); 
            }
        }
        String formattedOdds = String.format("%.2f", oddsReference);
        totalOdds.setText(oddsReference + "\n");

    }//GEN-LAST:event_totalOddsActionPerformed

    private void wagerAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wagerAmountActionPerformed
        wagerReference = Double.parseDouble(wagerAmount.getText());
    }//GEN-LAST:event_wagerAmountActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed

        jTextArea1.setText("");
        totalOdds.setText("0");
        wagerAmount.setText("0");
        for(int i = 0; i < buttons.length;i++){
            buttons[i].setBackground(Color.lightGray);
        }
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        File file = new File("BettingSlip.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(jTextArea1.getText());
            JOptionPane.showMessageDialog(this, "File was saved successfully to " + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try {
        String oddsText = totalOdds.getText();
        double totalOdds = Double.parseDouble(oddsText);
        potentialWinReference = wagerReference * totalOdds; 
        
        JOptionPane.showMessageDialog(this,"Potential Win: R" + String.format("%.2f", potentialWinReference));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please ensure you enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void var1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_var1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_var1ActionPerformed

    private void var11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_var11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_var11ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
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
            java.util.logging.Logger.getLogger(GutPicks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GutPicks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GutPicks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GutPicks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GutPicks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnForfighter1;
    private javax.swing.JButton btnForfighter10;
    private javax.swing.JButton btnForfighter11;
    private javax.swing.JButton btnForfighter12;
    private javax.swing.JButton btnForfighter13;
    private javax.swing.JButton btnForfighter14;
    private javax.swing.JButton btnForfighter15;
    private javax.swing.JButton btnForfighter16;
    private javax.swing.JButton btnForfighter17;
    private javax.swing.JButton btnForfighter18;
    private javax.swing.JButton btnForfighter19;
    private javax.swing.JButton btnForfighter2;
    private javax.swing.JButton btnForfighter20;
    private javax.swing.JButton btnForfighter21;
    private javax.swing.JButton btnForfighter22;
    private javax.swing.JButton btnForfighter3;
    private javax.swing.JButton btnForfighter4;
    private javax.swing.JButton btnForfighter5;
    private javax.swing.JButton btnForfighter6;
    private javax.swing.JButton btnForfighter7;
    private javax.swing.JButton btnForfighter8;
    private javax.swing.JButton btnForfighter9;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLayeredPane picksBoard;
    private javax.swing.JButton showFights;
    private javax.swing.JTextField totalOdds;
    private javax.swing.JButton var1;
    private javax.swing.JButton var10;
    private javax.swing.JButton var11;
    private javax.swing.JButton var12;
    private javax.swing.JButton var13;
    private javax.swing.JButton var14;
    private javax.swing.JButton var15;
    private javax.swing.JButton var16;
    private javax.swing.JButton var17;
    private javax.swing.JButton var18;
    private javax.swing.JButton var19;
    private javax.swing.JButton var2;
    private javax.swing.JButton var20;
    private javax.swing.JButton var22;
    private javax.swing.JButton var23;
    private javax.swing.JButton var3;
    private javax.swing.JButton var4;
    private javax.swing.JButton var5;
    private javax.swing.JButton var6;
    private javax.swing.JButton var7;
    private javax.swing.JButton var8;
    private javax.swing.JButton var9;
    private javax.swing.JTextField wagerAmount;
    // End of variables declaration//GEN-END:variables
}
