package nz.ac.auckland.se281;

/**
 * This class represents a custom exception that is thrown when the user enters an invalid country.
 */
public class InvalidCountryException extends Exception {
  public InvalidCountryException(String message) {
    super(message);
  }
}
