package ddc
object parse {
  def main(argv:Array[String]) {
    val prg = "main:()(void)={ printInt(add(1,2,3));printFloat(addf(1.1,0.1,0.2)) } add:(a:int,b:int,c:int)(int)={return a + b + c} addf:(a:float,b:float,c:float)(float)={return a .+ b .+ c}"
    val st = parse(prg)
    println("st="+st)
    val ast = st2ast(st)
    println("ast="+ast)
    val tast = typing(ast)
    println("tast="+tast)
    val s = setmem(tast)
    println("s="+s)
    val e = kNormal(s)
    println("e="+e)
    val m = memAlloc(e)
    println("m="+m)
    emit("e.s", m)
    exec("gcc -m64 -o e e.s lib.c") match {
      case 0 => exec("./e")
      case _ =>
    }

  }
  val comments = """(?s)^[\t\r\n ]*(#[^\r\n]*)(.*$)""".r
  val nums = """(?s)^[\t\r\n ]*([0-9]+)(.*$)""".r
  val floats = """(?s)^[\t\r\n ]*([0-9]+\.[0-9]+)(.*$)""".r
  val ns = """(?s)^[\t\r\n ]*([a-zA-Z_][a-zA-Z_0-9]*|\.\+|[\(\)\{\}+,=;:]|)(.*$)""".r
  def apply(str:String):Any = {
    var src = str
    var token:Any = ""
    var ptoken:Any = ""
    def lex():Any = {
      ptoken = token
      src match {
        case comments(a,b) => src = b; lex()
        case floats(a,b) => token = a.toFloat; src = b
        case nums(a,b) => token = a.toInt; src = b
        case ns(a,b) => token = a; src = b
      }
      ptoken
    }
    def eat(e:Any):Any = {
      if(lex() != e) {
        throw new Exception("syntax error. found unexpected token "+ptoken+ ". expected is "+e)
      }
      ptoken
    }
    lex()
    def prs(a:Any):Any = a match {
      case "fun"=>(0,"st")
      case "{" => (0, "p","}")
      case "(" => (0, "p",")")
      case "return" => (0, "l")
      case _ => -1
    }
    def ins(a:Any):Any = a match {
      case "+" => (10,"l")
      case ".+" => (10,"l")
      case "=" => (5,"r")
      case "," => (4,"l")
      case ":" => (6,"l")
      case "(" => (0,"p",")")
      case ";" => (0,"e")
      case _ => -1
    }
    def exp(p:Int):Any = {
      if(token ==")" || token == "}") return "void"
      def pr(t:Any):Any = {
        val op = t
        prs(op) match {
          case (_, "ep") => "void"
          case (np:Int,"st") =>
            eat("(")
            val e = exp(np)
            eat(")")
            (op,"(",e,")", exp(0))
          case (np:Int,"p",ep) => val e = loop(exp(0)); (op,e,eat(ep))
          case (np:Int,"l") => (op, exp(np))
          case _ => op
        }
      }
      var t = pr(lex())
      def in(t:Any):Any = {
        ins(token) match {
          case (np:Int,"e") if(np >= p) => val op = lex(); in(t, op)
          case (np:Int,"l") if(np > p) => val op = lex(); in(t, op, exp(np))
          case (np:Int,"r") if(np >= p) => val op = lex(); in(t, op, exp(np))
          case (np:Int,"p",ep) => val sp = lex(); val e = exp(np); in(t, sp, e, eat(ep))
          case _ => t
        }
      }
      in(t)
    }
    def loop(t:Any):Any = token match {
      case "" => t
      case "}" => t
      case ")" => t
      case "]" => t
      case _ => val e = (t,"@",exp(0)); loop(e)
    }
    loop(exp(0))
  }
}
