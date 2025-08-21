package entidades;
public class Veiculo {

    private static int contador = 1;
    private final int id;
    private String modelo;
    private String marca;
    private int ano;
    private double preco;

    public Veiculo(String modelo, String marca, int ano, double preco) {
        this.id=contador++;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public int getAno() { return ano; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Modelo: " + modelo +
               " | Marca: " + marca +
               " | Ano: " + ano +
               " | Preço: R$" + String.format("%.2f", preco);
    }
}