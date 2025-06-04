package atividade2;

public class ArvoreAVL {
    private No raiz;

    /** Insere um valor na AVL. */
    public void inserir(int valor) {
        raiz = inserirAVL(raiz, valor);
    }

    private No inserirAVL(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }
        if (valor < no.valor) {
            no.esquerdo = inserirAVL(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = inserirAVL(no.direito, valor);
        } else {
            // valor duplicado: não insere outra vez
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerdo), altura(no.direito));
        return balancear(no);
    }

    /** Remove um valor se existir; retorna true se removeu, false se não existe. */
    public boolean remover(int valor) {
        if (!contem(valor)) {
            return false;
        }
        raiz = removerAVL(raiz, valor);
        return true;
    }

    private No removerAVL(No no, int valor) {
        if (no == null) {
            return null;
        }
        if (valor < no.valor) {
            no.esquerdo = removerAVL(no.esquerdo, valor);
        } else if (valor > no.valor) {
            no.direito = removerAVL(no.direito, valor);
        } else {
            // achou nó a remover
            if (no.esquerdo == null || no.direito == null) {
                no = (no.esquerdo != null) ? no.esquerdo : no.direito;
            } else {
                No temp = menorNo(no.direito);
                no.valor = temp.valor;
                no.direito = removerAVL(no.direito, temp.valor);
            }
        }
        if (no == null) {
            return null;
        }
        no.altura = 1 + Math.max(altura(no.esquerdo), altura(no.direito));
        return balancear(no);
    }

    private No menorNo(No no) {
        while (no.esquerdo != null) {
            no = no.esquerdo;
        }
        return no;
    }

    private int altura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    private int balanco(No no) {
        return (no == null) ? 0 : altura(no.esquerdo) - altura(no.direito);
    }

    private No balancear(No no) {
        int balance = balanco(no);

        // Rotação simples à direita
        if (balance > 1 && balanco(no.esquerdo) >= 0) {
            return rotacaoDireita(no);
        }
        // Rotação dupla: esquerda→direita
        if (balance > 1 && balanco(no.esquerdo) < 0) {
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
        // Rotação simples à esquerda
        if (balance < -1 && balanco(no.direito) <= 0) {
            return rotacaoEsquerda(no);
        }
        // Rotação dupla: direita→esquerda
        if (balance < -1 && balanco(no.direito) > 0) {
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }
        return no;
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerdo;
        No T2 = x.direito;

        x.direito = y;
        y.esquerdo = T2;

        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direito;
        No T2 = y.esquerdo;

        y.esquerdo = x;
        x.direito = T2;

        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;

        return y;
    }

    /** Retorna true se a árvore contiver o valor. */
    public boolean contem(int valor) {
        return contem(raiz, valor);
    }

    private boolean contem(No no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        return (valor < no.valor) ? contem(no.esquerdo, valor) : contem(no.direito, valor);
    }

    /** Retorna em String a travessia em-ordem (in-order). */
    public String imprimirEmOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdem(raiz, sb);
        return sb.toString().trim();
    }

    private void emOrdem(No no, StringBuilder sb) {
        if (no != null) {
            emOrdem(no.esquerdo, sb);
            sb.append(no.valor).append(" ");
            emOrdem(no.direito, sb);
        }
    }

    /** Retorna em String a travessia pré-ordem (pre-order). */
    public String imprimirPreOrdem() {
        StringBuilder sb = new StringBuilder();
        preOrdem(raiz, sb);
        return sb.toString().trim();
    }

    private void preOrdem(No no, StringBuilder sb) {
        if (no != null) {
            sb.append(no.valor).append(" ");
            preOrdem(no.esquerdo, sb);
            preOrdem(no.direito, sb);
        }
    }

    /** Retorna em String a travessia pós-ordem (post-order). */
    public String imprimirPosOrdem() {
        StringBuilder sb = new StringBuilder();
        posOrdem(raiz, sb);
        return sb.toString().trim();
    }

    private void posOrdem(No no, StringBuilder sb) {
        if (no != null) {
            posOrdem(no.esquerdo, sb);
            posOrdem(no.direito, sb);
            sb.append(no.valor).append(" ");
        }
    }

    public No getRaiz() {
        return raiz;
    }

    /** 
     * Imprime no console a árvore de forma “sideways” (hierárquica). 
     * (Filho direito aparece acima, filho esquerdo abaixo, indentado por nível.)
     */
    public void imprimirHierarquicoNoConsole() {
        imprimirHierarquicoNoConsole(raiz, 0);
    }

    private void imprimirHierarquicoNoConsole(No no, int nivel) {
        if (no != null) {
            imprimirHierarquicoNoConsole(no.direito, nivel + 1);
            System.out.println("    ".repeat(nivel) + no.valor);
            imprimirHierarquicoNoConsole(no.esquerdo, nivel + 1);
        }
    }

    /** 
     * Retorna em String a representação “sideways” (para exibir no JTextArea). 
     */
    public String imprimirHierarquicoEmString() {
        StringBuilder sb = new StringBuilder();
        imprimirHierarquicoEmString(raiz, 0, sb);
        return sb.toString();
    }

    private void imprimirHierarquicoEmString(No no, int nivel, StringBuilder sb) {
        if (no != null) {
            imprimirHierarquicoEmString(no.direito, nivel + 1, sb);
            sb.append("    ".repeat(nivel)).append(no.valor).append("\n");
            imprimirHierarquicoEmString(no.esquerdo, nivel + 1, sb);
        }
    }

    /** 
     * Retorna em String a impressão “vertical com arestas”, 
     * desenhando apenas linhas diretas (pais → filhos) em ASCII. 
     * Exemplo de saída (para um conjunto de nós): 
     * 
     *               6 
     *              / \ 
     *             4   25 
     *            / \  / \ 
     *           2  5 15 35 
     *              \  \   \ 
     *               8  18  56 
     * 
     * A raiz (nível 0) fica na primeira linha, centralizada; 
     * cada filho aparece na linha abaixo, e há um “/” ou “\” logo abaixo de cada pai, apontando para o filho “imediato”. 
     */
    public String imprimirVerticalComArestasEmString() {
        int h = altura(raiz);
        if (h == 0) {
            return ""; // árvore vazia
        }

        // Número de níveis: h
        // Largura em colunas: nCols = 2^h - 1
        int nCols = (int) Math.pow(2, h) - 1;

        // Cada nó vai ocupar exatamente UMA posição em cada linha de nós,
        // mas no String final usaremos duas posições por coluna para inserir espaços entre colunas.
        // Então a largura em caracteres será: width = nCols * 2 - 1
        int width = nCols * 2 - 1;
        // E usaremos 2*h - 1 linhas de caracteres:
        //   - Para cada nível r (0..h-1) teremos uma linha de nós
        //   - E, exceto no último nível, teremos uma linha de arestas abaixo dos nós
        int nLines = h * 2 - 1;

        // Inicializa a matriz de caracteres com espaços em branco
        char[][] canvas = new char[nLines][width];
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = ' ';
            }
        }

        // Preenche recursivamente cada nó EM SUA COORDENADA no canvas, 
        // junto com as arestas “/” ou “\” imediatamente abaixo (se existir filho).
        preencherCanvas(raiz, 0, 0, nCols - 1, canvas, width);

        // Transforma o canvas em String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nLines; i++) {
            sb.append(canvas[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Método auxiliar que preenche:
     *  - A posição do nó (linha = nível * 2, coluna = coluna * 2) com o valor
     *  - Se existir filho esquerdo, desenha “/” em (linhaPai+1, colPai-1)
     *  - Se existir filho direito, desenha “\” em (linhaPai+1, colPai+1)
     * 
     * @param no       o nó atual
     * @param nivel    nível do nó (0 = raiz)
     * @param cLeft    coluna mínima (índice em [0..nCols-1]) para este sub-árvore
     * @param cRight   coluna máxima (índice em [0..nCols-1]) para este sub-árvore
     * @param canvas   matriz de caracteres [nLines][width] que será impressa
     * @param width    largura total em caracteres (equals 2*nCols-1)
     */
    private void preencherCanvas(No no,
                                 int nivel,
                                 int cLeft,
                                 int cRight,
                                 char[][] canvas,
                                 int width) {
        if (no == null) {
            return;
        }
        // Cálculo da coluna “meio” neste intervalo de colunas [cLeft .. cRight]:
        int meio = (cLeft + cRight) / 2;

        // Agora convertendo para posição no “canvas” em caracteres:
        // - linhaDoNo = nivel * 2   (cada nível ocupa 2 linhas: a própria e a das arestas)
        // - colDoNo   = meio * 2     (cada coluna ocupa 2 caracteres no final)
        int linhaDoNo = nivel * 2;
        int colDoNo = meio * 2;

        // Escreve o valor do nó no canvas (pode ser múltiplos dígitos, então convertemos para String)
        String sValor = String.valueOf(no.valor);
        for (int k = 0; k < sValor.length(); k++) {
            // Se o valor for, por exemplo, “15”, o caractere ‘1’ vai em canvas[linhaDoNo][colDoNo],
            // e o caractere ‘5’ em canvas[linhaDoNo][colDoNo+1], se couber. No entanto,
            // para manter alinhamento fixo, vamos sobrescrever a partir de colDoNo e, se exceder um dígito,
            // o próximo caractere ocupará colDoNo+1.
            if (colDoNo + k < width) {
                canvas[linhaDoNo][colDoNo + k] = sValor.charAt(k);
            }
        }

        // Desenha arestas para os filhos (se existirem)
        int linhaAresta = linhaDoNo + 1; // fica sempre na linha abaixo do nó
        // Filho esquerdo?
        if (no.esquerdo != null) {
            // Se filho existe, desenha “/” em (linhaAresta, colDoNo - 1)
            if (colDoNo - 1 >= 0) {
                canvas[linhaAresta][colDoNo - 1] = '/';
            }
            // Recuamos cRight para meio-1 e chamamos recursivamente
            preencherCanvas(no.esquerdo, nivel + 1, cLeft, meio - 1, canvas, width);
        }
        // Filho direito?
        if (no.direito != null) {
            // Desenha “\” em (linhaAresta, colDoNo + 1)
            if (colDoNo + 1 < width) {
                canvas[linhaAresta][colDoNo + 1] = '\\';
            }
            // Recuamos cLeft para meio+1 e chamamos recursivamente
            preencherCanvas(no.direito, nivel + 1, meio + 1, cRight, canvas, width);
        }
    }
}
