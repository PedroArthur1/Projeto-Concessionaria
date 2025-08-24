package concessionaria;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Terminal {

    private static final Scanner scanner = new Scanner(System.in);

    private static String lerLinha(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static int lerOpcao() {
        while (true) {
            String s = lerLinha("Escolha uma opção: ");
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    public static String lerString(String mensagem) {
        return lerLinha(mensagem); 
    }

    public static int lerInt(String mensagem) {
        while (true) {
            String s = lerLinha(mensagem);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            String s = lerLinha(mensagem).replace(',', '.'); 
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número (ex.: 123.45).");
            }
        }
    }

    public static boolean lerSimNao(String mensagem) {
        System.out.print(mensagem + " (S/N): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s");
    }

    public static LocalDate lerData(String mensagem) {
        while (true) {
            String s = lerLinha(mensagem + "(AAAA-MM-DD): ");
            try {
                return LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Formato esperado: AAAA-MM-DD (ex.: 2025-08-20).");
            }
        }
    }
}
