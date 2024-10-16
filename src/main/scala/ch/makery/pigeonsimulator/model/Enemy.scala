package ch.makery.pigeonsimulator.model

import scalafx.beans.property.IntegerProperty
import scalafx.scene.image.Image

import scala.util.Random

class Enemy(val id: Int, val enemyPosition: (Double, Double), val enemyHP: Int){
  var position = enemyPosition
  var hp = IntegerProperty(enemyHP)
  var image: Image = selectedEnemyIG()

  //Randomize the selection of available boss images
  private def selectedEnemyIG():Image = {
    val enemyImages = List(
      new Image(getClass.getResourceAsStream("/images/boss_demon.png")),
      new Image(getClass.getResourceAsStream("/images/boss_frost.png")),
      new Image(getClass.getResourceAsStream("/images/boss_lich.png"))
    )
    enemyImages(Random.nextInt(enemyImages.length))
  }
}
