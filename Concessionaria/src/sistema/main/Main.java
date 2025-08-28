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
        
        minhaConcessionaria.carregarVeiculos("Concessionaria\\src\\sistema\\repositorios\\clientes.txt");
        minhaConcessionaria.carregarClientes("Concessionaria\\src\\sistema\\repositorios\\veiculos.txt");
        // ------------ PARA TESTAR O CODIGO EM SEU COMPUTADOR VOCÊ DEVE MUDAR O CAMINHO DO ARQUIVO ☝️ PARA O CAMINHO QUE A PASTA DO PROJETO ESTÁ NO SEU COMPUTADOR -----------
        // ------------ ENTRE NO PACOTE REPOSITORIOS E COPIE O CAMINHO DOS ARQUIVOS TXT E COLE ANTES DE RODAR A MAIN --------------


        // Instanciar e iniciar a nova classe de menu
        MenuPrincipal menu = new MenuPrincipal(minhaConcessionaria, gerente, vendedor);
        menu.iniciar();
    }
}