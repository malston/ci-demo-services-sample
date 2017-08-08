package com.pivotalservices.sample.web

import com.pivotalservices.sample.domain.Post
import com.pivotalservices.sample.repository.PostRepository
import com.pivotalservices.sample.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@WebMvcTest(PostController::class)
class PostControllerTests {
    
    @MockBean
    lateinit var postRepository: PostRepository

    @MockBean
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var mockMvc: MockMvc
    
    @Test
    fun testPostCalls() {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/posts").param("page", "0").param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)   
    }
    
    @Before
    fun setup() {
        given(postRepository.findAll(Mockito.any(Pageable::class.java)))
                .willAnswer({invocation ->  
                    val pageable = invocation.getArgumentAt(0, Pageable::class.java)
                    PageImpl(
                            listOf(Post("title1", "content1")), pageable, 10)
                })
    }
    
    @TestConfiguration
    class PostConfig {
        @Bean
        fun postResourcesAssembler(): PostResourceAssembler {
            return PostResourceAssembler()
        }
    }
}