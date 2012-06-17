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


#### プリミティブな型のキャスト

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

implicitでどうするってはなし。



## 3. 構文木を複数の型に対応する


## 4. 型推論

型推論の話は長くなるけどまぁ、しかたない。書く。

