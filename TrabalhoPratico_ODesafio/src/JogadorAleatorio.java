import java.util.Random;

public class JogadorAleatorio extends Jogador {
    private static final Random random = new Random();

    public JogadorAleatorio() {
        super("Aleatório");
    }

    @Override
    public boolean deveComprar(Propriedade propriedade) {
        return random.nextBoolean();
    }
}

