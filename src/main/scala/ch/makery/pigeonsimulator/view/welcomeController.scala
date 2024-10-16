package ch.makery.pigeonsimulator.view

import ch.makery.pigeonsimulator.MainApp
import scalafxml.core.macros.sfxml

@sfxml
class welcomeController(){
  def handleGetStarted(): Unit = {
    //Calls the game(main map) scene on click
    MainApp.showGameScene()
  }
}