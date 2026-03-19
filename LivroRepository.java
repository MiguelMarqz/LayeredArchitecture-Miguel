import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroRepository {
    // Simulando o banco de dados em memória
    private static Map<Integer, Livro> banco = new HashMap<>();
    private static int contadorId = 1; // Para gerar IDs automáticos

    public Livro salvar(Livro livro) {
        if (livro.getId() == 0) {
            livro.setId(contadorId++);
        }
        banco.put(livro.getId(), livro);
        return livro;
    }

    public Livro buscarPorId(int id) {
        return banco.get(id); // Retorna o livro ou null se não existir
    }

    public List<Livro> buscarPorNome(String nome) {
        List<Livro> encontrados = new ArrayList<>();
        for (Livro livro : banco.values()) {
            if (livro.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(livro);
            }
        }    
            return encontrados;
    }

    public List<Livro> listarTodos() {
        return new ArrayList<>(banco.values());
    }

    public void atualizar(Livro livro) {
        if (banco.containsKey(livro.getId())) {
            banco.put(livro.getId(), livro);
        }
    }

    public void deletar(int id) {
        banco.remove(id);
    }
}
