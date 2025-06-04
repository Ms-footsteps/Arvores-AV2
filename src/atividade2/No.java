package atividade2;

public class No {
    int valor;
    int altura;
    No esquerdo, direito;

    public No(int valor) {
        this.valor = valor;
        this.altura = 1;
        this.esquerdo = null;
        this.direito = null;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
