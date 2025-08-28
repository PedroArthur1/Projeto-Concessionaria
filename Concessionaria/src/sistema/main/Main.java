package sistema.main;
import java.time.LocalDate;

import sistema.fachada.Concessionaria;
import sistema.fachada.FachadaGerente;
import sistema.fachada.FachadaVendedor;
import sistema.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Concessionaria minhaConcessionaria = new Concessionaria();
        FachadaGerente gerente = new FachadaGerente("João Gerente", "11122233344", LocalDate.of(1974, 3, 5));
        FachadaVendedor vendedor = new FachadaVendedor("Maria Vendedora", "55566677788", LocalDate.of(1989, 3, 20)); // retirar isso urgente
        
        minhaConcessionaria.carregarVeiculos("Concessionaria\\src\\concessionaria\\repositorios\\veiculos.txt");
        minhaConcessionaria.carregarClientes("Concessionaria\\src\\concessionaria\\repositorios\\clientes.txt");

        // Instanciar e iniciar a nova classe de menu
        MenuPrincipal menu = new MenuPrincipal(minhaConcessionaria, gerente, vendedor);
        menu.iniciar();
    }
}