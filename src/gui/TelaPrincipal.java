package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import model.Paciente;
import service.PacienteService;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JMenuBar barraMenu;
    private JMenu menuPaciente;
    private JMenuItem menuItemAdicionarPaciente;
    private JScrollPane scrollPane;
    private JTabbedPane tabbed;
    private JTable tablePacientes;
    private PacienteService pacService;
    private JMenu menuExames;
    private JMenuItem menuItemDescricaoExames;
    
    public TelaPrincipal(PacienteService pacService) {
        this.pacService = pacService;
        setTitle("Gerencia de Prontuarios");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Configuração da barra de menu
        barraMenu = new JMenuBar();
        menuPaciente = new JMenu("Paciente");
        menuItemAdicionarPaciente = new JMenuItem("Adicionar");
        menuItemAdicionarPaciente.addActionListener(e -> new TelaCadastrarPaciente(pacService, this));
        // Ação para atualizar via menu pode ser implementada conforme necessário
        
        menuPaciente.add(menuItemAdicionarPaciente);
        barraMenu.add(menuPaciente);
        
        menuExames = new JMenu("Exames");
        menuItemDescricaoExames = new JMenuItem("Descrição de Exames");
        menuExames.add(menuItemDescricaoExames);
        barraMenu.add(menuExames);
        
        add(barraMenu, BorderLayout.NORTH);
        
        // Configuração da tabela com scroll e painel de abas
        tablePacientes = new JTable();
        scrollPane = new JScrollPane(tablePacientes);
        tabbed = new JTabbedPane();
        tabbed.addTab("Pacientes", scrollPane);
        add(tabbed, BorderLayout.CENTER);
        
        loadTablePaciente();
    }
    
    protected void loadTablePaciente() {
        List<Paciente> itens = pacService.getPacientes();
        TabelaPacienteModel model = new TabelaPacienteModel(itens);
        tablePacientes.setModel(model);
        
        // Configura renderers e editores para as colunas "Editar" e "Excluir"
        tablePacientes.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        tablePacientes.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox()));
        tablePacientes.getColumn("Excluir").setCellRenderer(new ButtonRenderer());
        tablePacientes.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
    
    // Renderer para exibir botões nas células
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }
    
    // Editor para capturar cliques nos botões da tabela
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;
        private int selectedColumn;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    // Chama o método conforme a coluna: 3 para "Editar", 4 para "Excluir"
                    if (selectedColumn == 3) {
                        editarPaciente(selectedRow);
                    } else if (selectedColumn == 4) {
                        excluirPaciente(selectedRow);
                    }
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            selectedRow = row;
            selectedColumn = column;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    // Método para tratar a edição do paciente
    private void editarPaciente(int row) {
        // Recupera o ID do paciente (assumindo que a coluna 0 contém o ID)
        Long id = (Long) tablePacientes.getModel().getValueAt(row, 0);
        Paciente p = pacService.localizarPacientePorId(id);
        if (p != null) {
            // Abre a tela de edição passando o paciente encontrado
            new TelaEditarPaciente(pacService, p, this);
        } else {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!");
        }
    }

    
    // Método para tratar a exclusão do paciente
    private void excluirPaciente(int row) {
        Long id = (Long) tablePacientes.getModel().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do paciente com id: " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Paciente p = pacService.localizarPacientePorId(id);
            if (p != null) {
                pacService.deletarPaciente(p);
                JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!");
                loadTablePaciente();
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado!");
            }
        }
    }
}
