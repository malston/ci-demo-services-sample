package com.pivotalservices.sample.web

import com.pivotalservices.sample.domain.Post
import org.springframework.hateoas.Resource
import org.springframework.hateoas.ResourceAssembler
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.stereotype.Component

@Component
class PostResourceAssembler: ResourceAssembler<Post, Resource<Post>> {
    override fun toResource(entity: Post): Resource<Post> {
        val selflink = ControllerLinkBuilder.linkTo(PostController::class.java).slash(entity.id).withSelfRel();
        return Resource(entity, selflink)
    }
}