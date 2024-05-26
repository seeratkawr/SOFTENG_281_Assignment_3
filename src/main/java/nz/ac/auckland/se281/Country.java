package nz.ac.auckland.se281;

/** This class represents a country in the world map. */
public class Country {
  private String name;
  private String continent;
  private String taxFees;

  /**
   * Create a new country. 
   * 
   * @param name the name of the country
   * @param continent the continent in which the country is located
   * @param taxFees the tax fees of the country
   */
  public Country(String name, String continent, String taxFees) {
    this.name = name;
    this.continent = continent;
    this.taxFees = taxFees;
  }

  /**
   * Get the name of the country.
   * 
   * @returns the name of the country
   */
  public String getName() {
    return name;
  }

  /**
   * Get the continent in which the country is located.
   * 
   * @returns the continent of the country
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Get the tax fees of the country.
   * 
   * @returns the tax fees of the country
   */
  public String getTaxFees() {
    return taxFees;
  }

  /**
   * Set the name of the country.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * Check if two countries are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) return false;
    return true;
  }
}
