package sistema.negocio.entidades;

import java.time.LocalDate;

/**
 * Gerencia o estado e as informações de um veículo, como placa, modelo, preço e status de disponibilidade.
 * @author Pedro Arthur
 * @description Classe que define a entidade Veículo do sistema.
 */

public class Veiculo {

    private final String placa;
    private final String modelo;
    private final String marca;
    private final int ano;
    private double preco;
    private String statusDisponibilidade;
    private LocalDate disponivelApos;
    private double quilometragem;

    public Veiculo(String placa, String modelo, String marca, int ano, double preco, double quilometragem) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.statusDisponibilidade = "Disponivel";
        this.disponivelApos = null;
        this.quilometragem = quilometragem;
    }
    
    // construtor para facilitar a edição
    public Veiculo(String placa, String modelo, String marca, int ano, double preco) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.preco = preco;
        this.statusDisponibilidade = "Disponivel";
        this.disponivelApos = null;
    }

    public String getStatusDisponibilidade() { return statusDisponibilidade; }
    public void setStatusDisponibilidade(String statusDisponibilidade) { this.statusDisponibilidade = statusDisponibilidade; }
    public LocalDate getDisponivelApos() { return disponivelApos; }
    public void setDisponivelApos(LocalDate disponivelApos) { this.disponivelApos = disponivelApos; }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public String getMarca() { return marca; }
    public int getAno() { return ano; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public double getQuilometragem() { return quilometragem; }
    public void setQuilometragem(double quilometragem) { this.quilometragem = quilometragem; }

    @Override
    public String toString() {
        String status = statusDisponibilidade;
        if ("Indisponivel".equals(statusDisponibilidade) && disponivelApos != null) {
            status += " até " + disponivelApos;
        } // mostrar a data de devolucao do veiculo
        return "Placa: " + placa +
                " | Modelo: " + modelo +
                " | Marca: " + marca +
                " | Ano: " + ano +
                " | Preço: R$" + String.format("%.2f", preco) +
                " | Quilometragem: " + String.format("%.2f", quilometragem) + " Km" +
                " | Disponibilidade: " + status;
    }


}