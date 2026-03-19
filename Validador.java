public class Validador {
    public static void validarTextoNaoVazio(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo " + campo + " não pode ser vazio.");
        }
    }

    public static void validarIdPositivo(int id, String campo) {
        if (id <= 0) {
            throw new IllegalArgumentException("O " + campo + " deve ser maior que zero.");
        }
    }
}
