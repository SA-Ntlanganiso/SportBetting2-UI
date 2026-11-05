
# 🥊 SportBetting-UI — GutPICKS.bet

A Java Swing desktop application simulating an MMA/UFC sports-betting platform. Browse fighter profiles, explore configured match-ups, select fight outcomes and manage betting slips with automatic odds and potential-return calculations.

> **Project:** Educational portfolio project  
> **Language:** Java  
> **Interface:** Java Swing  
> **Database:** Apache Derby

---

## 🥊 Featured UFC Fighters

<table>
  <tr>
    <th align="center">Alex Pereira</th>
    <th align="center">Charles Oliveira</th>
    <th align="center">Zhang Weili</th>
    <th align="center">Conor McGregor</th>
  </tr>
  <tr>
    <td align="center">
      <a href="https://giphy.com/gifs/ufc-alex-pereira-kGxLW81gjIteWQJYKJ">
        <img src="https://media.giphy.com/media/kGxLW81gjIteWQJYKJ/giphy.gif" width="220" height="124" alt="Alex Pereira UFC GIF"/>
      </a>
    </td>
    <td align="center">
      <a href="https://giphy.com/gifs/ufc-sports-mma-ufc-269-9XPpZjsTPQXMY84bZ7">
        <img src="https://media.giphy.com/media/9XPpZjsTPQXMY84bZ7/giphy.gif" width="220" height="124" alt="Charles Oliveira UFC GIF"/>
      </a>
    </td>
    <td align="center">
      <a href="https://giphy.com/gifs/ufc-sports-sport-ufc-281-mma-tMcCEG931Vk6DcS6AI">
        <img src="https://media.giphy.com/media/tMcCEG931Vk6DcS6AI/giphy.gif" width="220" height="124" alt="Zhang Weili UFC GIF"/>
      </a>
    </td>
    <td align="center">
      <a href="https://giphy.com/gifs/ufc-sports-sport-mma-vAGq7eYHmYJu3s9zUS">
        <img src="https://media.giphy.com/media/vAGq7eYHmYJu3s9zUS/giphy.gif" width="220" height="124" alt="Conor McGregor UFC GIF"/>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center"><b>POATAN</b></td>
    <td align="center"><b>DO BRONX</b></td>
    <td align="center"><b>MAGNUM</b></td>
    <td align="center"><b>THE NOTORIOUS</b></td>
  </tr>
</table>

*Each fighter has a separate GIF source. These are featured fighter profiles, not a claim that they are competing against one another.*

---

## 🎯 Overview

**GutPICKS.bet** is a Java-based desktop application that simulates an MMA/UFC betting experience.

The application reads fighter information from a local text file, displays configured match-ups and odds, and allows users to create betting slips with automatic calculations of combined odds and potential returns.

The project demonstrates Java GUI development, object-oriented programming, database connectivity and file processing.

## ✨ Key Features

### 🎮 Fight Selection
- 22 fighter buttons across 11 configured match-ups.
- Interactive fighter selection.
- Green visual feedback for selected fighters.
- Color-coded odds display.
- Fighter profiles and statistics.

### 💰 Betting Slip Management
- Automatic combined parlay odds calculation.
- Custom wager input.
- Potential winnings calculation.
- Save betting slips to text files.
- Clear selections and start again.

### 📊 Fighter Statistics

The application supports displaying:

- Fighter name and nickname.
- Fight record (W-L-D).
- Betting odds.
- Date of birth and age.
- Nationality.
- Weight division.
- Height and weight.
- Knockout, decision and submission wins.

### 🗄️ Database Integration
- Apache Derby database.
- JDBC connectivity.
- Fighter record insertion.
- Prepared statements for database operations.
- External database configuration.

---

## 🏗️ Technical Architecture

```text
SportBetting-UI/
│
├── src/
│   └── GutPickMMA/
│       ├── GutPicks.java
│       ├── RosterMetrics.java
│       └── MMADatabase.java
│
├── assets/
│   └── charles-oliveira-ufc.gif
│
├── UFCRosterStats.txt
├── config.properties.txt
└── README.md
```

