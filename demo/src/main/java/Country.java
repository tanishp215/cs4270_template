// each country will have its name, a description, a population value, and a list of factors that impact the population

public class Country {
  private String country;
  private String description;
  private int population;
  private Factor[] factors;

  public Country(String country, String description, int population, Factor[] factors) {
    this.country = country;
    this.description = description;
    this.population = population;
    this.factors = factors;
  }    

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
    
  public String getDescription() {
    return this.description;
  }
    
  public void setDescription(String description) {
    this.description = description;
  }

  public int getPopulation() {
    return this.population;
  }

  public void setPopulation(int newPopulation) {
    this.population = newPopulation;
  }

  public Factor[] getFactors() {
    return factors;
  }

  public Factor getFactorByName(String name) {
    for (Factor f : factors) {
      if (f.getName().equalsIgnoreCase(name)) return f;
    }
    return null;
  }

  @Override
  public String toString() {
    String s = "Country: " + country + "\n" + description + "\n" + "Starting Population: " + population; 

    for (Factor f : factors) s += " - " + f + "\n";
    
    return s;
  }
}