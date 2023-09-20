package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.ProdutoDao;
import com.example.model.Produto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    @FXML private TextField txtNomeProd;
    @FXML private TextField txtDescProd;
    @FXML private TextField txtPrecoProd;
    @FXML private TextField txtValor;

    @FXML TableView<Produto> tabela;
    @FXML TableColumn<Produto, String> colMarca;
    @FXML TableColumn<Produto, String> colModelo;
    @FXML TableColumn<Produto, Integer> colAno;
    @FXML TableColumn<Produto, BigDecimal> colValor;

    private ProdutoDao produtoDao = new ProdutoDao();

    public void criarProduto(){

        var produto = new Produto(
            1, 
            txtNomeProd.getText(), 
            txtDescProd.getText(), 
            Double.valueOf(txtPrecoProd.getText())
        );

        try{
            produtoDao.inserir(produto);
            tabela.getItems().add(produto);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void consultar() {
        try {
            produtoDao.listarTodos().forEach(produto -> tabela.getItems().add(produto));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Não foi possível carregar os dados do banco");
        }
    }

    public void apagar(){
        if(!confirmarExclusao()) return;
        
        try {
            var veiculoSelecionado = tabela.getSelectionModel().getSelectedItem();
            if (veiculoSelecionado == null) {
                mostrarMensagemDeErro("Você deve selecionar um veículo para apagar");
                return;
            }
            
            produtoDao.apagar(veiculoSelecionado);
            tabela.getItems().remove(veiculoSelecionado);
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagemDeErro("Erro ao apagar o veículo do banco de dados");
        }

    }

    private boolean confirmarExclusao() {
        var alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Atenção");
        alert.setContentText("Tem certeza que deseja apagar o veículo selecionado? Essa ação não poderá ser desfeita.");
        return alert.showAndWait()
                .get()
                .getButtonData()
                .equals(ButtonData.OK_DONE);
    }

    private void mostrarMensagemDeErro(String mensagem) {
        var alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Erro");
        alert.setContentText(mensagem);
        alert.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colModelo.setCellFactory(TextFieldTableCell.forTableColumn());
        colModelo.setOnEditCommit(event -> {
            atualizar(event.getRowValue().modelo(event.getNewValue()));
        });

        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setCellFactory(TextFieldTableCell.forTableColumn());
        colMarca.setOnEditCommit(event -> {
            atualizar(event.getRowValue().marca(event.getNewValue()));
        });

        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colAno.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colAno.setOnEditCommit(event -> {
            atualizar(event.getRowValue().ano(event.getNewValue()));
        });

        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colValor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colValor.setOnEditCommit(event -> {
            atualizar(event.getRowValue().valor(event.getNewValue()));
        });

        tabela.setEditable(true);

        consultar();

    }

    // private void atualizar(Veiculo veiculo) {
    //     try {
    //         produtoDao.atualizar(veiculo);
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         mostrarMensagemDeErro("Erro ao atualizar dados do veículo");
    //     }
    // }

   
}
