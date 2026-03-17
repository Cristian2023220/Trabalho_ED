# 📚 Gerenciador de Livros - Estrutura de Dados

Este projeto foi desenvolvido como parte da disciplina de **Estrutura de Dados** em Java. O objetivo é aplicar conceitos fundamentais de manipulação de dados (Listas, Pilhas, Filas, Árvores e Persistência) através de um sistema de gerenciamento de livros.

## 🚀 Funcionalidades

O sistema opera através de um menu interativo com `JOptionPane` e oferece as seguintes operações:

### 1. Manipulação de Lista (LinkedList)
- **Incluir Livro:** Adiciona um novo livro contendo Título, Gênero e Número de Páginas.
- **Deletar Livro:** Remove a **última ocorrência** de um livro específico (busca pelo título).
- **Listar:** Exibe todos os livros cadastrados na ordem de inserção.

### 2. Estruturas Especiais
- **Fila de Prioridade (Queue):** Organiza os livros por ordem de leitura (menor número de páginas primeiro).
- **Pilhas por Gênero (Stacks):** Agrupa os livros em pilhas separadas baseadas no gênero literário (Ex: Uma pilha só para "Terror", outra para "Romance").
- **Árvore Binária (Binary Search Tree):**
    - Insere os livros ordenando pelo **número de páginas**.
    - Exibe percursos **Em-Ordem** (crescente) e **Pré-Ordem**.
    - Identifica e separa livros com títulos duplicados (não são inseridos na árvore).

### 3. Persistência e Arquivos
- **Serialização Automática:** Os dados são salvos automaticamente em `banco_livros.dat` ao fechar o programa e carregados ao iniciar.
- **Salvar/Recuperar Manual:** Opções dedicadas para forçar o salvamento ou recarregamento dos dados.
- **Índice Invertido:** Lê um arquivo `.txt` externo contendo títulos e conta a frequência de ocorrência dos livros cadastrados no sistema.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java (JDK 8 ou superior)
- **Interface:** Swing (`JOptionPane`, `JFileChooser`)
- **Conceitos Aplicados:**
    - `LinkedList` (Lista Encadeada)
    - `PriorityQueue` (Fila de Prioridade)
    - `Stack` & `HashMap` (Mapeamento de Pilhas)
    - Árvore Binária Manual (Classe `NoArvore` recursiva)
    - `Serializable` (Persistência de Objetos)
    - Manipulação de Arquivos (`File`, `BufferedReader`)

---

## 📂 Estrutura do Projeto

O código está organizado nas seguintes classes:

| Classe | Responsabilidade |
| :--- | :--- |
| **`Programa.java`** | Classe principal contendo o `main`, o menu, a lógica das estruturas de dados e a classe interna `NoArvore`. |
| **`Livro.java`** | Modelo de dados (Objeto) que implementa `Comparable` e `Serializable`. Atributos: Título, Gênero, Páginas. |
| **`Serializador.java`** | Classe genérica (`<Type>`) responsável por gravar e ler os objetos em arquivos binários. |

---

## ▶️ Como Executar

1. **Pré-requisitos:** Tenha o [Java JDK](https://www.oracle.com/java/technologies/downloads/) instalado.
2. **Clonar/Baixar:** Baixe os arquivos `.java` para uma pasta.
3. **Compilar:**
   ```bash
   javac Programa.java Livro.java Serializador.java
