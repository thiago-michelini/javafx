package com.t2m.controller;

import com.t2m.model.Consulta;
import com.t2m.model.Produto;
import com.t2m.model.ProdutoTableView;
import com.t2m.model.ProdutoResponse;
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
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerTeste implements Initializable {

    @FXML
    private Button btnConsultar;

    @FXML
    private ComboBox<Consulta> cbConsulta;

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

    private Client cInstance = ClientBuilder.newClient();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbConsulta.setItems(getOpcoesConsulta());
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colTipoNegocio.setCellValueFactory(new PropertyValueFactory<>("tipoNegocio"));
        colNegocio.setCellValueFactory(new PropertyValueFactory<>("negocio"));
        colNegocio.setCellFactory(p -> {
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
        lbData.setVisible(false);
        dpData.setVisible(false);
    }

    @FXML
    void exibirCamposConsultaTransacao() {
        Consulta opCons = cbConsulta.getSelectionModel().getSelectedItem();
        if (null != opCons && opCons.getTipo() == 2) {
            lbData.setVisible(true);
            dpData.setVisible(true);
        } else {
            lbData.setVisible(false);
            dpData.setVisible(false);
        }
    }

    @FXML
    void consultar(ActionEvent event) {
        System.out.println("está funfando!!");
        Consulta opCons = cbConsulta.getSelectionModel().getSelectedItem();
        if (null != opCons && opCons.getTipo() > 0) {
            if (opCons.getTipo() == 2 && "".equals(dpData.getEditor().getText())) {
                new Alert(Alert.AlertType.INFORMATION, "A data deve ser informada").show();
                return;
            }
            String codigo = opCons.getTipo() == 1 ? "\"81AA1C20B1688E4C7424C4E000334DD4\"}" : "\"abc\"}";
            System.out.println(opCons.getNome());
            Response resp = null;
            try {
                String auth = Base64.getEncoder().encodeToString("75862116000139:75862116000139".getBytes());
                resp = cInstance.target("https://services-uat.redetendencia.com.br/tnd-soa-comercial/tnd-ws-app")
                        .path("comercial/auth/produto-fornecedor/listar/v/20190925")
                        .request()
                        .header("Authorization", "Basic " + auth)
                        .post(Entity.entity("{\"codigo\":"+codigo, MediaType.APPLICATION_JSON));
                System.err.println("status-->"+resp.getStatusInfo().getStatusCode());
                if (resp.getStatusInfo().getStatusCode() == 200) {
                    ProdutoResponse r = resp.readEntity(ProdutoResponse.class);
                    List<ProdutoTableView> plv = r.getProdutosFornecedores().get(0).getProdutos().getProduto()
                        .stream()
                        .map(produto -> new ProdutoTableView(
                            produto.getNome(),
                            produto.getFornecedor().getNome(),
                            produto.getNegocio().getTipo(),
                            produto.getNegocio().getNome()))
                        .collect(Collectors.toList());

                    tvProduto.setItems(FXCollections.observableArrayList(plv));
                } else {
                    String r = resp.readEntity(String.class);
                    System.out.println(r);
                    tvProduto.setItems(null);
                    new Alert(Alert.AlertType.ERROR, r).show();
                }
            } catch (Exception e) {
                System.err.println("catch deu erro!!!"+resp.getStatusInfo().getStatusCode());
                e.printStackTrace();
            }
        }
    }

    private ObservableList<Consulta> getOpcoesConsulta() {
        List<Consulta> op = Arrays.asList(new Consulta(0, ""),  new Consulta(1, "Produtos"), new Consulta(2, "Transações"));
        return FXCollections.observableArrayList(op);
    }
}
