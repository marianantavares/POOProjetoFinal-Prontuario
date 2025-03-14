package model;

import java.util.Objects;

public class Exame {

    private Long id;
    private String descricao;
    private String dataExame;
    // Novo atributo para associar o exame a um paciente pelo ID
    private Long pacienteId;
    private String pacienteNome;


    /**
     * Construtor com todos os parâmetros.
     * 
     * @param id         Identificador do exame.
     * @param descricao  Descrição do exame.
     * @param dataExame  Data do exame.
     * @param pacienteId Identificador do paciente associado ao exame.
     */
    public Exame(Long id, String descricao, String dataExame, Long pacienteId) {
        this.id = id;
        this.descricao = descricao;
        this.dataExame = dataExame;
        this.pacienteId = pacienteId;
    }

    /**
     * Construtor padrão.
     */
    public Exame() {
    }

    // Métodos getter e setter para cada atributo

    /**
     * Retorna o identificador do exame.
     * 
     * @return id do exame.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador do exame.
     * 
     * @param id do exame.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna a descrição do exame.
     * 
     * @return descrição do exame.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do exame.
     * 
     * @param descricao do exame.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a data do exame.
     * 
     * @return data do exame.
     */
    public String getDataExame() {
        return dataExame;
    }

    /**
     * Define a data do exame.
     * 
     * @param dataExame a ser definida.
     */
    public void setDataExame(String dataExame) {
        this.dataExame = dataExame;
    }

    /**
     * Retorna o identificador do paciente associado ao exame.
     * 
     * @return pacienteId.
     */
    public Long getPacienteId() {
        return pacienteId;
    }

    /**
     * Chave estrangeira para realcionar o paciente ao exame que foi feito.
     * 
     * @param pacienteId a ser definido.
     */
    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
    
    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Verifica se dois exames são iguais com base no ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Exame other = (Exame) obj;
        return Objects.equals(id, other.id);
    }

    /**
     * Retorna uma representação em String do objeto Exame, incluindo o pacienteId.
     */
    @Override
    public String toString() {
        return "Exame [id=" + id + ", descricao=" + descricao + ", dataExame=" + dataExame + ", pacienteId=" + pacienteId + "]";
    }
}
