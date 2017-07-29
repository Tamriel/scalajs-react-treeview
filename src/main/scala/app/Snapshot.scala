package app

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.extra
import japgolly.scalajs.react.extra.StateSnapshot
import MonocleReact._
import monocle.macros._

object Snapshot {

  @Lenses
  case class Name(firstName: String, surname: String)

  val NameChanger = ScalaComponent
    .builder[StateSnapshot[String]]("Name changer")
    .render_P { stateSnapshot =>
      <.input.text(^.value := stateSnapshot.value,
                   ^.onChange ==> ((e: ReactEventFromInput) =>
                     stateSnapshot.setState(e.target.value)))
    }
    .configure(extra.LogLifecycle.default)
    .build

  val Main = ScalaComponent
    .builder[Unit]("StateSnapshot example")
    .initialState(Name("John", "Wick"))
    .render { $ =>
      val name = $.state
      val firstNameV = StateSnapshot.zoomL(Name.firstName).of($)
      val surnameV = StateSnapshot.zoomL(Name.surname).of($)
      <.div(
        <.label("First name:", NameChanger(firstNameV)),
        <.label("Surname:", NameChanger(surnameV)),
        <.p(s"My name is ${name.surname}, ${name.firstName} ${name.surname}.")
      )
    }
    .configure(extra.LogLifecycle.default)
    .build

  def root = Main()
}
