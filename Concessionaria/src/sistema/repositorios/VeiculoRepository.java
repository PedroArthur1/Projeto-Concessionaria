package sistema.repositorios;

import java.util.ArrayList;
import java.util.List;

import sistema.negocio.entidades.Veiculo;
import sistema.negocio.excessoes.PlacaDeveSerUnicaException;
import sistema.negocio.excessoes.VeiculoNaoEncontradoException;

public class VeiculoRepository {
    private final List<Veiculo> estoqueVeiculos;

    public VeiculoRepository() {
        this.estoqueVeiculos = new ArrayList<>();
    }
    
    public void adicionar(Veiculo veiculo) throws PlacaDeveSerUnicaException {
        if (estoqueVeiculos.stream().anyMatch(v -> v.getPlaca().equals(veiculo.getPlaca()))) {
            throw new PlacaDeveSerUnicaException("Já existe um veículo com a placa " + veiculo.getPlaca());
        }
        this.estoqueVeiculos.add(veiculo);
    }
    
    public Veiculo buscarPorPlaca(String placa) throws VeiculoNaoEncontradoException {
        return estoqueVeiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst()
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo com a placa " + placa + " não encontrado."));
    }

    public void remover(String placa) throws VeiculoNaoEncontradoException {
        boolean removido = estoqueVeiculos.removeIf(veiculo -> veiculo.getPlaca().equalsIgnoreCase(placa));
        if (!removido) {
            throw new VeiculoNaoEncontradoException("Veículo com placa " + placa + " não encontrado para remoção.");
        }
    }
    
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(estoqueVeiculos);
    }
}