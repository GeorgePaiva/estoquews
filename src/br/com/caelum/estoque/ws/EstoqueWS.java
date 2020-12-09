package br.com.caelum.estoque.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.estoque.modelo.item.Filtro;
import br.com.caelum.estoque.modelo.item.Filtros;
import br.com.caelum.estoque.modelo.item.Item;
import br.com.caelum.estoque.modelo.item.ItemDao;
import br.com.caelum.estoque.modelo.item.ItemValidador;
import br.com.caelum.estoque.modelo.item.ListaItens;
import br.com.caelum.estoque.modelo.usuario.AutorizacaoException;
import br.com.caelum.estoque.modelo.usuario.TokenDao;
import br.com.caelum.estoque.modelo.usuario.TokenUsuario;

// anotacao que indica que essa class é um webservices usando o HTTP e XML.
@WebService
public class EstoqueWS {
	
	private ItemDao dao = new ItemDao();
	
	// método getItens() devolve uma lista de itens que buscamos no DAO.
	// @WebMethod definimos o nome da operation no WSDL.
	// @WebResult mapear cada um dos elementos da lista como "item" no XML.
	// @ResponseWrapper(localName="itens") redefini o nome do elemento que embrulha a mensagem.
	@WebMethod(operationName="todosOsItens")
	// @ResponseWrapper(localName="itens")
	@WebResult(name="item")
	public ListaItens getItens(@WebParam(name="filtros") Filtros filtros) { 

	    System.out.println("Chamando getItens()");
	    
	    List<Filtro> listaFiltros = filtros.getLista();
	    List<Item> lista = dao.todosItens(listaFiltros);
	    
	    
	    return new ListaItens(lista);

	}
	
    @WebMethod(operationName="CadastrarItem") 
    public Item cadastrarItem(@WebParam(name="tokenUsuario", header=true) TokenUsuario token, @WebParam(name="item") Item item) throws AutorizacaoException {

        System.out.println("Cadastrando " + item + ", " + token);

        if(! new TokenDao().ehValido(token)) {
            throw new AutorizacaoException("Autorizacao falhou");
        }

        //novo
        new ItemValidador(item).validate();

        this.dao.cadastrar(item);
        return item;
    }

}
