import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws Exception {
    Country Wakanda = new Country("Country: Wakanda", "Description: Advanced African nation rich with vibranium, making it a powerful country", "Birth rate: 1 million per year, Death rate: 0.6 million per year, Health care access: 9/10, Food supply: 8/10, Disease: 8/10, Infant morality rate: 2,500 per year, Economy: 10/10, Education: 8/10, Birth control: 80%, Immigration: 0 per year ", 50_000_000);

    Country Asgard = new Country("Country: Asgard", "Description: Mythical Norse city full of lawfulness and order, home to Aesir gods such as Thor", "Birth rate: 0.3 million per year, Death rate: 0.35 million per year, Health care access: 5/10, Food supply: 7/10, Disease: 5/10, Infant morality rate: 5,000 per year, Economy: 2/10, Education: 4/10, Birth control: 0%, Immigration: 50,000 per year ", 10_000_000);

    Country Atlantis = new Country("Country: Atlantis", "Description: A legendary underwater city known for its incredible wealth and elaborate structure", "Birth rate: 0.8 million per year, Death rate: 0.7 million per year, Health care access: 6/10, Food supply: 9/10, Disease: 6/10, Infant morality rate: 4,000 per year, Economy: 10/10, Education: 6/10, Birth control: 60%, Immigration: 150,000 per year ", 25_000_000);

    Country Oz = new Country("Country: Oz", "Description: A strange land full of strange creatures", "Birth rate: 50 thousand per year, Death rate: 40 thousand per year, Health care access: 6/10, Food supply: 7/10, Disease: 2/10, Infant morality rate: 500 per year, Economy: 5/10, Education: 10/10, Birth control: 40%, Immigration: 10,000 per year ", 1_000_000);

    Country Narnia = new Country("Country: Narnia", "Description: A world in which magic comes to life, full of talking animals, mythical creatures, diverse lands", "Birth rate: 3 million per year, Death rate: 2.5 million per year, Health care access: 9/10, Food supply: 4/10, Disease: 6/10, Infant morality rate: 10,000 per year, Economy: 4/10, Education: 7/10, Birth control: 20%, Immigration: 250,000 per year ", 100_000_000);
  }
}

//birth rate, death rate, health care access, food supply, disease, infant mortality rate, economy, education, birth control and immigration. 
