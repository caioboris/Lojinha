package com.example.data;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import com.example.model.Pedido;
import com.example.model.Produto;

public class PedidoDao {

    Random random = new Random();
    final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "rm552496";
    final String PASS = "240103";

    public void inserir(Pedido pedido) throws SQLException{

        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO T_LJ_PEDIDO (ID_PEDIDO, DT_CRIACAO) VALUES (?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setDouble(1, pedido.getId());
        comando.setDate(2, Date.valueOf(pedido.getDataCriacao()));

        comando.executeUpdate();

        conexao.close();
        
    }

    public void apagar(Pedido pedido) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM T_LJ_PEDIDO WHERE ID_PEDIDO=?");
        comando.setLong(1, pedido.getId());
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Pedido pedido) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE T_LJ_PEDIDO SET DT_CRIACAO=? WHERE ID_PEDIDO=?");
        comando.setDate(1, Date.valueOf(pedido.getDataCriacao()));
        comando.setDouble(2, pedido.getId());
        comando.executeUpdate();
        conexao.close();
    }

    public void inserirProduto(Pedido pedido) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var sql = "INSERT INTO T_LJ_PEDIDO_PRODUTO (ID_PRODUTO_PEDIDO, ID_PEDIDO, ID_PRODUTO) VALUES (?, ?, ?)";
        var comando = conexao.prepareStatement(sql);

        for (Produto item  : pedido.getProdutos()) {
            comando.setInt(1,random.nextInt());
            comando.setInt(1,pedido.getId());
            
            comando.setInt(1,item.getId());
            comando.executeUpdate();            
        }
    
    }
    
}
