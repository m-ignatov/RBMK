package bg.unisofia.fmi.rbmk.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DefaultController.API_VERSION)
public class DefaultController {

    static final String API_VERSION = "/v1";

    @GetMapping("/version")
    public String getApiVersion() {
        return API_VERSION;
    }
}