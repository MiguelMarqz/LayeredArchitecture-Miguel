import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepository {
    private static Map<Integer, Usuario> bancoUsuarios = new HashMap<>();
    private static int contadorId = 1;

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == 0) {
            usuario.setId(contadorId++);
        }
        bancoUsuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public Usuario buscarPorId(int id) {
        return bancoUsuarios.get(id);
    }

    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario u : bancoUsuarios.values()) {
            if (u.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(u);
            }
        }
        return encontrados;
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(bancoUsuarios.values());
    }

    public void atualizar(Usuario usuario) {
        if (bancoUsuarios.containsKey(usuario.getId())) {
            bancoUsuarios.put(usuario.getId(), usuario);
        }
    }

    public void deletar(int id) {
        bancoUsuarios.remove(id);
    }
}