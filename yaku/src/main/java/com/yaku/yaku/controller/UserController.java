package com.yaku.yaku.controller;

import com.yaku.yaku.model.Person;
import com.yaku.yaku.service.CartService;
import com.yaku.yaku.service.PersonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final PersonService personService;
    private final CartService cartService;

    public UserController(PersonService personService, CartService cartService) {
        this.personService = personService;
        this.cartService = cartService;
    }

    @PostMapping("/set-email")
    public String setEmail(@RequestParam String email, HttpSession session, Model model) {
        session.setAttribute("userEmail", email); // Stocke l'email en session
        Person person = new Person();
        person.setEmail(email);
        personService.savePerson(person);
        model.addAttribute("userEmail", email);
        cartService.addCart(email);
        return "redirect:/main"; // Redirige vers la page principale
    }
}

