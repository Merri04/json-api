package com.example.demo.DTO;

/**
 *
 @Author Elise Strand Bråtveit
 @Version 29.01.2025
 */
public class TemplateResponseDTO {
    private String templateId;
    private String idToken;
    private String credentialFormat;

    public TemplateResponseDTO(String templateId, String idToken, String credentialFormat) {
        this.templateId = templateId;
        this.idToken = idToken;
        this.credentialFormat = credentialFormat;
    }
    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getCredentialFormat() {
        return credentialFormat;
    }
    public void setCredentialFormat(String credentialFormat) {
        this.credentialFormat = credentialFormat;
    }
}