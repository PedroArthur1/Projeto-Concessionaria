package concessionaria.main;
import concessionaria.ui.MenuPrincipal; // Importar a nova classe
import negocio.entidades.Cliente;
import negocio.entidades.Veiculo;
import negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import negocio.excessoes.cliente.CPFDeveSerUnicoException;
import negocio.excessoes.cliente.NomeDoClienteContemNumerosException;
import java.time.LocalDate;
import fachada.Gerente;
import fachada.Vendedor;

public class Main {
    public static void main(String[] args) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        Concessionaria minhaConcessionaria = new Concessionaria();
        Gerente gerente = new Gerente("João Gerente", "11122233344", LocalDate.of(1974, 3, 5));
        Vendedor vendedor = new Vendedor("Maria Vendedora", "55566677788", LocalDate.of(1989, 3, 20));
        
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