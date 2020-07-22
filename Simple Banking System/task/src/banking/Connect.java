package banking;

import java.sql.*;

public class Connect {
    private static final String currentDir = System.getProperty("user.dir");
    private final String dbName;

    public Connect(String dbName) {
        this.dbName = dbName;
    }

    public void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void createNewTable() {
        String createSQL =

                "CREATE TABLE IF NOT EXISTS card (\n"
                        + "	id INTEGER,\n"
                        + "	number TEXT,\n"
                        + "	pin TEXT,\n"
                        + "	balance INTEGER DEFAULT 0\n"
                        + ");";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(createSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertNewAccount(String number, String pin, Integer balance) {
        String sql = "INSERT INTO card(number,pin,balance) VALUES(?,?,?)";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.setInt(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getBalance(String number) {
        String sql = "SELECT balance FROM card WHERE number = ?";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, number);
            ResultSet rs  = pstmt.executeQuery();

            rs.next();
            return rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void depositIncome(String number, Integer income) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, income);
            pstmt.setString(2, number);
            pstmt.executeUpdate();
            System.out.println("Income was added!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeAccount(String number) {
        String sql = "DELETE FROM card WHERE number = ?";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, number);
            pstmt.executeUpdate();
            System.out.println("\nThe account has been closed!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean accountExists(String number) {
        String sql = "SELECT * FROM card WHERE number = ?";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void transferMoney(String fromAccount, String toAccount, Integer amount) {
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?;" +
                "UPDATE card SET balance = balance + ? WHERE number = ?";

        String url = "jdbc:sqlite:" + currentDir + "\\" + this.dbName;

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, amount);
            pstmt.setString(2, fromAccount);
            pstmt.setInt(3, amount);
            pstmt.setString(4, toAccount);
            pstmt.executeUpdate();
            System.out.println("Success!\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
