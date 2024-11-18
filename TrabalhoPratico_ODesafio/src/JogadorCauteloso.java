public class JogadorCauteloso extends Jogador {
    public JogadorCauteloso() {
        super("Cauteloso");
    }

    @Override
    public boolean deveComprar(Propriedade propriedade) {
        return moedas - propriedade.precoVenda >= 80;
    }
}
