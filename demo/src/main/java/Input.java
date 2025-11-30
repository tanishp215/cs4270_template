import javafx.application.Application;
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


public class Input extends Application{
    @Override
    public void start(Stage stage){
        Button button = new Button();
        button.setText("hello!");
        Text text = new Text(300, 500, "Hello World!");
        text.setFont(new Font(50));

        ChoiceBox<String> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().add("Wakanda");
        choiceBox.getItems().add("Asgard");
        choiceBox.getItems().add("Atlantis");
        choiceBox.getItems().add("Oz");
        choiceBox.getItems().add("Narnia");
        choiceBox.setPrefWidth(200); 
        choiceBox.setPrefHeight(40);
        choiceBox.setValue("Choose a country to begin with! ");

        HBox hbox = new HBox(20, text, button, choiceBox);
        hbox.setAlignment(Pos.CENTER);

        ObservableList<PieChart.Data> pieData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Factor 1", 40),
                        new PieChart.Data("Factor 2", 25),
                        new PieChart.Data("Factor 3", 20),
                        new PieChart.Data("Factor 4", 15)
                );

        PieChart pieChart = new PieChart(pieData);
        pieChart.setTitle("Market Share");
        BorderPane chart = new BorderPane();
        VBox vbox = new VBox(hbox);
        vbox.setAlignment(Pos.TOP_CENTER);
        chart.setTop(vbox);
        chart.setCenter(pieChart);

        Scene scene = new Scene(chart, 1920, 1120);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
        // if Wakanda 
        // PopulationChanges WakandaBirthRate = new PopulationChanges("Wakanda", "Birth Rate", 1_000_000, 1.00);
        // PopulationChanges WakandaDeathRate = new PopulationChanges("Wakanda", "Death Rate", -600_000, 1.00);
        // PopulationChanges WakandaHealthCare = new PopulationChanges("Wakanda", "Health Care", 0, 1.04);
        // PopulationChanges WakandaFoodSupply = new PopulationChanges("Wakanda", "Food Supply", 0, 1.03);
        // PopulationChanges WakandaDisease = new PopulationChanges("Wakanda", "Disease", 0, 1.03);
        // PopulationChanges WakandaInfantMortalityRate = new PopulationChanges("Wakanda", "Infant Mortality Rate", -2_500, 1.00);
        // PopulationChanges WakandaEconomy = new PopulationChanges("Wakanda", "Economy", 0, 1.05);
        // PopulationChanges WakandaEducation = new PopulationChanges("Wakanda", "Education", 0, 1.03);
        // PopulationChanges WakandaBirthControl = new PopulationChanges("Wakanda", "Birth Control", 0, 0.96);
        // PopulationChanges WakandaImmigration = new PopulationChanges("Wakanda", "Immigration", 0, 1.00);
        // PopulationChanges[] WakandaChanges = {WakandaBirthRate, WakandaDeathRate, WakandaHealthCare, WakandaFoodSupply, WakandaDisease, WakandaInfantMortalityRate, WakandaEconomy, WakandaEducation, WakandaBirthControl, WakandaImmigration};

        // if Asgard 
        // PopulationChanges AsgardBirthRate = new PopulationChanges("Asgard", "Birth Rate", 300_000, 1.00);
        // PopulationChanges AsgardDeathRate = new PopulationChanges("Asgard", "Death Rate", -350_000, 1.00);
        // PopulationChanges AsgardHealthCare = new PopulationChanges("Asgard", "Health Care", 0, 1.00);
        // PopulationChanges AsgardFoodSupply = new PopulationChanges("Asgard", "Food Supply", 0, 1.02);
        // PopulationChanges AsgardDisease = new PopulationChanges("Asgard", "Disease", 0, 1.00);
        // PopulationChanges AsgardInfantMortalityRate = new PopulationChanges("Asgard", "Infant Mortality Rate", -5_000, 1.00);
        // PopulationChanges AsgardEconomy = new PopulationChanges("Asgard", "Economy", 0, 0.97);
        // PopulationChanges AsgardEducation = new PopulationChanges("Asgard", "Education", 0, 0.99);
        // PopulationChanges AsgardBirthControl = new PopulationChanges("Asgard", "Birth Control", 0, 1.00);
        // PopulationChanges AsgardImmigration = new PopulationChanges("Asgard", "Immigration", 50_000, 1.00);
        // PopulationChanges[] AsgardChanges = {AsgardBirthRate, AsgardDeathRate, AsgardHealthCare, AsgardFoodSupply, AsgardDisease, AsgardInfantMortalityRate, AsgardEconomy, AsgardEducation, AsgardBirthControl, AsgardImmigration};

