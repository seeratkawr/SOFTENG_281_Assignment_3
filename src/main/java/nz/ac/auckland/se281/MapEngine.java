package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  Graph graph = new Graph();
  private Map<String, Country> countryMap = new HashMap<>();
  private List<Country> countries = new ArrayList<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String c : countries) {
      String[] parts = c.split(",");
      String country = parts[0];
      String continent = parts[1];
      String taxFees = parts[2];
      Country countryObj = new Country(country, continent, taxFees);
      countryMap.put(country, countryObj);
      this.countries.add(countryObj);
    }

    for (String a : adjacencies) {
      String[] parts = a.split(",");
      String country = parts[0];
      for (int i = 1; i < parts.length; i++) {
        graph.addEdge(country, parts[i]);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    boolean validInput = false;
    String input = "";

    while (!validInput) {
      try {
        MessageCli.INSERT_COUNTRY.printMessage();
        input = Utils.scanner.nextLine();
        input = Utils.capitalizeFirstLetterOfEachWord(input);

        if (!countryMap.containsKey(input)) {
          throw new InvalidCountryException(input);
        }

        validInput = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    Country country = countryMap.get(input);
    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), country.getTaxFees());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    boolean sourceValidInput = false;
    String source = "";
    boolean destinationValidInput = false;
    String destination = "";

    while (!sourceValidInput) {
      try {
        MessageCli.INSERT_SOURCE.printMessage();
        source = Utils.scanner.nextLine();
        source = Utils.capitalizeFirstLetterOfEachWord(source);

        if (!countryMap.containsKey(source)) {
          throw new InvalidCountryException(source);
        }

        sourceValidInput = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    while (!destinationValidInput) {
      try {
        MessageCli.INSERT_DESTINATION.printMessage();
        destination = Utils.scanner.nextLine();
        destination = Utils.capitalizeFirstLetterOfEachWord(destination);

        if (!countryMap.containsKey(destination)) {
          throw new InvalidCountryException(destination);
        }

        destinationValidInput = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    }
  }
}
