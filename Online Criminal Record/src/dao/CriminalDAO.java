package dao;

import model.Criminal;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriminalDAO {
    public void addCriminal(Criminal criminal) throws SQLException {
        String sql = "INSERT INTO criminals (name, crime_type, crime_date, punishment_years) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, criminal.getName());
            stmt.setString(2, criminal.getCrimeType());
            stmt.setDate(3, new java.sql.Date(criminal.getCrimeDate().getTime()));
            stmt.setInt(4, criminal.getPunishmentYears());

            stmt.executeUpdate();
        }
    }

    public List<Criminal> getAllCriminals() throws SQLException {
        List<Criminal> criminals = new ArrayList<>();
        String sql = "SELECT * FROM criminals";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Criminal criminal = new Criminal();
                criminal.setId(rs.getInt("id"));
                criminal.setName(rs.getString("name"));
                criminal.setCrimeType(rs.getString("crime_type"));
                criminal.setCrimeDate(rs.getDate("crime_date"));
                criminal.setPunishmentYears(rs.getInt("punishment_years"));
                criminals.add(criminal);
            }
        }
        return criminals;
    }
}