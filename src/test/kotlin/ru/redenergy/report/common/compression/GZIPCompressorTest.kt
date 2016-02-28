package ru.redenergy.report.common.compression

import org.junit.Assert.*
import org.junit.Test

class GZIPCompressorTest {

    @Test
    fun testGzipCompressor(){
        val text = "Don't think about a zebra"

        val compressed = GZIPCompressor.compress(text)
        val decompressed = GZIPCompressor.decompress(compressed)

        assertEquals(text, decompressed)
    }
}