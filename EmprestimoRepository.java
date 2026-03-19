import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmprestimoRepository {
    private static Map<Integer, Emprestimo> bancoEmprestimos = new HashMap<>();

    public Emprestimo salvar(Emprestimo emprestimo) {
        bancoEmprestimos.put(emprestimo.getId(), emprestimo);
        return emprestimo;
    }

    public Emprestimo buscarPorId(int id) {
        return bancoEmprestimos.get(id);
    }

    public List<Emprestimo> buscarPorUsuario(int usuarioId) {
        List<Emprestimo> encontrados = new ArrayList<>();
        for (Emprestimo e : bancoEmprestimos.values()) {
            if (e.getUsuarioId() == usuarioId) encontrados.add(e);
        }
        return encontrados;
    }

    public List<Emprestimo> buscarEmprestimosAtivos(int usuarioId) {
        List<Emprestimo> encontrados = new ArrayList<>();
        for (Emprestimo e : bancoEmprestimos.values()) {
            if (e.getUsuarioId() == usuarioId && e.isAtivo()) encontrados.add(e);
        }
        return encontrados;
    }

    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(bancoEmprestimos.values());
    }

    public List<Emprestimo> listarEmprestimosAtivos() {
        List<Emprestimo> encontrados = new ArrayList<>();
        for (Emprestimo e : bancoEmprestimos.values()) {
            if (e.isAtivo()) encontrados.add(e);
        }
        return encontrados;
    }

    public void atualizar(Emprestimo emprestimo) {
        if (bancoEmprestimos.containsKey(emprestimo.getId())) {
            bancoEmprestimos.put(emprestimo.getId(), emprestimo);
        }
    }

    public void deletar(int id) {
        bancoEmprestimos.remove(id);
    }
}