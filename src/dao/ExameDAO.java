package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;
import exceptions.ExameException;
import model.Exame;

public class ExameDAO implements GenericDAO<Exame, Long> {
    private DatabaseConnection db;

    public ExameDAO(DatabaseConnection db) {
        this.db = db;
    }

    public void add(Exame obj) {
        String query = "INSERT INTO EXAMES VALUES (?,?,?,?)";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, 0);
            pstm.setString(2, obj.getDescricao());
            pstm.setString(3, obj.getDataExame());
            pstm.setLong(4, obj.getPacienteId());
            pstm.execute();
        } catch (SQLException e) {
            throw new ExameException("Erro ao adicionar exame");
        }
    }


    public Exame findByID(Long id) {
        String query = "SELECT e.*, p.nome AS paciente_nome FROM EXAMES e " +
                       "JOIN pacientes p ON e.pacientes_id = p.id " +
                       "WHERE e.id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Exame exame = new Exame();
                    exame.setId(rs.getLong("id"));
                    exame.setDescricao(rs.getString("descricao"));
                    exame.setDataExame(rs.getString("data_exame"));
                    exame.setPacienteId(rs.getLong("pacientes_id"));
                    exame.setPacienteNome(rs.getString("paciente_nome")); // Preenche o nome do paciente
                    return exame;
                }
            }
        } catch (SQLException e) {
            throw new ExameException("Erro ao buscar exame por ID: " + e.getMessage());
        }
        throw new ExameException("Exame com ID " + id + " não encontrado.");
    }


    
    public void delete(Exame obj) {
        String query = "DELETE FROM EXAMES WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, obj.getId());
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExameException("Nenhum exame encontrado para deletar com ID " + obj.getId());
            }
        } catch (SQLException e) {
            throw new ExameException("Erro ao deletar exame");
        }
    }

    public void update(Exame obj) {
        // Corrigindo o nome da coluna para 'pacientes_id'
        String query = "UPDATE EXAMES SET descricao = ?, data_exame = ?, pacientes_id = ? WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setString(1, obj.getDescricao());
            pstm.setString(2, obj.getDataExame());
            pstm.setLong(3, obj.getPacienteId());
            pstm.setLong(4, obj.getId());
            int rowsUpdated = pstm.executeUpdate();
            if (rowsUpdated == 0) {
                throw new ExameException("Nenhum exame encontrado para atualizar com ID " + obj.getId());
            }
        } catch (SQLException e) {
            throw new ExameException("Erro ao atualizar exame");
        }
    }


    public List<Exame> getAll() {
        List<Exame> list = new ArrayList<>();
        String query = "SELECT e.*, p.nome AS paciente_nome FROM EXAMES e " +
                       "JOIN pacientes p ON e.pacientes_id = p.id";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                Exame exame = new Exame();
                exame.setId(rs.getLong("id"));
                exame.setDescricao(rs.getString("descricao"));
                exame.setDataExame(rs.getString("data_exame"));
                exame.setPacienteId(rs.getLong("pacientes_id"));
                exame.setPacienteNome(rs.getString("paciente_nome")); // Preenche o nome do paciente
                list.add(exame);
            }
            return list;
        } catch (SQLException e) {
            throw new ExameException("Erro ao listar exames");
        }
    }
}