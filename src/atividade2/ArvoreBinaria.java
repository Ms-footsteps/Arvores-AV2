package atividade2;
public class ArvoreBinaria {
    protected No raiz;

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    protected No inserirRec(No no, int valor) {
        if (no == null) return new No(valor);
        if (valor < no.valor)
            no.esquerda = inserirRec(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = inserirRec(no.direita, valor);
        return no;
    }

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    protected boolean buscarRec(No no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        return valor < no.valor ? buscarRec(no.esquerda, valor) : buscarRec(no.direita, valor);
    }

    public void remover(int valor) {
        raiz = removerRec(raiz, valor);
    }

    protected No removerRec(No no, int valor) {
        if (no == null) return null;
        if (valor < no.valor) {
            no.esquerda = removerRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerRec(no.direita, valor);
        } else {
            if (no.esquerda == null) return no.direita;
            else if (no.direita == null) return no.esquerda;
            no.valor = menorValor(no.direita);
            no.direita = removerRec(no.direita, no.valor);
        }
        return no;
    }

    protected int menorValor(No no) {
        int min = no.valor;
        while (no.esquerda != null) {
            min = no.esquerda.valor;
            no = no.esquerda;
        }
        return min;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRec(No no) {
        if (no != null) {
            imprimirEmOrdemRec(no.esquerda);
            System.out.print(no.valor + " ");
            imprimirEmOrdemRec(no.direita);
        }
    }
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
