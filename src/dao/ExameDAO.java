package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;
import model.Exame;

/*
 * ExameDAO implementa os métodos de CRUD para a entidade Exame,
 * utilizando o padrão DAO e o construtor atualizado com 4 parâmetros.
 */
public class ExameDAO implements GenericDAO<Exame, Long> {

    private DatabaseConnection db;

    // Construtor que recebe a conexão com o banco de dados
    public ExameDAO(DatabaseConnection db) {
        this.db = db;
    }

    /**
     * Adiciona um novo exame ao banco de dados.
     * O método utiliza o construtor com 4 parâmetros: id, descricao, dataExame e pacienteId.
     */
    @Override
    public void add(Exame obj) {
        // Consulta para inserir um exame. Assume que o id é auto-incrementado.
        String query = "INSERT INTO EXAMES VALUES (?,?,?,?)";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, 0); // Valor 0 para id, pois o banco gera automaticamente
            pstm.setString(2, obj.getDescricao());
            pstm.setString(3, obj.getDataExame());
            pstm.setLong(4, obj.getPacienteId()); // Associa o exame ao paciente via paciente_id
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca um exame pelo seu ID.
     * Retorna um objeto Exame com todos os atributos, utilizando o construtor com 4 parâmetros.
     */
    @Override
    public Exame findByID(Long id) {
        String query = "SELECT * FROM EXAMES WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, id); // Define o id na query
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    // Cria o objeto Exame utilizando os 4 parâmetros:
                    // id, descricao, data_exame e paciente_id
                    return new Exame(
                        rs.getLong("id"),
                        rs.getString("descricao"),
                        rs.getString("data_exame"),
                        rs.getLong("pacientes_id") 
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Retorna null se não encontrar o exame
        return null;
    }

    /**
     * Deleta um exame do banco de dados com base no seu ID.
     */
    @Override
    public void delete(Exame obj) {
        String query = "DELETE FROM EXAMES WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, obj.getId()); // Define o id do exame a ser deletado
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Atualiza os dados de um exame existente.
     * Atualiza os campos descricao, data_exame e paciente_id.
     */
    @Override
    public void update(Exame obj) {
        String query = "UPDATE EXAMES SET descricao = ?, data_exame = ?, paciente_id = ? WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setString(1, obj.getDescricao());
            pstm.setString(2, obj.getDataExame());
            pstm.setLong(3, obj.getPacienteId()); // Define o novo paciente associado
            pstm.setLong(4, obj.getId()); // Identifica qual exame atualizar
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna uma lista com todos os exames cadastrados no banco de dados.
     * Cada exame é instanciado utilizando o construtor com 4 parâmetros.
     */
    @Override
    public List<Exame> getAll() {
        List<Exame> list = new ArrayList<>();
        String query = "SELECT * FROM EXAMES";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {
            // Percorre todos os registros retornados e cria um objeto Exame para cada
            while (rs.next()) {
                Exame exame = new Exame(
                    rs.getLong("id"),
                    rs.getString("descricao"),
                    rs.getString("data_exame"),
                    rs.getLong("pacientes_id") 
                );
                list.add(exame);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Retorna null em caso de erro
        return null;
    }
}
