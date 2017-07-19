package app

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

case class Node(text: String, children: Vector[Node])

object TreeView {
  val childNode = Node("1.1", Vector())
  val parentNode = Node("1", Vector(childNode))

  val nodeComponent = ScalaComponent
    .builder[Node]("Node")
    .renderBackend[NodeBackend]
    .build

  class NodeBackend($ : BackendScope[Node, Unit]) {
    def render(node: Node) = {
      <.div(
        <.div(node.text)
//        ,nodeComponent(node.children(0)).when(node.children.nonEmpty)
      )
    }
  }

  def apply() = nodeComponent(parentNode)
}