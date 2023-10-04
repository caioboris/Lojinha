package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Pedido;

public class PedidoDao {

    private Connection conexao;

    public PedidoDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserir(Pedido pedido) throws SQLException {
        var sql = "INSERT INTO pedidos (id) VALUES ( seq_pedidos.nextVal) ";
        var comando = conexao.prepareStatement(sql);
        comando.executeUpdate();

    }

    public List<Pedido> buscarTodos() throws SQLException{
        var lista = new ArrayList<Pedido>();

        var comando = conexao.prepareStatement("SELECT * FROM pedidos");
        var resultado = comando.executeQuery();

        while(resultado.next()){
            lista.add (new Pedido(
                resultado.getLong("id")
            ));
        }

        return lista;
    }
}
