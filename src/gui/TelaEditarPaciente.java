package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import model.Paciente;
import service.PacienteService;

public class TelaEditarPaciente extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PacienteService pacService;
    private TelaPrincipal main;
    private Paciente paciente;
    
    private JTextField txfNome;
    private JTextField txfCpf;
    private JButton btnSalvar;
    private JButton btnCancelar;
    
    /**
     * Construtor para editar um paciente.
     * @param pacService serviço de pacientes para atualizar os dados.
     * @param paciente o paciente a ser editado.
     * @param main referência à TelaPrincipal para atualização da tabela.
     */
    public TelaEditarPaciente(PacienteService pacService, Paciente paciente, TelaPrincipal main) {
        this.pacService = pacService;
        this.main = main;
        this.paciente = paciente;
        
        setTitle("Editar Paciente");
        setSize(360,200);
        setModal(true);
        setLayout(new BorderLayout());
        
        // Painel do formulário
        JPanel panel = new JPanel();
        panel.add(new JLabel("CPF:"));
        txfCpf = new JTextField(paciente.getCpf(), 30);
        panel.add(txfCpf);
        panel.add(new JLabel("Nome:"));
        txfNome = new JTextField(paciente.getNome(), 28);
        panel.add(txfNome);
        add(panel, BorderLayout.CENTER);
        
        // Painel dos botões
        JPanel btnPanel = new JPanel();
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);
        add(btnPanel, BorderLayout.SOUTH);
        
        // Ação dos botões
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        
        setLocationRelativeTo(main);
        setVisible(true);
    }
    
    /**
     * Atualiza os dados do paciente e chama o método de atualização no service.
     */
    private void salvar(){
        // Atualiza os dados do paciente com os valores dos campos
        paciente.setCpf(txfCpf.getText());
        paciente.setNome(txfNome.getText());
        // Chama o método de atualização do serviço, que utiliza o DAO
        pacService.atualizarPaciente(paciente);
        JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
        // Atualiza a tabela na TelaPrincipal
        main.loadTablePaciente();
        dispose();
    }
}
