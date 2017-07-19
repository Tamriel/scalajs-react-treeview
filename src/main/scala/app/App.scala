package app

import scala.scalajs.js.JSApp
import org.scalajs.dom.document

object App extends JSApp {
  def main(): Unit = {
    TreeView.root.renderIntoDOM(document.getElementById("root"))
  }
}
