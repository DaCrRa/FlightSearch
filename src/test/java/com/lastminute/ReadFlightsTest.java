package com.lastminute;

import java.util.List;

import org.junit.Test;

import static com.lastminute.CsvFiles.readAllRecords;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class ReadFlightsTest
{
  private final int flightCount = 89;

  @Test
  public void readFlightPrices()
  {
    List<List<String>> prices = readAllRecords(fullPathTo("flight-prices.csv"));

    allFlightsRead(prices);
    startsWith(prices, asList("IB2818", "186"));
    endsWith(prices, asList("LH7260", "191"));
  }

  @Test
  public void readFlightRoutes()
  {
    List<List<String>> routes = readAllRecords(fullPathTo("flight-routes.csv"));

    allFlightsRead(routes);
    startsWith(routes, asList("CPH", "FRA", "IB2818"));
    endsWith(routes, asList("MAD", "AMS", "LH7260"));
  }

  private String fullPathTo(String fileName)
  {
    return getClass().getClassLoader().getResource(fileName).getPath();
  }

  private void allFlightsRead(List<List<String>> flights)
  {
    assertEquals(flightCount, flights.size());
  }

  private void startsWith(List<List<String>> actual, List<String> expected)
  {
    assertEquals(expected, actual.get(0));
  }

  private void endsWith(List<List<String>> actual, List<String> expected)
  {
    assertEquals(expected, actual.get(actual.size() - 1));
  }
}
