package ddc
import java.io._
object main {
  def main(argv:Array[String]) {
    val prg = exec.readAll(new FileInputStream(argv(0)))
    val st = parse(prg)
    val ast = st2ast(st)
    val tast = typing(ast)
    val s = setmem(tast)
    val e = kNormal(s)
    val m = memAlloc(e)
    emit("e.s", m)
    exec("gcc -m64 -o e e.s lib.c") match {
      case 0 => exec("./e")
      case _ =>
    }

  }
}
