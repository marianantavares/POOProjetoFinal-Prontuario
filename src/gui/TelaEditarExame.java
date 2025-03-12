package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Exame;
import model.Paciente;
import service.ExameService;
import service.PacienteService;

public class TelaEditarExame extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private ExameService exameService;
    private PacienteService pacienteService;
    private TelaPrincipal main;
    private Exame exame;
    
    private JPanel painelForm;
    private JPanel painelBotoes;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JLabel lblDescricao;
    private JLabel lblDataExame;
    private JLabel lblPaciente;
    private JTextField txfDescricao;
    private JTextField txfDataExame;
    private JComboBox<Paciente> cbPacientes;
    
    public TelaEditarExame(PacienteService pacienteService, ExameService exameService, Exame exame, TelaPrincipal main) {
        this.pacienteService = pacienteService;
        this.exameService = exameService;
        this.exame = exame;
        this.main = main;
        
        setTitle("Editar Exame");
        setSize(400,250);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        painelForm = new JPanel(new GridLayout(4,2,5,5));
        lblDescricao = new JLabel("Descrição:");
        txfDescricao = new JTextField(exame.getDescricao(), 30);
        lblDataExame = new JLabel("Data do Exame:");
        txfDataExame = new JTextField(exame.getDataExame(), 30);
        lblPaciente = new JLabel("Paciente:");
        
        // Preenche o combo com os pacientes e seleciona o associado ao exame
        List<Paciente> pacientes = pacienteService.getPacientes();
        cbPacientes = new JComboBox<>();
        for (Paciente p : pacientes) {
            cbPacientes.addItem(p);
            if(p.getId().equals(exame.getPacienteId())) {
                cbPacientes.setSelectedItem(p);
            }
        }
        
        painelForm.add(lblDescricao);
        painelForm.add(txfDescricao);
        painelForm.add(lblDataExame);
        painelForm.add(txfDataExame);
        painelForm.add(lblPaciente);
        painelForm.add(cbPacientes);
        add(painelForm, BorderLayout.CENTER);
        
        painelBotoes = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        setModal(true);
        setLocationRelativeTo(main);
        setVisible(true);
    }
    
    private void salvar() {
        String descricao = txfDescricao.getText();
        String dataExame = txfDataExame.getText();
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        if (descricao.isEmpty() || dataExame.isEmpty() || paciente == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        exame.setDescricao(descricao);
        exame.setDataExame(dataExame);
        exame.setPacienteId(paciente.getId());
        exameService.atualizarExame(exame);
        JOptionPane.showMessageDialog(this, "Exame atualizado com sucesso!");
        main.loadTableExame();
        dispose();
    }
}
