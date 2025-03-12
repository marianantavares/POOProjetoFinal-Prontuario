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
import javax.swing.table.TableCellRenderer;

import model.Exame;
import model.Paciente;
import service.ExameService;
import service.PacienteService;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JMenuBar barraMenu;
    private JMenu menuPaciente;
    private JMenuItem menuItemAdicionarPaciente;
    private JMenu menuExames;
    private JMenuItem menuItemAdicionarExame;
    
    private JTabbedPane tabbed;
    private JTable tablePacientes;
    private JTable tableExames;
    
    private PacienteService pacService;
    private ExameService exameService;
    
    public TelaPrincipal(PacienteService pacService, ExameService exameService) {
        this.pacService = pacService;
        this.exameService = exameService;
        
        setTitle("Gerenciamento de Pacientes e Exames");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Configuração da barra de menu
        barraMenu = new JMenuBar();
        
        menuPaciente = new JMenu("Paciente");
        menuItemAdicionarPaciente = new JMenuItem("Adicionar Paciente");
        menuItemAdicionarPaciente.addActionListener(e -> new TelaCadastrarPaciente(pacService, this));
        menuPaciente.add(menuItemAdicionarPaciente);
        barraMenu.add(menuPaciente);
        
        menuExames = new JMenu("Exames");
        menuItemAdicionarExame = new JMenuItem("Adicionar Exame");
        menuItemAdicionarExame.addActionListener(e -> new TelaCadastrarExame(pacService, exameService, this));
        menuExames.add(menuItemAdicionarExame);
        barraMenu.add(menuExames);
        
        add(barraMenu, BorderLayout.NORTH);
        
        // Configuração do painel de abas
        tabbed = new JTabbedPane();
        
        // Aba Pacientes
        tablePacientes = new JTable();
        JScrollPane scrollPacientes = new JScrollPane(tablePacientes);
        tabbed.addTab("Pacientes", scrollPacientes);
        
        // Aba Exames
        tableExames = new JTable();
        JScrollPane scrollExames = new JScrollPane(tableExames);
        tabbed.addTab("Exames", scrollExames);
        
        add(tabbed, BorderLayout.CENTER);
        
        loadTablePaciente();
        loadTableExame();
    }
    
    public void loadTablePaciente() {
        List<Paciente> pacientes = pacService.getPacientes();
        TabelaPacienteModel model = new TabelaPacienteModel(pacientes);
        tablePacientes.setModel(model);
        
        tablePacientes.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        tablePacientes.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), true));
        tablePacientes.getColumn("Excluir").setCellRenderer(new ButtonRenderer());
        tablePacientes.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), true));
    }
    
    public void loadTableExame() {
        List<Exame> exames = exameService.getExames();
        TabelaExameModel model = new TabelaExameModel(exames);
        tableExames.setModel(model);
        
        tableExames.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        tableExames.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), false));
        tableExames.getColumn("Excluir").setCellRenderer(new ButtonRenderer());
        tableExames.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), false));
    }
    
    // Renderer para exibir botões
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
    
    // Editor para tratar cliques nos botões (para pacientes e exames)
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;
        private int selectedColumn;
        private boolean isPaciente; // true para paciente, false para exame
        
        public ButtonEditor(JCheckBox checkBox, boolean isPaciente) {
            super(checkBox);
            this.isPaciente = isPaciente;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
                if (isPaciente) {
                    if (selectedColumn == 3) {
                        editarPaciente(selectedRow);
                    } else if (selectedColumn == 4) {
                        excluirPaciente(selectedRow);
                    }
                } else {
                    if (selectedColumn == 4) {
                        editarExame(selectedRow);
                    } else if (selectedColumn == 5) {
                        excluirExame(selectedRow);
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
    
    // Métodos para editar e excluir pacientes
    private void editarPaciente(int row) {
        Long id = (Long) tablePacientes.getModel().getValueAt(row, 0);
        Paciente p = pacService.localizarPacientePorId(id);
        if (p != null) {
            new TelaEditarPaciente(pacService, p, this);
        } else {
            JOptionPane.showMessageDialog(this, "Paciente não encontrado!");
        }
    }
    
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
    
    // Métodos para editar e excluir exames
    private void editarExame(int row) {
        Long id = (Long) tableExames.getModel().getValueAt(row, 0);
        Exame exame = exameService.localizarExamePorId(id);
        if (exame != null) {
            new TelaEditarExame(pacService, exameService, exame, this);
        } else {
            JOptionPane.showMessageDialog(this, "Exame não encontrado!");
        }
    }
    
    private void excluirExame(int row) {
        Long id = (Long) tableExames.getModel().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do exame com id: " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Exame exame = exameService.localizarExamePorId(id);
            if (exame != null) {
                exameService.deletarExame(exame);
                JOptionPane.showMessageDialog(this, "Exame excluído com sucesso!");
                loadTableExame();
            } else {
                JOptionPane.showMessageDialog(this, "Exame não encontrado!");
            }
        }
    }
}
