package org.oauth2client2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CheckConsentService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MyTokenService tokenService;

    public Boolean isConsentRemoved() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String principalName = authentication.getName();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(tokenService.getAccessToken());

            HttpEntity<String> entity = new HttpEntity<>(principalName, headers);

            ResponseEntity<Boolean> response =
                    restTemplate.exchange(
                            "http://authserver:9000/oauth2/check-consent",
                            HttpMethod.POST,
                            entity,
                            Boolean.class
                    );

            /*HttpEntity<Object> entity = new HttpEntity<>(headers);

            ResponseEntity<Boolean> response =
                    restTemplate.exchange(
                            "http://authserver:9000/oauth2/check-consent?principalName=" + principalName,
                            HttpMethod.GET,
                            entity,
                            Boolean.class
                    );*/

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        }

        return false;
    }

}
