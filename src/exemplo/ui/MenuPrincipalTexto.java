package exemplo.ui;

import java.util.Scanner;

public class MenuPrincipalTexto {

	private static final int OP_PESSOAS = 1;
	private static final int OP_DEPTOS = 2;
	private static final int OP_CASAS = 3;
	private static final int OP_CARROS = 4;

	private static final int OP_ADICIONAR = 1;
	private static final int OP_LISTAR = 2;
	private static final int OP_EDITAR = 3;
	private static final int OP_EXCLUIR = 4;

	private enum Estado {PRINCIPAL, PESSOAS, DEPTOS, CASAS, CARROS};

	private Estado estadoAtual;
	private Scanner entrada;

	public MenuPrincipalTexto() {
		estadoAtual = Estado.PRINCIPAL;
		entrada = new Scanner(System.in);
	}
	
	private void imprimeMenuPrincipal() {
		System.out.println("1 - Administração de Pessoas");
		System.out.println("2 - Administração de Departamentos");
		System.out.println("3 - Administração de Casas");
		System.out.println("4 - Administração de Carros");
	}
	
	private void imprimeMenuSecundário(String tipoMenu) {
		System.out.println("Administração de " + tipoMenu + "\n");
		System.out.println("1 - Adicionar");
		System.out.println("2 - Listar");
		System.out.println("3 - Editar");
		System.out.println("4 - Excluir");
	}

	public void executa() {
		int opcao;
		MenuEspecificoTexto menuEspecificoTexto;
		
		do {

			System.out.println("Administração de RH");
			System.out.println();

			switch(estadoAtual) {
			case PESSOAS:
				imprimeMenuSecundário("Pessoas");
				break;
			case DEPTOS:
				imprimeMenuSecundário("Departamentos");
				break;
			case CASAS:
				imprimeMenuSecundário("Casas");
				break;
			case CARROS:
				imprimeMenuSecundário("Casas");
				break;
			default:
				imprimeMenuPrincipal();
			}

			System.out.println();
			System.out.println("0 - Sair");

			System.out.println();
			System.out.print("Escolha uma opção: ");

			opcao = entrada.nextInt();
			entrada.nextLine();

			System.out.println("Voce escolheu a opção: " + opcao);

			if (estadoAtual == Estado.PRINCIPAL) {

				switch (opcao) {
				case OP_PESSOAS:
					estadoAtual = Estado.PESSOAS;
					menuEspecificoTexto = new MenuPessoaTexto();
					break;
				case OP_DEPTOS:
					estadoAtual = Estado.DEPTOS;
					menuEspecificoTexto = new MenuDepartamentoTexto();
					break;
				case OP_CARROS:
					estadoAtual = Estado.CARROS;
					menuEspecificoTexto = new MenuCarroTexto();
					break;
				case OP_CASAS:
					estadoAtual = Estado.CASAS;
					menuEspecificoTexto = new MenuCasaTexto();
					break;
				}

			} else {

				switch (opcao) {
					case OP_ADICIONAR:
						menuEspecificoTexto.adicionar();
						break;
					case OP_EDITAR:
						menuEspecificoTexto.editar();
						break;
					case OP_EXCLUIR:
						menuEspecificoTexto.excluir();
						break;
					case OP_LISTAR:
						menuEspecificoTexto.listarTodos();
						break;
					default:
						System.out.println("Opção inválida. Tente novamente!");
				}
				
			}


		} while (opcao != 0);
		
	}
	
}
