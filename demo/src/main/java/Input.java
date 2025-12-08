import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;


public class Input extends Application{

    private LineChart<Number, Number> lineChart;
    private PieChart pieChart;
    private ChoiceBox<String> choiceBox;
    private Country[] countries;
    private Timeline timeline;
    private VBox factorList;
    private Label countryLabel;
    private Label livePop;
    private Country chosenCountry;
    

    @Override
    public void start(Stage stage){

        countries = Data.getCountries();
        
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Year");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Population Over Time");

        pieChart = new PieChart();
        pieChart.setTitle("Individual Factor Impact");

        choiceBox = new ChoiceBox<>();
        for (Country c : countries){
            choiceBox.getItems().add(c.getCountry());
        }
        choiceBox.getSelectionModel().selectFirst();

        choiceBox.setOnAction(ignore -> {
            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if(index <0) {
                return;
            }

            Country[] newCountries = Data.getCountries();
            chosenCountry = newCountries[index];
            updateCountryHeader(chosenCountry);
            useFactorSliders(chosenCountry);
            updatePieChart(chosenCountry);
            lineChart.getData().clear();
        });

        chosenCountry = countries[choiceBox.getSelectionModel().getSelectedIndex()];
        countryLabel = new Label();
        livePop = new Label();
        updateCountryHeader(chosenCountry);

        Button simulate = new Button("Run the simulation");
        simulate.setOnAction(ignore -> {
            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index<0){
                return;
            }
            
            Country[] newCountries = Data.getCountries();
            Country selectedCountry = newCountries[index];
            runSimandCharts(selectedCountry);
        });

        Button liveSimulation = new Button("Start the live simulation");
        liveSimulation.setOnAction(ignore -> {
            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index<0){
                return;
            }

            Country[] newCountries = Data.getCountries();
            chosenCountry = newCountries[index];
            updateCountryHeader(chosenCountry);
            useFactorSliders(chosenCountry);
            liveSim(chosenCountry);

        });

        Button stopSim = new Button("Stop simulation");
        stopSim.setOnAction(ignore -> {
            if (timeline!= null){
                timeline.stop();
                timeline = null;
            }

            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                return;
            }

            Country[] newCountries = Data.getCountries();
            chosenCountry = newCountries[index];
            lineChart.getData().clear();
            updateCountryHeader(chosenCountry);
            updatePieChart(chosenCountry);
            useFactorSliders(chosenCountry);

        });

        HBox controls = new HBox(10, choiceBox, simulate, liveSimulation, stopSim);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        HBox status = new HBox(20, countryLabel, livePop);
        status.setAlignment(Pos.CENTER_LEFT);
        status.setPadding(new Insets(5,10,10,10));

        VBox top = new VBox(controls, status);

        factorList = new VBox(10);
        factorList.setPadding(new Insets(10));
        ScrollPane scrollFactor = new ScrollPane(factorList);
        scrollFactor.setFitToWidth(true);
        scrollFactor.setPrefWidth(320);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(lineChart);
        root.setRight(pieChart);
        root.setLeft(scrollFactor);
        BorderPane.setMargin(pieChart, new Insets(10));

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Population Growth Sim");
        stage.setScene(scene);
        useFactorSliders(chosenCountry);
        stage.show();

    }

    private void runSimandCharts(Country country){
        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(country.getCountry());

        int years = 20;
        int currentPop = country.getPopulation();
        series.getData().add(new XYChart.Data<>(0, currentPop));

        for(int year = 1; year <= years; year++){
            currentPop = Simulation.simulateYear(country);
            series.getData().add(new XYChart.Data<>(year, currentPop));
        }

        lineChart.getData().add(series);

        updatePieChart(country);    
    }

    private void updatePieChart(Country country){
        Factor[] factors = country.getFactors();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for(Factor f : factors){        
            int value = Math.abs(f.getPopulationAdd());
            double multiplier = Math.abs((f.getMultiplier() - 1.0) * country.getPopulation());
            double total = value + multiplier;
            if(total > 0){
                pieData.add(new PieChart.Data(f.getName(), total));
            }
        }

        pieChart.setData(pieData);
    }

    private void updateCountryHeader(Country country){
        if (countryLabel != null) {
            countryLabel.setText("Country: " + country.getCountry());
        }
        if (livePop != null){
            livePop.setText("Population: " + country.getPopulation());
        }
    }

    private void useFactorSliders(Country country){
        if (factorList == null){
            return;
        }

        factorList.getChildren().clear();
        Label title = new Label("Factors for " + country.getCountry());
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        factorList.getChildren().add(title);

        Factor[] factors = country.getFactors();

        for (Factor f : factors) {
            Label labelName = new Label(f.getName());
            labelName.setPrefWidth(180);

            Slider newSlider = new Slider(-2_000_000, 2_000_000, f.getPopulationAdd());
            newSlider.setShowTickMarks(true);
            newSlider.setShowTickLabels(true);
            newSlider.setMajorTickUnit(1_000_000);
            newSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                f.setPopulationAdd(newVal.intValue());
            });
            newSlider.setOnMouseReleased(ignore -> updatePieChart(country));

            Slider multiplierSlider = new Slider(0.5, 1.5, f.getMultiplier());
            multiplierSlider.setShowTickMarks(true);
            multiplierSlider.setShowTickLabels(true);
            multiplierSlider.setMajorTickUnit(0.25);
             multiplierSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                f.setMultiplier(newVal.doubleValue());
            });
            newSlider.setOnMouseReleased(ignore -> updatePieChart(country));


            VBox sliders = new VBox(
                new Label("Add: "), newSlider,
                new Label("Multiplier"), multiplierSlider
            );
            sliders.setSpacing(3);

            HBox row = new HBox(10, labelName, sliders);
            row.setAlignment(Pos.CENTER_LEFT);

            factorList.getChildren().add(row);
        }
    }

    private void liveSim(Country country){
        if(timeline != null){
            timeline.stop();
        }

        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(country.getCountry());
        lineChart.getData().add(series);

        final int[] year = {0};
        int startingPop = country.getPopulation();
        series.getData().add(new XYChart.Data<>(year[0], startingPop));
        updateCountryHeader(country);
        updatePieChart(country);

        timeline = new Timeline(
            new KeyFrame(Duration.millis(700), ignore -> {
                int newPop = Simulation.simulateYear(country);
                year[0]++;

                series.getData().add(new XYChart.Data<>(year[0], newPop));
                livePop.setText("Population: " + newPop);
                updatePieChart(country);
            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    

    public static void main(String[] args) {
        Application.launch(args);
    }
}
    