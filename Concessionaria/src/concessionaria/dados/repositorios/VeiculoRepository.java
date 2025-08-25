package concessionaria.repositorios;

import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoRepository {
    private final List<Veiculo> estoqueVeiculos;
    private int proximoId; // Atributo para controlar o próximo ID

    public VeiculoRepository() {
        this.estoqueVeiculos = new ArrayList<>();
        this.proximoId = 1; // Começa a contagem a partir de 1
    }
    
    // Método para adicionar veículo
    public void adicionar(Veiculo veiculo) {
        veiculo.setId(proximoId++); // Atribui o ID e incrementa o contador
        this.estoqueVeiculos.add(veiculo);
    }
    
    // Método para buscar veículo por modelo e ano
    public Veiculo buscar(String modelo, int ano) throws VeiculoNaoEncontradoException {
        for (Veiculo veiculo : estoqueVeiculos) {
            if (veiculo.getModelo().equalsIgnoreCase(modelo) && veiculo.getAno() == ano) {
                return veiculo;
            }
        }
        throw new VeiculoNaoEncontradoException("Veículo " + modelo + " (" + ano + ") não encontrado.");
    }

    // Método para buscar veículo por modelo
    public List<Veiculo> buscarPorModelo(String modelo) {
        return estoqueVeiculos.stream()
                .filter(v -> v.getModelo().equalsIgnoreCase(modelo))
                .collect(Collectors.toList());
    }
    
    // Método para buscar veículo por ID
    public Veiculo buscarPorId(int id) throws VeiculoNaoEncontradoException {
        return estoqueVeiculos.stream()
            .filter(v -> v.getId() == id)
            .findFirst()
            .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo com ID " + id + " não encontrado."));
    }

    // Método para remover veículo
    public void remover(String modelo) throws VeiculoNaoEncontradoException {
        boolean removido = estoqueVeiculos.removeIf(veiculo -> veiculo.getModelo().equalsIgnoreCase(modelo));
        if (!removido) {
            throw new VeiculoNaoEncontradoException("Veículo com modelo " + modelo + " não encontrado para remoção.");
        }
    }
    
    // Método para listar todos os veículos
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(estoqueVeiculos);
    }
}