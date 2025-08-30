package sistema.main;

import sistema.fachada.Concessionaria;
import sistema.fachada.FachadaGerente;
import sistema.fachada.FachadaVendedor;
import sistema.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Concessionaria minhaConcessionaria = new Concessionaria();
        FachadaGerente gerente = new FachadaGerente();
        FachadaVendedor vendedor = new FachadaVendedor();
        
        minhaConcessionaria.carregarVeiculos("Concessionaria\\src\\sistema\\repositorios\\veiculos.txt");
        minhaConcessionaria.carregarClientes("Concessionaria\\src\\sistema\\repositorios\\clientes.txt");

        MenuPrincipal menu = new MenuPrincipal(minhaConcessionaria, gerente, vendedor);
        menu.iniciar();
    }
}