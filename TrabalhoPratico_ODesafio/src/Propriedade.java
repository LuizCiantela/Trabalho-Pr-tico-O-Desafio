public class Propriedade {
    int precoVenda;
    int precoAluguel;
    Jogador dono;

    public Propriedade(int precoVenda, int precoAluguel) {
        this.precoVenda = precoVenda;
        this.precoAluguel = precoAluguel;
        this.dono = null;
    }
}
