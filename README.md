# ✈ SkyWing - Java MySQL Flight Booking & Seat Management System

A modern, standalone Java application built with **JDBC**, **MySQL**, and **FlatLaf Modern UI** to manage flight reservations, schedules, and real-time seat inventory with **ACID transaction safety**.

Packaged as a **single executable Fat JAR (`FlightBookingSystem.jar`)**, ready for distribution and one-click execution on any platform with Java installed.

---

## 🌟 Key Features

- **Real-Time Seat Inventory Synchronization**:
  - Automatically decrements available seat counts upon ticket confirmation.
  - Automatically restores seats upon ticket cancellation.
  - Uses `SELECT ... FOR UPDATE` and `setAutoCommit(false)` transaction isolation to eliminate overbooking and race conditions.
- **Flight Exploration & Search**:
  - Search routes by Origin, Destination, or Airline carrier.
  - Real-time seat occupancy status badges (*Available*, *Filling Fast*, *Sold Out*).
- **Ticket Reservation Wizard**:
  - Instant passenger registration with real-time class multiplier calculation (Economy, Premium Economy, Business, First Class).
  - Unique **PNR Reference Generator** (e.g. `FB-8291K`).
- **Boarding Pass & E-Ticket Generator**:
  - Visual boarding pass card modal with 1-click clipboard copy and `.txt` file export.
- **Booking Management & Cancellation**:
  - Search reservations by PNR code or passenger name/email.
  - 1-click cancellation with transactional seat restoration to inventory.
- **Flight Route Admin Panel**:
  - Add new flight routes with custom schedule, capacity, and pricing.
- **Dynamic MySQL Connection Manager**:
  - In-app database setup dialog with connection tester and **1-click automatic schema initializer / seed data generator**.
- **Dual Mode Interface**:
  - **Modern Swing GUI** (FlatLaf theme).
  - **Interactive CLI mode** (`--cli` flag) for terminal environments.

---

## 🚀 Quick Start (Running the Application)

### Option A: Run the Standalone Executable (Recommended)
You only need **Java (JRE/JDK 11 or newer)** installed.

1. Double-click **`FlightBookingSystem.jar`** or **`run.bat`**.
2. Or run from the command line:
   ```bash
   java -jar FlightBookingSystem.jar
   ```
3. To run in terminal CLI mode:
   ```bash
   java -jar FlightBookingSystem.jar --cli
   ```

---

## 🗄️ Database Setup (MySQL)

### Method 1: Automatic In-App Initialization (Easiest)
1. Launch the application.
2. If MySQL requires credentials, the **Database Setup Dialog** will appear automatically (or click **DB Setup** in the top bar).
3. Enter your MySQL username and password.
4. Click **"Auto-Init Schema"** — the application will automatically:
   - Create database `flight_booking_db`
   - Create `flights` and `bookings` tables
   - Seed sample flight routes and schedules
5. Click **"Save & Connect"**.

### Method 2: Manual SQL Script
You can also run the provided [schema.sql](file:///schema.sql) in MySQL Workbench or terminal:
```bash
mysql -u root -p < schema.sql
```

---

## 📦 How to Upload to GitHub as a Single Downloadable File

To allow anyone to download and run the single executable file directly from your GitHub repository:

### 1. Initialize Git and Push Code to GitHub
```bash
git init
git add .
git commit -m "Initial commit: Flight Booking System standalone release"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/flight-booking-system.git
git push -u origin main
```

### 2. Create a GitHub Release
1. Go to your GitHub repository in your web browser.
2. On the right sidebar, click **"Releases"** -> **"Draft a new release"** (or click **"Create a new release"**).
3. In **Tag version**, enter `v1.0.0`.
4. In **Release title**, enter `SkyWing Flight Booking System v1.0.0`.
5. Under **"Attach binaries by dropping them here or selecting them"**, upload:
   - **`FlightBookingSystem.jar`** (The single executable Fat JAR)
   - **`run.bat`** (Optional Windows shortcut launcher)
6. Click **"Publish release"**.

> **Note**: Users can now simply click on `FlightBookingSystem.jar` under the **Releases / Assets** section of your GitHub repository and double-click to run it instantly!

---

## 🛠️ Building from Source

To compile and repackage the Fat JAR locally:

### On Windows:
```cmd
build.bat
```
or with PowerShell:
```powershell
powershell -ExecutionPolicy Bypass -File .\build.ps1
```

This compiles all sources in `src/`, bundles the MySQL JDBC Driver (`lib/mysql-connector-j-8.4.0.jar`) and FlatLaf UI engine (`lib/flatlaf-3.5.4.jar`), and creates `FlightBookingSystem.jar`.

---

## 📂 Project Structure

```
flightbooking/
├── src/
│   └── com/flightbooking/
│       ├── Main.java                          # Main entry point (GUI/CLI launcher)
│       ├── model/
│       │   ├── Flight.java                    # Flight entity model
│       │   ├── Booking.java                   # Booking & PNR entity model
│       │   └── DatabaseConfig.java            # JDBC connection configuration
│       ├── dao/
│       │   ├── DatabaseManager.java           # Connection pool & schema auto-initializer
│       │   ├── FlightDAO.java                 # Flight DAO interface
│       │   ├── FlightDAOImpl.java             # Flight JDBC implementation
│       │   ├── BookingDAO.java                # Booking DAO interface
│       │   └── BookingDAOImpl.java            # Booking ACID transaction implementation
│       ├── service/
│       │   └── FlightBookingService.java      # Business logic, seat pricing & validations
│       ├── ui/
│       │   ├── ModernUIUtils.java             # Design system, themes & custom renderers
│       │   ├── MainFrame.java                 # Modern dashboard window
│       │   ├── TicketDialog.java              # E-Ticket / Boarding pass receipt modal
│       │   └── DbConfigDialog.java            # MySQL credentials & setup modal
│       ├── cli/
│       │   └── ConsoleApp.java                # Interactive command-line application
│       └── test/
│           └── SystemIntegrationTest.java     # Automated unit & integration tests
├── lib/
│   ├── mysql-connector-j-8.4.0.jar            # MySQL JDBC Connector
│   └── flatlaf-3.5.4.jar                      # FlatLaf Modern UI library
├── schema.sql                                 # MySQL database DDL & sample data
├── build.bat                                  # Windows build batch script
├── build.ps1                                  # PowerShell build script
├── run.bat                                    # One-click Windows runner
├── FlightBookingSystem.jar                    # Standalone Executable Fat JAR
└── README.md                                  # Documentation & GitHub release guide
```

---

## 🧪 Testing & Verification

Run the test suite with:
```bash
java -cp FlightBookingSystem.jar com.flightbooking.test.SystemIntegrationTest
```
