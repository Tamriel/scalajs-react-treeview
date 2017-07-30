package app

import japgolly.scalajs.react.MonocleReact._
import japgolly.scalajs.react.{extra, _}
import japgolly.scalajs.react.extra.StateSnapshot
import japgolly.scalajs.react.vdom.html_<^._
import monocle.Lens
import monocle.macros._
import monocle.syntax._
import monocle.function.all._ // to get each and other typeclass based optics such as at or headOption
import monocle.Traversal
import monocle.macros.GenLens
import monocle.function.At.at // to get at Lens
import monocle.std.map._ // to get Map instance for At
import monocle.Lens
import monocle.macros._
import monocle.syntax._
import monocle.function.all._ // to get each and other typeclass based optics such as at or headOption
import monocle.Traversal
import monocle.macros.GenLens
import monocle.function.At.at // to get at Lens
import monocle.std.map._ // to get Map instance for At
import monocle.macros.GenLens // require monocle-macro module
import monocle.function.all._ // to get each and other typeclass based optics such as at or headOption
import monocle.Traversal
import monocle.function.At.at // to get at Lens
import monocle.std.map._ // to get Map instance for At

object NameChanger {

  case class Node(id: String, text: String, childrenIds: Vector[String])

  case class Store(nodes: Map[String, Node])

  val NodeComponent = ScalaComponent
    .builder[StateSnapshot[Option[Node]]]("Name changer")
    .render_P { stateSnapshot =>
      def a = {
        val s = stateSnapshot.value.get
        stateSnapshot.setState(Option(s.copy(text = "Neu")))
      }

      val nodesLens = GenLens[Store](_.nodes)
      stateSnapshot.value.get.childrenIds
      val children =
        store.nodes.toVdomArray(node => {
          val childLens = nodesLens composeLens at(node)
          val childAt = StateSnapshot.zoomL(childLens).of($)
          <.label("Child:", NodeComponent(childAt))
        })

      <.div(
        <.input.text(
          ^.value := stateSnapshot.value.get.text,
          ^.onChange ==> ((e: ReactEventFromInput) => a)
        ),
        children
      )
    }
    .configure(extra.LogLifecycle.default)
    .build

  val childNode = Node("1", "child", Vector.empty)
  val parentNode = Node("0", "root", Vector("1"))
  val store = Store(Map("0" -> parentNode, "1" -> childNode))

  val newChildNode = Node("2", "new child", Vector.empty)

  val Main = ScalaComponent
    .builder[Unit]("StateSnapshot example")
    .initialState(store)
    .render { $ =>
      def a = {
        $.setState($.state.copy(nodes = $.state.nodes + ("2" -> newChildNode)))
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
