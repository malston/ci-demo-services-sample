package com.pivotalservices.sample.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.pivotalservices.sample.domain.User
import com.pivotalservices.sample.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
class UserControllerSliceTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testUsers() {
        this.mockMvc.perform(get("/users").param("page", "0").param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$._embedded.userList[0].fullName").value("one"))

        this.mockMvc
                .perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk)

        this.mockMvc.perform(get("/users/3"))
                .andDo(print())
                .andExpect(status().isNotFound)

        this.mockMvc
                .perform(post("/users")
                        .content(objectMapper.writeValueAsString(User(fullName = "new", password = "one", email = "new@one.com")))
                        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated)

    }

    @Before
    fun setUp(): Unit {
        given(userRepository.findAll(Matchers.any(Pageable::class.java)))
                .willAnswer({ invocation ->
                    val pageable = invocation.arguments[0] as Pageable
                    PageImpl(
                            listOf(
                                    User(id = 1, fullName = "one", password = "one", email = "one@one.com"),
                                    User(id = 2, fullName = "two", password = "two", email = "two@two.com"))
                            , pageable, 10)
                })

        given(userRepository.findOne(1L))
                .willReturn(User(id = 1, fullName = "one", password = "one", email = "one@one.com"))

        given(userRepository.save(Matchers.any(User::class.java)))
                .willAnswer({ invocation ->
                    val user = invocation.getArgumentAt(0, User::class.java)
                    user
                })
    }

    @TestConfiguration
    class SpringConfig {

        @Bean
        fun userResourceAssembler(): UserResourceAssembler {
            return UserResourceAssembler()
        }

        @Bean
        fun userRepository(): UserRepository {
            return mock(UserRepository::class.java)
        }
    }

}