import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.*;
import java.util.*;

public class Programa {

    // --- VARIÁVEIS GLOBAIS ---
    private static List<Livro> listaLivros = new LinkedList<>();
    // Nome fixo para o arquivo de persistência
    private static final String ARQUIVO_DADOS = "banco_livros.dat";

    // --- CLASSE INTERNA PARA O NÓ DA ÁRVORE ---
    private static class NoArvore {
        Livro livro;
        NoArvore esquerda;
        NoArvore direita;

        public NoArvore(Livro livro) {
            this.livro = livro;
            this.esquerda = null;
            this.direita = null;
        }
    }

    // --- MÉTODOS AUXILIARES DA ÁRVORE ---

    // Insere ordenado pelo número de páginas
    private static NoArvore inserirNaArvore(NoArvore atual, Livro novoLivro) {
        if (atual == null) {
            return new NoArvore(novoLivro);
        }

        // Critério: Número de Páginas
        if (novoLivro.getNumeroPaginas() < atual.livro.getNumeroPaginas()) {
            atual.esquerda = inserirNaArvore(atual.esquerda, novoLivro);
        } else {
            atual.direita = inserirNaArvore(atual.direita, novoLivro);
        }
        return atual;
    }

    // Percurso Em-Ordem
    private static void imprimirEmOrdem(NoArvore no, StringBuilder sb) {
        if (no != null) {
            imprimirEmOrdem(no.esquerda, sb);
            sb.append(no.livro.toString()).append("\n");
            imprimirEmOrdem(no.direita, sb);
        }
    }

    // Percurso Pré-Ordem
    private static void imprimirPreOrdem(NoArvore no, StringBuilder sb) {
        if (no != null) {
            sb.append(no.livro.toString()).append("\n");
            imprimirPreOrdem(no.esquerda, sb);
            imprimirPreOrdem(no.direita, sb);
        }
    }

    // --- MÉTODO MAIN ---
    public static void main(String[] args) {

        // 1. AUTO-RECUPERAÇÃO AO INICIAR
        Serializador<List<Livro>> serializador = new Serializador<>();
        List<Livro> dadosRecuperados = serializador.lerArquivoAutomatico(ARQUIVO_DADOS);

        if (dadosRecuperados != null) {
            listaLivros = dadosRecuperados;
        }

        int opcao = 0;
        Object[] botoesMenu = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

        do {
            String menu = "Escolha uma das opções abaixo:\n" +
                    "1 - Incluir livro na LISTA \n" +
                    "2 - Deletar livro da LISTA \n" +
                    "3 - Mostrar LISTA de livros \n" +
                    "4 - Inserir lista de livros em uma FILA \n" +
                    "5 - Criar PILHAS de livros (mesmo gênero) \n" +
                    "6 - Inserir lista de livros em ARVORE \n" +
                    "7 - Salvar Lista \n" +
                    "8 - Recuperar Lista Salva \n" +
                    "9 - [Extra] Criar índice invertido \n" +
                    "10 - Finalizar o Programa";

            opcao = JOptionPane.showOptionDialog(null, menu, "Menu Principal - Gestão de Livros",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, botoesMenu, botoesMenu[0]);

            // --- 1. Incluir livro na LISTA ---
            if (opcao == 0) {
                String titulo = JOptionPane.showInputDialog(null, "Digite o Título do livro:");

                if (titulo != null && !titulo.trim().isEmpty()) {
                    String genero = JOptionPane.showInputDialog(null, "Digite o Gênero (Ex: Romance, Terror, Técnico):");
                    String paginasStr = JOptionPane.showInputDialog(null, "Digite o número de páginas:");

                    try {
                        int numeroPaginas = Integer.parseInt(paginasStr);
                        Livro novoLivro = new Livro(titulo, genero, numeroPaginas);
                        listaLivros.add(novoLivro);
                        JOptionPane.showMessageDialog(null, "Livro inserido com sucesso!");
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Erro: O número de páginas deve ser um valor inteiro válido.");
                    }
                }
            }

            // --- 2. Deletar livro da LISTA (Última ocorrência) ---
            if (opcao == 1) {
                String tituloParaRemover = JOptionPane.showInputDialog(null,
                        "Digite o título do livro para remover (apenas a última ocorrência):");

                if (tituloParaRemover != null && !tituloParaRemover.trim().isEmpty()) {
                    boolean removido = false;
                    for (int i = listaLivros.size() - 1; i >= 0; i--) {
                        Livro livroAtual = listaLivros.get(i);
                        if (livroAtual.getTitulo().equalsIgnoreCase(tituloParaRemover)) {
                            listaLivros.remove(i);
                            removido = true;
                            JOptionPane.showMessageDialog(null, "A última ocorrência do livro '" + tituloParaRemover + "' foi removida.");
                            break;
                        }
                    }
                    if (!removido) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado na lista.");
                    }
                }
            }

            // --- 3. Mostrar LISTA de livros ---
            if (opcao == 2) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista de livros está vazia!");
                } else {
                    StringBuilder listaTexto = new StringBuilder();
                    listaTexto.append("--- LIVROS CADASTRADOS ---\n\n");
                    for (Livro l : listaLivros) {
                        listaTexto.append(l.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, listaTexto.toString());
                }
            }

