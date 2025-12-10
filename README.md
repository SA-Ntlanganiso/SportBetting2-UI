# 🥊 SportBetting-UI

A Java-based Sports Betting GUI application with robust features for MMA/UFC fight predictions and betting slip management.

![Charles Oliveira Sport GIF by UFC](https://media.giphy.com/media/3o7TKF1fSIs1R19B8k/giphy.gif)

---

## 📋 Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Technical Architecture](#technical-architecture)
- [Setup Instructions](#setup-instructions)
- [Usage Guide](#usage-guide)
- [Database Schema](#database-schema)
- [Technologies Used](#technologies-used)

---

## 🎯 Overview

**GutPICKS.bet** is a desktop application that simulates a sports betting platform focused on UFC/MMA fights. The application reads fighter data from text files, displays match-ups with real-time odds, and allows users to create betting slips with automatic calculation of potential winnings.

---

## ✨ Key Features

### 🎮 Interactive Fight Selection
- **22 Fighter Buttons** - Select fighters from 11 different match-ups
- **Visual Feedback** - Selected fighters turn green, unselected remain gray
- **Real-time Odds Display** - Color-coded odds buttons (Blue for favorites, Gray/Red for underdogs)

### 💰 Betting Slip Management
- **Automatic Odds Calculation** - Parlay odds computed in real-time
- **Wager Input** - Enter custom bet amounts
- **Potential Winnings Calculator** - "Cash Out" button shows potential returns
- **Save Functionality** - Export betting slips to text files
- **Clear Function** - Reset all selections and start fresh

### 📊 Fighter Information Display
- Comprehensive fighter stats including:
  - Name and nickname
  - Fight record (W-L-D)
  - Betting odds
  - Date of birth and age
  - Nationality
  - Weight class

### 🗄️ Database Integration
- **Apache Derby Database** - Persistent storage of fighter data
- **CRUD Operations** - Add fighters to database dynamically
- **Configuration Management** - Properties file for database credentials

---

## 🏗️ Technical Architecture

```
SportBetting-UI/
├── src/
│   └── GutPickMMA/
│       ├── GutPicks.java         # Main GUI application
│       ├── RosterMetrics.java    # Fighter data model
│       └── MMADatabase.java      # Database handler
├── assets/
│   └── charles-oliveira-ufc.gif  # UI decoration
├── UFCRosterStats.txt            # Fighter data source
├── config.properties.txt         # Database configuration
└── README.md
```

### Class Breakdown

#### 1. **GutPicks.java** (Main Application)
- **Purpose**: Core GUI and application logic
- **Key Components**:
  - 22 fighter buttons with action listeners
  - Betting slip panel with odds calculator
  - File I/O for saving betting slips
  - Dynamic fighter data loading

#### 2. **RosterMetrics.java** (Data Model)
- **Purpose**: Fighter information container
- **Attributes**: 16 fighter properties (ranking, name, record, odds, etc.)
- **Methods**: Getters, setters, and formatted display methods

#### 3. **MMADatabase.java** (Database Layer)
- **Purpose**: Apache Derby database operations
- **Methods**:
  - `getConnection()` - Establishes database connection
  - `addFighter()` - Inserts fighter records using PreparedStatement
- **Features**: SQL injection protection, proper connection handling

---

## 🚀 Setup Instructions

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Derby database
- NetBeans IDE (recommended) or any Java IDE

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/SportBetting-UI.git
   cd SportBetting-UI
   ```

2. **Configure Database**
   
   Create `config.properties.txt` in the project root:
   ```properties
   db.url=jdbc:derby://localhost:1527/MMADB
   db.username=your_username
   db.password=your_password
   ```

3. **Prepare Fighter Data**
   
   Ensure `UFCRosterStats.txt` exists with format:
   ```
   ranking#name#weightclass#gender#record#nickname#odds#nationality#dob#age#height#weight#debut#ko_wins#dec_wins#sub_wins
   ```

4. **Setup GIF Asset**
   - Download your UFC GIF
   - Rename to `charles-oliveira-ufc.gif`
   - Place in `assets/` folder

5. **Run the Application**
   ```bash
   javac -cp ".:derby.jar" GutPickMMA/*.java
   java -cp ".:derby.jar" GutPickMMA.GutPicks
   ```

---

## 📖 Usage Guide

### Starting the Application
1. Launch `GutPicks.java`
2. Click **"Show Fights"** button to load fighter data
3. Fighter names and odds will populate the interface

### Creating a Bet
1. **Select Fighters** - Click fighter buttons (they turn green when selected)
2. **View Odds** - Total parlay odds display automatically
3. **Enter Wager** - Type amount in "ENTER WAGER AMOUNT" field
4. **Calculate** - Click "Cash Out" to see potential winnings
5. **Save** - Click "BET" to save betting slip to `BettingSlip.txt`

### Clearing Selections
- Click **"CLEAR"** button to reset all selections and start over

---

## 🗃️ Database Schema

### FIGHTERS Table
```sql
CREATE TABLE FIGHTERS (
    fighterID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    ranking INTEGER,
    fighterName VARCHAR(100),
    weightDivison VARCHAR(50),
    record VARCHAR(20),
    nickName VARCHAR(50),
    odds DECIMAL(5,2),
    nationality VARCHAR(100),
    DOB VARCHAR(20),
    age INTEGER,
    height VARCHAR(10),
    weight VARCHAR(15),
    debute VARCHAR(20),
    winsViaKO VARCHAR(50),
    winsViaDec VARCHAR(50),
    winsViaSub VARCHAR(50)
)
```

---

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| **Java Swing** | GUI framework for desktop interface |
| **Apache Derby** | Embedded relational database |
| **JDBC** | Database connectivity |
| **Java I/O** | File reading/writing operations |
| **Properties API** | Configuration management |

---

## 🎨 UI Features

- **Color-Coded Odds**: Visual indication of favorite vs underdog
- **Responsive Design**: Organized layered pane layout
- **Real-time Updates**: Instant odds calculation on selection
- **Duplicate Prevention**: HashSet prevents duplicate fighter additions to slip

---

## 📝 Sample Fighter Data Format

```
1#Alex Pereira#LIGHT HEAVYWEIGHT#M#9-2-0 (W-L-D)#POATAN#1.65#São Bernardo do Campo, Brazil#1987-07-07#36#6'4#204.0 lbs#2012-03-31#7 KO wins#2 Decision wins#0 Submission wins
2#Jamahal Hill#LIGHT HEAVYWEIGHT#M#12-1-0 (W-L-D)#SWEET DREAMS#2.30#United States#1991-05-19#32#6'4#204.5 lbs#2016-01-02#7 KO wins#5 Decision wins#0 Submission wins
```

---

## 🐛 Known Issues & Future Enhancements

### Current Limitations
- Fighter data must be manually added to text file
- Database connection requires manual configuration
- Limited to 22 fighters (11 match-ups)

### Planned Features
- 🔄 Live odds API integration
- 📊 Historical betting statistics
- 👤 User account system
- 💳 Mock payment processing
- 📱 Mobile-responsive version

---

## 👨‍💻 For Hiring Managers

### Key Technical Highlights

**1. Object-Oriented Design**
- Clean separation of concerns (Model-View-Controller pattern)
- Reusable `RosterMetrics` class for fighter data
- Modular database handler

**2. Database Integration**
- Prepared statements prevent SQL injection
- Proper connection management with try-catch blocks
- Configuration externalization for security

**3. Error Handling**
- Try-catch blocks throughout application
- User-friendly error dialogs
- Graceful degradation when data unavailable

**4. Concurrency Awareness**
- `synchronized` methods for thread-safe data loading
- HashSet for duplicate prevention

**5. UI/UX Considerations**
- Intuitive color-coded visual feedback
- Real-time calculation updates
- Clear button states and disabled states

---

## 📄 License

This project is created for educational and portfolio purposes.

---

## 📧 Contact

For questions or collaboration opportunities, please reach out via:
- GitHub: [@yourusername](https://github.com/yourusername)
- Email: your.email@example.com

---

**Built with ❤️ and ☕ by [Your Name]**
