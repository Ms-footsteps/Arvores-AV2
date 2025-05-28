package atividade2;
public class ArvoreRubroNegra extends ArvoreBinaria {
    private NoRN raiz;

    private boolean ehVermelho(NoRN no) {
        return no != null && no.vermelho;
    }

    private NoRN rotacaoEsquerda(NoRN h) {
        NoRN x = (NoRN) h.direita;
        h.direita = x.esquerda;
        x.esquerda = h;
        x.vermelho = h.vermelho;
        h.vermelho = true;
        return x;
    }

    private NoRN rotacaoDireita(NoRN h) {
        NoRN x = (NoRN) h.esquerda;
        h.esquerda = x.direita;
        x.direita = h;
        x.vermelho = h.vermelho;
        h.vermelho = true;
        return x;
    }

    private void inverterCores(NoRN h) {
        h.vermelho = !h.vermelho;
        if (h.esquerda != null) ((NoRN) h.esquerda).vermelho = !((NoRN) h.esquerda).vermelho;
        if (h.direita != null) ((NoRN) h.direita).vermelho = !((NoRN) h.direita).vermelho;
    }

    public void inserir(int valor) {
        raiz = inserir(raiz, valor);
        raiz.vermelho = false;
    }

    private NoRN inserir(NoRN h, int valor) {
        if (h == null) return new NoRN(valor);

        if (valor < h.valor)
            h.esquerda = inserir((NoRN) h.esquerda, valor);
        else if (valor > h.valor)
            h.direita = inserir((NoRN) h.direita, valor);

        if (ehVermelho((NoRN) h.direita) && !ehVermelho((NoRN) h.esquerda))
            h = rotacaoEsquerda(h);
        if (ehVermelho((NoRN) h.esquerda) && ehVermelho((NoRN) ((NoRN) h.esquerda).esquerda))
            h = rotacaoDireita(h);
        if (ehVermelho((NoRN) h.esquerda) && ehVermelho((NoRN) h.direita))
            inverterCores(h);

        return h;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRec(NoRN no) {
        if (no != null) {
            imprimirEmOrdemRec((NoRN) no.esquerda);
            System.out.print(no.valor + (no.vermelho ? "R " : "N "));
            imprimirEmOrdemRec((NoRN) no.direita);
        }
    }
    // Remoção completa em árvores Rubro-Negras é mais extensa, mas pode ser adicionada se desejar.
 public String imprimir() {
    StringBuilder sb = new StringBuilder();
    imprimirRec(raiz, sb);
    return sb.toString();
}

private void imprimirRec(No no, StringBuilder sb) {
    if (no != null) {
        imprimirRec(no.esquerda, sb);
        sb.append(no.valor).append(" ");
        imprimirRec(no.direita, sb);
    }
}

}


