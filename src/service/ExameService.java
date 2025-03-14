package service;

import java.util.List;
import dao.GenericDAO;
import exceptions.ExameException;
import model.Exame;

public class ExameService {
    private GenericDAO<Exame, Long> daoExame;
    
    public ExameService(GenericDAO<Exame, Long> dao) {
        this.daoExame = dao;
    }
    
    public void adicionarExame(Exame exame) {
        try {
            daoExame.add(exame);
        } catch (Exception e) {
            throw new ExameException("Erro ao adicionar exame");
        }
    }
    
    public Exame localizarExamePorId(Long id) {
        try {
            return daoExame.findByID(id);
        } catch (Exception e) {
            throw new ExameException("Erro ao localizar exame");
        }
    }
    
    public void deletarExame(Exame exame) {
        try {
            daoExame.delete(exame);
        } catch (Exception e) {
            throw new ExameException("Erro ao deletar exame");
        }
    }
    
    public List<Exame> getExames() {
        try {
            return daoExame.getAll();
        } catch (Exception e) {
            throw new ExameException("Erro ao listar exames");
        }
    }
    
    public void atualizarExame(Exame exame) {
        try {
            daoExame.update(exame);
        } catch (Exception e) {
            throw new ExameException("Erro ao atualizar exame");
        }
    }
}
