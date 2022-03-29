package patmat

class HuffmanSuite extends munit.FunSuite :

  import Huffman.*

  trait TestTrees {
    val t1: Fork = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2: Fork = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }


  test("weight of a larger tree (10pts)") {
    new TestTrees :
      assertEquals(weight(t1), 5)
  }


  test("chars of a larger tree (10pts)") {
    new TestTrees :
      assertEquals(chars(t2), List('a', 'b', 'd'))
  }

  test("string2chars hello world") {
    assertEquals(string2Chars("hello, world"), List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("make ordered leaf list for some frequency table (15pts)") {
    assertEquals(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))), List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }


  test("combine of some leaf list (15pts)") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(combine(leaflist), List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }


  test("decode and encode a very short text should be identity (10pts)") {
    new TestTrees :
      assertEquals(decode(t1, encode(t1)("ab".toList)), "ab".toList)
  }

  test("decodeSecret") {
    assertEquals(decodedSecret.mkString, "huffmanestcool")
  }

  test("singleton empty") {
    assertEquals(singleton(List()), false)
  }

  test("singleton 1 elem") {
    assertEquals(singleton(List(Leaf('a', 2))), true)
  }

  test("singleton more than 1 elem") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assertEquals(singleton(leaflist), false)
  }

  test("combine no elem") {
    val initList = List()
    assertEquals(combine(initList), initList)
  }

  test("combine 1 elem") {
    val initList = List(Leaf('a', 2))
    assertEquals(combine(initList), initList)
  }

  test("combine many elem") {
    val initList = List(Leaf('a', 2), Leaf('b', 3), Leaf('d', 4))
    val expected1 = List(Leaf('d', 4), Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5))
    assertEquals(combine(initList), expected1)
  }

  test("until empty") {
    val f1: List[CodeTree] => Boolean = _.isEmpty
    val f2: List[CodeTree] => List[CodeTree] = _.tail
    assertEquals(until(f1, f2)(List()), List())
  }

  test("until non empty") {
    val initList = List(Leaf('a', 2), Leaf('b', 3))
    val expected = List(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5))
    assertEquals(until(singleton, combine)(initList), expected)
  }


  import scala.concurrent.duration.*

  override val munitTimeout: Duration = 10.seconds
