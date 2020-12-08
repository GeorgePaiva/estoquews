package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebService;

import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;

// anotacao que indica que essa class é um webservices usando o HTTP e XML.
@WebService
public class EstoqueWS {
	
	private ItemDao dao = new ItemDao();
	
	// método getItens() devolve uma lista de itens que buscamos no DAO.
	public List<Item> getItens() {
		System.out.println("Camando getItes()");
		List<Item> lista = dao.todosItens();
		return lista;
	}

}
