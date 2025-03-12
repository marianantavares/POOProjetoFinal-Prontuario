package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Exame;
import model.Paciente;
import service.ExameService;
import service.PacienteService;

public class TelaCadastrarExame extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private ExameService exameService;
    private PacienteService pacienteService;
    private TelaPrincipal main;
    
    private JPanel painelForm;
    private JPanel painelBotoes;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;
    private JLabel lblDescricao;
    private JLabel lblDataExame;
    private JLabel lblPaciente;
    private JTextField txfDescricao;
    private JTextField txfDataExame;
    private JComboBox<Paciente> cbPacientes;
    
    public TelaCadastrarExame(PacienteService pacienteService, ExameService exameService, TelaPrincipal main) {
        this.pacienteService = pacienteService;
        this.exameService = exameService;
        this.main = main;
        
        setTitle("Cadastro de Exame");
        setSize(400,250);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        painelForm = new JPanel(new GridLayout(4,2,5,5));
        
        lblDescricao = new JLabel("Descrição:");
        txfDescricao = new JTextField(30);
        lblDataExame = new JLabel("Data do Exame:");
        txfDataExame = new JTextField(30);
        lblPaciente = new JLabel("Paciente:");
        
        // Preenche o combo com os pacientes cadastrados
        List<Paciente> pacientes = pacienteService.getPacientes();
        cbPacientes = new JComboBox<>();
        for (Paciente p : pacientes) {
            cbPacientes.addItem(p);
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
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");
        
        btnSalvar.addActionListener(e -> addExame());
        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> dispose());
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);
        
        add(painelBotoes, BorderLayout.SOUTH);
        
        setModal(true);
        setLocationRelativeTo(main);
        setVisible(true);
    }
    
    private void limparCampos() {
        txfDescricao.setText("");
        txfDataExame.setText("");
        if(cbPacientes.getItemCount() > 0) {
            cbPacientes.setSelectedIndex(0);
        }
    }
    
    private void addExame() {
        String descricao = txfDescricao.getText();
        String dataExame = txfDataExame.getText();
        Paciente paciente = (Paciente) cbPacientes.getSelectedItem();
        if (descricao.isEmpty() || dataExame.isEmpty() || paciente == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        Exame exame = new Exame(0L, descricao, dataExame, paciente.getId());
        exameService.adicionarExame(exame);
        JOptionPane.showMessageDialog(this, "Exame cadastrado com sucesso!");
        limparCampos();
        main.loadTableExame();
    }
}
