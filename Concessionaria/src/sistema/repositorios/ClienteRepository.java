package sistema.repositorios;

import java.util.ArrayList;
import java.util.List;

import sistema.negocio.entidades.Cliente;
import sistema.negocio.excessoes.cliente.CPFClienteDeveConterOnzeNumeros;
import sistema.negocio.excessoes.cliente.CPFDeveSerUnicoException;
import sistema.negocio.excessoes.cliente.ClienteNaoEncontradoException;
import sistema.negocio.excessoes.cliente.NomeDoClienteContemNumerosException;

/**
 * Repositório para gerenciar e preservar os objetos de Cliente.
 * @author Emanuel Bezerra
 * @description Repositório para gerenciar as operações de persistência de clientes.
 */

public class ClienteRepository {
    private final List<Cliente> clientes;

    public ClienteRepository() {
        this.clientes = new ArrayList<>();
    }
    
    public void adicionar(Cliente cliente) throws NomeDoClienteContemNumerosException, CPFClienteDeveConterOnzeNumeros, CPFDeveSerUnicoException {
        if (cliente.getNome().matches(".*\\d.*")) {
            throw new NomeDoClienteContemNumerosException("O nome do cliente não pode conter números.");
        }
        if (cliente.getCpf().length() != 11) {
            throw new CPFClienteDeveConterOnzeNumeros("O CPF do cliente deve conter 11 números.");
        }
        for (Cliente c : this.clientes) {
            if (c.getCpf().equals(cliente.getCpf())) {
                throw new CPFDeveSerUnicoException("O CPF " + cliente.getCpf() + " já está cadastrado.");
            }
        }
        this.clientes.add(cliente);
    }
    
    public Cliente buscar(String cpf) throws ClienteNaoEncontradoException {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente com CPF " + cpf + " não encontrado.");
    }
    
    public void remover(String cpf) throws ClienteNaoEncontradoException {
        Cliente clienteRemover = buscar(cpf);
        clientes.remove(clienteRemover);
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
}