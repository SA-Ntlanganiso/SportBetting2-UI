SportBetting-UI 🥊
A Java-based Sports Betting GUI application with robust features for MMA/UFC fight betting. This application allows users to select fighters, calculate odds, place wagers, and save betting slips.

https://via.placeholder.com/800x400?text=MMA+Betting+Demo+GIF

🚀 Features
Core Functionality
Fighter Selection System: Interactive GUI with 22 fighter buttons for UFC matchups

Real-time Odds Calculation: Dynamic odds calculation for selected fighters

Betting Slip Management: Save and manage betting slips as text files

Wager Calculation: Calculate potential winnings based on odds and wager amount

Database Integration: JDBC connectivity for fighter data persistence

Technical Features
Swing GUI: Modern, responsive user interface with color-coded elements

File I/O Operations: Read fighter data from text files, save betting slips

Data Validation: Input validation and error handling

Multi-threading: Synchronized data loading and processing

Object-Oriented Design: Clean separation of concerns with dedicated classes

📋 Prerequisites
Software Requirements
Java JDK 8 or higher

Apache Derby Database (for database functionality)

IDE with Swing support (NetBeans recommended)

Dependencies
JDBC Driver for Apache Derby

Java Swing libraries

Standard Java IO/NIO packages

🛠️ Installation & Setup
1. Clone or Download the Project
bash
git clone https://github.com/yourusername/SportBetting-UI.git
cd SportBetting-UI
2. Database Setup
Install Apache Derby

Create database schema:

sql
CREATE TABLE FIGHTERS (
    ranking INT PRIMARY KEY,
    fighterName VARCHAR(100),
    weightDivision VARCHAR(50),
    record VARCHAR(20),
    nickName VARCHAR(50),
    odds DECIMAL(5,2),
    nationality VARCHAR(100),
    DOB VARCHAR(20),
    age INT,
    height VARCHAR(20),
    weight VARCHAR(20),
    debute VARCHAR(20),
    winsViaKO VARCHAR(20),
    winsViaDec VARCHAR(20),
    winsViaSub VARCHAR(20)
);
3. Configuration
Create config.properties.txt in the classpath:

properties
db.url=jdbc:derby://localhost:1527/UFCDatabase
db.username=your_username
db.password=your_password
4. Fighter Data File
Place UFCRosterStats.txt in the project root with format:

text
#Ranking#Name#WeightClass#Gender#Record#Nickname#Odds#Nationality#BirthDate#Age#Height#Weight#Debut#KOWins#DecisionWins#SubmissionWins
1#Alex Pereira#LIGHT HEAVYWEIGHT#M#9-2-0#POATAN#1.65#Brazil#1987-07-07#36#6'4#204.0 lbs#2012-03-31#7#2#0
🎮 How to Use
Starting the Application
java
// Run the main class
java GutPickMMA.GutPicks
User Workflow
Load Fights: Click "Show Fights" to load all fighter data

Select Fighters: Click on fighter buttons to select winners (turns green)

Enter Wager: Input bet amount in the wager field

Calculate Odds: View automatically calculated total odds

Cash Out: Click "Cash Out" to see potential winnings

Save Slip: Click "BET" to save the betting slip

Clear All: Use "CLEAR" to reset selections

Color Coding System
Green: Selected fighter

Blue: Favorite fighter (lower odds)

Red: Underdog fighter (higher odds)

Gray: Unselected fighter

📁 Project Structure
text
GutPickMMA/
├── GutPicks.java              # Main GUI application class
├── RosterMetrics.java         # Data model for fighter metrics
├── MMADatabase.java           # Database connectivity layer
├── UFCRosterStats.txt         # Fighter data file
├── config.properties.txt      # Database configuration
├── BettingSlip.txt           # Generated betting slips
└── assets/
    └── charles-oliveira-ufc.gif  # Application GIF
🔧 Technical Architecture
Key Classes
1. GutPicks (Main GUI)
Responsibilities: GUI rendering, event handling, data coordination

Features: Button listeners, color coding, bet calculations

Pattern: MVC Controller component

2. RosterMetrics (Data Model)
Attributes: 16 fighter metrics including ranking, odds, record

