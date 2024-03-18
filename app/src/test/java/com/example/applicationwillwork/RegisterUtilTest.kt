package com.example.applicationwillwork

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat // Import assertThat from AssertJ

class RegisterUtilTest{

    @Test
    fun `empty username returns false`(){
        var result = RegisterUtil.validateRegisterInput(
            email = "",
            password = "123456",
            confirmedPassword = "123456"
        )
        assertThat(result).isFalse
    }

    @Test
    fun `empty password returns false`(){
        var result = RegisterUtil.validateRegisterInput(
            email = "brian123@gmail.com",
            password = "",
            confirmedPassword = ""
        )
        assertThat(result).isFalse
    }

    @Test
    fun `password not matching password returns false`(){
        var result = RegisterUtil.validateRegisterInput(
            email = "brian123",
            password = "123",
            confirmedPassword = "123456"
        )
        assertThat(result).isFalse
    }
}