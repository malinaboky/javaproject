import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static final String path = "src/main/resources/Показатель счастья по странам 2017 - Показатель счастья по странам 2017.csv";
    private static final String DB_URL = "jdbc:sqlite:database.sqlite3";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS IndexOfHappiness (" +
            "Country STRING PRIMARY KEY, " +
            "Region TEXT NOT NULL, " +
            "HappinessRank INTEGER NOT NULL, " +
            "HappinessScore DOUBLE NOT NULL, " +
            "WhiskerHigh DOUBLE NOT NULL, " +
            "WhiskerLow DOUBLE NOT NULL, " +
            "Economy DOUBLE NOT NULL, " +
            "Family DOUBLE NOT NULL, " +
            "Health DOUBLE NOT NULL, " +
            "Freedom DOUBLE NOT NULL, " +
            "Generosity DOUBLE NOT NULL, " +
            "TrustGovernment DOUBLE NOT NULL, " +
            "DystopiaResidual DOUBLE NOT NULL)";

    public static void fillDataBase() {
        String sql = "INSERT INTO IndexOfHappiness VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String tableName = "IndexOfHappiness";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            boolean exists = connection.getMetaData().getTables(null, null, tableName,null).next();
            if (!exists) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(CREATE_TABLE_QUERY);

                    connection.setAutoCommit(false);
                    PreparedStatement ps = connection.prepareStatement(sql);

                    for (var row : CSVParser.parseCSV(path)) {
                        ps.setObject(1, row.getCountry());
                        ps.setObject(2, row.getRegion());
                        ps.setObject(3, row.getHappinessRank());
                        ps.setObject(4, row.getHappinessScore());
                        ps.setObject(5, row.getWhiskerHigh());
                        ps.setObject(6, row.getWhiskerLow());
                        ps.setObject(7, row.getEconomy());
                        ps.setObject(8, row.getFamily());
                        ps.setObject(9, row.getHealth());
                        ps.setObject(10, row.getFreedom());
                        ps.setObject(11, row.getGenerosity());
                        ps.setObject(12, row.getTrustGovernment());
                        ps.setObject(13, row.getDystopiaResidual());

                        ps.addBatch();
                    }
                    ps.executeBatch();
                    ps.clearBatch();
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else
                System.out.println("Таблица уже создана");
        } catch (SQLException throwable) {
            System.out.println("Не удалось подключить базу данных!1");
        }
    }

    public static Map<String, Float> getDataHealth() {
        Map<String, Float> dataset = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement statement = connection.createStatement()) {
                ResultSet dataFromDb = statement.executeQuery("SELECT Country, Health FROM IndexOfHappiness");
                while (dataFromDb.next())
                    dataset.put(dataFromDb.getString("Country"), dataFromDb.getFloat("Health"));
            }
        } catch (SQLException throwable) {
            System.out.println("Не удалось подключить базу данных!2");
        }
        return dataset;
    }

    public static double getMiddleValueOfHealth() {
        double result = 0.0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement statement = connection.createStatement()) {
                ResultSet data = statement.executeQuery("SELECT AVG(Health) as Result FROM IndexOfHappiness " +
                        "WHERE Region = 'Western Europe' or Region = 'Sub-Saharan Africa'");
                result = data.getDouble("Result");
            }
        } catch (SQLException throwable) {
            System.out.println("Не удалось подключить базу данных!3");
        }
        return result;
    }

    public static String getMiddleCountry() {
        String result = "";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement statement = connection.createStatement()) {
                ResultSet middledata = statement.executeQuery(
                        "SELECT MIN(ABS((SELECT AVG(Economy) FROM IndexOfHappiness)-Economy) + " +
                                "ABS((SELECT AVG(Family) FROM IndexOfHappiness)-Family) +" +
                                "ABS((SELECT AVG(Health) FROM IndexOfHappiness)-Health) +" +
                                "ABS((SELECT AVG(Freedom) FROM IndexOfHappiness)-Freedom) +" +
                                "ABS((SELECT AVG(Generosity) FROM IndexOfHappiness)-Generosity) +" +
                                "ABS((SELECT AVG(TrustGovernment) FROM IndexOfHappiness)-TrustGovernment) +" +
                                "ABS((SELECT AVG(DystopiaResidual) FROM IndexOfHappiness)-DystopiaResidual)) as Result, Country " +
                                "FROM IndexOfHappiness " +
                                "WHERE Region = 'Western Europe' or Region = 'Sub-Saharan Africa'");
                result = middledata.getString("Country");
            }
        } catch (SQLException throwable) {
            System.out.println("Не удалось подключить базу данных!4");
        }
        return result;
    }

    public static void getGraph(Map<String, Float> dataset, double middle) {
        EventQueue.invokeLater(() -> {
            CountryChart ex = new CountryChart(dataset, middle);
            ex.setVisible(true);
        });
    }
}
