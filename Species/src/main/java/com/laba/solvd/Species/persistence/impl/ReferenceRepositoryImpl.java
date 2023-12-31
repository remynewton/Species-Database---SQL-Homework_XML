package com.laba.solvd.Species.persistence.impl;

import com.laba.solvd.Species.domain.Image;
import com.laba.solvd.Species.domain.Reference;
import com.laba.solvd.Species.persistence.ConnectionPool;
import com.laba.solvd.Species.persistence.ReferenceRepository;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReferenceRepositoryImpl implements ReferenceRepository {
    private final Logger logger = Logger.getLogger("GLOBAL");
    private static final ConnectionPool CONNECTION_POOL;

    static {
        try {
            CONNECTION_POOL = ConnectionPool.getInstance();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Reference reference) {
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO references (title, author, date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reference.getTitle());
            ps.setString(2, reference.getAuthor());
            ps.setDate(3, (Date) reference.getDate());
            ps.executeUpdate();
            ResultSet idResultSet = ps.getGeneratedKeys();
            while (idResultSet.next()) {
                reference.setId(idResultSet.getInt("id"));
            }
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to connect", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Reference> findByID(int id) {
        Reference reference = null;
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id, title, author, year FROM references WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reference = new Reference();
                reference.setId(rs.getInt("id"));
                reference.setTitle(rs.getString("title"));
                reference.setAuthor(rs.getString("author"));
                reference.setDate(rs.getDate("date"));
            }
            rs.close();
            ps.close();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to connect", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(reference);
    }

    public static List<Reference> mapRow(ResultSet rs) throws SQLException {
        List<Reference> references = new ArrayList<>();
        while (rs.next()) {
            Reference reference = new Reference();
            reference.setId(rs.getInt("id"));
            reference.setTitle(rs.getString("title"));
            reference.setAuthor(rs.getString("author"));
            reference.setDate(rs.getDate("date"));
            references.add(reference);
        }
        return references;
    }
}
