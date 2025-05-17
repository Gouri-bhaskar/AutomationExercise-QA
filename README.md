# 🧪 AutomationExercise - Selenium Test Framework

## 📖 About This Project

This project automates end-to-end testing of the [AutomationExercise.com](https://www.automationexercise.com/) website using **Java**, **Selenium WebDriver**, **TestNG**, and **ExtentReports**. It simulates real user interactions such as signup, login, product browsing, cart operations, review submissions, and account deletion — all fully validated via assertions and logged reports.

## 👨‍💻 About the Author

**Author**: Gouri Bhaskar  
**Contact**: gouri.bhaskar21 [at] gmail [dot] com  
**Expertise**:  QA Automation

## 🎯 Project Aim

To build a modular and reusable automation framework that:
- Validates major functionalities of an e-commerce platform
- Supports data-driven testing using Excel
- Logs every step with screenshots and assertions
- Provides detailed reports with visual logs

## ⚙️ Requirements

- Java JDK 11 or higher
- Maven 3.6+
- Chrome Browser (latest)
- ChromeDriver (auto-handled by Selenium)
- Git
- IDE (IntelliJ / Eclipse recommended)

## 🛠️ Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/automationexercise.git
cd automationexercise
```

### 2. Run All Tests
```bash
mvn clean install
```

### 3. Run Specific Class
```bash
mvn test -Dtest=tests.Testcase1
```

## 📁 Report Location

After every run, a visual HTML report is generated at:

```
AutomationExercisee/Reports/Report_<timestamp>/Test_Automation_Report_<timestamp>.html
```

### 🔍 How to Find the Latest Report
- Go to `AutomationExercisee/Reports`
- Sort folders by **date modified**
- Open the latest `Report_<yyyy-MM-dd_HH_mm_ss>` folder
- Open the file named `Test_Automation_Report_<timestamp>.html`

Screenshots are automatically stored in the `Screenshots/` subfolder of each run.

---

## ✅ Detailed Test Case Matrix

| Test ID | Class Name         | Description                                                                 | Status |
|---------|--------------------|-----------------------------------------------------------------------------|--------|
| TC001   | Testcase1          | Validate base URL and home page loaded                                      | ✅     |
| TC002   | Testcase1          | Banner visibility and CTA check                                            | ✅     |
| TC003   | Testcase1          | Validate product addition to cart                                           | ✅     |
| TC004   | Testcase1          | View cart and proceed to checkout without login                             | ✅     |
| TC005   | Testcase1          | Click 'Register/Login'                                                      | ✅     |
| TC006   | Testcase1          | Validate full signup with name/email and navigate to signup page            | ✅     |
| TC007   | Testcase1          | Fill out signup form and create user                                        | ✅     |
| TC008   | Testcase1          | Verify redirected to `account_created` page                                 | ✅     |
| TC009   | Testcase1          | Continue and check if user is logged in                                     | ✅     |
| TC010   | Testcase1          | Click cart after login                                                      | ✅     |
| TC011   | Testcase1          | Proceed to checkout                                                         | ✅     |
| TC012   | Testcase1          | Review order + check billing and delivery address                           | ✅     |
| TC013   | Testcase1          | Place order after entering comment                                          | ✅     |
| TC014   | Testcase1          | Fill out card payment and verify success                                    | ✅     |
| TC020   | SignupWithExcelTest| Signup using multiple rows from Excel                                       | ✅     |
| TC022   | Testcase1          | Logout and login with invalid credentials                                   | ✅     |
| TC023   | Testcase1          | Login with valid credentials after failure                                  | ✅     |
| TC030   | ProductListingTest | Verify product listings, categories, brands, add to cart, search filters    | ✅     |
| TC031   | SubscriptionTest   | Footer subscription with valid email                                        | ✅     |
| TC032   | SubscriptionTest   | Subscription with invalid email                                             | ✅     |
| TC033   | SubscriptionTest   | Subscription with duplicate email                                           | ✅     |
| TC034   | AccountDeletionTest| Verify 'Delete Account', confirm deletion, click 'Continue'                 | ✅     |
| TC035   | ContactUsTest      | Fill out contact form, upload file, handle alert, verify success message    | ✅     |

---

## 🗂️ Project Directory Structure

```bash
AutomationExercisee/
├── pom.xml                          # Maven build config
├── Reports/                         # HTML test report output
│   └── Report_<timestamp>/         # Timestamped folder for each test run
│       ├── Test_Automation_Report_<timestamp>.html
│       └── Screenshots/            # Screenshot evidence for failed steps
├── src/
│   ├── main/
│   │   └── resources/
│   │       └── Extensions/
│   │           └── AdBlock.crx     # Chrome extension to suppress ads
│   └── test/
│       ├── java/
│       │   ├── helper/             # Utility classes for config and Excel
│       │   ├── modules/            # Extent report modules and listeners
│       │   ├── pages/              # Page Object Model classes
│       │   └── tests/              # Test classes (Testcase1, SubscriptionTest, etc.)
│       └── resources/
│           ├── test_users_data.xlsx
│           ├── created_accounts.json
│           └── uploads/
│               └── image001.jpg    # Used in contact form test
└── .github/
    └── workflows/
        └── maven.yml              # CI setup with GitHub Actions
```

---

## 🧪 Technologies Used

- Java 11
- Selenium WebDriver 4.x
- TestNG
- Maven
- Apache POI (for Excel)
- ExtentReports
- Log4j2
- ChromeDriver with AdBlock extension
- GitHub Actions (CI/CD pipeline)

---

© 2025 Gouri Bhaskar. All rights reserved.