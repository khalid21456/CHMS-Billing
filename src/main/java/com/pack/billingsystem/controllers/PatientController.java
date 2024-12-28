package com.pack.billingsystem.controllers;

import com.pack.billingsystem.models.Patient;
import javafx.scene.chart.PieChart;

import java.sql.*;

@SuppressWarnings("unused")
public class PatientController {

    public static Patient getPatientById(int id) throws SQLException {
        Patient patient = null;

        String query = """
                SELECT p.Nom AS nom_patient, p.Prenom AS prenom_patient, p.Tel AS tel, i.Pourcentage AS assurance
                FROM Patient p
                JOIN reclamationassurance r ON p.IDPatient = r.IDPatient
                JOIN insurance i ON r.IDInsurance = i.IDInsurance
                WHERE p.IDPatient = ?
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                    rs.next();
                    patient = new Patient();
                    patient.setPourcentageAssurance(rs.getDouble("assurance"));
                    patient.setPrenom(rs.getString("prenom_patient"));
                    patient.setNom(rs.getString("nom_patient"));
                    patient.setTelephone(rs.getString("tel"));

            }
        }
        return patient;
    }


    public static Patient getPatient(int id) throws SQLException{
        Connection connection = DatabaseConnection.getConnection();

        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM PATIENT INNER JOIN insurance ON Patient.IDInsurance = insurance.IDInsurance WHERE IDPatient = "+id);
        Patient patient = new Patient();
        set.next();
        patient.setIdPatient(set.getInt("IDPatient"));
        patient.setPourcentageAssurance(set.getDouble("Pourcentage"));
        patient.setNom(set.getString("Nom"));
        patient.setPrenom(set.getString("Prenom"));
        patient.setTelephone(set.getString("Tel"));
        return patient;
    }
}

