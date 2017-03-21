import spray.json._
import DefaultJsonProtocol._

import OboGraphJsonProtocol._

object Run extends App {
  val m = Meta(definition=Some("test"),comments=Some(List("xxxxx")))
  println(m)
  val n = Node("x",Some("x"),Some(m))
  println(n)
  //val j = JsonAST.render(n)
  val n2 = Node("x")
  println(n2)
  val j2 = n.toJson
  println(j2)
}
