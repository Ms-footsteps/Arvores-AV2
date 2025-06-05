package atividade2;

public class ArvoreAVL {
    private No raiz;
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
    public boolean contem(int valor) {
        return contem(raiz, valor);
    }
    private boolean contem(No no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        return (valor < no.valor) ? contem(no.esquerdo, valor) : contem(no.direito, valor);
    }
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
    public String imprimirVerticalComArestasEmString() {
        int h = altura(raiz);
        if (h == 0) {
            return ""; // árvore vazia
        }
        int nCols = (int) Math.pow(2, h) - 1;
        int width = nCols * 2;
        int nLines = h * 2 - 1;

        // Inicializa a matriz de caracteres com espaços em branco
        char[][] canvas = new char[nLines][width];
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = ' ';
            }
        }

        preencherCanvas(raiz, 0, 0, nCols - 1, canvas, width);

        // Transforma o canvas em String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nLines; i++) {
            sb.append(canvas[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void preencherCanvas(No no,
                                 int nivel,
                                 int cLeft,
                                 int cRight,
                                 char[][] canvas,
                                 int width) {
        if (no == null) {
            return;
        }
        int meio = (cLeft + cRight) / 2;

        int linhaDoNo = nivel * 2;
        int colDoNo = meio * 2;

        String sValor = String.valueOf(no.valor);
        for (int k = 0; k < sValor.length(); k++) {
           
            if (colDoNo + k < width) {
                canvas[linhaDoNo][colDoNo + k] = sValor.charAt(k);
            }
        }

        int linhaAresta = linhaDoNo + 1; 
        if (no.esquerdo != null) {
            if (colDoNo - 1 >= 0) {
                canvas[linhaAresta][colDoNo - 1] = '/';
            }
            
            preencherCanvas(no.esquerdo, nivel + 1, cLeft, meio - 1, canvas, width);
        }
        if (no.direito != null) {
            if (colDoNo + 1 < width) {
                canvas[linhaAresta][colDoNo + 1] = '\\';
            }
           
            preencherCanvas(no.direito, nivel + 1, meio + 1, cRight, canvas, width);
        }
    }
}
