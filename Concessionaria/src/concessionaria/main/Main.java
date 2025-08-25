package concessionaria.main;
import concessionaria.ui.MenuPrincipal; // Importar a nova classe
import concessionaria.negocio.entidades.Cliente;
import concessionaria.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import concessionaria.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import concessionaria.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import java.time.LocalDate;
import concessionaria.fachada.Concessionaria;
import concessionaria.fachada.FachadaGerente;
import concessionaria.fachada.FachadaVendedor;

public class Main {
    public static void main(String[] args) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        Concessionaria minhaConcessionaria = new Concessionaria();
        FachadaGerente gerente = new FachadaGerente("João Gerente", "11122233344", LocalDate.of(1974, 3, 5));
        FachadaVendedor vendedor = new FachadaVendedor("Maria Vendedora", "55566677788", LocalDate.of(1989, 3, 20));
        
        // Adicionar dados iniciais
        minhaConcessionaria.adicionarVeiculo("Onix", "Chevrolet", 2023, 75000.00);
        minhaConcessionaria.adicionarVeiculo("Corolla", "Toyota", 2024, 180000.00);
        minhaConcessionaria.adicionarVeiculo("Gol", "Volkswagen", 2015, 45000.00);
        minhaConcessionaria.adicionarCliente(new Cliente("Ana Silva", "99988877766", LocalDate.of(1993, 3, 5)));
        minhaConcessionaria.adicionarCliente(new Cliente("Carlos Souza", "12345678900", LocalDate.of(2003, 3, 5)));

        // Instanciar e iniciar a nova classe de menu
        MenuPrincipal menu = new MenuPrincipal(minhaConcessionaria, gerente, vendedor);
        menu.iniciar();
    }
}