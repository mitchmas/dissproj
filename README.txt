INSTALLATION INSTRUCTIONS AND USER GUIDE FOR CRM & ROTA MULTI-MANAGER

INSTALLATION INSTRUCTIONS
extract the project to a place of your choosing.

for this example we will use intellij as the IDE, load up the project in your IDE. You can do this by opening MMProject\src\main\java\adbt171\mmproject and right clicking on any class and opening in your IDE (preferably IntelliJ.)

build the project with the configuration of the run being set to 'ApplicationMain'.
This can be done in IntelliJ by pressing the hammer icon and right clicking on the class listed 'ApplicationMain and clicking run ApplicationMain.

When the project has built successfully (sometimes may take one or two build attempts and a delay before being successfully build on new machine) close the pop up screen titled Log in.

Step II - connecting the db.

If you do not already have a mySQL schema on your machine, you can download MySQL by going to https://dev.mysql.com/downloads/installer/ and installing the appropriate version for your machine. choose setup type Developer Default and press execute. Continue executing until you get to Type and Networking, note down the port used in the 'Connectivity' section.

On the next screen you can select your password type and then it will ask you to create a root password. set this to something you will remember. Apply configurations and follow the steps asked by the MySQL installer until the installation is complete.

Once the installation is complete, open up the MySQL Workbench and select on your connection loaded near the bottom of the screen. Make sure to remember your name (typically root, shown by the person icon underneath the database name (typically Local Instance MySQL80)) and the password.

Underneath the file tab, select the fourth button from the left or look for the button called 'create schema'. Set this to whatever you would like, but you must remember it.

Create the schema and ensure it is there by making sure its visible when you select the Schema page on the left hand side (tab next to administration).

click the left most button underneath file to open up an sql sheet.

when this SQL sheet loads, please copy and paste the following code into the SQL sheet, replacing the term 'mySchema' with your schema name wherever it appears:
(do not include the lines like this one directly underneath, this is just to contain the sql statements.
------------------------------------------------------

CREATE TABLE IF NOT EXISTS `mySchema`.`usersdb` (
  `id_user` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `mySchema`.`employeedb` (
  `forename` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `rateOfPay` DOUBLE NULL DEFAULT NULL,
  `startDate` DATE NULL DEFAULT NULL,
  `NINum` VARCHAR(45) NOT NULL,
  `contactNum` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`NINum`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `mySchema`.`contractsdb` (
  `contractsID` INT NOT NULL AUTO_INCREMENT,
  `contracts_company_name` VARCHAR(45) NULL DEFAULT NULL,
  `contracts_company_address` VARCHAR(45) NULL DEFAULT NULL,
  `contracts_primary_contact_name` VARCHAR(45) NULL DEFAULT NULL,
  `contracts_primary_contact_number` VARCHAR(45) NULL DEFAULT NULL,
  `contracts_start_date` DATE NULL DEFAULT NULL,
  `contracts_end_date` DATE NULL DEFAULT NULL,
  `contracts_value` DECIMAL(10,2) NULL DEFAULT NULL,
  `contracts_notes` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`contractsID`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `mySchema`.`quotesdb` (
  `quotesID` INT NOT NULL AUTO_INCREMENT,
  `quotes_company_name` VARCHAR(45) NULL DEFAULT NULL,
  `quotes_valuation` DECIMAL(10,2) NULL DEFAULT NULL,
  `quotes_date` DATE NULL DEFAULT NULL,
  `quotes_primary_contact` VARCHAR(45) NULL DEFAULT NULL,
  `quotes_contact_email` VARCHAR(45) NULL DEFAULT NULL,
  `quotes_notes` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`quotesID`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `mySchema`.`rotadb` (
  `rotaID` INT NOT NULL AUTO_INCREMENT,
  `rota_type` VARCHAR(45) NULL DEFAULT NULL,
  `rota_name` VARCHAR(45) NULL DEFAULT NULL,
  `rota_start_date` DATE NULL DEFAULT NULL,
  `rota_end_date` DATE NULL DEFAULT NULL,
  `rota_start_time` VARCHAR(45) NULL DEFAULT NULL,
  `rota_end_time` VARCHAR(45) NULL DEFAULT NULL,
  `rota_comments` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`rotaID`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


------------------------------------------------------

After you have run the SQL by replacing the schema name and pressing the lightning arrow third from the left above the query, right click on the Tables section of your schema and select 'Refresh all', you should now have a drop down bar listing the 5 tables necessary to run the project.

To include this db in the project to allow it to run, simply go into the IDE with the loaded java file for the project (built successfully), go to the class DBConn 
inside of the folder adbt171.mmproject inside of java inside of main inside of src.
Go to line 20, and replace the inside speech marks for private static String dbUrl with your database in the form of jdbc:mysql://localhost:(write your selected port number from earlier here, in this case mine is 3306)/(write your schema name you chose earlier and generated the tables in here,in the base case it is mmprojdb)

please also input your database name (as seen earlier with the person icon as you load the schema, or by clicking Session in the bottom left after selecting the schema and look for the 'Login User' value. In my example, the login user was left as root. Set line 23 in DBConn to the name you found earlier following the steps.

Finally, in line 24 dbPass, enter the password you entered to log in to your schema for table creation earlier. After this, make sure you hit save on your IDE, and rebuild the project. Then, run the project using the configuration of ApplicationMain. Your database should now be up and working and connected to the software.

USER GUIDE
Congratulations! Now you may test and use the software. Please be aware that on your first time running, you will not be able to access the management hub without creating a new account by selecting create account in the log in screen.

Please use the left hand side buttons of the java application to navigate round the management hub and test. When testing, ensure when creating/editing/deleting data that every part of the form is filled out. When editing data, do note that you cannot change the data's primary key, therefore if you wish to edit a primary key (ID for all tables except for employee, where NI number is the primary key) you must Remove the entry and then create a new one.

When manipulating tables, please be sure to read all prompts when inputting data. Prompts that have a £ symbol must be written in the form 1000.00 for example. Forms that mention date must be written in form YYYY-MM-DD where Y = year, M = month and D = day. The - inbetween is also mandatory. For fields requiring time, please insert in the form hh:mm where h = hours, m = minutes. This field is in 24 hour form, so for example half past 9 in the morning would be in the form 09:30, where as half past 9 at night would be 21:30. Some fields require specific inputs, please read carefully to ensure you are entering the correct possible text inputs.

To edit a table row, simply select the row and click the Add/Edit/Remove button above its respective table. The form will automatically fill out for you, and you can either select Remove to delete the row, or edit the row's qualities and select edit to save changes. Please note again that you cannot change an entry's primary key (see above).

After making a change to a table, please select the refresh table button above it to see the effect you have had on it.

If you open a pop up window and wish to close it, you may close it at any time my pressing the X in the top right.

If you have a value such as Notes where you have no notes for a particular entry, please input N/A to avoid any incomplete form rejections.

To change password or log out, please select the options screen.

Thank you for trying and testing the CRM & ROTA Management Hub by Mitchell Massiah.

If you have any troubles following this guide or have further questions, feel free to email me at mitchell.massiah@city.ac.uk , where I can hopefully clear up any confusions. have fun creating and manipulating data!