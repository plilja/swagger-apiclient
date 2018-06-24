package se.plilja.swaggerapiclient

import org.junit.Test
import kotlin.test.assertEquals


class ModelClassTest {
    @Test
    fun test() {
        val m = ModelClass("Foo", "se.plilja.foo")
        m.addField("bar", String::class.java)
        m.addCollectionField("baz", List::class.java, Integer::class.java)
        val exp = """package se.plilja.foo;

import java.util.List;

public final class Foo {
   private String bar;
   private List<Integer> baz;

   public String getBar() {
      return bar;
   }

   public Foo setBar(String name) {
      this.bar = bar;
      return this;
   }

   public List<Integer> getBaz() {
      return baz;
   }

   public Foo setBaz(List<Integer> name) {
      this.baz = baz;
      return this;
   }
}
"""
        assertEquals(exp, m.toJavaCode())

    }

}