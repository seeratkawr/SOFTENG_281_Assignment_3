package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Graph graph = new Graph();
  private Map<String, Country> countryMap = new HashMap<>();
  private List<Country> countries = new ArrayList<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    // read the countries and adjacencies from the files
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // create the countries and add them to graph
    for (String c : countries) {
      String[] parts = c.split(",");
      String countryName = parts[0];
      String continent = parts[1];
      String taxFees = parts[2];

      Country country = new Country(countryName, continent, taxFees);
      countryMap.put(countryName, country);
      this.countries.add(country);
    }

    // add the adjacencies to the graph
    for (String a : adjacencies) {
      String[] parts = a.split(",");
      Country country = countryMap.get(parts[0]);

      for (int i = 1; i < parts.length; i++) {
        Country neighbour = countryMap.get(parts[i]);
        graph.addEdge(country, neighbour);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String input = getValidCountryInput(MessageCli.INSERT_COUNTRY);

    // get the country object from the map and print its information
    Country country = countryMap.get(input);
    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(), country.getContinent(), country.getTaxFees());
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String source = getValidCountryInput(MessageCli.INSERT_SOURCE);
    String destination = getValidCountryInput(MessageCli.INSERT_DESTINATION);

    // check if the source and destination countries are the same
    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // get the source and destination country objects from the map and find the shortest path
    Country sourceCountry = countryMap.get(source);
    Country destinationCountry = countryMap.get(destination);
    List<Country> path = graph.getShortestPath(sourceCountry, destinationCountry);

    // initialise the route string, continents set, and total tax fees variables
    StringBuilder routeStr = new StringBuilder("[");
    Set<String> continents = new LinkedHashSet<>();
    int totalTaxFees = 0;
    boolean firstCountry = true;

    // iterate over the path, concatenate the route, continents, and calculate the total tax
    // fees
    for (Country country : path) {
      // if the country is not the first country, add the tax fees to the total
      if (!firstCountry) {
        totalTaxFees += Integer.parseInt(country.getTaxFees());
      }
      firstCountry = false;

      // append the country name to the route string
      routeStr.append(country.getName());
      if (!country.equals(destinationCountry)) {
        routeStr.append(", ");
      }

      // add the continent to the set of continents
      continents.add(country.getContinent());
    }

    routeStr.append("]");

    // create a string representation of the continents
    StringBuilder continentStr = new StringBuilder("[");
    for (String continent : continents) {
      continentStr.append(continent).append(", ");
    }

    if (continentStr.length() > 1) {
      continentStr.setLength(continentStr.length() - 2);
    }

    continentStr.append("]");

    // print the route, continents, and tax fees information
    MessageCli.ROUTE_INFO.printMessage(routeStr.toString());
    MessageCli.CONTINENT_INFO.printMessage(continentStr.toString());
    MessageCli.TAX_INFO.printMessage(String.valueOf(totalTaxFees));
  }

  private String getValidCountryInput(MessageCli promptMessage) {
    boolean validInput = false;
    String input = "";

    // validate the input
    while (!validInput) {
      try {
        // ask the user to enter the country name and read it from the console
        promptMessage.printMessage();
        input = Utils.scanner.nextLine();
        String capitalisedInput = Utils.capitalizeFirstLetterOfEachWord(input);

        // check if the country is valid, throw a custom exception if not
        validInput = checkCountryValid(capitalisedInput);
        input = capitalisedInput;

      } catch (InvalidCountryException e) {
        // print the error message if the custom exception is thrown
        System.out.println(e.getMessage());
      }
    }

    return input;
  }

  private boolean checkCountryValid(String countryName) throws InvalidCountryException {
    if (!countryMap.containsKey(countryName)) {
      throw new InvalidCountryException(MessageCli.INVALID_COUNTRY.getMessage(countryName));
    }

    return true;
  }
}
