package ddc;
object genid {
  var counter = 0
  def apply(s:String):String = {
    counter += 1
    s + counter
  }
}
