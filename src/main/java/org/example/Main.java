package org.example;

import org.example.models.ChatModelConnector;
import org.example.models.Person;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        GoogleSheetScraper scraper = new GoogleSheetScraper();
        Person pers = scraper.returnPickedPerson();


        FolderManager folderManager;

        var model = new ChatModelConnector().connectModel();

        String prompt = """
                You're my nutritionist. I need you to personalize a meal plan for me and include how many calories each meal has. I'm looking for an example of meals based on my preferences, including my liked/disliked food, alergies and my diet goal. I'm trying to
                """ + pers.getDietGoal() + ". I'm a " + pers.getAge() + " years old, " + pers.getGender() + " and I weight "+ pers.getWeight() + " kg, I'm " + pers.getHeight() + " cm tall. My daily activity level is : "
                + pers.getActivityLevel() + " this should be taken in consideration as well. I like eating " + pers.getLikedFood() + " and I don't really enjoy eating " + pers.getDislikedFood() + ". Make the plan so it has  " + pers.getMealsPerDay()
                + " meals per day. Make sure to make me the plan for the entire week Monday-Sunday and mark each day like this **Monday**, **Tuesday** , **Wednesday** etc";

        if(!pers.getAlergies().contains("null"))
            prompt +=  " Make sure to avoid the following foods by all means: " + pers.getAlergies() + " cause I have alergies.";
        else {
            prompt +=  " I don't have any allergies. ";
        }

        String mealPrepResponse = model.generate(prompt);
        System.out.println(mealPrepResponse);
        folderManager = new FolderManager(pers.getName());
        folderManager.createDocxFile(mealPrepResponse);

        String template = """
                Host: “This meal was personalized for [NAME], if you want one tailored just for you, make sure to drop a comment and fill the form in BIO. Now let’s start with the delicious meals.” (10 seconds)
                                
                Monday
                (Visuals of breakfast food appear on screen)
                Host: "Good morning! Today's breakfast is greek yogurt with banana, almond butter, and granola, for a total of 550 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "For dinner, you'll be having grilled chicken breast with sweet potato, brown rice, and avocado 700 calories." (5 seconds)
                Tuesday
                (Visuals of breakfast food appear on screen)
                Host: "Start your day off right with Whole-grain waffles with scrambled eggs, turkey sausage, and mixed berries 500 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "For dinner you’ll enjoy beef and vegetable stir-fry (excluding bell peppers) with brown rice 750 calories." (5 seconds)
                Wednesday
                (Visuals of breakfast food appear on screen)
                Host: "Kick off your day with a protein smoothie with banana, spinach, almond milk, and protein powder 600 calories ." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "For dinner, you'll be having Grilled salmon with quinoa, broccoli, and whole-grain bread 700 calories." (5 seconds)
                Thursday
                (Visuals of breakfast food appear on screen)
                Host: "Today's breakfast is Avocado toast on whole-grain bread with scrambled eggs and cherry tomatoes 500 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "Dinner is a chicken fajita with brown rice, black beans, and mixed vegetables 750 calories." (10 seconds)
                Friday
                (Visuals of breakfast food appear on screen)
                Host: "Start your day with oatmeal with almond butter, banana, and honey 500 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "Lunch is a Grilled turkey burger on whole-grain bun with sweet potato fries 750 calories." (5 seconds)
                Saturday
                (Visuals of breakfast food appear on screen)
                Host: "Today's breakfast is Whole-grain pancakes with scrambled eggs, turkey bacon, and mixed berries 550 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "For dinner, you'll be having Beef and mushroom gravy over brown rice with steamed asparagus 700 calories." (5 seconds)
                Sunday
                (Visuals of breakfast food appear on screen)
                Host: "Start your day off right with Greek yogurt parfait with granola, banana, and mixed berries 500 calories." (5 seconds)
                (Visuals of dinner food appear on screen)
                Host: "Dinner is a grilled chicken breast with roasted vegetables and quinoa (700 calories." (5 seconds)
                Snacks
                (Visuals of snack foods appear on screen)
                Host: "Remember to stay hydrated and grab one of these snacks: fresh fruits, cut veggies with hummus, Greek yogurt with mixed nuts and seeds, or hard-boiled eggs. Each snack has approximately 150-200 calories and 5-10g of protein." (5 seconds)
                Outro
                Host: "That's it for this week! Remember to stay hydrated and listen to your body throughout your meal plan. Thanks for watching!"
                                                              
                """;
        String script = model.generate("Make a video script that's maximum 1 minute and 40 seconds long, following this template: " + template + " but using only the following meals for each day. Here's the meals you should use': " + mealPrepResponse);
        System.out.println(script);

    }
}