import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializando os Repositories
        LivroRepository livroRepo = new LivroRepository();
        UsuarioRepository usuarioRepo = new UsuarioRepository();
        EmprestimoRepository emprestimoRepo = new EmprestimoRepository();

        // Inicializando os Services
        LivroService livroService = new LivroService(livroRepo);
        EmprestimoService emprestimoService = new EmprestimoService(emprestimoRepo, usuarioRepo, livroRepo);

        // Inicializando o Controller
        LivroController livroController = new LivroController(livroService);

        int opcao = 0;

        // Criando um usuário de teste automático para facilitar a correção do professor
        usuarioRepo.salvar(new Usuario(1, "João Silva", "joao@email.com", "12345678900", "999999999"));

        while (opcao != 6) {
            System.out.println("\n===== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA =====");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Emprestar Livro");
            System.out.println("4. Devolver Livro");
            System.out.println("5. Listar Usuários (Teste)");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Digite o nome do livro: ");
                        String nome = scanner.nextLine();
                        System.out.print("Digite o autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Digite a quantidade em estoque: ");
                        int qtd = Integer.parseInt(scanner.nextLine());
                        
                        Livro novoLivro = new Livro(0, nome, autor, 1, qtd);
                        livroController.cadastrar(novoLivro);
                        break;

                    case 2:
                        System.out.println("\n--- Lista de Livros ---");
                        for (Livro l : livroController.listarTodos()) {
                            System.out.println("ID: " + l.getId() + " | Título: " + l.getNome() + " | Estoque: " + l.getQuantidade());
                        }
                        break;

                    case 3:
                        System.out.print("Digite o ID do Usuário: ");
                        int idUsuarioEmprestimo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Digite o ID do Livro: ");
                        int idLivro = Integer.parseInt(scanner.nextLine());
                        System.out.print("Quantos dias de empréstimo? ");
                        int dias = Integer.parseInt(scanner.nextLine());
                        
                        emprestimoService.realizarEmprestimo(idUsuarioEmprestimo, idLivro, dias);
                        System.out.println("Empréstimo realizado com sucesso!");
                        break;

                    case 4:
                        System.out.print("Digite o ID do Empréstimo para devolver: ");
                        int idEmprestimo = Integer.parseInt(scanner.nextLine());
                        
                        emprestimoService.devolverLivro(idEmprestimo);
                        System.out.println("Livro devolvido com sucesso!");
                        break;

                    case 5:
                        System.out.println("\n--- Lista de Usuários ---");
                        for (Usuario u : usuarioRepo.listarTodos()) {
                            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome());
                        }
                        break;

                    case 6:
                        System.out.println("Saindo do sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de Regra de Negócio: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }
}