package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
      String countryName = parts[0];
      String continent = parts[1];
      String taxFees = parts[2];
      Country country = new Country(countryName, continent, taxFees);
      countryMap.put(countryName, country);
      this.countries.add(country);
    }

    for (String a : adjacencies) {
      String[] parts = a.split(",");
      Country country = countryMap.get(parts[0]);
      for (int i = 1; i < parts.length; i++) {
        Country neighbour = countryMap.get(parts[i]);
        graph.addEdge(country, neighbour);
      }
    }

    /*System.out.println("Country Map Contents:");
    for (Map.Entry<String, Country> entry : countryMap.entrySet()) {
      String countryName = entry.getKey();
      Country country = entry.getValue();
      System.out.println(
          countryName
              + " -> "
              + country.getName()
              + ", "
              + country.getContinent()
              + ", "
              + country.getTaxFees());
    }

    System.out.println("\nAdjacency List Contents:");
    for (Map.Entry<Country, List<Country>> entry : graph.adjNodes.entrySet()) {
      Country country = entry.getKey();
      List<Country> neighbors = entry.getValue();
      System.out.print(country.getName() + " -> ");
      for (Country neighbor : neighbors) {
        System.out.print(neighbor.getName() + ", ");
      }
      System.out.println();
    }*/
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
    } else {
      Country sourceCountry = countryMap.get(source);
      Country destinationCountry = countryMap.get(destination);
      List<Country> path = graph.getShortestPath(sourceCountry, destinationCountry);
      if (path.isEmpty()) {
        MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      } else {
        StringBuilder routeStr = new StringBuilder("[");
        Set<String> continents = new HashSet<>();
        int totalTaxFees = 0;

        boolean firstCountry = true;
        for (Country country : path) {
          if (!firstCountry) {
            totalTaxFees += Integer.parseInt(country.getTaxFees());
          }
          firstCountry = false;

          routeStr.append(country.getName());

          if (!country.equals(destinationCountry)) {
            routeStr.append(", ");
          }

          continents.add(country.getContinent());
          totalTaxFees += Integer.parseInt(country.getTaxFees());
        }

        routeStr.append("]");

        StringBuilder continentStr = new StringBuilder("[");
        for (String continent : continents) {
          continentStr.append(continent).append(", ");
        }

        if (continentStr.length() > 1) {
          continentStr.setLength(continentStr.length() - 2);
        }

        continentStr.append("]");

        MessageCli.ROUTE_INFO.printMessage(routeStr.toString());
        MessageCli.CONTINENT_INFO.printMessage(continentStr.toString());
        MessageCli.TAX_INFO.printMessage(String.valueOf(totalTaxFees));
      }
    }
  }
}
