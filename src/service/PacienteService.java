package service;

import java.util.List;
import dao.GenericDAO;
import exceptions.PacienteException;
import model.Paciente;

public class PacienteService {

    // Declaração do DAO genérico para gerenciar as operações do banco de dados para Paciente
    private GenericDAO<Paciente, Long> daoPaciente;

    // Construtor que recebe uma instância de GenericDAO para manipular os pacientes no banco de dados
    public PacienteService(GenericDAO<Paciente, Long> dao) {
        this.daoPaciente = dao;
    }

    /**
     * Adiciona um novo paciente ao banco de dados.
     * Valida se o paciente não é nulo e se possui um nome válido antes de adicionar.
     * Lança uma exceção personalizada se as regras de negócio não forem atendidas.
     */
    public void adicionarPaciente(Paciente p) {
        try {
            if (p == null) {
                throw new PacienteException("Paciente não pode ser nulo.");
            }
            if (p.getNome() == null || p.getNome().trim().isEmpty()) {
                throw new PacienteException("Nome do paciente não pode estar vazio.");
            }
            // Chama o método 'add' do DAO para inserir o paciente no banco de dados
            daoPaciente.add(p);
        } catch (PacienteException e) {
            System.err.println("Erro ao adicionar paciente");
        }
    }

    /**
     * Localiza um paciente pelo seu ID no banco de dados.
     * Verifica se o ID é válido antes de tentar buscar o paciente.
     * Lança uma exceção se o ID for inválido ou se o paciente não for encontrado.
     * @param id - Identificação do paciente
     * @return Paciente encontrado ou null se não encontrado
     */
    public Paciente localizarPacientePorId(Long id) {
        try {
            if (id == null || id <= 0) {
                throw new PacienteException("ID do paciente inválido.");
            }
            Paciente paciente = daoPaciente.findByID(id);
            if (paciente == null) {
                throw new PacienteException("Paciente não encontrado para o ID informado.");
            }
            return paciente;
        } catch (PacienteException e) {
            System.err.println("Erro ao localizar paciente");
            return null;
        }
    }

    /**
     * Remove um paciente do banco de dados.
     * Verifica se o paciente não é nulo antes de tentar a remoção.
     * @param p - Objeto Paciente a ser removido
     */
    public void deletarPaciente(Paciente p) {
        try {
            if (p == null) {
                throw new PacienteException("Paciente não pode ser nulo.");
            }
            daoPaciente.delete(p);
        } catch (PacienteException e) {
            System.err.println("Erro ao deletar paciente");
        }
    }

    /**
     * Retorna uma lista de todos os pacientes cadastrados no banco de dados.
     * Caso não existam pacientes, uma exceção é lançada e uma lista vazia é retornada.
     * @return Lista de pacientes cadastrados ou lista vazia caso não haja pacientes
     */
    public List<Paciente> getPacientes() {
        try {
            List<Paciente> pacientes = daoPaciente.getAll();
            if (pacientes == null || pacientes.isEmpty()) {
                throw new PacienteException("Nenhum paciente encontrado.");
            }
            return pacientes;
        } catch (PacienteException e) {
            System.err.println("Erro ao listar pacientes");
            return List.of(); // Retorna uma lista vazia em caso de erro
        }
    }

    /**
     * Atualiza os dados de um paciente no banco de dados.
     * Verifica se o paciente não é nulo e se o nome não está vazio antes da atualização.
     * Em caso de erro, uma exceção específica é lançada e tratada.
     * @param p - Objeto Paciente a ser atualizado
     */
    public void atualizarPaciente(Paciente p) {
        try {
            if (p == null) {
                throw new PacienteException("Paciente não pode ser nulo.");
            }
            if (p.getNome() == null || p.getNome().trim().isEmpty()) {
                throw new PacienteException("Nome do paciente não pode estar vazio.");
            }
            // Chama o método update do DAO para atualizar o paciente no banco de dados
            daoPaciente.update(p);
        } catch (PacienteException e) {
            System.err.println("Erro ao atualizar paciente");
        }
    }
}
