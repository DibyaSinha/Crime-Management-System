package dao;

import model.Complaint;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    public void fileComplaint(Complaint complaint) throws SQLException {
        String sql = "INSERT INTO complaints (complainant_name, description, incident_date, police_station_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, complaint.getComplainantName());
            stmt.setString(2, complaint.getDescription());
            stmt.setDate(3, new java.sql.Date(complaint.getIncidentDate().getTime()));
            stmt.setInt(4, complaint.getPoliceStationId());

            stmt.executeUpdate();
        }
    }

    public void convertToFIR(int complaintId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update complaint status
            String updateSql = "UPDATE complaints SET status = 'FIR_REGISTERED' WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setInt(1, complaintId);
                updateStmt.executeUpdate();
            }

            // Create FIR record
            String insertSql = "INSERT INTO firs (complaint_id, registration_date) VALUES (?, CURDATE())";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, complaintId);
                insertStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public List<Complaint> getPendingComplaints() throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE status = 'PENDING'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setId(rs.getInt("id"));
                complaint.setComplainantName(rs.getString("complainant_name"));
                complaint.setDescription(rs.getString("description"));
                complaint.setIncidentDate(rs.getDate("incident_date"));
                complaint.setStatus(rs.getString("status"));
                complaint.setPoliceStationId(rs.getInt("police_station_id"));
                complaints.add(complaint);
            }
        }
        return complaints;
    }
}