package gui;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Paciente;
import service.PacienteService;

public class TelaCadastrarPaciente extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private PacienteService pacService;
	private TelaPrincipal main;
	private JPanel painelForm;
	private JPanel painelBotoes;
	private JButton btnSalvar;
	private JButton btnLimpar;
	private JButton btnSair;
	private JLabel lblNome;
	private JLabel lblCpf;
	private JTextField txfNome;
	private JTextField txfCpf;

	
	public TelaCadastrarPaciente(PacienteService pacService, TelaPrincipal main) {
		this.pacService = pacService;
		this.main = main;
		setSize(360,200);
		setResizable(false);
		setTitle("Cadastro de Pacientes");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		painelForm = new JPanel();
		lblNome =  new JLabel("Nome: ");
		lblCpf = new JLabel("CPF:   ");
		txfNome = new JTextField(26);
		txfCpf = new JTextField(26);
		painelForm.add(lblCpf);
		painelForm.add(txfCpf);
		painelForm.add(lblNome);
		painelForm.add(txfNome);
		add(painelForm, BorderLayout.CENTER);
		
		painelBotoes = new JPanel();
		btnSair = new JButton("Sair");
		btnSair.addActionListener(e -> fecharTela());
		btnLimpar = new JButton("Limpar");
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(e -> addPaciente());
		painelBotoes.add(btnSalvar);
		painelBotoes.add(btnLimpar);
		painelBotoes.add(btnSair);
		add(painelBotoes, BorderLayout.SOUTH);
		
		
		setModal(true);
		setVisible(true);
	}
	
	private void fecharTela() {
		this.hide();
	}
	
	private void addPaciente() {
		Paciente p = new Paciente(0L,  txfCpf.getText(),txfNome.getText());
		pacService.adicionarPaciente(p);
		JOptionPane.showMessageDialog(null, "Paciente Cadastrado com Sucesso");
		txfCpf.setText("");
		txfNome.setText("");
		main.loadTablePaciente();
	}
	
}