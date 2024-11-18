public class JogadorImpulsivo extends Jogador {
    public JogadorImpulsivo() {
        super("Impulsivo");
    }

    @Override
    public boolean deveComprar(Propriedade propriedade) {
        return true;
    }
}

