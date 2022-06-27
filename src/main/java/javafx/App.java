/**
 * @author Tobias Hering
 * @comment Initialize JavaFX
 * @documentation https://openjfx.io/
 */


package javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

  private static Scene scene;
  private String stage_name = "Java MauMau";
  // private String icon_path = "";
  private String default_scene = "startScreen";
  private boolean window_resize = true;

  @Override
  public void start(Stage stage) throws IOException {
    scene = new Scene(loadFXML(default_scene));
    // stage.getIcons().add(new Image(icon_path));
    stage.setTitle(stage_name);
    stage.setScene(scene);

    // always show the whole stage
    stage.sizeToScene();
    stage.setResizable(window_resize);
    stage.show();
    stage.setMinWidth(stage.getWidth());
    stage.setMinHeight(stage.getHeight());
  }

  // Handle closing the application
  @Override
  public void stop() {
    Platform.exit();
    System.exit(0);
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }
}