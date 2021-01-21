package com.bnpparibas.itg.mylibraries.libraries.exposition;

import io.swagger.annotations.Authorization;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SecuredController {


    @RequestMapping(method = RequestMethod.GET, path = {"/user"})
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public String getHelloUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return ("Hello world mister "+ name + " with roles : " + authorities );
    }

    @RequestMapping(method = RequestMethod.GET, path = {"/admin"})
    @Secured("ROLE_ADMIN")
    public String getHelloAdmin(){
        return ("Hello world mister admin" );
    }
}
