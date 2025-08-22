package entidades;

import java.time.LocalDate;

public class Veiculo {

    private int id;
    private final String modelo;
    private final String marca;
    private final int ano;
    private double preco;
    private boolean disponivelParaAluguel;
    private LocalDate disponivelApos;

    public Veiculo(String modelo, String marca, int ano, double preco) {
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.disponivelParaAluguel = true;
        this.disponivelApos = null;
    }
    
    // Adicionado um construtor com o ID
    public Veiculo(int id, String modelo, String marca, int ano, double preco) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.disponivelParaAluguel = true;
        this.disponivelApos = null;
    }

    public boolean isDisponivelParaAluguel() { return disponivelParaAluguel;}
    public void setDisponivelParaAluguel(boolean disponivelParaAluguel) {this.disponivelParaAluguel = disponivelParaAluguel;}
    public LocalDate getDisponivelApos() {return disponivelApos;}
    public void setDisponivelApos(LocalDate disponivelApos) {this.disponivelApos = disponivelApos;}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
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
                " | Preço: R$" + String.format("%.2f", preco) +
                " | Disponibilidade: " + disponivelParaAluguel;
    }
}