package br.edu.cruzeirodosul.calculadoradegorjetas;

public class Calculadora {
    /**
     * Calcula o valor de uma gorjeta dado o percentual
     * do valor total da conta.
     * @param valor valor total da conta
     * @param percentual Percentual usado para calcular a gorjeta
     * @return o valor da gorjeta
     */
    static double gorjeta (double valor, double percentual) {
        return valor * percentual / 100;
    }

    /**
     * calcula os valores padrão de gorjetas dado ao valor da conta
     * @param valor valor total da conta
     * @return Um vetor com três posições com as gorjetas
     */
    static double[] gorjeta(double valor) {
        double[] saida = new double[3];
        double[] percentuais = {5, 10, 15}; // Percentuais fixos

        for (int i = 0; i < percentuais.length; i++) {
            saida[i] = gorjeta(valor, percentuais[i]);
        }

        return saida;
    }

}
