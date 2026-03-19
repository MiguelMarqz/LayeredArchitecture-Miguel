import java.util.List;

public class LivroService {
    private LivroRepository livroRepository;

    public LivroService() {
        this.livroRepository = new LivroRepository();
    }

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro cadastrar(Livro livro) {
        // Validação básica antes de salvar
        if (livro.getNome() == null || livro.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do livro não pode ser vazio.");
        }
        if (livro.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
        return livroRepository.salvar(livro);
    }

    public Livro buscarPorId(int id) {
        return livroRepository.buscarPorId(id);
    }

    public List<Livro> buscarPorNome(String nome) {
        return livroRepository.buscarPorNome(nome);
    }

    public List<Livro> listarTodos() {
        return livroRepository.listarTodos();
    }

    public void alterar(Livro livro) {
        if (livro.getId() <= 0) {
            throw new IllegalArgumentException("ID do livro inválido para alteração.");
        }
        livroRepository.atualizar(livro);
    }

    public void remover(int id) {
        livroRepository.deletar(id);
    }
}