package ch.makery.pigeonsimulator.view

import ch.makery.pigeonsimulator.MainApp
import ch.makery.pigeonsimulator.model.Enemy
import scalafx.scene.control.Label
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class encounterController(private val encounterTalk: Label){
  private var enemy: Enemy = _
  var dialogStage : Stage  = null

  def prepareEnemy(enemy: Enemy): Unit = {
    this.enemy = enemy
  }
  def handleEncounterTalk(): Unit = {
    encounterTalkDialog()
  }
  def handleEncounterBattle(): Unit = {
    MainApp.showBattleScene(enemy)
    dialogStage.close()

  }
  def handleEncounterCancel(): Unit = {
    dialogStage.close()
  }
  def encounterTalkDialog():Unit ={
    encounterTalk.text = "Welcome Challenger!! FACE ME!"
  }
}