import java.io.*;
import java.util.*;

public class JogoBankrupt {
    private static final int RODADAS_MAX = 1000;
    private static final int SIMULACOES = 300;

    private List<Propriedade> propriedades;
    private List<Jogador> jogadores;
    private Random random = new Random();

    public JogoBankrupt() {
        propriedades = carregarPropriedades();
        jogadores = List.of(
                new JogadorImpulsivo(),
                new JogadorExigente(),
                new JogadorCauteloso(),
                new JogadorAleatorio()
        );
    }

    private List<Propriedade> carregarPropriedades() {
        List<Propriedade> propriedades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/gameConfig.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split("\\s+");
                int precoVenda = Integer.parseInt(partes[0]);
                int precoAluguel = Integer.parseInt(partes[1]);
                propriedades.add(new Propriedade(precoVenda, precoAluguel));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler gameConfig.txt: " + e.getMessage());
        }
        return propriedades;
    }

    public void jogarPartida() {
        for (Jogador jogador : jogadores) {
            jogador.moedas = 300;
            jogador.posicao = 0;
            jogador.ativo = true;
        }

        int rodadas = 0;
        while (rodadas < RODADAS_MAX && jogadores.stream().filter(j -> j.ativo).count() > 1) {
            for (Jogador jogador : jogadores) {
                if (!jogador.ativo) continue;

                int passos = random.nextInt(6) + 1;
                jogador.mover(passos, propriedades);

                Propriedade propriedade = propriedades.get(jogador.posicao);
                if (propriedade.dono == null && jogador.moedas >= propriedade.precoVenda && jogador.deveComprar(propriedade)) {
                    jogador.moedas -= propriedade.precoVenda;
                    propriedade.dono = jogador;
                } else {
                    jogador.pagarAluguel(propriedade, propriedades);
                }
            }
            rodadas++;
        }

        int maxMoedas = jogadores.stream().mapToInt(j -> j.moedas).max().orElse(0);
        Jogador vencedor = jogadores.stream().filter(j -> j.moedas == maxMoedas && j.ativo).findFirst().orElse(null);
        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.tipo + "!");
        } else {
            System.out.println("Jogo terminou por timeout!");
        }
    }

    public static void main(String[] args) {
        int timeouts = 0;
        int totalRodadas = 0;
        Map<String, Integer> vitorias = new HashMap<>();

        for (int i = 0; i < SIMULACOES; i++) {
            JogoBankrupt jogo = new JogoBankrupt();
            jogo.jogarPartida();

            int maxMoedas = jogo.jogadores.stream().mapToInt(j -> j.moedas).max().orElse(0);
            Jogador vencedor = jogo.jogadores.stream().filter(j -> j.moedas == maxMoedas && j.ativo).findFirst().orElse(null);

            if (vencedor != null) {
                vitorias.put(vencedor.tipo, vitorias.getOrDefault(vencedor.tipo, 0) + 1);
            } else {
                timeouts++;
            }
            totalRodadas += jogo.jogadores.stream().mapToInt(j -> j.posicao).sum();
        }

        System.out.println("Timeouts: " + timeouts);
        System.out.println("Rodadas Médias: " + (totalRodadas / (double) SIMULACOES));
        vitorias.forEach((tipo, contagem) -> System.out.printf("Porcentagem de Vitórias (%s): %.2f%%\n", tipo, (contagem * 100.0 / SIMULACOES)));
    }
}
