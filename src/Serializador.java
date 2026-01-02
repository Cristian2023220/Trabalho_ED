import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.LinkedList;

/**
 * A classe Serializador realiza a serialização e desserialização de classes java e arquivos serializados respectivamente
 * @param <Type>
 * @author Bruno Sousa
 */
public class Serializador<Type> {

    private Type element;

    public Serializador() { }

    public Serializador(Type element) {
        this.element = element;
    }

    /**
     * O metodo gravadorObjeto é um metodo que realiza o salvamento dos arquivos serializados
     * @param elemento
     */
    public void gravadorObjeto(Type elemento) {

        try {
            // Classe de janela de escolha de arquivos
            JFileChooser chooser = new JFileChooser();

            // A variavel retorno vai receber um inteiro da opção escolhida pelo usuário
            // na tela de salvamento de arquivo
            int opcaoRetornada = chooser.showSaveDialog(null);

            // Se a opção de salvamento do arquivo foi escolhida pelo usuário,
            // então faremos o processamento do salvamento do arquivo
            if(opcaoRetornada == JFileChooser.APPROVE_OPTION) {

                // Nesse ponto pegamos a URL absoluta do arquivo que foi salvo (local do arquivo e nome)
                String arquivoABSPath = chooser.getSelectedFile().getAbsolutePath();

                // Usamos a String "file" para construir a string de salvamento do arquivo + a extensão do arquivo
                String file = arquivoABSPath + ".txt";

                File arquivo = new File(file);

                // OutputStream do arquivo escolhido, passando sua URL de localização + a extensão como parâmetro
                FileOutputStream fos = new FileOutputStream(arquivo);

                ObjectOutputStream oos = new ObjectOutputStream(fos);

                // Efetiva o salvamento do arquivo
                oos.writeObject(elemento);

                System.out.println("Arquivo Salvo com Sucesso!");

                // Fecha as Streams. Este metodo deve ser chamado para liberar quaisquer recursos associados as Streams.
                oos.close();
                fos.close();
            } else {
                System.out.println("O arquivo NÃO foi salvo!");
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

    }

    /**
     * O metodo leitorObjeto faz a leitura de um arquivo serializado salvo no computador
     * @return
     */
    public Type leitorObjeto() {

        Type elemento = null;

        try {
            // Classe de janela de escolha de arquivos
            JFileChooser chooser = new JFileChooser();

            // Classe de filtro de extensão de arquivos usada para filtrar os arquivos da janela de seleção "chooser"
            // Parametros 1 - Descrição exibida na janela de seleção dos tipos de arquivos filtrados
            // Parametros 2 - Extensão dos arquivos que serão filtrados, podendo ser passada, mais de uma extensão
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Selecione apenas arquivos de texto",
                    "txt"
            );

            // Settamos o filtro na janela de seleção
            chooser.setFileFilter(filter);

            // A variavel retorno vai receber um inteiro que determinará se algum arquivo foi escolhido pelo usuário
            int opcaoRetornada = chooser.showOpenDialog(null);

            // Se algum arquivo foi selecionado pelo usuário, então faremos a leitura do arquivo escolhido
            if(opcaoRetornada == JFileChooser.APPROVE_OPTION) {
                // InputStream do arquivo escolhido, passando sua URL de localização como parâmetro
                FileInputStream fis = new FileInputStream(chooser.getSelectedFile().getAbsolutePath());

                ObjectInputStream ois = new ObjectInputStream(fis);

                // Feito a leitura do objeto dentro arquivo,
                // e fazendo um casting para garantir que os dados correspondam a respectiva classe
                elemento = (Type) ois.readObject();

                // Fecha as Streams. Este metodo deve ser chamado para liberar quaisquer recursos associados as Streams.
                ois.close();
                fis.close();
            } else {
                System.out.println("Nenhum arquivo foi selecionado pelo usuário!");
            }

            // O Java 7 introduziu o recurso multi-catch,
            // permitindo que você manipule vários tipos de exceção em um único bloco catch.
            // Isso pode levar a um código mais conciso e legível,
            // especialmente quando você deseja manipular diferentes exceções da mesma maneira.
        }catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return elemento;
    }
    
    public void gravarArquivoAutomatico(Type elemento, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(elemento);
            // Opcional: System.out.println("Auto-save realizado."); 
        } catch (IOException e) {
            System.err.println("Erro ao salvar automaticamente: " + e.getMessage());
        }
    
}
    
    
    public Type lerArquivoAutomatico(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return null; // Arquivo ainda não existe (primeira execução)
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Type) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler dados automáticos: " + e.getMessage());
            return null;
        }
    }
    
    
}
