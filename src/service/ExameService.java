package service;

import java.util.List;
import dao.GenericDAO;
import model.Exame;

public class ExameService {
    private GenericDAO<Exame, Long> daoExame;
    
    public ExameService(GenericDAO<Exame, Long> dao) {
        this.daoExame = dao;
    }
    
    public void adicionarExame(Exame exame) {
        daoExame.add(exame);
    }
    
    public Exame localizarExamePorId(Long id) {
        return daoExame.findByID(id);
    }
    
    public void deletarExame(Exame exame) {
        daoExame.delete(exame);
    }
    
    public List<Exame> getExames() {
        return daoExame.getAll();
    }
    
    public void atualizarExame(Exame exame) {
        daoExame.update(exame);
    }
}
