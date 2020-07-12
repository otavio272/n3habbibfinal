package exemplo.ui;

import exemplo.dao.CasaDao;
import exemplo.modelo.Casa;

import java.util.List;

public class MenuCasaTexto extends MenuEspecificoTexto {
    private CasaDao dao;

    public MenuCasaTexto() {
        super();
        dao = new CasaDao();
    }

    private int obterIdCasa() {
        System.out.print("Digite o id do Casa: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        return id;
    }

    private Casa obterDadosCasa(Casa Casa) {
        Casa c;

        if (Casa == null) {
            c = new Casa();
        } else {
            c = Casa;
        }

        System.out.print("Digite o id da casa: ");
        int id = Integer.parseInt(entrada.nextLine());

        System.out.print("Digite o número da casa: ");
        int numero = Integer.parseInt(entrada.nextLine());

        System.out.print("Digite o nome do dono da casa: ");
        String nome = entrada.nextLine();

        c.setId(id);
        c.setNumero(numero);
        c.setDono(nome);

        return c;
    }

    @Override
    public void adicionar() {
        System.out.println("Adicionar casa");
        System.out.println();

        Casa novaCasa = obterDadosCasa(null);

        dao.insert(novaCasa);
    }

    @Override
    public void editar() {
        System.out.println("Editar casa");
        System.out.println();

        imprimirCasa();

        int id = obterIdCasa();

        Casa CasaAModificar = dao.getById(id);

        Casa novaCasa = obterDadosCasa(CasaAModificar);

        novaCasa.setId(CasaAModificar.getId());
        dao.update(novaCasa);
    }

    @Override
    public void excluir() {
        System.out.println("Excluir casa");
        System.out.println();

        imprimirCasa();
        int id = obterIdCasa();

        dao.delete(id);
    }

    @Override
    public void listarTodos() {
        System.out.println("Lista de casas");
        System.out.println();

        imprimirCasa();
    }

    private void imprimirCasa() {

    	List<Casa> Casas = dao.getAll();

        System.out.println("id\tNúmero\tDono");

        for (Casa c : Casas) {
            System.out.println(c.getId() + "\t" + c.getNumero() + "\t" + c.getDono());
        }
    }
}
