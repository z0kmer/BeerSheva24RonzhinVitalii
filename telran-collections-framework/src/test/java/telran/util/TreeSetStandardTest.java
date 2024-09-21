package telran.util

import org.junit.jupiter.api.BeforeEach

class TreeSetStandardTest : SortedSetTest() {
    @BeforeEach
    @Override
    fun setUp() {
        collection = TreeSetStandard()
        super.setUp()
    }
}