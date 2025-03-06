package org.oauth2client2.controllers;

import lombok.RequiredArgsConstructor;
import org.oauth2client2.dtos.Author;
import org.oauth2client2.services.AuthorRestService;
import org.oauth2client2.services.CheckConsentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRestService authorService;
    private final CheckConsentService checkConsentService;

    @GetMapping
    public String getAuthors(Model model) {
        if (checkConsentService.isConsentRemoved()) {
            return "redirect:/my-logout?consentRemoved=true";
        }
        model.addAttribute("authors", authorService.getAuthors());
        return "authors-list";
    }

    @GetMapping("/{id}")
    public String getAuthorById(Model model, @PathVariable Long id) {
        if (checkConsentService.isConsentRemoved()) {
            return "redirect:/my-logout?consentRemoved=true";
        }
        model.addAttribute("author", authorService.getAuthorById(id));
        return "author-view";
    }

    @GetMapping("/new")
    public String openAuthorCreationForm(@ModelAttribute("author") Author author) {
        if (checkConsentService.isConsentRemoved()) {
            return "redirect:/my-logout?consentRemoved=true";
        }
        return "author-create";
    }

    @PostMapping
    public String createAuthor(Author author) {
        if (checkConsentService.isConsentRemoved()) {
            return "redirect:/my-logout?consentRemoved=true";
        }
        authorService.createAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Long id) {
        if (checkConsentService.isConsentRemoved()) {
            return "redirect:/my-logout?consentRemoved=true";
        }
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

}
