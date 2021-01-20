package com.bnpparibas.itg.mylibraries.libraries.exposition;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnsecuredController {


    @RequestMapping(method = RequestMethod.GET, path = {"/unsecured/toto"})
    public String getSansAuthent(){
        return ("Hello world");
    }
}
