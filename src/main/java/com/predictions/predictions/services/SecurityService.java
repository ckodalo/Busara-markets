package com.predictions.predictions.services;

import com.predictions.predictions.models.Security;
import com.predictions.predictions.repositories.SecurityRepository;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    public SecurityService (SecurityRepository securityRepository) {

        this.securityRepository = securityRepository;
    }


    public void saveSecurity(Security security) {

        securityRepository.save(security);
    }

}
