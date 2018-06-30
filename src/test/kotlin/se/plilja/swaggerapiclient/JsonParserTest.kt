package se.plilja.swaggerapiclient

import org.junit.Test
import kotlin.test.assertEquals


class JsonParserTest {
    val jsonParser = JsonParser.newInstance();

    @Test
    fun empty() {
        val r = jsonParser.parse("{}");
        assertEquals(emptyMap(), r)
    }

    @Test
    fun simple() {
        val r = jsonParser.parse("""{"foo":"bar"}""");
        assertEquals(mapOf(Pair("foo", "bar")), r)
    }

    @Test
    fun array() {
        val r = jsonParser.parse("""["foo", "bar"]""");
        assertEquals(mapOf(Pair("0", "foo"), Pair("1", "bar")), r)
    }

    @Test
    fun nested() {
        val r = jsonParser.parse("""
            {
                "foo":{"bar":"baz"}
            }
            """)

        assertEquals(mapOf(Pair("foo", mapOf(Pair("bar", "baz")))), r)
    }
}