        // if Atlantis 
        // PopulationChanges AtlantisBirthRate = new PopulationChanges("Atlantis", "Birth Rate", 800_000, 1.00);
        // PopulationChanges AtlantisDeathRate = new PopulationChanges("Atlantis", "Death Rate", -700_000, 1.00);
        // PopulationChanges AtlantisHealthCare = new PopulationChanges("Atlantis", "Health Care", 0, 1.01);
        // PopulationChanges AtlantisFoodSupply = new PopulationChanges("Atlantis", "Food Supply", 0, 1.04);
        // PopulationChanges AtlantisDisease = new PopulationChanges("Atlantis", "Disease", 0, 1.01);
        // PopulationChanges AtlantisInfantMortalityRate = new PopulationChanges("Atlantis", "Infant Mortality Rate", -4_000, 1.00);
        // PopulationChanges AtlantisEconomy = new PopulationChanges("Atlantis", "Economy", 0, 1.05);
        // PopulationChanges AtlantisEducation = new PopulationChanges("Atlantis", "Education", 0, 1.01);
        // PopulationChanges AtlantisBirthControl = new PopulationChanges("Atlantis", "Birth Control", 0, 0.97);
        // PopulationChanges AtlantisImmigration = new PopulationChanges("Atlantis", "Immigration", 150_000, 1.00);
        // PopulationChanges[] AtlantisChanges = {AtlantisBirthRate, AtlantisDeathRate, AtlantisHealthCare, AtlantisFoodSupply, AtlantisDisease, AtlantisInfantMortalityRate, AtlantisEconomy, AtlantisEducation, AtlantisBirthControl, AtlantisImmigration};

        // if Oz 
        // PopulationChanges OzBirthRate = new PopulationChanges("Oz", "Birth Rate", 50_000, 1.00);
        // PopulationChanges OzDeathRate = new PopulationChanges("Oz", "Death Rate", -40_000, 1.00);
        // PopulationChanges OzHealthCare = new PopulationChanges("Oz", "Health Care", 0, 1.01);
        // PopulationChanges OzFoodSupply = new PopulationChanges("Oz", "Food Supply", 0, 1.02);
        // PopulationChanges OzDisease = new PopulationChanges("Oz", "Disease", 0, 0.97);
        // PopulationChanges OzInfantMortalityRate = new PopulationChanges("Oz", "Infant Mortality Rate", -500, 1.00);
        // PopulationChanges OzEconomy = new PopulationChanges("Oz", "Economy", 0, 1.00);
        // PopulationChanges OzEducation = new PopulationChanges("Oz", "Education", 0, 1.05);
        // PopulationChanges OzBirthControl = new PopulationChanges("Oz", "Birth Control", 0, 0.98);
        // PopulationChanges OzImmigration = new PopulationChanges("Oz", "Immigration", 10_000, 1.00);
        // PopulationChanges[] OzChanges = {OzBirthRate, OzDeathRate, OzHealthCare, OzFoodSupply, OzDisease, OzInfantMortalityRate, OzEconomy, OzEducation, OzBirthControl, OzImmigration};

        // if Narnia 
        // PopulationChanges NarniaBirthRate = new PopulationChanges("Narnia", "Birth Rate", 3_000_000, 1.00);
        // PopulationChanges NarniaDeathRate = new PopulationChanges("Narnia", "Death Rate", -2_500_000, 1.00);
        // PopulationChanges NarniaHealthCare = new PopulationChanges("Narnia", "Health Care", 0, 1.04);
        // PopulationChanges NarniaFoodSupply = new PopulationChanges("Narnia", "Food Supply", 0, 0.99);
        // PopulationChanges NarniaDisease = new PopulationChanges("Narnia", "Disease", 0, 1.01);
        // PopulationChanges NarniaInfantMortalityRate = new PopulationChanges("Narnia", "Infant Mortality Rate", -10_000, 1.00);
        // PopulationChanges NarniaEconomy = new PopulationChanges("Narnia", "Economy", 0, 1.05);
        // PopulationChanges NarniaEducation = new PopulationChanges("Narnia", "Education", 0, 1.02);
        // PopulationChanges NarniaBirthControl = new PopulationChanges("Narnia", "Birth Control", 0, 0.99);
        // PopulationChanges NarniaImmigration = new PopulationChanges("Narnia", "Immigration", 250_000, 1.00);
        // PopulationChanges[] NarniaChanges = {NarniaBirthRate, NarniaDeathRate, NarniaHealthCare, NarniaFoodSupply, NarniaDisease, NarniaInfantMortalityRate, NarniaEconomy, NarniaEducation, NarniaBirthControl, NarniaImmigration};

        // country, name, population add, population rate
        // birth rate, death rate, health care access, food supply, disease, infant mortality rate, economy, education, birth control and immigration. 

    }
}

