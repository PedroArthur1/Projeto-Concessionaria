package concessionaria.repositorios;

import entidades.Veiculo;
import excessoes.VeiculoNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VeiculoRepository {
    private final List<Veiculo> estoqueVeiculos;

    public VeiculoRepository() {
        this.estoqueVeiculos = new ArrayList<>();
    }
    
    // Método para adicionar veículo
    public void adicionar(Veiculo veiculo) {
        this.estoqueVeiculos.add(veiculo);
    }
    
    // Método para buscar veículo
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