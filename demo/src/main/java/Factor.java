// each factor has its name, a populationAdd or multiplier, if it uses add then the multiplier is just 1.00, if it uses 
// multiplier then the add is just 0, and a type, either a add or mult so the sliders later on only allow the user to
// change either the add or multiplier

public class Factor {
  private String name;
  private int populationAdd;
  private double multiplier;
  private String type;

  public Factor(String name, int populationAdd, double multiplier, String type) {
    this.name = name;
    this.populationAdd = populationAdd;
    this.multiplier = multiplier;
    this.type = type;
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

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  @Override
  public String toString() {
    return "\n" + name + " | Population Add: " + populationAdd + " | Population Multiplier: x" + multiplier;
  }
}