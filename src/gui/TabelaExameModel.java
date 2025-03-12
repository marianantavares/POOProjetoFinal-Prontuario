package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Exame;

public class TabelaExameModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Exame> exames;
    private String[] colunas = {"idExame", "Descrição", "Data do Exame", "Paciente ID", "Editar", "Excluir"};
    
    public TabelaExameModel(List<Exame> exames) {
        this.exames = exames;
    }
    
    @Override
    public int getRowCount() {
        return exames.size();
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
        Exame exame = exames.get(rowIndex);
        switch(columnIndex) {
            case 0: return exame.getId();
            case 1: return exame.getDescricao();
            case 2: return exame.getDataExame();
            case 3: return exame.getPacienteId();
            case 4: return "Editar";
            case 5: return "Excluir";
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        // Apenas as colunas "Editar" e "Excluir" serão editáveis para capturar o clique
        return col == 4 || col == 5;
    }
}
