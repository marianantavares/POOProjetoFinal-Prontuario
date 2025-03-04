package service;

import java.util.List;

import dao.GenericDAO;
import model.Paciente;

public class PacienteService {

	private GenericDAO<Paciente, Long> daoPaciente;
	
	
	public PacienteService(GenericDAO<Paciente, Long>dao) {
		this.daoPaciente = dao;
	}
	
	public void adicionarPaciente(Paciente p) {
		daoPaciente.add(p);
	}
	
	public Paciente localizarPacientePorId(Long id) {
		return daoPaciente.findByID(id);
	}
	
	public void deletarPaciente(Paciente p) {
		daoPaciente.delete(p);
	}
	
	public List<Paciente> getPacientes(){
		return daoPaciente.getAll();
	}
	
	public void atualizarPaciente(Paciente p) {
		daoPaciente.update(p);
	}
	
}
