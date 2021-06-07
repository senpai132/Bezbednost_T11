package com.example.bolnicaServer.service;

import com.example.bolnicaServer.config.RestTemplateConfiguration;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.*;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.example.bolnicaServer.dto.request.CertificateSignRequestDTO;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.DatatypeConverter;

@Service
public class CertificateSignService {
    private String requestUrl = "https://localhost:8080/api/certificate-sign-request";


    @Autowired
    private RestTemplateConfiguration restTemplateConfiguration;
    public void sendRequest(CertificateSignRequestDTO csrDTO) throws IOException, OperatorCreationException {
        KeyPair pair = generateKeyPair();

        HashMap<String, String> subjectData = new HashMap<>();

        subjectData.put("CN", csrDTO.getCommonName());
        subjectData.put("SURNAME", csrDTO.getLastName());
        subjectData.put("GIVENNAME", csrDTO.getFirstName());
        subjectData.put("O", csrDTO.getOrganization());
        subjectData.put("OU", csrDTO.getOrganizationUnit());
        subjectData.put("C", csrDTO.getCountry());
        subjectData.put("emailAddress", csrDTO.getEmail());
        subjectData.put("L", csrDTO.getLocality());
        subjectData.put("serialNumber", csrDTO.getSerialNumber());

        String mapAsString = subjectData.keySet().stream()
                .filter(key -> subjectData.get(key) != null && !subjectData.get(key).isEmpty())
                .map(key -> key + "=" + subjectData.get(key))
                .collect(Collectors.joining(","));


        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                new X500Principal(mapAsString), pair.getPublic());
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(pair.getPrivate());
        /*GeneralName[] subjectAltNames = new GeneralName[]{
                new GeneralName(GeneralName.dNSName, "localhost")
        };

        Extension[] extensions = new Extension[] {
                Extension.create(Extension.subjectAlternativeName, true, new GeneralNames(subjectAltNames))
        };

        p10Builder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, new Extensions(extensions));
        */
        PKCS10CertificationRequest csr = p10Builder.build(signer);


        StringBuilder builder = new StringBuilder();

        builder.append("-----BEGIN CERTIFICATE REQUEST-----\n");
        builder.append(DatatypeConverter.printBase64Binary(csr.getEncoded()));
        builder.append("\n-----END CERTIFICATE REQUEST-----");

        RestTemplate restTemplate = restTemplateConfiguration.getRestTemplate();//new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(builder.toString());

        try {
            HttpStatus httpStatus = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.POST,
                    request,
                    String.class).getStatusCode();
        } catch (HttpClientErrorException exception) {
            exception.printStackTrace();
            //throw new InvalidAPIResponse("Invalid API response.");
        }
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

}
