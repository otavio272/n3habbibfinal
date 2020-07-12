package exemplo.ui;

import exemplo.dao.DepartamentoDao;
import exemplo.modelo.Departamento;

import java.util.List;

public class MenuDepartamentoTexto extends MenuEspecificoTexto {
    private DepartamentoDao dao;

    public MenuDepartamentoTexto() {
        super();
        dao = new DepartamentoDao();
    }

    private int obterIdDepartamento() {
        System.out.print("Digite o id do departamento: ");
        int id = entrada.nextInt();
        entrada.nextLine();

        return id;
    }

    private Departamento obterDadosDepartamento(Departamento departamento) {
        Departamento d;

        if (departamento == null) {
            d = new Departamento();
        } else {
            d = departamento;
        }

        System.out.print("Digite o id do departamento: ");
        int id = Integer.parseInt(entrada.nextLine());
        
        System.out.print("Digite o nome do departamento: ");
        String nome = entrada.nextLine();

        d.setNome(nome);
        d.setId(id);

        return d;
    }

    @Override
    public void adicionar() {
        System.out.println("Adicionar Departamento");
        System.out.println();

        Departamento novoDepartamento = obterDadosDepartamento(null);

        dao.insert(novoDepartamento);
    }

    @Override
    public void editar() {
        System.out.println("Editar Departamento");
        System.out.println();

        imprimirDepartamento();

        int id = obterIdDepartamento();

        Departamento departamentoAModificar = dao.getById(id);

        Departamento novoDepartamento = obterDadosDepartamento(departamentoAModificar);

        novoDepartamento.setId(departamentoAModificar.getId());
        dao.update(novoDepartamento);
    }

    @Override
    public void excluir() {
        System.out.println("Excluir Departamento");
        System.out.println();

        imprimirDepartamento();
        int id = obterIdDepartamento();

        dao.delete(id);
    }

    @Override
    public void listarTodos() {
        System.out.println("Lista de Departamentos");
        System.out.println();

        imprimirDepartamento();
    }

    private void imprimirDepartamento() {

    	List<Departamento> departamentos = dao.getAll();

        System.out.println("id\tNome\tIdade");

        for (Departamento d : departamentos) {
            System.out.println(d.getId() + "\t" + d.getNome());
        }
    }
}
