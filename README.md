# SportBetting-UI --- GutPICKS.bet

A Java Swing desktop application that simulates an MMA/UFC
sports-betting experience. Users can browse fighter information, select
fighters from available match-ups, build a betting slip, and calculate
potential returns from the odds stored with the fighter data.

> **Project type:** Educational portfolio project\
> **Status:** Desktop application / simulation\
> **Important:** The current version loads fighter and odds data from a
> local text file. It does not provide verified live bookmaker odds or
> place real-money bets.

![Alex Pereira
UFC](https://media.giphy.com/media/pu9a1N1ePf4M6pzAsv/giphy.gif)

------------------------------------------------------------------------

## Featured fighters

The interface showcases MMA fighters including:

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
                               Alex Pereira                                                         Charles Oliveira                                                         Zhang Weili
  ---------------------------------------------------------------------- ----------------------------------------------------------------------- --------------------------------------------------------------------
                                  ![Alex                                                                ![Charles                                                              ![Zhang
   Pereira](https://media.giphy.com/media/G4u0EILLmEG8vexxgG/giphy.gif)   Oliveira](https://media.giphy.com/media/ojgCUHr9TIzSWwAWlY/giphy.gif)   Weili](https://media.giphy.com/media/pu9a1N1ePf4M6pzAsv/giphy.gif)

                                **POATAN**                                                            **DO BRONX**                                                            **MAGNUM**
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

These are featured fighter profiles, not a claim that the three fighters
are opponents in the same bout. Match-ups displayed by the application
are determined by the records in `UFCRosterStats.txt`.

------------------------------------------------------------------------

## Overview

**GutPICKS.bet** is a desktop application built in Java to demonstrate
the core flow of an MMA betting interface. It reads fighter records from
a local text file, displays available match-ups and their configured
odds, and lets a user select fighters to create a betting slip.

The application calculates combined parlay odds and estimated returns
from the selected odds and entered stake. It also demonstrates database
connectivity, file handling, object-oriented design and desktop GUI
development.

## Key features

### Fight and fighter selection

-   Displays 22 fighter-selection buttons across 11 configured
    match-ups.
-   Allows a user to select fighters and provides visual feedback for
    selections.
-   Displays fighter information such as name, nickname, record,
    nationality, weight division and other roster fields.
-   Shows the odds stored in the local fighter data.

### Betting slip simulation

-   Builds a betting slip from selected fighters.
-   Calculates combined parlay odds from the selected odds.
-   Accepts a user-entered stake and calculates estimated potential
    returns.
-   Saves the betting slip to a text file.
-   Provides a clear/reset action to remove selections.

### Fighter data and database

-   Loads roster information from `UFCRosterStats.txt`.
-   Uses an Apache Derby database through JDBC for fighter data
    persistence.
-   Includes an operation to add fighter records to the database.
-   Uses prepared statements for database inserts.

------------------------------------------------------------------------

## Technology stack

  Technology            Use
  --------------------- ---------------------------------------------------
  Java                  Application logic and object-oriented programming
  Java Swing            Desktop graphical user interface
  Apache Derby          Relational database
  JDBC                  Database connectivity
  Java I/O              Reading roster data and saving betting slips
  Java Properties API   External database configuration

------------------------------------------------------------------------

## Project structure

``` text
SportBetting-UI/
├── src/
│   └── GutPickMMA/
│       ├── GutPicks.java          # Main desktop application and GUI
│       ├── RosterMetrics.java     # Fighter data model
│       └── MMADatabase.java       # Derby database operations
├── assets/
│   └── charles-oliveira-ufc.gif   # Optional UI image asset
├── UFCRosterStats.txt             # Local fighter and odds data
├── config.properties.txt          # Database connection settings
└── README.md
```

## Architecture and main classes

### `GutPicks.java` --- presentation and application flow

-   Creates the Java Swing interface.
-   Displays fighter-selection controls and betting-slip information.
-   Handles user selections and updates the calculated odds.
-   Reads fighter data and writes saved betting slips.

### `RosterMetrics.java` --- fighter data model

-   Represents the fighter information loaded by the application.
-   Holds roster attributes such as ranking, name, division, record,
    nickname, odds and fight statistics.
-   Provides accessors and formatted display information.

### `MMADatabase.java` --- database access

-   Handles the Apache Derby connection.
-   Provides database operations such as adding fighter records.
-   Uses JDBC and prepared statements for parameterised database
    operations.

------------------------------------------------------------------------

## Getting started

### Prerequisites

-   JDK 8 or later.
-   Apache Derby, configured for the connection mode used by the
    project.
-   NetBeans or another Java IDE (NetBeans was the recommended
    development environment).
-   Derby JDBC driver available on the classpath.

### 1. Clone the repository

``` bash
git clone https://github.com/SA-Ntlanganiso/SportBetting-UI.git
cd SportBetting-UI
```

### 2. Configure the database

Create or update `config.properties.txt` in the project root using the
property names expected by `MMADatabase.java`. For example:

``` properties
db.url=jdbc:derby://localhost:1527/MMADB
db.username=your_username
db.password=your_password
```

Update the URL and credentials to match your local Derby configuration.
Do not commit real credentials.

### 3. Prepare the fighter roster

Ensure `UFCRosterStats.txt` is available in the location expected by the
application. The documented record layout is:

``` text
ranking#name#weightclass#gender#record#nickname#odds#nationality#dob#age#height#weight#debut#ko_wins#dec_wins#sub_wins
```

The application uses the roster file as its local data source. Add or
update fighter records there using the same field order and delimiter.

### 4. Add the optional GIF asset

If the GUI references the local GIF, place the image in the `assets/`
directory using the filename expected by the source code.

### 5. Compile and run

The following is an example command-line approach. Adjust the source
path and Derby JAR filename to match your local project setup.

``` bash
javac -cp ".:derby.jar" src/GutPickMMA/*.java
java -cp ".:derby.jar:src" GutPickMMA.GutPicks
```

On Windows, use `;` rather than `:` to separate classpath entries. If
you use NetBeans, open the project and configure the Derby library
before running the main class.

------------------------------------------------------------------------

## How to use the application

1.  Launch the application.
2.  Select **Show Fights** to load the available fighter data.
3.  Review the fighter details and configured odds.
4.  Select the fighter buttons for the outcomes you want to include.
5.  Enter a stake in the wager field.
6.  Select **Cash Out** to calculate the estimated potential return.
7.  Select **BET** to save the betting slip to `BettingSlip.txt`.
8.  Select **CLEAR** to reset the current selections.

This is a local simulation. The displayed odds are read from the
project's data and are not guaranteed to reflect current market prices.

------------------------------------------------------------------------

## Database

The project uses an Apache Derby `FIGHTERS` table to store fighter
records. The schema below reflects the fields documented for the
application; confirm the exact column spelling and types against the SQL
in the source before creating or migrating a database.

``` sql
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

------------------------------------------------------------------------

## Engineering concepts demonstrated

-   **Object-oriented design:** separates the main GUI, fighter data
    model and database handler into distinct classes.
-   **Database integration:** connects Java to Apache Derby using JDBC.
-   **Safer SQL operations:** uses prepared statements for parameterised
    inserts.
-   **File processing:** reads structured fighter records and exports
    betting slips.
-   **Event-driven GUI programming:** uses Swing controls and action
    listeners to respond to user interaction.
-   **Collection handling:** uses a `HashSet` to prevent duplicate
    fighter selections in a betting slip.
-   **Error handling:** uses exception handling and user-facing dialogs
    for application errors.

## Current limitations and possible improvements

### Current limitations

-   Fighter and odds data are maintained locally in a text file.
-   The application is limited to the match-ups and fighter records
    configured in its data source.
-   Database setup requires local configuration.
-   There is no verified live odds feed, live event schedule, user
    account service or real payment processing in the described version.

### Potential future enhancements

-   Integrate a licensed sports data provider for current fight cards
    and odds.
-   Add user accounts and persistent betting-slip history.
-   Add historical statistics and performance visualisations.
-   Improve input validation and automated test coverage.
-   Build a responsive web or mobile client.
-   Add simulated payment flows for demonstration purposes.

------------------------------------------------------------------------

## For reviewers and hiring teams

This project demonstrates my practical application of Java desktop
development, object-oriented programming, GUI event handling, relational
database connectivity and file processing. It also reflects my interest
in sports technology and building software around domain-specific data
and user workflows.

The project is intended as a learning and portfolio demonstration. It
does not represent a licensed betting product and does not accept
real-money wagers.

## Repository and contact

-   **GitHub repository:**
    https://github.com/SA-Ntlanganiso/SportBetting-UI
-   **GitHub profile:** https://github.com/SA-Ntlanganiso
-   **Portfolio:** https://sa-ntlanganiso.web.app
-   **Email:** asinalo87@gmail.com

------------------------------------------------------------------------

**Developed by Sinalo Alizwa Ntlanganiso**
