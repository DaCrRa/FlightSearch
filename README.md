# Flight search

This project implements a simple flight search.

**Given**

* airport of origin
* airport of destination
* date of departure
* number of passengers

**When** you perform the search

**Then** you get the list of:

* flight code matching the route
* total flight price (for all passengers)

## Implementation considerations

Flight routes and base prices are given in the CSV format, under `src/main/resources` directory. There is a rudimentary CSV client for reading the data (`com.lastminute.CsvFiles`) with simple tests.

## Business rules and constraints

* Search only for direct flights (no stopovers)
* Assume there is always seat availability
* All flights leave every day
* There is only one currency (€)
* Ticket price is based on the number of days to the flight departure and the base price of the flight:

| days prior to the departure date | % of the base price |
|----------------------------------|---------------------|
| more than 30 (i.e. >= 31)        | 80%                 |
| 30 - 16                          | 100%                |
| 15 - 3                           | 120%                |
| less that 3 (i.e. <= 2)          | 150%                |

## Examples

* 1 passenger, 31 days to the departure date, flying AMS -> FRA

  flights:

    * TK2372, 157.6 €
    * TK2659, 198.4 €
    * LH5909, 90.4 €

* 3 passengers, 15 days to the departure date, flying LHR -> IST

  flights:

    * TK8891, 900 € (3 * (120% of 250))
    * LH1085, 532.8 € (3 * (120% of 148))

* 2 passengers, 2 days to the departure date, flying BCN -> MAD

  flights:

    * IB2171, 777 € (2 * (150% of 259))
    * LH5496, 879 € (2 * (150% of 293))

* CDG -> FRA

  no flights available
