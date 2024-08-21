Personalized Meal Plan Generator
This project is a Java-based application that generates personalized meal plans and corresponding video scripts using AI. It scrapes user information from a Google Spreadsheet, processes the data, and uses a local AI model to generate customized meal plans. The generated plans are saved as .docx files, and video scripts are created for each meal plan.

Features
Google Sheets Scraper: Extracts user data from a Google Spreadsheet.
AI Meal Plan Generator: Connects to a local AI model to create personalized meal plans based on user preferences and dietary goals.
Video Script Generator: Automatically generates a video script for presenting the meal plan.
Document Management: Saves the generated meal plans as .docx files in a specified directory.
Project Structure
Main.java: The entry point of the application. It orchestrates the entire workflow from scraping data to generating meal plans and scripts.
Person.java: A model class representing a user with attributes like name, age, gender, dietary preferences, and goals.
GoogleSheetScraper.java: Handles the extraction of user data from the Google Spreadsheet.
ChatModelConnector.java: Connects to a locally hosted AI model to generate meal plans and video scripts.
FolderManager.java: Manages the creation of directories and .docx files to store generated meal plans.
Setup
Prerequisites
Java 8+
Maven
Local AI model running on http://localhost:11434
Internet Connection to access Google Sheets
Installation
Clone the repository:

sh
Copiază codul
git clone https://github.com/yourusername/meal-plan-generator.git
cd meal-plan-generator
Install the required dependencies:

sh
Copiază codul
mvn clean install
Ensure that the local AI model is running on http://localhost:11434.

Update the Google Sheets URL in GoogleSheetScraper.java if necessary.

Running the Project
Run the application:

sh
Copiază codul
mvn exec:java -Dexec.mainClass="org.example.Main"
The application will scrape the Google Spreadsheet, generate a personalized meal plan, and save it as a .docx file in the specified directory.

A video script will also be generated based on the meal plan.

Customization
Google Sheets URL: Update the URL in GoogleSheetScraper.java to point to your specific Google Sheet.
AI Model Configuration: Modify ChatModelConnector.java to change the AI model parameters such as temperature and modelName.
Document Directory: The output directory can be changed in FolderManager.java by updating the PATH constant.
Contributing
Feel free to open issues or submit pull requests if you have suggestions or improvements.
