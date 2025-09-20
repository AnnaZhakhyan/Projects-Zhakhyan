package persistence;

import datarecords.Account;
import datarecords.Customer;
import datarecords.Discount;
import datarecords.Client;
import datarecords.Flight;
import datarecords.StaticDiscount;
import datarecords.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.sql.DataSource;

public class DatabaseConnection {
    Exception e;
    DataSource dataSource = DBProvider.getDataSource("aisdb");


    public DatabaseConnection(){};


    public Exception getExeption() {
        return e;
    }

    public Account authenticateUser(String user, String pass) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "select employee.firstname, employee.lastname, employee.pos, employee.id from employee where username = ? and password = ?;";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, user);
                    statement.setString(2, pass);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String firstname = resultSet.getString("firstname").trim();
                        String lastname = resultSet.getString("lastname").trim();
                        String role = resultSet.getString("pos");
                        int id = resultSet.getInt("id");

                        Account account = new Account(firstname, lastname, role, id);
                        return account;
                    } else {
                        System.out.println("Account Not Found!");
                        return null;
                    }
                }
            } else {
                e = new Exception("Failed to establish connection to the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }

    }

    public int createBooking(int employee, Customer customer, int price, int transaction){
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "INSERT INTO public.booking (timerecord, customer, employee, price, email, address, transaction) VALUES(?::timestamp, ?, ?, ?, ?, ?, ?);";
                System.out.println(sql);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
                    statement.setString(1,timestamp);
                    statement.setInt(2, customer.id());
                    statement.setInt(3, employee);
                    statement.setInt(4, price);
                    statement.setString(5, customer.email());
                    statement.setString(6, customer.address());
                    statement.setInt(7, transaction);
                    statement.executeUpdate();

                    int id = -1;
                    try (PreparedStatement statement2 = connection.prepareStatement("SELECT b.id FROM booking b WHERE b.timerecord = ?::timestamp")) {
                        statement2.setString(1,timestamp);
                        ResultSet resultSet = statement2.executeQuery();
                        while (resultSet.next()) {
                            id = resultSet.getInt("id");
                        }
                    }
                    return id;
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return -1;
        }
    }

    public int createTicket(int booking_id, Ticket ticket){
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                String sql = "INSERT INTO tickets(passenger, doc_type, doc_id, seat, booking, flight) VALUES";
                sql += "( " + ticket.passenger() + ", '" + ticket.documentType() + "', '" + ticket.documentID() + "', '" + ticket.seat() + "', " + ticket.booking() + ", " + ticket.flight() + ");";
                System.out.println(sql);
                int id = -1;
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                    System.out.println("Ticket created successfully !");
                }
                try (PreparedStatement statement = connection.prepareStatement("SELECT t.id FROM tickets t WHERE doc_id = ? and t.flight = ?")) {
                    statement.setString(1, ticket.documentID());
                    statement.setInt(2, ticket.flight());
                    System.out.println(statement);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        id = resultSet.getInt("id");
                    }
                }
                return id;
            } else {
                System.out.println("Failed to establish connection to the database.");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return -1;
        }
    }

    public void createStaticDiscount(int ticket, ArrayList<StaticDiscount> discounts){
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                String sql = "INSERT INTO public.static_discounts (ticket, amount, proc, discountname) VALUES";
                for (StaticDiscount staticDiscount : discounts) {
                    sql += "(" + ticket + ", " + staticDiscount.amount() + ", " + staticDiscount.proc() + ", '" + staticDiscount.discountName() + "'),";
                }
                sql = sql.substring(0, sql.length()-1);
                sql += ";";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    System.out.println(statement);
                    statement.executeUpdate();
                    System.out.println("Discounts added Correctly!");
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void createExtra(int ticket, ArrayList<String> extras){
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                String sql = "INSERT INTO public.extras (extra, ticket) VALUES";
                for (String string : extras) {
                    sql += "('" + string + "', " + ticket + "),";
                }
                sql = sql.substring(0, sql.length()-1);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    System.out.println(statement);
                    statement.executeUpdate();
                    System.out.println("Extras added Correctly!");
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public ArrayList<Flight> findFlights(String depIATA, String arrIATA, String outDate) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "select flights.id, airlines.fullname as airline, TRIM(a1.name) as departure, TRIM(a2.name) as arrival, ceiling((airlines.costs * routes.distance)/100) as cost, (flights.dep_date + flights.dep_time) as dep_datetime, (flights.dep_date + flights.dep_time) + CEILING(routes.distance / 900) * INTERVAL '1 hour' AS arr_datetime, CEILING(routes.distance / 900) as duration , a1.timezone as a1_tz, a2.timezone as a2_tz FROM flights JOIN routes on flights.route = routes.route_id join airlines on routes.airline = airlines.iata JOIN airport2 a1 on routes.src_airport = a1.iata JOIN airport2 a2 on routes.des_airport = a2.iata WHERE a1.name = ? and a2.name = ? AND flights.dep_date >= DATE(?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, depIATA);
                    statement.setString(2, arrIATA);
                    statement.setString(3, outDate);
                    System.out.println(statement);
                    ResultSet resultSet = statement.executeQuery();
                    ArrayList<Flight> arrayList = new ArrayList<>();
                    while (resultSet.next()) {
                        ArrayList<Integer> flightIDs = new ArrayList<>();
                        flightIDs.add(Integer.valueOf(resultSet.getString("id")));

                        ArrayList<String> airlines = new ArrayList<>();
                        airlines.add(resultSet.getString("airline"));

                        ArrayList<String> airports = new ArrayList<>();
                        airports.add(resultSet.getString("departure"));
                        airports.add(resultSet.getString("arrival"));

                        ArrayList<String> departures = new ArrayList<>();
                        departures.add(resultSet.getString("dep_datetime"));

                        ArrayList<String> arrivals = new ArrayList<>();
                        arrivals.add(resultSet.getString("arr_datetime"));
                        
                        ArrayList<String> timezones = new ArrayList<>();
                        timezones.add(resultSet.getString("a1_tz"));
                        timezones.add(resultSet.getString("a2_tz"));

                        int price = resultSet.getInt("cost");

                        arrayList.add(new Flight(flightIDs, airlines, airports, departures, arrivals, null, price, timezones));
                    }
                    sql = "select CEILING(r1.distance * l1.costs/100) + CEILING(r2.distance * l2.costs/100)as price, (f1.dep_date + f1.dep_time) as f1_dep, f1.id as f1_id, l1.fullName as f1_al, TRIM(a1.name) as f1_src, TRIM(a2.name) as f1_des, (f1.dep_date + f1.dep_time) + INTERVAL '1 hour' * ROUND(r1.distance / 900) as f1_arr, (f2.dep_date + f2.dep_time) - ((f1.dep_date + f1.dep_time) + INTERVAL '1 hour' * ROUND(r1.distance / 900)) AS diff, (f2.dep_date + f2.dep_time) as f2_dep, f2.id as f2_id, l2.fullName as f2_al, TRIM(a3.name) as f2_des, (f2.dep_date + f2.dep_time) + INTERVAL '1 hour' * ROUND(r2.distance / 900) as f2_arr , a1.timezone as a1_tz, a2.timezone as a2_tz, a3.timezone as a3_tz from flights f1 join routes r1 on r1.route_id = f1.route join routes r2 on r2.src_airport = r1.des_airport join flights f2 on r2.route_id = f2.route join airport2 a1 on r1.src_airport = a1.iata join airport2 a2 on r1.des_airport = a2.iata join airport2 a3 on r2.des_airport = a3.iata join airlines l1 on l1.iata = r1.airline join airlines l2 on l2.iata = r2.airline where a1.name = ? and a3.name = ? AND f1.dep_date >= DATE(?) AND (f2.dep_date + f2.dep_time) - ((f1.dep_date + f1.dep_time) + INTERVAL '1 hour' * ROUND(r1.distance / 900)) between INTERVAL '30 minutes' and INTERVAL '6 hours' order by (f1.dep_date + f1.dep_time);";
                    try (PreparedStatement statement2 = connection.prepareStatement(sql)) {
                        statement2.setString(1, depIATA);
                        statement2.setString(2, arrIATA);
                        statement2.setString(3, outDate);
                        System.out.println(statement2);
                        ResultSet resultSet2 = statement2.executeQuery();
                        while (resultSet2.next()) {
                            ArrayList<Integer> flightIDs = new ArrayList<>();
                            flightIDs.add(Integer.valueOf(resultSet2.getString("f1_id")));
                            flightIDs.add(Integer.valueOf(resultSet2.getString("f2_id")));

                            ArrayList<String> airlines = new ArrayList<>();
                            airlines.add(resultSet2.getString("f1_al"));
                            airlines.add(resultSet2.getString("f2_al"));

                            ArrayList<String> airports = new ArrayList<>();
                            airports.add(resultSet2.getString("f1_src"));
                            airports.add(resultSet2.getString("f1_des"));
                            airports.add(resultSet2.getString("f2_des"));

                            ArrayList<String> departures = new ArrayList<>();
                            departures.add(resultSet2.getString("f1_dep"));
                            departures.add(resultSet2.getString("f2_dep"));

                            ArrayList<String> arrivals = new ArrayList<>();
                            arrivals.add(resultSet2.getString("f1_arr"));
                            arrivals.add(resultSet2.getString("f2_arr"));

                            String timeBetween = resultSet2.getString("diff");
                            int price = resultSet2.getInt("price");

                            ArrayList<String> timezones = new ArrayList<>();
                            timezones.add(resultSet2.getString("a1_tz"));
                            timezones.add(resultSet2.getString("a2_tz"));
                            timezones.add(resultSet2.getString("a3_tz"));

                            arrayList.add(new Flight(flightIDs, airlines, airports, departures, arrivals, timeBetween, price, timezones));
                        }
                        return arrayList;
                    }
                }

            } else {
                System.out.println("Failed to establish connection to the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public Double[] getLangLati(String IATA1, String IATA2) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "SELECT a.latitude AS latitude_a, a.longitude AS longitude_a, b.latitude AS latitude_b, b.longitude AS longitude_b FROM airport2 a, airport2 b WHERE a.iata = ? AND b.iata = ?;";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, IATA1);
                    statement.setString(2, IATA2);
                    ResultSet resultSet = statement.executeQuery();
                    Double[] string = { null, null, null, null };
                    while (resultSet.next()) {
                        string[0] = Double.valueOf(resultSet.getFloat("longitude_a"));
                        string[1] = Double.valueOf(resultSet.getFloat("latitude_a"));
                        string[2] = Double.valueOf(resultSet.getFloat("longitude_b"));
                        string[3] = Double.valueOf(resultSet.getFloat("latitude_b"));
                    }
                    return string;
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }

    }

    public ArrayList<Client> findClient(String string) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = !string.isEmpty()
                        ? "SELECT * from clients WHERE firstname LIKE ? OR lastname LIKE ?;"
                        : "SELECT * from clients";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    if (!string.isEmpty()) {
                        string = "%" + string + "%";
                        statement.setString(1, string);
                        statement.setString(2, string);
                    }
                    System.out.println(statement);
                    ResultSet resultSet = statement.executeQuery();
                    ArrayList<Client> arrayList = new ArrayList<>();
                    while (resultSet.next()) {
                        String id = String.valueOf(resultSet.getInt("id"));
                        String bday = resultSet.getString("birthday").trim();

                        String firstname = resultSet.getString("firstname").trim();
                        String lastname = resultSet.getString("lastname").trim();

                        arrayList.add(new Client(id, firstname, lastname, bday));
                    }
                    return arrayList;
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    public Boolean registerClient(Client client) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "INSERT INTO clients (firstname, lastname, birthday) VALUES(?, ?, CAST(? AS DATE));";
                System.out.println(sql);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, client.firstName());
                    statement.setString(2, client.lastName());
                    statement.setString(3, client.birthday());
                    System.out.println(statement);
                    statement.executeUpdate();
                    return true;
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<String> getAirport(String city) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                city = "%" + city.toLowerCase() + "%";
                String sql = "select name, city, country from airport2 a where lower(a.city) like(?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, city);
                    ResultSet resultSet = statement.executeQuery();
                    ArrayList<String> strings = new ArrayList<>();
                    while (resultSet.next()) {
                        String string = resultSet.getString("name").trim() + " - " + resultSet.getString("city").trim()
                                + " - " + resultSet.getString("country").trim();
                        strings.add(string);
                    }
                    return strings;
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return null;
        }
    }

    //// General discount stuff
    public ArrayList<Discount> findDiscounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "SELECT DiscountName, Percentage, StartDate, EndDate FROM generalDiscounts";
                System.out.println(sql);
                try (PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        String name = resultSet.getString("DiscountName");
                        int percentage = resultSet.getInt("Percentage");
                        String startDate = resultSet.getString("StartDate");
                        String endDate = resultSet.getString("EndDate");

                        discounts.add(new Discount(name, percentage, startDate, endDate));
                    }
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
        return discounts;
    }

    public void addDiscount(Discount discount) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "INSERT INTO generalDiscounts (DiscountName, Percentage, StartDate, EndDate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, discount.getName());
                    statement.setInt(2, discount.getPercentage());
                    statement.setString(3, discount.getStartDate());
                    statement.setString(4, discount.getEndDate());
                    statement.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void removeDiscount(Discount discount) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "DELETE FROM generalDiscounts WHERE DiscountName = ? AND Percentage = ? AND StartDate = ? AND EndDate = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, discount.getName());
                    statement.setInt(2, discount.getPercentage());
                    statement.setString(3, discount.getStartDate());
                    statement.setString(4, discount.getEndDate());
                    statement.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    public void updateDiscount(Discount discount) {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                System.out.println("Connection to the database established successfully.");
                String sql = "UPDATE generalDiscounts SET Percentage = ?, StartDate = ?, EndDate = ? WHERE DiscountName = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, discount.getPercentage());
                    statement.setString(2, discount.getStartDate());
                    statement.setString(3, discount.getEndDate());
                    statement.setString(4, discount.getName());
                    statement.executeUpdate();
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
