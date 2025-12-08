public class Simulation {
    public static int simulateYear(Country country) {
        int population = country.getPopulation();
        Factor[] factors = country.getFactors();

        for (Factor f : factors) {
            population += f.getPopulationAdd();
        }

        for (Factor f : factors) {
            population = (int)(population * f.getMultiplier());
        }

        applyLinkedFactors(country);

        country.setPopulation(population);
        return population;
    }

    private static void applyLinkedFactors(Country country) {
        Factor food = country.getFactorByName("Food Supply");
        Factor birth = country.getFactorByName("Birth Rate");
        Factor death = country.getFactorByName("Death Rate");
        Factor education = country.getFactorByName("Education");
        Factor economy = country.getFactorByName("Economy");
        Factor health = country.getFactorByName("Health Care");
        Factor disease = country.getFactorByName("Disease");
        Factor infant = country.getFactorByName("Infant Mortality Rate");
        if (food.getMultiplier() < 0.98 && birth.getPopulationAdd() > country.getPopulation() * 0.1) {
            death.setPopulationAdd((int)(death.getPopulationAdd() * 1.1));
        } 
        if (education.getMultiplier() > 1.20) {
            economy.setMultiplier(economy.getMultiplier() * 1.1);
            birth.setPopulationAdd((int)(birth.getPopulationAdd() * 0.9));
        }
        if (health.getMultiplier() < 0.95) {
            disease.setMultiplier(disease.getMultiplier() * 0.9);
            infant.setPopulationAdd((int)(infant.getPopulationAdd() * 0.9));
        }
    }
}