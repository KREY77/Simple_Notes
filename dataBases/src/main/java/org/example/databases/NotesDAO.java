package org.example.databases;

import java.sql.*;
import java.util.ArrayList;

public class NotesDAO {
    static ArrayList<Note> getListNote(Long user_id) {
        ArrayList<Note> noteArrayList = new ArrayList<>();
        String request = "SELECT * FROM notes WHERE user_id = ?";
        try (Connection con = DBManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement(request);
            statement.setLong(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Note note = new Note(resultSet.getLong("id"), resultSet.getString("title"), resultSet.getString("text"), resultSet.getTimestamp("created_at").toLocalDateTime(), resultSet.getLong("user_id"));
                noteArrayList.add(note);
            }
            return noteArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void refreshNote(Note note){
        String updateNote = "UPDATE notes SET text = ? WHERE id = ?";
        try (Connection con = DBManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement(updateNote);
            statement.setString(1, note.getText());
            statement.setLong(2, note.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean addNote(Note note) {
        String addNote = "INSERT INTO notes (title, text, created_at, user_id) VALUES (?, ?, ?, ?)";
        try (Connection con = DBManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement(addNote);
            statement.setString(1, note.getTitle());
            statement.setString(2, note.getText());
            statement.setTimestamp(3, Timestamp.valueOf(note.getCreated_at()));
            statement.setLong(4, note.getUser_id());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    static void deleteNote(Long id) {
        String request = "DELETE FROM notes WHERE id = ?";
        try (Connection con = DBManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement(request);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
