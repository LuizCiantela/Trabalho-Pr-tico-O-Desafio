public class JogadorExigente extends Jogador {
    public JogadorExigente() {
        super("Exigente");
    }

    @Override
    public boolean deveComprar(Propriedade propriedade) {
        return propriedade.precoAluguel > 50;
    }
}
