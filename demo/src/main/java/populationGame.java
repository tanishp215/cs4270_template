import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class populationGame {
  public static void main(String[] args) {
    Country[] countries = Data.getCountries();
    Scanner sc = new Scanner(System.in);
    System.out.println("Choose a country:");
    for (int i = 0; i<countries.length; i++) {
      System.out.println((i+1) + ": " + countries[i].getCountry());
    }
    int choice = Integer.parseInt(sc.nextLine());
    Country playerCountry = countries[choice - 1];
    System.out.println(playerCountry);

    System.out.println("\n Simulating population over 20 years...\n");
    for (int year = 1; year<=20; year++) {
      int population = Simulation.simulateYear(playerCountry);
      System.out.println("Year: " + year + "Population: " + population);
    }
  }
}

// birth rate, death rate, health care access, food supply, disease, infant mortality rate, economy, education, birth control and immigration. 
