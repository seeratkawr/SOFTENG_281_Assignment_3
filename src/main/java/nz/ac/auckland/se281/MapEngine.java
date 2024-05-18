package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  List<String> country = new ArrayList<>();
  List<String> continent = new ArrayList<>();
  List<String> fees = new ArrayList<>();

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
      country.add(parts[0]);
      continent.add(parts[1]);
      fees.add(parts[2]);
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

        if (!country.contains(input)) {
          throw new InvalidCountryException(input);
        }

        validInput = true;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }

    int index = country.indexOf(input);
    MessageCli.COUNTRY_INFO.printMessage(country.get(index), continent.get(index), fees.get(index));
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
