package org.oauth2client120250105.services;

import lombok.RequiredArgsConstructor;
import org.oauth2client120250105.dtos.Author;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorRestService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MyTokenService tokenService;

    public List<Author> getAuthors() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getAccessToken());

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Author[]> response =
                restTemplate.exchange(
                        "http://localhost:8081/authors",
                        HttpMethod.GET,
                        entity,
                        Author[].class
                );
        if (response.getStatusCode().is2xxSuccessful()) {
            Author[] authors = response.getBody();
            if (authors != null) return Arrays.asList(authors);
        }
        return List.of();
    }

    public Author getAuthorById(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getAccessToken());

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Author> response =
                restTemplate.exchange(
                        "http://localhost:8081/authors/" + id,
                        HttpMethod.GET,
                        entity,
                        Author.class
                );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    public void createAuthor(Author author) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getAccessToken());

        HttpEntity<Author> entity = new HttpEntity<>(author, headers);

        restTemplate.exchange(
                "http://localhost:8081/authors",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }

    public void deleteAuthor(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenService.getAccessToken());

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                "http://localhost:8081/authors/" + id,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }
}
