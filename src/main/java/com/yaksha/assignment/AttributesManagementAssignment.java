package com.yaksha.assignment;

class Address {  // Task 1: Using Multiple Classes
    // Attributes (Class properties)
    String street;
    String city;

    // Constructor to initialize Address object
    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    // Method to display address details
    public void displayAddress() {
        System.out.println("Street: " + street);
        System.out.println("City: " + city);
    }
}

class Person {  // Task 1: Using Multiple Classes
    // Attributes (Class properties)
    String name;
    int age;
    Address address;  // Reference to Address class

    // Constructor to initialize Person object
    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Method to display person details and address details
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        address.displayAddress();  // Accessing Address class method
    }

    // Method to modify person's details
    public void modifyDetails(String newName, int newAge) {
        this.name = newName;
        this.age = newAge;
    }

    // Method to modify address
    public void modifyAddress(String newStreet, String newCity) {
        this.address.street = newStreet;
        this.address.city = newCity;
    }
}

public class AttributesManagementAssignment {
    public static void main(String[] args) {
        // Task 2: Class Attributes and Task 3: Accessing Attributes
        Address address1 = new Address("Main St", "New York");  // Create an Address object
        Person person1 = new Person("John", 25, address1);  // Create a Person object
        person1.displayDetails();  // Accessing and displaying attributes

        // Task 4: Modifying Attributes
        System.out.println("\nModifying Details...");
        person1.modifyDetails("John Doe", 30);  // Modify Person attributes
        person1.modifyAddress("Broadway St", "Los Angeles");  // Modify Address attributes

        // Task 3: Accessing Modified Attributes
        person1.displayDetails();  // Display updated details
    }
}
