package gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Paciente;

/**
 * Modelo de tabela para exibir os dados dos pacientes, 
 * incluindo duas colunas extras para as ações de editar e excluir.
 */
public class TabelaPacienteModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private List<Paciente> pacientes;
    // Acrescentamos as colunas "Editar" e "Excluir"
    private String[] colunas = {"idPacientes", "cpf", "nome", "Editar", "Excluir"};
    
    public TabelaPacienteModel(List<Paciente> itens) {
        this.pacientes = itens;
    }
    
    @Override
    public int getRowCount() {
        return pacientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int index) {
        return colunas[index];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Paciente p = pacientes.get(rowIndex);
        // Para as 3 primeiras colunas retornamos os dados do paciente,
        // para as colunas "Editar" e "Excluir" retornamos o rótulo do botão
        return switch(columnIndex) {
            case 0 -> p.getId();
            case 1 -> p.getCpf();
            case 2 -> p.getNome();
            case 3 -> "Editar";
            case 4 -> "Excluir";
            default -> null;
        };
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        // Apenas as colunas de ação (índices 3 e 4) serão "editáveis"
        return col == 3 || col == 4;
    }
}