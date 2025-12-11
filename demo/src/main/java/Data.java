// setting the starting values for each factor of each country, each one either adds to the population or multiplies the population

public class Data {
    public static Country[] getCountries() {
        Factor[] wakandaFactors = {
            new Factor("Birth Rate", 500_000, 1.00, "add"),
            new Factor("Death Rate", -300_000, 1.00, "add"),
            new Factor("Health Care", 0, 1.02, "mult"),
            new Factor("Food Supply", 0, 1.015, "mult"),
            new Factor("Disease", 0, 1.015, "mult"),
            new Factor("Infant Mortality Rate", -1_250, 1.00, "add"),
            new Factor("Economy", 0, 1.025, "mult"),
            new Factor("Education", 0, 1.015, "mult"),
            new Factor("Birth Control", 0, 0.98, "mult"),
            new Factor("Immigration", 0, 1.00, "add"),
        };

        Country wakanda = new Country("Wakanda", "Advanced African nation rich with vibranium", 50_000_000, wakandaFactors);

        Factor[] asgardFactors = {
            new Factor("Birth Rate", 150_000, 1.00, "add"),
            new Factor("Death Rate", -200_000, 1.00, "add"),
            new Factor("Health Care", 0, 1.00, "mult"),
            new Factor("Food Supply", 0, 1.01, "mult"),
            new Factor("Disease", 0, 1.00, "mult"),
            new Factor("Infant Mortality Rate", -2_500, 1.00, "add"),
            new Factor("Economy", 0, 0.985, "mult"),
            new Factor("Education", 0, 0.995, "mult"),
            new Factor("Birth Control", 0, 1.00, "mult"),
            new Factor("Immigration", 25_000, 1.00, "add"),
        };

        Country asgard = new Country("Asgard", "Mythical Norse city full of lawfulness and order, home to Aesir gods such as Thor", 10_000_000, asgardFactors);

        Factor[] atlantisFactors = {
            new Factor("Birth Rate", 400_000, 1.00, "add"),
            new Factor("Death Rate", -350_000, 1.00, "add"),
            new Factor("Health Care", 0, 1.05, "mult"),
            new Factor("Food Supply", 0, 1.02, "mult"),
            new Factor("Disease", 0, 1.005, "mult"),
            new Factor("Infant Mortality Rate", -2_000, 1.00, "add"),
            new Factor("Economy", 0, 1.025, "mult"),
            new Factor("Education", 0, 1.01, "mult"),
            new Factor("Birth Control", 0, 0.985, "mult"),
            new Factor("Immigration", 75_000, 1.00, "add"),
        };

        Country atlantis = new Country("Atlantis", "A legendary underwater city known for its incredible wealth and elaborate structure", 25_000_000, atlantisFactors);

        Factor[] ozFactors = {
            new Factor("Birth Rate", 25_000, 1.00, "add"),
            new Factor("Death Rate", -20_000, 1.00, "add"),
            new Factor("Health Care", 0, 1.005, "mult"),
            new Factor("Food Supply", 0, 1.01, "mult"),
            new Factor("Disease", 0, 0.985, "mult"),
            new Factor("Infant Mortality Rate", -250, 1.00, "add"),
            new Factor("Economy", 0, 1.00, "mult"),
            new Factor("Education", 0, 1.025, "mult"),
            new Factor("Birth Control", 0, 0.99, "mult"),
            new Factor("Immigration", 5_000, 1.00, "add"),
        };

        Country oz = new Country("Oz", "A strange land full of strange creatures", 1_000_000, ozFactors);

        Factor[] narniaFactors = {
            new Factor("Birth Rate", 1_500_000, 1.00, "add"),
            new Factor("Death Rate", -1_250_000, 1.00, "add"),
            new Factor("Health Care", 0, 1.02, "mult"),
            new Factor("Food Supply", 0, 0.995, "mult"),
            new Factor("Disease", 0, 1.005, "mult"),
            new Factor("Infant Mortality Rate", -5_000, 1.00, "add"),
            new Factor("Economy", 0, 1.025, "mult"),
            new Factor("Education", 0, 1.01, "mult"),
            new Factor("Birth Control", 0, 0.995, "mult"),
            new Factor("Immigration", 125_000, 1.00, "add"),
        };

        Country narnia = new Country("Narnia", "A world in which magic comes to life, full of talking animals, mythical creatures, diverse lands", 100_000_000, narniaFactors);

        return new Country[] {wakanda, asgard, atlantis, oz, narnia};
    }
}