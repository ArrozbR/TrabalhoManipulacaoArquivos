import java.io.*;
import java.util.Scanner;

public class Principal {
    /* atributos */
    private String nomeDoArquivo;

    /* construtor */
    public Principal(String nomeArquivo) {
        this.nomeDoArquivo = nomeArquivo;
    }

    /* métodos */
    public void inserirDados(String registro) {
        File fArquivo = null;
        try {
            fArquivo = new File(this.nomeDoArquivo);
            FileWriter fwArquivo = null;

            fwArquivo = new FileWriter(fArquivo, true);

            BufferedWriter bw = new BufferedWriter(fwArquivo);

            //escreve o registro no arquivo e pula uma linha com o \n
            bw.write(registro + "\n");

            System.out.println("Registro adicionado com sucesso...");

            //fecha o arquivo
            bw.close();
            fwArquivo.close();

        }
        catch (IOException e) {
            System.err.println("Erro ao inserir linhas no arquivo: " + fArquivo);
        }
    }

    public void listarDados() {
        Scanner lendoArquivo = null;
        File arquivo = null;
        try {
            arquivo = new File(this.nomeDoArquivo);
            lendoArquivo = new Scanner(arquivo);

            while (lendoArquivo.hasNextLine()) {
                this.processandoLinha(lendoArquivo.nextLine());
            }
        }
        catch (FileNotFoundException e){
            System.err.println("Arquivo nao existe! " + arquivo);
        }
        finally{
            try{
                lendoArquivo.close();
            }
            catch (Exception e){
            }
        }
    }

    private void processandoLinha(String linha){
        if (linha != null && !linha.trim().isEmpty()){
            try{
                String[] campos = linha.split(":");
                if (campos.length >= 2) {
                    String nome = campos[0].trim();
                    String telefone = campos[1].trim();
                    Contato c = new Contato(nome, telefone);
                    System.out.print(c);

                }
                else{
                    System.err.println("Linha com formato invalido no arquivo: " + linha);
                }
            }
            catch (Exception e){
                System.err.println("Falha ao processar linha: " + linha);
            }
        }
    }

    public void buscarDados(String nomeBusca) {
        Scanner lendoArquivo = null;
        File arquivo = null;
        boolean encontrado = false;

        try {
            arquivo = new File(this.nomeDoArquivo);
            lendoArquivo = new Scanner(arquivo);
            System.out.println("..:: Resultado da Busca ::..");

            while (lendoArquivo.hasNextLine()) {
                String linha = lendoArquivo.nextLine();
                String[] campos = linha.split(":");

                if (campos.length >= 2) {
                    String nomeNoArquivo = campos[0].trim();
                    if (nomeNoArquivo.equalsIgnoreCase(nomeBusca)) {
                        System.out.print("Nome: " + nomeNoArquivo);
                        System.out.println("\tFone: " + campos[1].trim());
                        System.out.println("--------------------------------------\n");
                        encontrado = true;
                    }
                }
            }
            if (!encontrado) {
                System.out.println("Nenhum contato encontrado com o nome " + nomeBusca + "!");
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Arquivo nao existe! " + arquivo);
        }
        finally {
            try {
                if (lendoArquivo != null) {
                    lendoArquivo.close();
                }
            }
            catch (Exception e) {
            }
        }
    }

    public void menu() {
        Scanner teclado = new Scanner(System.in);
        int op = 0;
        do {
            System.out.println("..:: Trabalhando com Arquivos Texto ::..");
            System.out.println("1 - Inserir linha");
            System.out.println("2 - Listar todo arquivo");
            System.out.println("3 - Buscar por Nome");
            System.out.println("4 - Sair");
            System.out.print("Entre com uma opcao: ");
            op = teclado.nextInt();
            switch (op) {
                case 1:
                    teclado.nextLine();
                    String nome;
                    String telefone;
                    System.out.println("Entre com os dados:");
                    System.out.print("Nome: ");
                    nome = teclado.nextLine();
                    System.out.print("Fone: ");
                    telefone = teclado.nextLine();
                    this.inserirDados(nome + ":" + telefone);
                    break;
                case 2:
                    this.listarDados();
                    break;
                case 3:
                    teclado.nextLine();
                    System.out.print("Digite o nome para busca: ");
                    String nomeBusca = teclado.nextLine();
                    this.buscarDados(nomeBusca);
                    break;
                case 4:
                    System.out.println("Encerrando Aplicacao...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (op != 4);
    }

    public static void main(String[] args) {
        Principal p = new Principal("agenda-poo.txt");

        p.menu();
    }
}
