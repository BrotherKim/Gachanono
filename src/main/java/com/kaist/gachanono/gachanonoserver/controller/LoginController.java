package com.kaist.gachanono.gachanonoserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class LoginController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    
    @SuppressWarnings("unchecked")
    @GetMapping("/login") 
    public String login(Model model) throws Exception {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->{
                oauth2AuthenticationUrls.put(
                    registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getRegistrationId()
                    );
        });
        model.addAttribute("urls", oauth2AuthenticationUrls);
 
        return "login"; 
    }

    @GetMapping("/logout") 
    public String logout(Model model) throws Exception {
        model.addAttribute("user", null);
        return "logout"; 
    }

}
