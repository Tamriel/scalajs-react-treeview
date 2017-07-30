package app

import scala.scalajs.js.JSApp
import org.scalajs.dom.document

object App extends JSApp {
  def main(): Unit = {
    NameChanger.root.renderIntoDOM(document.getElementById("root"))
  }
}
