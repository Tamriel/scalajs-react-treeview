package app

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

case class Node(id: String, text: String, children: Vector[Node])

object TreeView {
  val childNode = Node("1.1", "", Vector.empty)
  val parentNode = Node("1", "", Vector(childNode))

  val NodeComponent = ScalaComponent.builder[Node]("Node")
    .initialStateFromProps(identity)
    .renderBackend[NodeBackend]
    .build

  class NodeBackend($: BackendScope[Node, Node]) {

    def addChild =
      $.modState(s =>
        s.copy(children = s.children :+ Node("1.2", "", Vector.empty)))

    val onTextChange: ReactEventFromInput => Callback =
      _.extract(_.target.value)(t => $.modState(_.copy(text = t)))

    def render(state: Node): VdomElement = {
      val children =
        state.children.toVdomArray(child =>
          NodeComponent.withKey(child.id)(child))

      val input =
        <.input.text(
          ^.value := state.text,
          ^.onChange ==> onTextChange)

      <.div(
        state.id, input, <.button("Add child", ^.onClick --> addChild),
        children
      )
    }
  }

  def root = NodeComponent(parentNode)
}
