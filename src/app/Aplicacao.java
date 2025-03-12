package app;

import javax.swing.SwingUtilities;

import dao.ExameDAO;
import dao.PacienteDAO;
import db.MySQLDatabaseConnection;
import gui.TelaPrincipal;
import service.ExameService;
import service.PacienteService;


public class Aplicacao {
    public static void main(String[] args) {
        MySQLDatabaseConnection conn = new MySQLDatabaseConnection();
        PacienteService pacServ = new PacienteService(new PacienteDAO(conn));
        ExameService exameServ = new ExameService(new ExameDAO(conn));
        
        SwingUtilities.invokeLater(() -> new TelaPrincipal(pacServ, exameServ).setVisible(true));
    }
}
