package org.example;

import org.example.models.Person;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoogleSheetScraper {

    private Person pickedPerson;

    public GoogleSheetScraper() {
        pickedPerson = new Person();
        scrapeSpreadsheet();
    }

    private void scrapeSpreadsheet(){
        String url = "https://docs.google.com/spreadsheets/d/1PnfSUFj-2wsLn4iNLTjVn54RKEJN0_3ikivoEsbwZcA/edit?usp=sharing";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table.waffle tr");

            List<Person> eligiblePeople = new ArrayList<>();

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");



                // Check if the row has enough columns, the age column is valid, and "Done?" is "Undone"
                if (cols.size() >= 13 && isNumeric(cols.get(2).text()) && isUndone(cols.get(12))) {
                    eligiblePeople.add(createPersonFromRow(cols));
                }

            }
            if (!eligiblePeople.isEmpty()) {
                Random rand = new Random();
                pickedPerson = eligiblePeople.get(rand.nextInt(eligiblePeople.size()));
                printPersonDetails(pickedPerson);
            } else {
                System.out.println("No eligible person found. \nStopping program.");
                System.exit(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Person returnPickedPerson(){
        if(pickedPerson.getAge() < 18 ){
            return null;
        }
        return pickedPerson;
    }


    private boolean isUndone(Element cell) {
        return cell.text().trim().equalsIgnoreCase("Undone");
    }

    private Person createPersonFromRow(Elements cols) {
        Person person = new Person();  // Create a new Person object for each row
        person.setName(cols.get(1).text());
        person.setAge(Integer.parseInt(cols.get(2).text()));
        person.setGender(cols.get(3).text());
        person.setHeight(Integer.parseInt(cols.get(4).text()));
        person.setWeight(Integer.parseInt(cols.get(5).text()));
        person.setLikedFood(cols.get(6).text());
        person.setDislikedFood(cols.get(7).text());
        person.setMealsPerDay(Integer.parseInt(cols.get(8).text()));
        person.setAlergies(cols.get(9).text().isEmpty() ? "None" : cols.get(9).text());
        person.setActivityLevel(cols.get(10).text());
        person.setDietGoal(cols.get(11).text());
        return person;  // Return the newly created Person object
    }

    private void printPersonDetails(Person person) {
        System.out.println("Selected Person: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Gender: " + person.getGender());
        System.out.println("Height: " + person.getHeight());
        System.out.println("Weight: " + person.getWeight());
        System.out.println("Liked Food: " + person.getLikedFood());
        System.out.println("Disliked Food: " + person.getDislikedFood());
        System.out.println("Meals Per Day: " + person.getMealsPerDay());
        System.out.println("Allergies: " + person.getAlergies());
        System.out.println("Activity Level: " + person.getActivityLevel());
        System.out.println("Diet Goal: " + person.getDietGoal());
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}