            // --- 4. Inserir em FILA (PriorityQueue) ---
            if (opcao == 3) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista está vazia! Não há livros para enfileirar.");
                } else {
                    PriorityQueue<Livro> filaPrioridade = new PriorityQueue<>(
                            (l1, l2) -> Integer.compare(l1.getNumeroPaginas(), l2.getNumeroPaginas())
                    );
                    filaPrioridade.addAll(listaLivros);

                    StringBuilder filaTexto = new StringBuilder();
                    filaTexto.append("--- FILA DE PRIORIDADE (Menos páginas primeiro) ---\n\n");
                    while (!filaPrioridade.isEmpty()) {
                        filaTexto.append(filaPrioridade.poll().toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, filaTexto.toString());
                }
            }

            // --- 5. Criar PILHAS por Gênero ---
            if (opcao == 4) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista está vazia! Não há livros para empilhar.");
                } else {
                    Map<String, Stack<Livro>> mapaPilhas = new HashMap<>();

                    for (Livro l : listaLivros) {
                        String genero = l.getGenero().trim().toUpperCase();
                        if (!mapaPilhas.containsKey(genero)) {
                            mapaPilhas.put(genero, new Stack<>());
                        }
                        mapaPilhas.get(genero).push(l);
                    }

                    StringBuilder pilhasTexto = new StringBuilder();
                    pilhasTexto.append("--- PILHAS DE LIVROS (Por Gênero) ---\n");

                    for (String generoChave : mapaPilhas.keySet()) {
                        pilhasTexto.append("\n[GÊNERO: ").append(generoChave).append("]\n");
                        Stack<Livro> pilhaAtual = mapaPilhas.get(generoChave);
                        for (int i = pilhaAtual.size() - 1; i >= 0; i--) {
                            pilhasTexto.append("  (Topo) -> ").append(pilhaAtual.get(i).toString()).append("\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, pilhasTexto.toString());
                }
            }

            // --- 6. Inserir em ÁRVORE ---
            if (opcao == 5) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista está vazia! Não há livros para inserir na árvore.");
                } else {
                    NoArvore raiz = null;
                    List<Livro> repetidos = new ArrayList<>();
                    Set<String> titulosNaArvore = new HashSet<>();

                    for (Livro l : listaLivros) {
                        String tituloNorm = l.getTitulo().trim().toUpperCase();
                        if (titulosNaArvore.contains(tituloNorm)) {
                            repetidos.add(l);
                        } else {
                            raiz = inserirNaArvore(raiz, l);
                            titulosNaArvore.add(tituloNorm);
                        }
                    }

                    StringBuilder saida = new StringBuilder();
                    saida.append("--- ÁRVORE EM-ORDEM (Crescente por Páginas) ---\n");
                    imprimirEmOrdem(raiz, saida);

                    saida.append("\n--- ÁRVORE PRÉ-ORDEM (Raiz -> Esq -> Dir) ---\n");
                    imprimirPreOrdem(raiz, saida);

                    if (!repetidos.isEmpty()) {
                        saida.append("\n--- LIVROS NÃO INSERIDOS (Títulos Repetidos) ---\n");
                        for (Livro rep : repetidos) {
                            saida.append(rep.toString()).append("\n");
                        }
                    } else {
                        saida.append("\n(Nenhum livro foi rejeitado por repetição de título)\n");
                    }
                    JOptionPane.showMessageDialog(null, saida.toString());
                }
            }

            // --- 7. Salvar Lista (Serialização) ---
            if (opcao == 6) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Lista vazia. Nada a salvar.");
                } else {
                    serializador.gravarArquivoAutomatico(listaLivros, ARQUIVO_DADOS);
                    JOptionPane.showMessageDialog(null, "Lista salva com sucesso em " + ARQUIVO_DADOS);
                }
            } // Faltava fechar esta chave no seu código original

            // --- 8. Recuperar (Manual) ---
            if (opcao == 7) {
                List<Livro> rec = serializador.lerArquivoAutomatico(ARQUIVO_DADOS);
                if (rec != null) {
                    listaLivros = rec;
                    JOptionPane.showMessageDialog(null, "Lista recuperada manualmente com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhum arquivo de dados encontrado.");
                }
            }

            // --- 9. [Extra] Criar índice invertido ---
            if (opcao == 8) {
                if (listaLivros.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A lista de livros do sistema está vazia.");
                } else {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Selecione o arquivo de texto com os títulos");
                    int retorno = chooser.showOpenDialog(null);

                    if (retorno == JFileChooser.APPROVE_OPTION) {
                        File arquivo = chooser.getSelectedFile();
                        List<String> linhasDoArquivo = new ArrayList<>();

                        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                            String linha;
                            while ((linha = br.readLine()) != null) {
                                if (!linha.trim().isEmpty()) {
                                    linhasDoArquivo.add(linha.trim());
                                }
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo: " + e.getMessage());
                            continue;
                        }

                        List<String> indiceInvertido = new ArrayList<>();
                        for (Livro livroDoSistema : listaLivros) {
                            int contador = 0;
                            for (String tituloNoArquivo : linhasDoArquivo) {
                                if (livroDoSistema.getTitulo().equalsIgnoreCase(tituloNoArquivo)) {
                                    contador++;
                                }
                            }
                            indiceInvertido.add(livroDoSistema.getTitulo() + " - " + contador);
                        }

                        StringBuilder resultado = new StringBuilder();
                        resultado.append("--- FREQUÊNCIA DE LIVROS (Índice) ---\n\n");
                        for (String item : indiceInvertido) {
                            resultado.append(item).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, resultado.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Operação cancelada.");
                    }
                }
            }

        } while (opcao != 9 && opcao != -1); // 9 = Botão 10 (Sair)

        // --- SALVAMENTO FINAL AO SAIR ---
        if (!listaLivros.isEmpty()) {
            serializador.gravarArquivoAutomatico(listaLivros, ARQUIVO_DADOS);
        }
    }
}