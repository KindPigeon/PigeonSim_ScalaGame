package ch.makery.pigeonsimulator.view

import ch.makery.pigeonsimulator.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label}
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml
import scala.util.Random

@sfxml
class gameSceneController(
                         private val playerHPText: Label,
                         private val enemyHPText: Label,
                         private val battleDialog: Label
                         ){
  private var playerHP: Int = 100
  private var enemyHP: Int = 200

  //Get the player choice and enemy choice then call next
  def actionLogic(playerAction: Int): Unit = {
    val enemyAction = Random.nextInt(3) + 1
    actionResult(playerAction, enemyAction)
  }

  //Logic for battle scene (Rock, Paper Scissors format)
  def actionResult(playerAction:Int , enemyAction:Int): Unit = {
    (playerAction, enemyAction) match {
      case (1, 2) => playerHP -= 25
      battleDialog.text = "You Chose Attack, Enemy Chose Defend. Enemy Wins"
      case (2, 3) => playerHP -= 25
        battleDialog.text = "You Chose Defend, Enemy Chose Power. Enemy Wins"
      case (3, 1) => playerHP -= 25
        battleDialog.text = "You Chose Power, Enemy Chose Attack. Enemy Wins"
      case (2, 1) => enemyHP -= 25
        battleDialog.text = "You Chose Defend, Enemy Chose Attack. You Win"
      case (3, 2) => enemyHP -= 25
        battleDialog.text = "You Chose Power, Enemy Chose Defend. You Win"
      case (1, 3) => enemyHP -= 25
        battleDialog.text = "You Chose Attack, Enemy Chose Power. You Win"
      case _ => //Nothing(Draw)
        battleDialog.text = "You and Enemy Draws!"
    }
    newHP()
    battleFinish()
  }

  //Check if Battle finish for when hp < 0
  def battleFinish():Unit = {
    if (playerHP <=0){
      println("You have lost")
      MainApp.showGameScene()
    }
    else if (enemyHP <= 0){
      println("You have won")
      MainApp.showGameScene()
    }
  }
  // Display the user and enemy health
  def newHP():Unit ={
    playerHPText.text = "Player Hp - " + playerHP.toString
    enemyHPText.text = "Enemy Hp - " + enemyHP.toString
  }

  //Assign value for each Button representating an action
  def handleBattleAttack(action: ActionEvent): Unit = {
    actionLogic(1)
  }
  def handleBattleDefend(action: ActionEvent): Unit = {
    actionLogic(2)
  }
  def handleBattlePower(action: ActionEvent): Unit = {
    actionLogic(3)
  }
  def handleBattleRetreat(action: ActionEvent): Unit = {
    MainApp.showGameScene()
  }
}