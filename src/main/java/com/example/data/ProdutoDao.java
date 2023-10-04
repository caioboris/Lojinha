package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Pedido;
import com.example.model.Produto;

public class ProdutoDao {

    static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    static final String USER = "rm97861";
    static final String PASS = "180303";

    public static void inserir(Produto produto) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO produtos (id, descricao, valor, id_pedido) VALUES (seq_pedidos.nextVal, ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1,produto.getDescricao());
        comando.setBigDecimal(2, produto.getValor());
        comando.setLong(3, produto.getPedido().getId());
        comando.executeUpdate();

        conexao.close();

    }

    public static List<Produto> buscarTodos() throws SQLException{
        var lista = new ArrayList<Produto>();

        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("SELECT produtos.*, pedidos.id FROM produtos INNER JOIN pedidos ON produtos.id_pedido=pedidos.id");
        var resultado = comando.executeQuery();

        while(resultado.next()){
            lista.add (new Produto(
                resultado.getLong("id"), 
                resultado.getString("descricao"), 
                resultado.getBigDecimal("valor"),
                new Pedido(
                    resultado.getLong("id_pedido")
                )
            ));
        }

        conexao.close();
        return lista;
    }

    public static void apagar(Long id) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM produtos WHERE id=?");
        comando.setLong(1, id);
        comando.executeUpdate();
        conexao.close();
    }

    public static void atualizar(Produto produto) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE produtos SET descricao=?, valor=? WHERE id=?");
        comando.setString(1, produto.getDescricao());
        comando.setBigDecimal(2, produto.getValor());
        comando.setLong(3, produto.getId());
        comando.executeUpdate();
        conexao.close();
    }
}
