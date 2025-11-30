public class Factor {
  private String name;
  private int populationAdd;
  private double multiplier;

  public Factor(String name, int populationAdd, double multiplier) {
    this.name = name;
    this.populationAdd = populationAdd;
    this.multiplier = multiplier;
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
    
  public double getMultiplier() {
    return this.multiplier;
  }
    
  public void setMultiplier(double multiplier) {
    this.multiplier = multiplier;
  }

  @Override
  public String toString() {
    return "\n" + name + " | Population Add: " + populationAdd + " | Population Multiplier: x" + multiplier;
  }
}