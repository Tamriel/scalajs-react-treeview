package app

import japgolly.scalajs.react.MonocleReact._
import japgolly.scalajs.react.{extra, _}
import japgolly.scalajs.react.extra.StateSnapshot
import japgolly.scalajs.react.vdom.html_<^._
import monocle.Lens
import monocle.macros._
import monocle.syntax._
import monocle.function.all._
import monocle.Traversal
import monocle.macros.GenLens
import monocle.function.At.at
import monocle.std.map._
import monocle.Lens
import monocle.macros._
import monocle.syntax._
import monocle.function.all._
import monocle.Traversal
import monocle.macros.GenLens
import monocle.function.At.at
import monocle.std.map._
import monocle.macros.GenLens
import monocle.function.all._
import monocle.Traversal
import monocle.function.At.at
import monocle.std.map._ // to get Map instance for At

object NameChanger {

  case class Node(id: String, text: String, childrenIds: Vector[String])

  case class Store(nodes: Map[String, Node])

//  case class Props(storeSnap: StateSnapshot[Option[Node]])


  val NodeComponent = ScalaComponent
    .builder[StateSnapshot[Option[Node]]]("Node")
    .renderBackend[NodeBackend]
    .configure(extra.LogLifecycle.default)
    .build

  class NodeBackend($ : BackendScope[StateSnapshot[Option[Node]], Unit]) {

    def render(stateSnapshot: StateSnapshot[Option[Node]]) = {
      def a = {
        val s = stateSnapshot.value.get
        stateSnapshot.setState(Option(s.copy(text = "Neu")))
      }

      //      val nodesLens = GenLens[Store](_.nodes)
      //      stateSnapshot.value.get.childrenIds
      //      val children =
      //        store.nodes.toVdomArray(node => {
      //          val childLens = nodesLens composeLens at(node)
      //          val childAt = StateSnapshot.zoomL(childLens).of($)
      //          <.label("Child:", NodeComponent(childAt))
      //        })

      <.div(
        <.input.text(
          ^.value := stateSnapshot.value.get.text,
          ^.onChange ==> ((e: ReactEventFromInput) => a)
        )
      )
    }
  }

//  val NodeComponent = ScalaComponent
//    .builder[StateSnapshot[Option[Node]]]("Name changer")
//    .render_P { stateSnapshot =>
//      def a = {
//        val s = stateSnapshot.value.get
//        stateSnapshot.setState(Option(s.copy(text = "Neu")))
//      }
//
////      val nodesLens = GenLens[Store](_.nodes)
////      stateSnapshot.value.get.childrenIds
////      val children =
////        store.nodes.toVdomArray(node => {
////          val childLens = nodesLens composeLens at(node)
////          val childAt = StateSnapshot.zoomL(childLens).of($)
////          <.label("Child:", NodeComponent(childAt))
////        })
//
//      <.div(
//        <.input.text(
//          ^.value := stateSnapshot.value.get.text,
//          ^.onChange ==> ((e: ReactEventFromInput) => a)
//        )
//
//      )
//    }
//    .configure(extra.LogLifecycle.default)
//    .build

  val childNode = Node("1", "child", Vector.empty)
  val parentNode = Node("0", "root", Vector("1"))
  val store = Store(Map("0" -> parentNode, "1" -> childNode))

  val newChildNode = Node("2", "new child", Vector.empty)

  val Main = ScalaComponent
    .builder[Unit]("StateSnapshot example")
    .initialState(store)
    .render { $ =>
      def a = {
//        $.setState($.state.copy(nodes = $.state.nodes + ("2" -> newChildNode)))

        val newL = $.state.nodes + ("0" -> $.state
          .nodes("0")
          .copy(text = "neuer Text"))
        $.setState($.state.copy(nodes = newL))
      }

      val rootNode = {
        val nodesLens = GenLens[Store](_.nodes)
        val childLens = nodesLens composeLens at("0")
        val childAt = StateSnapshot.zoomL(childLens).of($)
        NodeComponent(childAt)
      }

      <.div(
        rootNode,
        <.input.text(
          ^.value := "update child comp",
          ^.onChange ==> ((e: ReactEventFromInput) => a)
        )
      )
    }
    .configure(extra.LogLifecycle.default)
    .build

  def root = Main()
}
