public class Country {
  private String country;
  private String description;
  private String initialFactors;
  private int population;

  public Country(String country, String description, String initialFactors, int population) {
    this.country = country;
    this.description = description;
    this.initialFactors = initialFactors;
    this.population = population;
  }    

  @Override
  public String toString() {
    return "\n" + country + ": " + description + initialFactors + "Starting Population: " + population; 
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
    
  public String getInitialFactors() {
    return this.initialFactors;
  }
    
  public void setInitialFactors(String initialFactors) {
    this.initialFactors = initialFactors;
  }

  public int getPopulation() {
    return this.population;
  }

  public void setPopulation(int population) {
    this.population = population;
  }
}