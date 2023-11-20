package org.java.license.services;

import com.mysql.cj.util.StringUtils;
import org.java.license.config.ServiceConfig;
import org.java.license.model.License;
import org.java.license.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;

    public License getLicense(String licenseId, String organizationId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(licenseId, organizationId);
        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(
                            messages.getMessage("license.search.error.message", null, null),
                            licenseId,
                            organizationId
                    )
            );
        }

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {

        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license, String organizationId) {
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId, String organizationId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(
                messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