Methods: Getters/setters, toString, data validation

Usage: Data transfer object between layers

3. MMADatabase (Persistence Layer)
Responsibilities: Database connectivity, CRUD operations

Features: Prepared statements, connection pooling, error handling

Pattern: DAO (Data Access Object)

Design Patterns Used
MVC (Model-View-Controller): Separation of GUI, data, and logic

Singleton: Database connection management

Observer: Event-driven button interactions

DAO: Abstract database operations

📊 Data Flow
text
Text File → RosterMetrics → GutPicks GUI → User Selection
    ↓                              ↓
Database ← MMADatabase ← Calculations → Betting Slip
💡 Key Algorithms
1. Odds Calculation
java
private void totalOddsActionPerformed(java.awt.event.ActionEvent evt) {
    oddsReference = 0.0; 
    for (int i = 0; i < buttonClicked.length; i++) {
        if (buttonClicked[i] && i < ufcRoster.size()) {
            oddsReference += ufcRoster.get(i).getOdds(); 
        }
    }
    totalOdds.setText(String.format("%.2f", oddsReference));
}
2. Fighter Selection Toggle
java
private void toggleFighterSelection(int index) {
    if (index < buttonClicked.length) {
        buttonClicked[index] = !buttonClicked[index];
        // Visual feedback: green for selected, gray for deselected
    }
}
3. Data Tokenization
java
public ArrayList<String> tokenization(String str) {
    ArrayList<String> tokens = new ArrayList<>();
    String[] words = str.split("\\s+");
    return tokens;
}
🎨 GUI Components
Main Sections
Fight Card Display: 11 fight matchups with "vs" separators

Betting Slip Panel: Right-side panel for wager management

Odds Display: Color-coded odds buttons for each fighter

Control Buttons: Show Fights, BET, CLEAR, Cash Out

Interactive Elements
22 Fighter Buttons: Click to select/deselect

22 Odds Buttons: Display live odds with color coding

Text Area: Show selected fighter details

Input Fields: Wager amount and total odds display

🔒 Error Handling
Implemented Safeguards
File I/O Errors: Try-catch with user-friendly messages

Database Errors: Connection validation and fallbacks

Input Validation: Number format checking for wagers

Bounds Checking: Array index validation

Concurrency: Synchronized data access methods

Example Error Handling
java
try {
    // Database operation
} catch (SQLException e) {
    System.err.println("Database error: " + e.getMessage());
    JOptionPane.showMessageDialog(this, "Database connection failed", 
        "Error", JOptionPane.ERROR_MESSAGE);
}
📈 Performance Optimizations
Memory Management
Lazy Loading: Data loaded only when needed

Object Pooling: Database connection reuse

Efficient Collections: ArrayList for dynamic data, HashSet for uniqueness

UI Performance
Event-Driven Updates: Only update changed components

Background Loading: Data loading in separate threads

Minimal Repaints: Batch UI updates

🧪 Testing Features
Built-in Testing Capabilities
Data Integrity: Validates fighter data format on load

Calculation Verification: Manual odds calculation check

File System Access: Verifies read/write permissions

Database Connectivity: Connection test on startup

🔄 Future Enhancements
Planned Features
Live Odds Updates: Real-time odds from API

User Accounts: Login system with bet history

Multiple Sports: Expand beyond MMA

Advanced Analytics: Betting trends and statistics

Mobile Version: Android/iOS compatibility

Technical Improvements
Unit Tests: JUnit test coverage

Logging Framework: Structured logging with Log4j

Configuration GUI: Visual database setup

Internationalization: Multi-language support

🤝 Contributing
Development Setup
Fork the repository

Create feature branch

Commit changes

Push to branch

Create Pull Request

Coding Standards
Follow Java naming conventions

Add Javadoc comments for public methods

Write unit tests for new features

Update README for significant changes

📄 License
This project is licensed under the MIT License - see the LICENSE file for details.

👏 Acknowledgements
UFC for fighter data inspiration

Java Swing community for GUI best practices

Apache Derby for lightweight database solution

All contributors and testers

Note: This application is for educational purposes only. Gambling should be done responsibly and in accordance with local laws.
