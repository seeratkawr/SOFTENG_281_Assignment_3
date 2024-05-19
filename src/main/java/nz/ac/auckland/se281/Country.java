package nz.ac.auckland.se281;

public class Country {
  private String name;
  private String continent;
  private String taxFees;

  public Country(String name, String continent, String taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public String getTaxFees() {
    return taxFees;
  }
}
