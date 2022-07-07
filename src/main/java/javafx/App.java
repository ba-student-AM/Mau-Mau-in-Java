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
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {

  private static Scene scene;
  private String stage_name = "Java MauMau";
  private String icon_file = "icon.png";
  private String default_scene = "startScreen";
  private boolean window_resize = true;

  @Override
  public void start(Stage stage) {
    // load the default scene
    scene = new Scene(loadFXML(default_scene));

    // set the stage title and icon
    stage.setTitle(stage_name);
    stage.setScene(scene);
    try {
      Image icon = new Image(getClass().getResource("/" + icon_file).toString());
      stage.getIcons().add(icon);
    } catch (Exception e) {
      System.out.println("ERROR: Icon not found: " + icon_file);
    }

    // always show the whole content of the stage
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

  // Load a FXML file (switch the scene)
  static void setRoot(String fxml) {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    try {
      return fxmlLoader.load();
    } catch (Exception e) {
      System.out.println("ERROR: FXML not found: " + fxml + ".fxml");
      return null;
    }
  }

  public static void main(String[] args) {
    launch();
  }
}