package app

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

case class Node(text: Int, children: Vector[Node])

object TreeView {
  val childNode = Node(2, Vector())
  val parentNode = Node(1, Vector(childNode))

  val nodeComponent = ScalaComponent
    .builder[Node]("Node")
    .renderBackend[NodeBackend]
    .build

  class NodeBackend($ : BackendScope[Node, Unit]) {

    def render(node: Node): VdomElement = {
      val children =
        if (node.children.nonEmpty)
          node.children.toVdomArray(child =>
            nodeComponent.withKey(child.text)(child))
        else EmptyVdom

      <.div(
        node.text,
        <.input(),
        <.button("+1"),
        children
      )
    }
  }

  def apply() = nodeComponent(parentNode)
}
