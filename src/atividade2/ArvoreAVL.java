package atividade2;
public class ArvoreAVL extends ArvoreBinaria {

    private int altura(No no) {
        if (no == null) return 0;
        return Math.max(altura(no.esquerda), altura(no.direita)) + 1;
    }

    private int fatorBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        return y;
    }

    @Override
    protected No inserirRec(No no, int valor) {
        if (no == null) return new No(valor);

        if (valor < no.valor) no.esquerda = inserirRec(no.esquerda, valor);
        else if (valor > no.valor) no.direita = inserirRec(no.direita, valor);
        else return no;

        int balance = fatorBalanceamento(no);

        if (balance > 1 && valor < no.esquerda.valor)
            return rotacaoDireita(no);

        if (balance < -1 && valor > no.direita.valor)
            return rotacaoEsquerda(no);

        if (balance > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balance < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    @Override
    protected No removerRec(No no, int valor) {
        if (no == null) return no;

        if (valor < no.valor) no.esquerda = removerRec(no.esquerda, valor);
        else if (valor > no.valor) no.direita = removerRec(no.direita, valor);
        else {
            if (no.esquerda == null || no.direita == null) {
                no = (no.esquerda != null) ? no.esquerda : no.direita;
            } else {
                no.valor = menorValor(no.direita);
                no.direita = removerRec(no.direita, no.valor);
            }
        }

        if (no == null) return no;

        int balance = fatorBalanceamento(no);

        if (balance > 1 && fatorBalanceamento(no.esquerda) >= 0)
            return rotacaoDireita(no);

        if (balance > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balance < -1 && fatorBalanceamento(no.direita) <= 0)
            return rotacaoEsquerda(no);

        if (balance < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
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

