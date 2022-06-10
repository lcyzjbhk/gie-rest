package cn.whjg.taotao.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    @GetMapping("/{name}")
    public String topage(@PathVariable String name){
        return name;
    }
}
