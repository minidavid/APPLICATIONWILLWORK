package com.example.applicationwillwork

import org.assertj.core.api.Assertions
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test

class LoginUtilTest{
    @Ignore
    @Test
    fun `empty username returns false`(){
        var result = RegisterUtil.validateRegisterInput(
            email = "",
            password = "123456",
            confirmedPassword = "false",
        )
        Assertions.assertThat(result).isFalse
    }




}