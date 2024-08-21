package org.example.models;

public class Person {
    private String name;
    private int age;
    private String gender;
    private int height;
    private int weight;
    private String likedFood;
    private String dislikedFood;
    private int mealsPerDay;
    private String activityLevel;
    private String alergies;
    private String dietGoal; // Weight loss, muscle gain, improved energy, better health
    private int budget;
    private String placeToWorkout;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceToWorkout() {
        return placeToWorkout;
    }

    public void setPlaceToWorkout(String placeToWorkout) {
        this.placeToWorkout = placeToWorkout;
    }

    public Person() {

    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age > 18 && age < 100)
            this.age = age;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLikedFood() {
        return likedFood;
    }

    public void setLikedFood(String likedFood) {
        this.likedFood = likedFood;
    }

    public String getDislikedFood() {
        return dislikedFood;
    }

    public void setDislikedFood(String dislikedFood) {
        this.dislikedFood = dislikedFood;
    }

    public int getMealsPerDay() {
        return mealsPerDay;
    }

    public void setMealsPerDay(int mealsPerDay) {
        this.mealsPerDay = mealsPerDay;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getAlergies()
    { if(alergies == null)
        return "null";

        return alergies;
    }

    public void setAlergies(String alergies) {
        this.alergies = alergies;
    }

    public String getDietGoal() {
        return dietGoal;
    }

    public void setDietGoal(String dietGoal) {
        this.dietGoal = dietGoal;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
