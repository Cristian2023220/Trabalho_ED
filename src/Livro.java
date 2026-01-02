import java.io.Serializable; // Importação necessária

// Adicione "implements Serializable" aqui
public class Livro implements Comparable<Livro>, Serializable {
    
    // Boa prática em serialização (controle de versão da classe), mas opcional em projetos simples
    private static final long serialVersionUID = 1L; 

    private String titulo;
    private String genero;
    private int numeroPaginas;

    public Livro(String titulo, String genero, int numeroPaginas) {
        this.titulo = titulo;
        this.genero = genero;
        this.numeroPaginas = numeroPaginas;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public int getNumeroPaginas() { return numeroPaginas; }

    @Override
    public String toString() {
        return "Título: " + titulo + " | Gênero: " + genero + " | Páginas: " + numeroPaginas;
    }

    @Override
    public int compareTo(Livro outroLivro) {
        return this.titulo.compareToIgnoreCase(outroLivro.titulo);
    }
}