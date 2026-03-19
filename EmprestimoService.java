import java.time.LocalDate;
import java.util.List;

public class EmprestimoService {
    private EmprestimoRepository emprestimoRepository;
    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;
    private static int proximoId = 1;

    public EmprestimoService() {
        this.emprestimoRepository = new EmprestimoRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.livroRepository = new LivroRepository();
    }

    public EmprestimoService(EmprestimoRepository emprestimoRepository, 
                            UsuarioRepository usuarioRepository, 
                            LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public Emprestimo realizarEmprestimo(int usuarioId, int livroId, int diasEmprestimo) {
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        Livro livro = livroRepository.buscarPorId(livroId);

        if (usuario == null) throw new IllegalArgumentException("Usuário não encontrado.");
        if (livro == null) throw new IllegalArgumentException("Livro não encontrado.");
        
        if (livro.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Livro indisponível no estoque.");
        }

        List<Emprestimo> ativosDoUsuario = emprestimoRepository.buscarEmprestimosAtivos(usuarioId);
        if (ativosDoUsuario != null) {
            for (Emprestimo emp : ativosDoUsuario) {
                if (emp.getLivroId() == livroId) {
                    throw new IllegalArgumentException("Usuário já possui este livro emprestado.");
                }
            }
        }

        livro.setQuantidade(livro.getQuantidade() - 1);
        livroRepository.atualizar(livro);

        LocalDate dataAtual = LocalDate.now();
        LocalDate dataDevolucao = dataAtual.plusDays(diasEmprestimo);
        Emprestimo novoEmprestimo = new Emprestimo(proximoId++, usuarioId, livroId, dataAtual, dataDevolucao);
        
        // Retorna o empréstimo salvo!
        return emprestimoRepository.salvar(novoEmprestimo);
    }

    public void devolverLivro(int emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        
        if (emprestimo == null || !emprestimo.isAtivo()) {
            throw new IllegalArgumentException("Empréstimo inválido ou já finalizado.");
        }

        emprestimo.setAtivo(false);
        emprestimo.setDataDevoluçaoReal(LocalDate.now());
        emprestimoRepository.atualizar(emprestimo);

        Livro livro = livroRepository.buscarPorId(emprestimo.getLivroId());
        if (livro != null) {
            livro.setQuantidade(livro.getQuantidade() + 1);
            livroRepository.atualizar(livro);
        }
    }

    public List<Emprestimo> buscarEmprestimosDoUsuario(int usuarioId) {
        return emprestimoRepository.buscarPorUsuario(usuarioId);
    }

    public List<Emprestimo> buscarEmprestimosAtivosDoUsuario(int usuarioId) {
        return emprestimoRepository.buscarEmprestimosAtivos(usuarioId);
    }

    public List<Emprestimo> listarTodosEmprestimos() {
        return emprestimoRepository.listarTodos();
    }

    public Emprestimo buscarPorId(int id) {
        return emprestimoRepository.buscarPorId(id);
    }

    public boolean verificarAtraso(int emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        if (emprestimo == null || !emprestimo.isAtivo()) {
            return false; 
        }
        
        LocalDate hoje = LocalDate.now();
        return hoje.isAfter(emprestimo.getDataDevolucaoPrevista());
    }
}
