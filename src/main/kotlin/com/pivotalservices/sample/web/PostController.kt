package com.pivotalservices.sample.web

import com.pivotalservices.sample.domain.Post
import com.pivotalservices.sample.repository.PostRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
        private val postRepository: PostRepository,
        private val postResourceAssembler: PostResourceAssembler
) {

    @GetMapping
    fun getPosts(pageable: Pageable, pagedResourcesAssembler: PagedResourcesAssembler<Post>): PagedResources<Resource<Post>> {
        val posts = this.postRepository.findAll(pageable)
        return pagedResourcesAssembler.toResource(posts, postResourceAssembler)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable("id") id: Long): HttpEntity<Resource<Post>> {
        val post = this.postRepository.findOne(id);

        if (post != null) {
            return ResponseEntity(postResourceAssembler.toResource(post), HttpStatus.OK)
        }
        
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}