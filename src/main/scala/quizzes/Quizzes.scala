package quizzes

object Quizzes extends App {
  val xmlData = <entities><entity type="player" x="3" y="2"/><entity type="ghost" x="7" y="1"/></entities>
  val counted = (xmlData \\ "entity").map(n => n \ "@x").count(_.text.toInt < 5)
  println(counted)
}
