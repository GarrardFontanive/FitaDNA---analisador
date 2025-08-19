import java.io.*;
import java.util.*;

public class FitaDNA {

    public static void main(String[] args) {
        System.out.println("--- Iniciando processador de fitas de DNA ---");

        String[] arquivos = {
                "dna-0.txt", "dna-1.txt", "dna-2.txt", "dna-3.txt",
                "dna-4.txt", "dna-5.txt", "dna-6.txt", "dna-7.txt",
                "dna-8.txt", "dna-9.txt"
        };

        for (String nomeArquivo : arquivos) {
            String nomeArquivoSaida = nomeArquivo.replace(".txt", "-out.txt");
            processarArquivo(nomeArquivo, nomeArquivoSaida);
        }

        System.out.println("--- Processamento concluído ---");
    }

    public static void processarArquivo(String arquivoEntrada, String arquivoSaida) {
        int total = 0;
        int validas = 0;
        int invalidas = 0;
        List<String> fitasInvalidas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada));
             PrintWriter pw = new PrintWriter(new FileWriter(arquivoSaida))) {

            String linha;
            int numLinha = 0;
            while ((linha = br.readLine()) != null) {
                numLinha++;
                String fita = linha.trim().toUpperCase();
                if (fita.isEmpty()) {
                    continue;
                }

                total++;
                String fitaComplementar = gerarComplementar(fita);

                if (fitaComplementar == null) {
                    pw.println("****FITA INVALIDA - " + fita);
                    invalidas++;
                    fitasInvalidas.add("Linha " + numLinha + ": " + fita);
                } else {
                    pw.println(fitaComplementar);
                    validas++;
                }
            }

            System.out.println("Arquivo: " + arquivoEntrada);
            System.out.println("Total de fitas: " + total);
            System.out.println("Fitas válidas: " + validas);
            System.out.println("Fitas inválidas: " + invalidas);
            if (!fitasInvalidas.isEmpty()) {
                System.out.println("Lista de fitas inválidas:");
                for (String invalida : fitasInvalidas) {
                    System.out.println("  " + invalida);
                }
            }
            System.out.println("-----------------------------------");

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo " + arquivoEntrada + ": " + e.getMessage());
        }
    }

    public static String gerarComplementar(String fita) {
        StringBuilder complementar = new StringBuilder();
        for (char base : fita.toCharArray()) {
            switch (base) {
                case 'A':
                    complementar.append('T');
                    break;
                case 'T':
                    complementar.append('A');
                    break;
                case 'C':
                    complementar.append('G');
                    break;
                case 'G':
                    complementar.append('C');
                    break;
                default:
                    return null;
            }
        }
        return complementar.toString();
    }
}