public class PopulationChanges {
  private String country;
  private String name;
  private int populationAdd;
  private double populationRate;

  public PopulationChanges(String country, String name, int populationAdd, double populationRate) {
    this.country = country;
    this.name = name;
    this.populationAdd = populationAdd;
    this.populationRate = populationRate;
  }    

  @Override
  public String toString() {
    return "\n" + country + ": " + name + " | Population Addition: " + populationAdd + " | Population Rate: " + populationRate;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getName() {
    return this.name;
  }
    
  public void setName(String name) {
    this.name = name;
  }

  public int getPopulationAdd() {
    return this.populationAdd;
  }

  public void setPopulationAdd(int populationAdd) {
    this.populationAdd = populationAdd;
  }
    
  public double getPopulationRate() {
    return this.populationRate;
  }
    
  public void setPopulationRate(double populationRate) {
    this.populationRate = populationRate;
  }
}