package com.pivotalservices.sample.batch

import com.pivotalservices.sample.domain.Post
import com.pivotalservices.sample.domain.User
import com.pivotalservices.sample.repository.CommentRepository
import com.pivotalservices.sample.repository.PostRepository
import com.pivotalservices.sample.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataPopulator(
        private val userRepository: UserRepository,
        private val postRepository: PostRepository,
        private val commentRepository: CommentRepository

):CommandLineRunner {
    override fun run(vararg args: String?) {
        val user1 = userRepository.save(User(fullName = "User 1", password = "pwd1", email = "user1@gmail.com"))

        val p1 = Post("sometitle", "somecontent")
        p1.user = user1
        postRepository.save(p1)
    }
}