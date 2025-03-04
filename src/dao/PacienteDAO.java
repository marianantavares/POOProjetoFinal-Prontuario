package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;
import model.Paciente;

/* 
 * Classe que implementa o padrão DAO para o objeto Paciente.
 * Separa a lógica de acesso ao banco de dados da lógica de negócio.
 */
public class PacienteDAO implements GenericDAO<Paciente, Long> {

    private DatabaseConnection db;
    
    // Construtor que recebe a conexão com o banco de dados
    public PacienteDAO(DatabaseConnection db) {
        this.db = db;
    }
    
    // Método para adicionar um paciente no banco de dados
    @Override
    public void add(Paciente obj) {
        String query = "INSERT INTO PACIENTES VALUES (?,?,?)";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            pstm.setLong(1, 0); // Utiliza 0 para id, assumindo que o banco gera o valor automaticamente (AUTO_INCREMENT)
            pstm.setString(2, obj.getCpf());
            pstm.setString(3, obj.getNome());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para buscar um paciente por ID
    @Override
    public Paciente findByID(Long id) {
        String query = "SELECT * FROM PACIENTES WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            // Define o parâmetro de ID na query
            pstm.setLong(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                // Se um registro for encontrado, cria e retorna o objeto Paciente
                if (rs.next()) {
                    return new Paciente(
                        rs.getLong("id"),
                        rs.getString("cpf"),
                        rs.getString("nome")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Se não encontrar o paciente, retorna null
        return null;
    }
    
    // Método para deletar um paciente do banco de dados
    @Override
    public void delete(Paciente obj) {
        String query = "DELETE FROM PACIENTES WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            // Define o parâmetro de ID na query para identificar qual registro deletar
            pstm.setLong(1, obj.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para atualizar os dados de um paciente já cadastrado
    @Override
    public void update(Paciente obj) {
        String query = "UPDATE PACIENTES SET cpf = ?, nome = ? WHERE id = ?";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query)) {
            // Define os parâmetros: novo CPF, novo nome e o ID do paciente a ser atualizado
            pstm.setString(1, obj.getCpf());
            pstm.setString(2, obj.getNome());
            pstm.setLong(3, obj.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para retornar todos os pacientes cadastrados
    @Override
    public List<Paciente> getAll() {
        List<Paciente> temp = new ArrayList<>();
        String query = "SELECT * FROM PACIENTES;";
        try (PreparedStatement pstm = db.getConnection().prepareStatement(query); 
             ResultSet rs = pstm.executeQuery()){
            // Percorre todos os registros e adiciona cada paciente na lista
            while(rs.next()) {
                Paciente p = new Paciente(rs.getLong("id"), rs.getString("cpf"), rs.getString("nome"));
                temp.add(p);
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