### Main Classes

**1. GutPicks.java — Main Application**
- Java Swing GUI.
- Fighter selection and event handling.
- Betting slip management.
- Odds calculations.
- File reading and writing.

**2. RosterMetrics.java — Fighter Data Model**
- Stores fighter information.
- Handles fighter attributes.
- Provides getters and setters.
- Formats fighter information for display.

**3. MMADatabase.java — Database Layer**
- Apache Derby connectivity.
- Fighter record insertion.
- JDBC prepared statements.
- Database connection management.

---

## 🚀 Setup Instructions

### Prerequisites

- Java Development Kit (JDK 8 or higher).
- Apache Derby.
- NetBeans IDE or another Java IDE.
- Derby JDBC driver.

### 1. Clone Repository

```bash
git clone https://github.com/SA-Ntlanganiso/SportBetting-UI.git

cd SportBetting-UI
```

### 2. Configure Database

Create `config.properties.txt`:

```properties
db.url=jdbc:derby://localhost:1527/MMADB
db.username=your_username
db.password=your_password
```

Use your local Derby database credentials.

### 3. Prepare Fighter Data

Ensure `UFCRosterStats.txt` exists.

Expected format:

```text
ranking#name#weightclass#gender#record#nickname#odds#nationality#dob#age#height#weight#debut#ko_wins#dec_wins#sub_wins
```

### 4. Run Application

Example compilation command:

```bash
javac -cp ".:derby.jar" src/GutPickMMA/*.java

java -cp ".:derby.jar:src" GutPickMMA.GutPicks
```

On Windows, replace `:` in the classpath with `;`.

---

## 📖 Usage Guide

1. Launch `GutPicks.java`.
2. Click **Show Fights**.
3. Review the available fighters and odds.
4. Select fighters using the fighter buttons.
5. Enter your wager amount.
6. Click **Cash Out** to calculate potential returns.
7. Click **BET** to save the betting slip.
8. Click **CLEAR** to reset selections.

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
);
```

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Application development |
| Java Swing | Desktop GUI |
| Apache Derby | Relational database |
| JDBC | Database connectivity |
| Java I/O | File operations |
| Properties API | Configuration management |

---

## 💡 Engineering Concepts Demonstrated

**Object-Oriented Programming**
- Separation of application logic, data models and database operations.
- Reusable Java classes.

**Database Development**
- JDBC connectivity.
- Prepared statements.
- Relational data storage.

**Event-Driven Programming**
- Swing action listeners.
- Interactive fighter selection.
- Dynamic odds calculations.

**File Processing**
- Reading structured fighter records.
- Exporting betting slips.

**Data Structures**
- HashSet for duplicate-selection prevention.

**Error Handling**
- Exception handling.
- User-facing error dialogs.
- Database connection management.

---

## 🔮 Future Improvements

- Live fight-card and odds API integration.
- Historical fighter performance analytics.
- User authentication and betting history.
- Improved automated testing.
- Web and mobile application versions.
- Simulated payment processing.

---

## 👨‍💻 For Hiring Managers

This project demonstrates my practical experience with Java application development, desktop GUI design, relational databases, JDBC, file handling and object-oriented programming.

It also reflects my interest in sports technology and developing software around domain-specific data and user workflows.

The current application is an educational simulation. Fighter information and odds are locally configured; it does not provide verified live bookmaker odds or accept real-money bets.

---

## 📌 Project Information

| Detail | Information |
|---|---|
| Project | SportBetting-UI |
| Application | GutPICKS.bet |
| Language | Java |
| Interface | Java Swing |
| Database | Apache Derby |
| Category | Sports Technology / Software Development |

## 📫 Contact

**Sinalo Alizwa Ntlanganiso**

- GitHub: https://github.com/SA-Ntlanganiso
- Portfolio: https://sa-ntlanganiso.web.app
- Email: asinalo87@gmail.com

---

**Built by Sinalo Alizwa Ntlanganiso**
