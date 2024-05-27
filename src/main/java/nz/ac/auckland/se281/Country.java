package nz.ac.auckland.se281;

/** This class represents a country in the world map. */
public class Country {
  private String name;
  private String continent;
  private String taxFees;

  /**
   * Constructor for creating a new country.
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
   * @return the continent in which the country is located
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Get the tax fees of the country.
   *
   * @return the tax fees of the country
   */
  public String getTaxFees() {
    return taxFees;
  }

  /** Set the name of the country. */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /** Check if two countries are equal. */
  @Override
  public boolean equals(Object obj) {
    // If the object is the same as this country, return true, as this country has already been
    // initialised
    if (this == obj) {
      return true;
    }
    // If the object is null, return false, as this country has not been initialised
    if (obj == null) {
      return false;
    }
    // If the object is not an instance of the Country class, return false, as the object is not a
    // country
    if (getClass() != obj.getClass()) {
      return false;
    }
    // Cast the object to a country
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }
}
