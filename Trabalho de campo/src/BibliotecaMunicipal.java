import java.util.Scanner;
import java.time.LocalDate;

 class Biblioteca {


    static class Livro {
        int id; String titulo; String autor; int ano; int qtd; int totalEmprestado = 0;
    }
    static class Usuario {
        int id; String nome;
    }
    static class Emprestimo {
        int idLivro; int idUsuario; String dataEmp; String dataDev; boolean devolvido = false;
    }

    static Livro[] livros = new Livro[50];
    static Usuario[] usuarios = new Usuario[50];
    static Emprestimo[] emprestimos = new Emprestimo[50];
    static int totalLivros = 0, totalUsuarios = 0, totalEmp = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== SISTEMA BIBLIOTECA MUNICIPAL ===");
            System.out.println("1. Registar Livro");
            System.out.println("2. Consultar Catálogo");
            System.out.println("3. Registar Usuário");
            System.out.println("4. Emprestar Livro");
            System.out.println("5. Devolver Livro");
            System.out.println("6. Estatísticas");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt(); sc.nextLine();

            switch(opcao){
                case 1: registarLivro(); break;
                case 2: consultarCatalogo(); break;
                case 3: registarUsuario(); break;
                case 4: emprestarLivro(); break;
                case 5: devolverLivro(); break;
                case 6: estatisticas(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while(opcao!= 0);
    }

    static void registarLivro() {
        Livro l = new Livro();
        l.id = totalLivros + 1;
        System.out.print("Título: "); l.titulo = sc.nextLine();
        System.out.print("Autor: "); l.autor = sc.nextLine();
        System.out.print("Ano: "); l.ano = sc.nextInt();
        System.out.print("Quantidade: "); l.qtd = sc.nextInt();
        livros[totalLivros++] = l;
        System.out.println("Livro registado com sucesso! ID: " + l.id);
    }

    static void consultarCatalogo() {
        System.out.println("\n1. Listar Todos 2. Buscar por Autor 3. Buscar por Título");
        int op = sc.nextInt(); sc.nextLine();
        if(op == 1){
            for(int i=0; i<totalLivros; i++)
                System.out.println(livros[i].id + " | " + livros[i].titulo + " | " + livros[i].autor + " | Qtd: " + livros[i].qtd);
        }
        else if(op == 2){
            System.out.print("Digite o autor: "); String a = sc.nextLine();
            for(int i=0; i<totalLivros; i++)
                if(livros[i].autor.equalsIgnoreCase(a))
                    System.out.println(livros[i].id + " | " + livros[i].titulo);
        }
    }

    static void registarUsuario() {
        Usuario u = new Usuario();
        u.id = totalUsuarios + 1;
        System.out.print("Nome: "); u.nome = sc.nextLine();
        usuarios[totalUsuarios++] = u;
        System.out.println("Usuário registado! ID: " + u.id);
    }

    static void emprestarLivro() {
        System.out.print("ID do Usuário: "); int idU = sc.nextInt();
        System.out.print("ID do Livro: "); int idL = sc.nextInt();
        if(livros[idL-1].qtd > 0){
            livros[idL-1].qtd--;
            livros[idL-1].totalEmprestado++;
            Emprestimo e = new Emprestimo();
            e.idLivro = idL; e.idUsuario = idU;
            e.dataEmp = LocalDate.now().toString();
            emprestimos[totalEmp++] = e;
            System.out.println("Empréstimo efetuado!");
        } else System.out.println("Livro indisponível!");
    }

    static void devolverLivro() {
        System.out.print("ID do Livro: "); int idL = sc.nextInt();
        livros[idL-1].qtd++;
        for(int i=0; i<totalEmp; i++){
            if(emprestimos[i].idLivro == idL &&!emprestimos[i].devolvido){
                emprestimos[i].devolvido = true;
                emprestimos[i].dataDev = LocalDate.now().toString();
                break;
            }
        }
        System.out.println("Devolução registada!");
    }

    static void estatisticas() {
        int maisEmp = 0; String tituloMais = "";
        for(int i=0; i<totalLivros; i++){
            if(livros[i].totalEmprestado > maisEmp){
                maisEmp = livros[i].totalEmprestado;
                tituloMais = livros[i].titulo;
            }
        }
        System.out.println("Livro mais emprestado: " + tituloMais + " - " + maisEmp + " vezes");
        System.out.println("Total de livros no acervo: " + totalLivros);
    }
}

