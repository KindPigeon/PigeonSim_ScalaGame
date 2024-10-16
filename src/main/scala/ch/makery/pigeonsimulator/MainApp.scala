package ch.makery.pigeonsimulator

import ch.makery.pigeonsimulator.model.{Pigeon, Enemy}
import ch.makery.pigeonsimulator.view.encounterController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.image.{Image, ImageView}
import scalafx.stage.{Modality, Stage}
import scala.util.Random

object MainApp extends JFXApp {
  val rootResource = getClass.getResource("view/rootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  //Starting Stage
  stage = new PrimaryStage {
    title = "Pigeon Simulator"
    scene = new Scene {
      root = roots
    }
  }

  //Represent a encounter box (portal) in the map
  case class EncounterBox(id: Int, x: Double, y: Double){
    val eId = 0
    val eWidth: Double = 50
    val eHeight: Double = 50
  }


  //Trigger Welcome Scene fxml
  private def showWelcomeScene(): Unit={
    val resource = getClass.getResource("view/welcomeScene.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //Trigger Battle Scene fxml
  def showBattleScene(enemy: Enemy): Unit={
    val resource = getClass.getResource("view/gameScene.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]

    val battleEnemyIG = new ImageView(enemy.image) {
      fitHeight = 300
      fitWidth = 350
      layoutX = 250
      layoutY = -10
    }
    roots.getChildren.add(battleEnemyIG)
  //Create battle scene with style
    val battleScene = new Scene{
      stylesheets = List(getClass.getResource("view/Style.css").toString)
      root = roots
    }
    stage.scene = battleScene
    stage.title = "Battle Enounter"
  }

  //Trigger Encounter d fxml
  def showEncounterDialog(): Unit={
    println("Encounter!")
    val resource = getClass.getResource("view/encounterDialog.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val dialogRoot = loader.getRoot[jfxs.Parent]

    val enemy = new Enemy(
      id = Random.nextInt(1000),
      enemyPosition = (Random.nextDouble() * 600, Random.nextDouble() * 600),
      enemyHP = 100
    )
    val control = loader.getController[encounterController#Controller]
    control.prepareEnemy(enemy)
    val dialogStage = new Stage {
      title = "Encounter!!!"
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene{
        stylesheets = List(getClass.getResource("view/Style.css").toString)
        root = dialogRoot
      }
    }
    control.dialogStage = dialogStage
    dialogStage.showAndWait()

  }

  //Trigger Game Map and Movement Logic
  def showGameScene(): Unit={
    val resource = getClass.getResource("view/gameMap.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val mapRoot = loader.getRoot[jfxs.layout.BorderPane]
    val gameContent = new Group()
    var direction = 4
    // Creating new Pigeon from the class
    var pigeon = new Pigeon((100, 100), 100)
    val birdIg = new Image(getClass.getResourceAsStream("/images/pigeon.png"))
    val encounters = scala.collection.mutable.ListBuffer[EncounterBox]()
    val encounterPortal = new Image(getClass.getResourceAsStream("/images/portal.png"))

    //Add the Encounter Boxes into the Scene
    for (id <- 1 to 5){
      val randomX = Random.nextInt(30) * 20
      val randomY = Random.nextInt(30) * 20
      val encounter = EncounterBox(id, randomX, randomY)
      encounters += encounter

      val encounterIg = new ImageView(encounterPortal){
          x = encounter.x
          y = encounter.y
          fitWidth = encounter.eWidth
          fitHeight = encounter.eHeight
        }
        gameContent.getChildren.add(encounterIg)
      }

    //Add the Pigeon into the Scene
    val pigeonIg = new ImageView(birdIg) {
      x = pigeon.position._1
      y = pigeon.position._2
      fitWidth = 50
      fitHeight = 50
    }
    gameContent.getChildren.add(pigeonIg)
    // create game map scene with handling movement system
    val gameArena = new Scene(mapRoot, 600, 600) {
      stylesheets = List(getClass.getResource("view/Style.css").toString)
      root = mapRoot
      onKeyPressed = key => {
        key.getText match {
          case "w" => direction = 1
          case "s" => direction = 2
          case "a" => direction = 3
          case "d" => direction = 4
          case _ => direction
        }
        pigeon = pigeon.move(direction)
        pigeonIg.x = pigeon.position._1
        pigeonIg.y = pigeon.position._2

        encounters.foreach { encounter =>
          if (pigeon.position._1 == encounter.x &&
            pigeon.position._2 == encounter.y) {
            showEncounterDialog()
          }
        }
      }
    }
  mapRoot.setCenter(gameContent)
    stage.scene = gameArena
    stage.title = "Pigeon Simulator"
  }
  //Initiate Whole Program
  showWelcomeScene()
}
