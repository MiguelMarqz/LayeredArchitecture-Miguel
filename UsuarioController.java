import java.util.List;

public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    public void cadastrar(Usuario usuario) {
        try {
            usuarioService.cadastrar(usuario);
            System.out.println("Sucesso: Usuário '" + usuario.getNome() + "' cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public Usuario buscarPorId(int id) {
        return usuarioService.buscarPorId(id);
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioService.buscarPorNome(nome);
    }

    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    public void alterar(Usuario usuario) {
        try {
            usuarioService.alterar(usuario);
            System.out.println("✅ Sucesso: Usuário alterado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro ao alterar usuário: " + e.getMessage());
        }
    }

    public void remover(int id) {
        try {
            usuarioService.remover(id);
            System.out.println("✅ Sucesso: Usuário removido do sistema.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao remover usuário: " + e.getMessage());
        }
    }
}