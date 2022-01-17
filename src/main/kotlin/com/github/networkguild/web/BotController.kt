package com.github.networkguild.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/"])
class BotController {

    @GetMapping(value = ["/bot"])
    fun somethingNice(): String = "Just testing..."
}
