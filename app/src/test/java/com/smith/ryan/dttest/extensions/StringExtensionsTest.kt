package com.smith.ryan.dttest.extensions

import com.smith.ryan.dttest.feature.core.extensions.doesNotContain
import com.smith.ryan.dttest.feature.core.extensions.removeAllAmpersands
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StringExtensionsTest {

    @Test
    fun checkAmpersandRemoval() {
        assert("test-string&amp;isTest=true&amp;stillTesting=true&amp;oneMoreTime=true".removeAllAmpersands()
            .doesNotContain("amp;"))
    }
}