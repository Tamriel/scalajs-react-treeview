package app

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

case class Node(text: String, children: Vector[Node])

object TreeView {
  val childNode = Node("1.1", Vector())
  val parentNode = Node("1", Vector(childNode))

  val rootNode = ScalaComponent
    .builder[Unit]("Node")
    .initialState(parentNode)
    .renderBackend[NodeBackend]
    .build

  class NodeBackend($ : BackendScope[Unit, Node]) {

    def addChild =
      $.modState(
        _.copy(children = $.state.runNow().children :+ Node("1.2", Vector())))

    def render(node: Node): VdomElement = {
      val children =
        if (node.children.nonEmpty)
          node.children.toVdomArray(child => {
            val childNode = ScalaComponent
              .builder[Unit]("Node")
              .initialState(child)
              .renderBackend[NodeBackend]
              .build
            childNode.withKey(child.text)()
          })
        else EmptyVdom

      <.div(
        node.text,
        <.input(),
        <.button("Add child", ^.onClick --> addChild),
        children
      )
    }
  }

  def apply() = rootNode()
}
