package cn.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.naming.Name;

@Controller
public class PageController {
    @GetMapping("/page/{name}")
    public String page(@PathVariable String name){
      return name;
    }
}
