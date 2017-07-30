//package app
//
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.MonocleReact._
//import japgolly.scalajs.react.{extra, _}
//import japgolly.scalajs.react.extra.StateSnapshot
//import japgolly.scalajs.react.vdom.html_<^._
//import monocle.macros._
//
//object TreeView {
//
//  @Lenses
//  case class Node(id: String, text: String, children: Vector[Node])
//
//  val childNode = Node("1.1", "", Vector.empty)
//  val parentNode = Node("1", "", Vector(childNode))
//
//  val NodeComponent = ScalaComponent
//    .builder[Node]("Node")
//    .initialState(Node("1.1", "", Vector.empty))
//    .renderBackend[NodeBackend]
//    .build
//
//  class NodeBackend($ : BackendScope[Node, Node]) {
//
//    def addChild =
//      $.modState(s =>
//        s.copy(children = s.children :+ Node("1.2", "", Vector.empty)))
//
//    val onTextChange: ReactEventFromInput => Callback =
//      _.extract(_.target.value)(t => $.modState(_.copy(text = t)))
//
//    def render(state: Node): VdomElement = {
//      val name = $.state
//
//      val text = StateSnapshot.zoomL(Node.text).of(state)
//
//      val children =
//        state.children.toVdomArray(child =>
//          NodeComponent.withKey(child.id)(child))
//
//      val input =
//        <.input.text(^.value := text, ^.onChange ==> onTextChange)
//
//      <.div(
//        state.id,
//        input,
//        <.button("Add child", ^.onClick --> addChild),
//        children
//      )
//    }
//  }
//
//  def root = NodeComponent(parentNode)
//}
