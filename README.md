# 型付きコンパイラを作ろう

## 1. はじめに

コンパイラの作成は、昔は、１パスの高速なコンパイラが主流でした。
しかし、現在では、関数型言語のパターンマッチを用いたコンパイラ作りが主流となりつつあります。
そこで、関数型言語の機能を持ったScalaを使ってコンパイラを作ります。
ワンパスなコンパイラに比べると重い作りになっていますが、
複数パスで作る事で分かりやすく最適化も考慮に入れた作りとなっています。
前回作った言語はint型しかサポートしていませんでした。
今回は char, short, int, long, float, double の複数の型を持った言語を作成します。
まだ、配列や構造体がないので実用言語とは言えないかも知れませんが、
暗黙の型変換、型推論、型付きのコンパイラ作成を学ぶ事が出来ます。


この文書はmarkdownで記述しました。chromeのアプリケーションを使う事で、
リアルタイムに表示しながら気軽に記述することが出来ました。

## 2. アセンブラを複数の型に対応する

### 2.0. サイズ

### 2.1. 足し算

  // test_type.c
	
	char char_add(char a, char b) {
		char r = a + b;
		return r;
	}


以上のような関数を作って

	gcc -S test_add.c 

としてコンパイルし、整形してみます。

	; test_add.S
	movb
	movb

### 2.2. 引き算
### 2.3. かけ算
### 2.4. 割り算
### 2.5. 余り
### 2.6. キャスト
### 2.7. 即値

そろそろテストをしたいのですが、現状の機能ではテストが出来ません。
テストをするには、即値をコンパイルできないといけません。
また、結果を出力出来ると良いです。

そこで、2.7では即値のコンパイルを 2.8ではprintを出来るようにします。


### 2.7. print
そこから、計算して、表示する。

### 2.8. テスト

さて、ずいぶん沢山実装したので、間違いがあるかも知れません。
そこで、しっかりとテストをしましょう。


#### 問題

##### 型をどう持つか？

どうしよう？

##### キャストをどう表現するか？

こまるよなぁ

* TyCon("cast",List(TyCon("Char",List()), TyCon("Short",List()))
* Cast(Char(3),TShort())

例えば、こんな感じ


## 3. プリミティブな型のキャスト

* Char2Short
* Char2Int
* Char2Long
* Char2Float
* Char2Double

* Short2Char
* Short2Int
* Short2Long
* Short2Float
* Short2Double

* Int2Char
* Int2Short
* Int2Long
* Int2Float
* Int2Double

* Long2Char
* Long2Short
* Long2Int
* Long2Float
* Long2Double

* Float2Char
* Float2Short
* Float2Int
* Float2Long
* Float2Double

* Double2Char
* Double2Short
* Double2Int
* Double2Long
* Double2Float


そうすると、以上のコードが得られます。
このようにして、コンパイラ結果を取得して、Scalaのコードに落として行きます。

## 3. 暗黙の型変換


型の中に式があるのではなくて、型と式のタプルに修正しました。

こんな感じで、式に対して、castを必要な箇所に挿入できるようになります。

これを、関数のある言語について拡張していければいいんでしょうが、

その為には環境を持ってうんぬんしないといけません。

関数まで出来たら、型推論を入れられると非常に嬉しいのですが、まだまだ先です。

型と式と式に型を付ける関数とcastを付ける関数で暗黙の型変換ができるということのようです。

	object ttest {
	  trait TType
	  case class TFloat() extends TType
	  case class TInt() extends TType
	  case class TNone() extends TType
	
	  trait T
	  case class CastInt(a:T) extends T
	  case class CastFloat(a:T) extends T
	  case class Int(a:scala.Int) extends T
	  case class Float(a:Double) extends T
	  case class Add(a:T, b:T) extends T
	  case class FAdd(a:T, b:T) extends T
	
	  def main(argv:Array[String]) {
	    println(t(Add(Float(1),Int(2))))
	    println(t(Add(Int(1),Int(2))))
	    println(t(Add(Add(Float(1),Int(1)),Int(2))))
	  }
	
	  def t(a:T):(TType,T) = a match {
	    case Int(_) => (TInt(),a)
	    case Float(_) => (TFloat(),a)
	    case CastInt(_) => (TInt(),a)
	    case CastFloat(_) => (TFloat(),a)
	    case Add(a, b) =>
	      ic(t(a), t(b)) match {
	        case (TInt(), a, b) => (TInt(),Add(a,b))
	        case (TFloat(), a, b) => (TFloat(),FAdd(a,b))
	      }
	    case FAdd(a,b) => t(Add(a,b))
	  }
	
	  def ic(a:(TType,T), b:(TType,T)):(TType,T,T) = (a,b) match {
	    case ((TFloat(),a), (TFloat(),b)) => (TFloat(),a,b)
	    case ((TFloat(),a), (_,b)) => (TFloat(),a,CastFloat(b))
	    case ((_,a), (TFloat(),b)) => (TFloat(),CastFloat(a), b)
	    case ((TInt(),a), (TInt(),b)) => (TInt(),a,b)
	    case ((TInt(),a), (_,b)) => (TInt(),a,CastInt(b))
	    case ((_,a), (TInt(),b)) => (TInt(),CastInt(a),b)
	  }
	}



## 3. 構文木を複数の型に対応する


## 4. 型推論

型推論の話は長くなるけどまぁ、しかたない。書く。

