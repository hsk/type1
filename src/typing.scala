package ddc

object typing {
  def main(args:Array[String]) {
    val st = List((((1.0,"+",2),"+",3),"+",4))
    val ast = typing(st)
    println(ast)
  }

  def apply(e:List[Any]):List[Any] = { e }
/*
    e match {
      case List()=>List()
      case x::xs => t(x)::apply(xs)
    }
  }
*/
  def t(e:Any):Any = {
    e match {
      case (a, "+", b) => ic(t(a), t(b)){
        case "float"=>".+"
        case "int"=> "+"
      }
      case a:Int => (a,"int")
      case a:Double => (a,"float")
    }
  }
  def ic(a:Any, b:Any)(f:(String)=>Any):Any = (a,b) match {
    case ((a,"float"),(b,"float")) => ((a,f("float"),b),"float")
    case ((a,"float"),(b,_)) => ((a,f("float"),("cast_float", b)),"float")
    case ((a,_),(b,"float")) => ((("cast_float",a),f("float"),b), "float")
    case ((a,_),(b,_))=>((a,f("float"),b),"int")
  }
}

