package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Produto;

public class ProdutoDao {

    final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    final String USER = "rm552496";
    final String PASS = "240103";

    public void inserir(Produto produto) throws SQLException{

        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO T_LJ_PRODUTO (ID_PRODUTO, NM_PRODUTO, DS_PRODUTO, PRECO_PRODUTO) VALUES (?, ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setInt(1, produto.getId());
        comando.setString(2, produto.getNome());
        comando.setString(3, produto.getDescricao());
        comando.setDouble(4, produto.getPreco());
        comando.executeUpdate();

        conexao.close();
        
    }

    public List<Produto> listarTodos() throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("SELECT * FROM T_LJ_PRODUTO");
        var resultado = comando.executeQuery();

        var lista = new ArrayList<Produto>();

        while(resultado.next()){
            lista.add (
                new Produto(
                    resultado.getInt("ID_PRODUTO"), 
                    resultado.getString("NM_PRODUTO"), 
                    resultado.getString("DS_PRODUTO"), 
                    resultado.getDouble("PRECO_PRODUTO")
                 )
            );
        }

        conexao.close();
        return lista;
    }

    public void apagar(Produto produto) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM T_LJ_PRODUTO WHERE ID_PRODUTO=?");
        comando.setLong(1, produto.getId());
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Produto produto) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE T_LJ_PRODUTO SET NM_PRODUTO=?, DS_PRODUTO=?, PRECO_PRODUTO=? WHERE ID_PRODUTO=?");
        comando.setString(1, produto.getNome());
        comando.setString(2, produto.getDescricao());
        comando.setDouble(3, produto.getPreco());
        comando.executeUpdate();
        conexao.close();
    }
    
}
