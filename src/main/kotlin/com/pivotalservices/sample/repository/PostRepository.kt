package com.pivotalservices.sample.repository

import com.pivotalservices.sample.domain.Post
import com.pivotalservices.sample.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface PostRepository: PagingAndSortingRepository<Post, Long> {
    fun findByUser(user: User, pageable: Pageable): Page<Post>
}