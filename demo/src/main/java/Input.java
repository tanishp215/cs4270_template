import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


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
    private StackPane mapPane;
    private ImageView mapImageView;
    private Pane mapDots;

    private java.util.List<CityMarker> cityMarkers = new java.util.ArrayList<>();

    // different colors for each of the factors on the pie chart

    private static final java.util.Map<String, String> FACTOR_COLORS = new java.util.HashMap<>();
    static {
        FACTOR_COLORS.put("Birth rate", "#ff5733");
        FACTOR_COLORS.put("Death rate", "#2e86de");
        FACTOR_COLORS.put("Age percentages", "#9b59b6");
        FACTOR_COLORS.put("Health care access", "#27ae60");
        FACTOR_COLORS.put("Food supply", "#f1c40f");
        FACTOR_COLORS.put("Disease", "#e74c3c");
        FACTOR_COLORS.put("Infant mortality rate", "#8e44ad");
        FACTOR_COLORS.put("Economy", "#16a085");
        FACTOR_COLORS.put("Education", "#2980b9");
        FACTOR_COLORS.put("Birth control", "#d35400");
        FACTOR_COLORS.put("Immigration", "#7f8c8d");
    }

    // each dot of a city with the city name, x and y coordinates, size, and shape

    private static class CityMarker {
        String name;
        double x;
        double y;
        double weight;
        Circle circle;

    }
    
    @Override
    public void start(Stage stage){

        countries = Data.getCountries();

        // population vs time chart on the x and y axis
        
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Year");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Population");

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Population Over Time");

        // pie chart showing the impact of each factor

        pieChart = new PieChart();
        pieChart.setTitle("Individual Factor Impact");

        // dropdown to choose a country to simulate

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

            chosenCountry = countries[index];
            updateCountryHeader(chosenCountry);
            useFactorSliders(chosenCountry);
            updatePieChart(chosenCountry);
            updateCountryMap(chosenCountry);
            lineChart.getData().clear();
        });

        chosenCountry = countries[choiceBox.getSelectionModel().getSelectedIndex()];
        countryLabel = new Label();
        livePop = new Label();
        updateCountryHeader(chosenCountry);

        // run 20 year simulation
        Button simulate = new Button("Run the simulation");
        simulate.setOnAction(ignore -> {
            Country selectedCountry = resetCountry();
            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index<0){
                return;
            }

            resetDotSizes();
            runSimandCharts(selectedCountry);
        });

        // run live simulation to continue changing factors for as long as the user pleases

        Button liveSimulation = new Button("Start the live simulation");
        liveSimulation.setOnAction(ignore -> {
            Country selectedCountry = resetCountry();
            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index<0){
                return;
            }
            liveSim(selectedCountry);

        });

        // stop live simulation

        Button stopSim = new Button("Stop simulation");
        stopSim.setOnAction(ignore -> {
            if (timeline!= null){
                timeline.stop();
                timeline = null;
            }

            mapDots.getChildren().clear();
            cityMarkers.clear();

            int index = choiceBox.getSelectionModel().getSelectedIndex();
            if (index < 0) {
                return;
            }

            chosenCountry = countries[index];
            lineChart.getData().clear();
            updateCountryHeader(chosenCountry);
            updatePieChart(chosenCountry);
            useFactorSliders(chosenCountry);
            resetCountry();
            

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

        mapImageView = new ImageView();
        mapImageView.setPreserveRatio(true);

        mapImageView.setFitWidth(1000);
        mapImageView.setFitHeight(600);  
        mapDots = new Pane();
        mapDots.setPickOnBounds(false);

        mapPane = new StackPane(mapImageView, mapDots);
        mapPane.setPadding(new Insets(10));
        mapPane.setPrefHeight(360);

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(lineChart);
        root.setRight(pieChart);
        root.setLeft(scrollFactor);
        root.setBottom(mapPane);
        BorderPane.setMargin(pieChart, new Insets(10));

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Population Growth Sim");
        stage.setScene(scene);
        useFactorSliders(chosenCountry);
        updateCountryMap(chosenCountry);
        stage.show();

    }

    // runs the simulations by updating the xy chart and the current population value

    private void runSimandCharts(Country country){

        updateCountryMap(country);
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
        updateHeatmap(country); 
    }

    // live updates of the pie chart based on the importance of each factor - how much its impacting the population

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

        for (PieChart.Data data : pieChart.getData()) {
            String color = FACTOR_COLORS.getOrDefault(data.getName(), "#aaaaaa");
            data.getNode().setStyle("-fx-pie-color: " + color + ";");
        }
    }

    // sets the name of the country and the population which will update live
    private void updateCountryHeader(Country country){
        if (countryLabel != null) {
            countryLabel.setText("Country: " + country.getCountry());
        }
        if (livePop != null){
            livePop.setText("Population: " + country.getPopulation());
        }
    }

    // code to use the sliders by changing values of the factors

    private void useFactorSliders(Country country) {
        if (factorList == null) return;

        factorList.getChildren().clear();

        Label title = new Label("Factors for " + country.getCountry());
        title.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        factorList.getChildren().add(title);

        Factor[] factors = country.getFactors();

        // for each factor it sets a slider

        for (Factor f : factors) {

            Label labelName = new Label(f.getName());
            labelName.setPrefWidth(180);

            VBox sliders = new VBox(3);

            if (f.getType().equals("add")) {
                // max and min values of population add on the slider, same thing for multiplier
                Slider addSlider = new Slider(-2_000_000, 2_000_000, f.getPopulationAdd());
                addSlider.setShowTickMarks(true);
                addSlider.setShowTickLabels(true);
                addSlider.setMajorTickUnit(1_000_000);

                addSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                    f.setPopulationAdd(newVal.intValue());
                });
                // once user releases mouse, the pie chart and heat map are updated, same thing for multiplier
                addSlider.setOnMouseReleased(e -> {
                    updatePieChart(country);
                    updateHeatmap(country);
                });

                sliders.getChildren().addAll(
                    new Label("Add:"), addSlider
                );
            }

            if (f.getType().equals("mult")) {
                Slider multSlider = new Slider(0.5, 1.5, f.getMultiplier());
                multSlider.setShowTickMarks(true);
                multSlider.setShowTickLabels(true);
                multSlider.setMajorTickUnit(0.25);

                multSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                    f.setMultiplier(newVal.doubleValue());
                });

                multSlider.setOnMouseReleased(e -> {
                    updatePieChart(country);
                    updateHeatmap(country);
                });

                sliders.getChildren().addAll(
                    new Label("Multiplier:"), multSlider
                );
            }

            HBox row = new HBox(10, labelName, sliders);
            factorList.getChildren().add(row);
        }
    }

    // adds the location of the city with a color, a circle, a radius, and an x and y coordinate
    private void addCityMarker(String name, double x, double y, double weight) {
        CityMarker cm = new CityMarker();
        cm.name = name;
        cm.x = x;
        cm.y = y;
        cm.weight = weight;

        Circle circle = new Circle(8);
        circle.setFill(Color.rgb(255, 0, 0, 0.6));
        circle.setStroke(Color.rgb(120, 0, 0, 0.8));
        circle.setStrokeWidth(1.0);
        cm.circle = circle;

        cityMarkers.add(cm);
        mapDots.getChildren().add(circle);
        positionCityMarkers();
    }

      private void positionCityMarkers() {
        Image img = mapImageView.getImage();
        if (img == null) {
            return;
        }
        double baseW = img.getWidth();
        double baseH = img.getHeight();

        if (baseW <=0  || baseH <= 0){
            return;
        }

        double viewW = mapImageView.getBoundsInParent().getWidth();
        double viewH = mapImageView.getBoundsInParent().getHeight();
        if (viewW <= 0 || viewH <= 0) {
            return;
        }

        double scaleX = viewW / baseW;
        double scaleY = viewH / baseH;

        for (CityMarker cm : cityMarkers) {
            if (cm.circle == null){
                 continue;
            }
            double px = cm.x * scaleX;
            double py = cm.y * scaleY;
            cm.circle.setTranslateX(px);
            cm.circle.setTranslateY(py);
        }
    }

    // set the map of the chosen country to maps we drew ourselves
    private void updateCountryMap(Country country) {
        if (mapImageView == null || mapDots == null){
            return;
        }

        cityMarkers.clear();
        mapDots.getChildren().clear();

        String name = country.getCountry();

        Image img = null;
        try {
            if (name.equals("Wakanda")) {
                img = new Image(getClass().getResourceAsStream("/maps/wakanda.png"));
            } else if (name.equals("Asgard")) {
                img = new Image(getClass().getResourceAsStream("/maps/asgard.png"));
            } else if (name.equals("Atlantis")) {
                img = new Image(getClass().getResourceAsStream("/maps/atlantis.png"));
            } else if (name.equals("Narnia")) {
                img = new Image(getClass().getResourceAsStream("/maps/narnia.png"));
            } else if (name.equals("Oz")) {
                img = new Image(getClass().getResourceAsStream("/maps/oz.png"));
            }
        // in case image doesn't load for a country
        } catch (Exception e) {
            System.out.println("Could not load map image for " + name + ": " + e.getMessage());
        }

        mapImageView.setImage(img);

        mapImageView.boundsInParentProperty().addListener((obs, oldB, newB) -> {
            positionCityMarkers();
        });

        mapPane.widthProperty().addListener((obs, oldV, newV) -> positionCityMarkers());
        mapPane.heightProperty().addListener((obs, oldV, newV) -> positionCityMarkers());

        // different cities for each of the countries
        if (name.equals("Wakanda")) {
            addCityMarker("Warrior Falls", 1500, 550, 1.0);
            addCityMarker("Mt. Boshenga", 1700, 700, 0.6);
            addCityMarker("Marta's Initiation", 1925, 350, 0.4);
            addCityMarker("Golden City", 1700, 525, 0.4);
        } else if (name.equals("Asgard")) {
            addCityMarker("City of Asgard", 2200, 250, 1.0);
            addCityMarker("Valhalla", 2400, 290, 0.7);
            addCityMarker("Nastrand", 2530, 530, 0.6);
            addCityMarker("Alfhelm", 2350, 490, 0.5);
            addCityMarker("Hidden Forest", 2380, 740, 0.4);
            addCityMarker("Nornheim", 2380, 840, 0.7);
        } else if (name.equals("Atlantis")) {
            addCityMarker("Africa", 630, 210, 1.0);
            addCityMarker("Hypania", 600, 480, 0.6);
            addCityMarker("Infula Atlantis", 1075, 420, 0.5);
            addCityMarker("America", 1475, 420, 0.4);
        } else if (name.equals("Oz")) {
            addCityMarker("Emerald City", 1325, 450, 1.0);
            addCityMarker("Munchkin Country", 1675, 200, 0.6);
            addCityMarker("Quadling Country", 1325, 675, 0.5);
            addCityMarker("Gillikin Country", 1250, 150, 0.4);
            addCityMarker("Winkie Country", 975, 520, 0.8);
        } else if (name.equals("Narnia")) {
            addCityMarker("Cair Paravel", 900, 175, 1.0);
            addCityMarker("Lantern Waste", 1100, 650, 0.6);
            addCityMarker("Beaver Dam", 1500, 680, 0.5);
            addCityMarker("The Great Woods", 1500, 150, 0.4);
        }


        updateHeatmap(country);

    }

    // update map of country with heat map of dots increasing and decreasing with size
    private void updateHeatmap(Country country){
        if(cityMarkers.isEmpty()){
            return;
        }

        double pop = country.getPopulation();
        // scale of the dots based on population
        double scale = pop / 100_000_000.0;

        for (CityMarker cm : cityMarkers) {
            double radius = 5  + cm.weight * scale * 2;

            if (radius < 5.0){
                radius = 5.0;
            }
            if (radius > 25.0){
                radius = 25.0;
            }

            cm.circle.setRadius(radius);

            double t = (radius - 5) / 20.0;
            cm.circle.setFill(Color.color(1.0,1.0 -t, 0.0, 0.6));
        }
    }

    private void resetDotSizes() {
        if(cityMarkers == null || cityMarkers.isEmpty()) {
            return;
        }

        for(CityMarker cm : cityMarkers) {
            if (cm.circle != null) {
                cm.circle.setRadius(5);
                cm.circle.setFill(Color.rgb(255,0,0,0.6));
            }
        }
    }

private Country resetCountry() {
    int index = choiceBox.getSelectionModel().getSelectedIndex();
    if (index < 0) {
        return null;
    }

    countries = Data.getCountries();
    Country fresh = countries[index];

    chosenCountry = fresh;
    updateCountryHeader(fresh);   
    useFactorSliders(fresh);        
    updatePieChart(fresh);           
    updateCountryMap(fresh);        
    lineChart.getData().clear();

    return fresh;
}
    // code for the live simulation to update everything as time runs

    private void liveSim(Country country){
        if(timeline != null){
            timeline.stop();
        }

        updateCountryMap(country);
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
            new KeyFrame(Duration.millis(1500), ignore -> {
                int newPop = Simulation.simulateYear(country);
                year[0]++;

                series.getData().add(new XYChart.Data<>(year[0], newPop));
                livePop.setText("Population: " + newPop);
                updatePieChart(country);
                updateHeatmap(country);
            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    

    public static void main(String[] args) {
        Application.launch(args);
    }

}

    