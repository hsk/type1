package ddc

object st2ast {
  def main(argv:Array[String]) {
    val st = ( ( (("main",":",(("(","void",")"),"(","void",")")),
        "=",
        ("{",(
          (("printInt","(",("add","(",((1,",",2),",",3),")"),")"),";"),
          "@",
           ("printFloat","(",("addf","(",((1.1,",",0.1),",",0.2),")"),")")),
        "}")),
        "@",
        (("add",":",(("(",((("a",":","int"),",",("b",":","int")),",",("c",":","int")),")"),"(","int",")")),
        "=",
        ("{",("return",(("a","+","b"),"+","c")),"}"))),
        "@",
        (("addf",":",(("(",((("a",":","float"),",",("b",":","float")),",",("c",":","float")),")"),"(","float",")")),
        "=",
        ("{",("return",(("a",".+","b"),".+","c")),"}")))


    val ast = st2ast(st)
    println("ast="+ast)
    val s = setmem(ast)
    val e = kNormal(s)
    val m = memAlloc(e)
    emit("e.s", m)
    exec("gcc -m64 -o e e.s lib.c") match {
      case 0 => exec("./e")
      case _ =>
    }
  }

  def apply(st:Any):List[Any] = st match {
    case (a,"@",b) => apply(a):::apply(b)
    case a => List(f(a))
  }

  def f(fn:Any):Any = fn match {
    case ((n,":",(("(",a,")"),"(",t,")")),"=",b) => ("_"+n, params(a):::List(t), bodys(b))
  }

  def params(e:Any):List[Any] = e match {
    case (a,",",b) => params(a):::params(b)
    case (a,":",b) => List((a,b))
    case "void" => List(("void","void"))
  }
  def fargs(e:Any):List[Any] = e match {
    case (a,",",b) => fargs(a):::fargs(b)
    case a => List(exp(a))
  }

  def exp(e:Any):Any = e match {
    case ("{",b,"}") => bodys(b)
    case ("(",b,")") => exp(b)
    case (a,"(",b,")") => ("call","_"+a,fargs(b))
    case (a,"=",b) => ("mov", exp(b), exp(a))
    case (a,"+",b) => ("add",exp(a), exp(b))
    case (a,".+",b) => ("addf",exp(a), exp(b))
    case ("return", a) => ("ret", exp(a))
    case (a,";") => exp(a)
    case a:Int => a
    case a:String => a
    case a:Float => a
  }
  def bodys(e:Any):List[Any] = e match {
    case (a,"@",b) => bodys(a):::bodys(b)
    case a =>
      exp(a) match {
        case e:List[Any] => e
        case a => List(a)
      }
  }
}
