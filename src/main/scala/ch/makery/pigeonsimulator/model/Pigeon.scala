package ch.makery.pigeonsimulator.model

import scalafx.beans.property.IntegerProperty

class Pigeon(val pigeonPosition: (Double, Double), val pigeonHP: Int){
  var position = pigeonPosition
  var hp = IntegerProperty(pigeonHP)

  //handle the movement logic after receiving input from main
  def move(dir: Int): Pigeon = {
    val(x,y) = position
    val(newx, newy) = dir match{
      case 1 => (x, y - 10)
      case 2 => (x, y + 10)
      case 3 => (x - 10, y)
      case 4 => (x + 10, y)
      case _ => (x, y)
    }
    new Pigeon((newx,newy), hp.value)
  }

}

