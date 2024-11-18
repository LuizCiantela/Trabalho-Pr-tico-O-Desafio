import java.util.List;

public abstract class Jogador {
    int moedas = 300;
    int posicao = 0;
    boolean ativo = true;
    String tipo;

    public Jogador(String tipo) {
        this.tipo = tipo;
    }

    public abstract boolean deveComprar(Propriedade propriedade);

    public void mover(int passos, List<Propriedade> propriedades) {
        posicao = (posicao + passos) % propriedades.size();
        if (posicao == 0) moedas += 100;
    }

    public void pagarAluguel(Propriedade propriedade, List<Propriedade> propriedades) {
        if (propriedade.dono != null && propriedade.dono != this) {
            if (moedas >= propriedade.precoAluguel) {
                moedas -= propriedade.precoAluguel;
                propriedade.dono.moedas += propriedade.precoAluguel;
            } else {
                ativo = false;
                liberarPropriedades(propriedades);
            }
        }
    }

    private void liberarPropriedades(List<Propriedade> propriedades) {
        for (Propriedade p : propriedades) {
            if (p.dono == this) p.dono = null;
        }
    }
}
