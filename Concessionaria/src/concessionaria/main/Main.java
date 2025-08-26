package concessionaria.main;
import concessionaria.ui.MenuPrincipal; // Importar a nova classe
import java.time.LocalDate;
import concessionaria.fachada.Concessionaria;
import concessionaria.fachada.FachadaGerente;
import concessionaria.fachada.FachadaVendedor;

public class Main {
    public static void main(String[] args) {
        Concessionaria minhaConcessionaria = new Concessionaria();
        FachadaGerente gerente = new FachadaGerente("João Gerente", "11122233344", LocalDate.of(1974, 3, 5));
        FachadaVendedor vendedor = new FachadaVendedor("Maria Vendedora", "55566677788", LocalDate.of(1989, 3, 20));
        
        minhaConcessionaria.carregarVeiculos("C:\\Users\\eusin\\OneDrive\\Documentos\\GitHub\\Projeto-Concession-ria\\Concessionaria\\src\\concessionaria\\repositorios\\veiculos.txt");
        minhaConcessionaria.carregarClientes("C:\\Users\\eusin\\OneDrive\\Documentos\\GitHub\\Projeto-Concession-ria\\Concessionaria\\src\\concessionaria\\repositorios\\clientes.txt");

        // Instanciar e iniciar a nova classe de menu
        MenuPrincipal menu = new MenuPrincipal(minhaConcessionaria, gerente, vendedor);
        menu.iniciar();
    }
}