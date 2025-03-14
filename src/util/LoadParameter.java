package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadParameter {

    private static Properties properties;

    static {
        try {
            // Inicializa o objeto Properties e tenta carregar o arquivo
            properties = new Properties();
            try (InputStream is = new FileInputStream("file.config")) {
                properties.load(is);
                System.out.println("Arquivo de configuração carregado com sucesso.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo de configuração não encontrado. Verifique se 'file.config' está no diretório correto.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de configuração: " + e.getMessage());
        }
    }

    public static String getValor(String chave) {
        try {
            String valor = properties.getProperty(chave);
            
            // Verifica se a chave foi encontrada
            if (valor == null) {
                throw new IllegalArgumentException("A chave '" + chave + "' não foi encontrada no arquivo de configuração.");
            }

            return valor;
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao buscar valor: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao obter o valor da chave '" + chave + "': " + e.getMessage());
        }
        return null; // Retorna null caso ocorra erro
    }
}
