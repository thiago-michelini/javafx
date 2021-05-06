package com.t2m.controller;

import com.t2m.model.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerTeste implements Initializable {

    @FXML
    private Button btnConsultar;

    @FXML
    private TableView<ProdutoTableView> tvProduto;

    @FXML
    private TableColumn<ProdutoTableView, String> colNome;

    @FXML
    private TableColumn<ProdutoTableView, String> colFornecedor;

    @FXML
    private TableColumn<ProdutoTableView, Integer> colTipoNegocio;

    @FXML
    private TableColumn<ProdutoTableView, String> colNegocio;

    @FXML
    private DatePicker dpData;

    @FXML
    private Label lbData;

    @FXML
    private Label lbMenuAtivo;

    @FXML
    private TableView<TransacaoTableView> tvTransacao;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransProduto;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransFornecedor;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransIdTransacao;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransStatus;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransValor;

    @FXML
    private TableColumn<TransacaoTableView, String> colTransDtHr;

    @FXML
    private TableColumn<TransacaoTableView, Long> colTransNsuFornecedor;

    @FXML
    private Button btnMenuTransacao;

    @FXML
    private Button btnMenuProduto;

    @FXML
    private TabPane tpMenu;

    @FXML
    private Tab tiTransacao;

    @FXML
    private Tab tiProduto;

    @FXML
    private Tab tiBemVindo;

    private Client cInstance = ClientBuilder.newClient();

    enum MenuAtivoEnum {
        TRANSACAO, PRODUTO;
    }

    private MenuAtivoEnum menuAtivo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tpMenu.getSelectionModel().select(tiBemVindo);

        colTransProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        colTransFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colTransIdTransacao.setCellValueFactory(new PropertyValueFactory<>("idTransacao"));
        colTransValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colTransDtHr.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
        colTransNsuFornecedor.setCellValueFactory(new PropertyValueFactory<>("nsuFornecedor"));
        colTransStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        desenharLinhasTableTransacao(colTransStatus);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colTipoNegocio.setCellValueFactory(new PropertyValueFactory<>("tipoNegocio"));
        colNegocio.setCellValueFactory(new PropertyValueFactory<>("negocio"));
        desenharColunaNegocioTableProduto(colNegocio);
    }



    private void desenharColunaNegocioTableProduto(TableColumn<ProdutoTableView, String> tc) {
        tc.setCellFactory(p -> {
            return new TableCell<ProdutoTableView, String>() {
                @Override
                protected void updateItem(String s, boolean empty) {
                    super.updateItem(s, empty);
                    if (null == s || empty) {
                        setText(null);
                        setStyle(null);
                    } else {
                        //Produto prod = getTableView().getItems().get(getIndex());
                        setText(s);
                        if ("recarga telefone".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: red; fx-font-weight: bold; -fx-text-fill: white;");
                        else if ("google play".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: green; fx-font-weight: bold; -fx-text-fill: white;");
                        else if ("netflix".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: orange; fx-font-weight: bold; -fx-text-fill: white;");
                        else if ("uber".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: blue; fx-font-weight: bold; -fx-text-fill: white;");
                        else if ("entretenimento".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: pink; fx-font-weight: bold; -fx-text-fill: black;");
                        else if ("jogos".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: olive; fx-font-weight: bold; -fx-text-fill: white;");
                        else if ("tv pre paga".equalsIgnoreCase(s))
                            setStyle("-fx-background-color: yellow; fx-font-weight: bold; -fx-text-fill: black;");
                        else
                            setStyle("-fx-background-color: black; fx-font-weight: bold; -fx-text-fill: white;");
                    }
                }
            };
        });
    }

    private void desenharLinhasTableTransacao(TableColumn<TransacaoTableView, String> tc) {
        tc.setCellFactory(p -> {
            return new TableCell<TransacaoTableView, String>() {
                @Override
                protected void updateItem(String status, boolean empty) {
                    super.updateItem(status, empty);
                    TableRow<TransacaoTableView> row = getTableRow();
                    if (null == status || empty) {
                        setText(null);
                        row.setStyle(null);
                    } else {
                        setText(status);
                        if ("efetivada".equalsIgnoreCase(status))
                            row.setStyle("-fx-background-color: #CEF6EC;");
                        else if ("negada".equalsIgnoreCase(status))
                            row.setStyle("-fx-background-color: #F6CEE3;");
                        else if ("autorizada".equalsIgnoreCase(status))
                            row.setStyle("-fx-background-color: #D8D8D8;");
                        else if ("cancelada".equalsIgnoreCase(status))
                            row.setStyle("-fx-background-color: #F5F6CE;");
                        else
                            row.setStyle(null);
                    }
                }
            };
        });
    }

    @FXML
    void ativarMenuTransacao(ActionEvent event) {
        menuAtivo = MenuAtivoEnum.TRANSACAO;
        lbMenuAtivo.setText("Área de Transações");
        tpMenu.getSelectionModel().select(tiTransacao);
    }

    @FXML
    void ativarMenuProduto(ActionEvent event) {
        menuAtivo = MenuAtivoEnum.PRODUTO;
        lbMenuAtivo.setText("Área de Produtos");
        consultar(null);
        tpMenu.getSelectionModel().select(tiProduto);
    }

    @FXML
    void consultar(ActionEvent event) {
        System.out.println("está funfando!!");
//        Consulta opCons = cbConsulta.getSelectionModel().getSelectedItem();
//        if (null != opCons && opCons.getTipo() > 0) {
//            if (opCons.getTipo() == 2 && "".equals(dpData.getEditor().getText())) {
            if (MenuAtivoEnum.TRANSACAO.equals(menuAtivo) && "".equals(dpData.getEditor().getText())) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("A data deve ser informada!");
                a.show();
                return;
            }
            if (MenuAtivoEnum.TRANSACAO.equals(menuAtivo) && !validateDateFromDatePicker(dpData)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("A data informada é inválida!");
                a.show();
                return;
            }

            try {
                String auth = Base64.getEncoder().encodeToString("75862116000139:75862116000139".getBytes());
//                if (opCons.getTipo() == 1) {
                if (MenuAtivoEnum.PRODUTO.equals(menuAtivo)) {
                    ObservableList<ProdutoTableView> produtoTV = invocarProdutoAPI(auth);
                    tvProduto.setItems(produtoTV);
                } else {
                    ObservableList<TransacaoTableView> transacaoTV = invocarTransacaoAPI(auth);
                    tvTransacao.setItems(transacaoTV);
                }
            } catch (Exception e) {
                System.err.println("catch deu erro!!!");
                e.printStackTrace();
            }
//        }
    }

    private ObservableList<TransacaoTableView> invocarTransacaoAPI(String auth) {
        Response resp;
        resp = cInstance.target("https://services-uat.redetendencia.com.br/tnd-soa-comercial/tnd-ws-app")
            .path("comercial/auth/transacao/listar/v/20190925")
            .request()
            .header("Authorization", "Basic " + auth)
            .post(Entity.entity("{\"codigo\":\"81AA1C20B1688E4C7424C4E000334DD4\",\"dataTransacao\":\""+dpData.getValue()+"\"}", MediaType.APPLICATION_JSON));
        System.err.println("status-->"+resp.getStatusInfo().getStatusCode());
        if (resp.getStatusInfo().getStatusCode() == 200) {
            TransacaoResponse r = resp.readEntity(TransacaoResponse.class);
            List<TransacaoTableView> ttv = r.getTransacoes()
                .stream()
                .map(TransacaoTableView::new)
                .collect(Collectors.toList());
            return FXCollections.observableArrayList(ttv);
        }
        String r = resp.readEntity(String.class);
        System.out.println(r);
        new Alert(Alert.AlertType.ERROR, r).show();
        return null;
    }

    private ObservableList<ProdutoTableView> invocarProdutoAPI(String auth) {
        Response resp;
        resp = cInstance.target("https://services-uat.redetendencia.com.br/tnd-soa-comercial/tnd-ws-app")
            .path("comercial/auth/produto-fornecedor/listar/v/20190925")
            .request()
            .header("Authorization", "Basic " + auth)
            .post(Entity.entity("{\"codigo\":\"81AA1C20B1688E4C7424C4E000334DD4\"}", MediaType.APPLICATION_JSON));
        System.err.println("status-->"+resp.getStatusInfo().getStatusCode());
        if (resp.getStatusInfo().getStatusCode() == 200) {
            ProdutoResponse r = resp.readEntity(ProdutoResponse.class);
            List<ProdutoTableView> plv = r.getProdutosFornecedores().get(0).getProdutos().getProduto()
                .stream()
                .map(ProdutoTableView::new)
                .collect(Collectors.toList());
            return FXCollections.observableArrayList(plv);
        }
        String r = resp.readEntity(String.class);
        System.out.println(r);
        new Alert(Alert.AlertType.ERROR, r).show();
        return null;
    }

    private boolean validateDateFromDatePicker(DatePicker dp) {
        try {
            LocalDate dateFromText = LocalDate.from(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(dp.getEditor().getText()));
            dp.setValue(dateFromText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
