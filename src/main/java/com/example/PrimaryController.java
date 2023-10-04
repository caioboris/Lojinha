package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.PedidoDao;
import com.example.data.ProdutoDao;
import com.example.model.Pedido;
import com.example.model.Produto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;

public class PrimaryController implements Initializable {

    @FXML TextField txtDescricao;
    @FXML TextField txtValor;

    @FXML TableView<Produto> tabelaProduto;

    @FXML TableColumn<Produto, String> colDescricao;
    @FXML TableColumn<Produto, BigDecimal> colValor;
    @FXML TableColumn<Produto, Pedido> colPedido;

    @FXML TextField txtId;

    @FXML TableView<Pedido> tabelaPedido;

    @FXML TableColumn<Pedido, String> colId;

    @FXML ComboBox<Pedido> cbPedido;

    PedidoDao pedidoDao;
    ProdutoDao produtoDao;

    public void adicionarProduto(){
        var produto = new Produto(
         null, 
            txtDescricao.getText(), 
            new BigDecimal( txtValor.getText() ),
            cbPedido.getSelectionModel().getSelectedItem()
        );

        try{
            ProdutoDao.inserir(produto);
            tabelaProduto.getItems().add(produto);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void carregarProdutos(){
        tabelaProduto.getItems().clear();
        try {
            var produtos = ProdutoDao.buscarTodos();
            produtos.forEach(produto -> tabelaProduto.getItems().add(produto));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarPedido(){
        var pedido = new Pedido(
            null
        );

        try{
            pedidoDao.inserir(pedido);
            tabelaPedido.getItems().add(pedido);
        }catch(SQLException e){
            e.printStackTrace();
        }


        carregarPedidos();
    }

    public void carregarPedidos(){
        tabelaPedido.getItems().clear();
        try {
            var pedidos = pedidoDao.buscarTodos();
            pedidos.forEach(pedido -> tabelaPedido.getItems().add(pedido));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void apagarProduto(){
        var produto = tabelaProduto.getSelectionModel().getSelectedItem();
        if (produto == null) {
            mostrarMensagem("Erro", "Você deve selecionar um produto para apagar");
            return;
        }
        try {
            ProdutoDao.apagar(produto.getId());
            tabelaProduto.getItems().remove(produto);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro o apagar");
        }
    }

    public void atualizarProduto(Produto produto){
        try {
            ProdutoDao.atualizar(produto);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados");
            e.printStackTrace();
        }
    }

   private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

 @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescricao.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescricao.setOnEditCommit(e -> atualizarProduto(e.getRowValue().descricao(e.getNewValue())));
        
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(e -> atualizarProduto(e.getRowValue().valor(e.getNewValue())));

        colPedido.setCellValueFactory(new PropertyValueFactory<>("pedido"));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        try {
            pedidoDao = new PedidoDao();
            produtoDao = new ProdutoDao();
            cbPedido.getItems().addAll(pedidoDao.buscarTodos());
        } catch (SQLException e1) {
            mostrarMensagem("Erro", "Erro ao buscar pedidos");
            e1.printStackTrace();
        }

        carregarProdutos();
        carregarPedidos();

        tabelaProduto.setEditable(true);
        tabelaPedido.setEditable(true);
    }

   
}
