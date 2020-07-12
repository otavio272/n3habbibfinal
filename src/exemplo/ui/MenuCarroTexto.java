package exemplo.ui;

import exemplo.dao.CarroDao;
import exemplo.modelo.Carro;

import java.util.List;

public class MenuCarroTexto extends MenuEspecificoTexto {
    private CarroDao dao;

    public MenuCarroTexto() {
        super();
        dao = new CarroDao();
    }

    private int obterIdCarro() {
        System.out.print("Digite o id do carro: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        return id;
    }

    private Carro obterDadosCarro(Carro Carro) {
        Carro c;

        if (Carro == null) {
            c = new Carro();
        } else {
            c = Carro;
        }

        System.out.print("Digite o id do carro: ");
        int id = Integer.parseInt(entrada.nextLine());

        System.out.print("Digite a placa do carro: ");
        String placa = entrada.nextLine();

        System.out.print("Digite o nome do proprietário do carro: ");
        String nome = entrada.nextLine();

        c.setId(id);
        c.setPlaca(placa);
        c.setProprietario(nome);

        return c;
    }

    @Override
    public void adicionar() {
        System.out.println("Adicionar carro");
        System.out.println();

        Carro novoCarro = obterDadosCarro(null);

        dao.insert(novoCarro);
    }

    @Override
    public void editar() {
        System.out.println("Editar carro");
        System.out.println();

        imprimirCarro();

        int id = obterIdCarro();

        Carro CarroAModificar = dao.getById(id);

        Carro novoCarro = obterDadosCarro(CarroAModificar);

        novoCarro.setId(CarroAModificar.getId());
        dao.update(novoCarro);
    }

    @Override
    public void excluir() {
        System.out.println("Excluir carro");
        System.out.println();

        imprimirCarro();
        int id = obterIdCarro();

        dao.delete(id);
    }

    @Override
    public void listarTodos() {
        System.out.println("Lista de carros");
        System.out.println();

        imprimirCarro();
    }

    private void imprimirCarro() {

    	List<Carro> Carros = dao.getAll();

        System.out.println("id\tPlaca\tProprietário");

        for (Carro c : Carros) {
            System.out.println(c.getId() + "\t" + c.getPlaca() + "\t" + c.getProprietario());
        }
    }
}
