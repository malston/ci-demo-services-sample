package com.pivotalservices.sample.web

import com.pivotalservices.sample.domain.User
import com.pivotalservices.sample.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
        private val userRepository: UserRepository,
        private val userResourceAssembler: UserResourceAssembler) {

    @GetMapping
    fun getUsers(pageable: Pageable, 
                 pagedResourcesAssembler: PagedResourcesAssembler<User>): PagedResources<Resource<User>> {
        val users = userRepository.findAll(pageable)
        return pagedResourcesAssembler.toResource(users, this.userResourceAssembler)
    }
    
    @PostMapping
    fun createUser(@RequestBody user: User): HttpEntity<Resource<User>> {
        val savedUser = userRepository.save(user)
        return ResponseEntity(this.userResourceAssembler.toResource(savedUser), HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") id: Long, @RequestBody user: User): HttpEntity<Resource<User>> {
        val updatedUser = userRepository.save(user)
        
        return ResponseEntity(this.userResourceAssembler.toResource(updatedUser), HttpStatus.ACCEPTED)
    }
    
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): HttpEntity<String> {
        this.userRepository.delete(id)
        
        return ResponseEntity(HttpStatus.ACCEPTED)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long): HttpEntity<Resource<User>> {
        val user = userRepository.findOne(id)
        if (user != null) {
            return ResponseEntity(this.userResourceAssembler.toResource(userRepository.findOne(id)), HttpStatus.OK)
        } 
        
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
    
}