package local.verifopposition.client;

import local.verifopposition.wsdl.VerifOppositionRequest;
import local.verifopposition.wsdl.VerifOppositionResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Client SOAP autonome basé sur WebServiceTemplate (Spring-WS).
 * Usage : mvn exec:java ... ou lancer main() depuis l'IDE.
 * Arguments : [url du service] [numéro d'immatriculation]
 */
public class VerifOppositionClient {

    public static void main(String[] args) throws Exception {
        String url = args.length > 0 ? args[0] : "http://localhost:8080/ws";
        String immat = args.length > 1 ? args[1] : "1234-AB-5";

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("local.verifopposition.wsdl");
        marshaller.afterPropertiesSet();

        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        template.setDefaultUri(url);

        VerifOppositionRequest request = new VerifOppositionRequest();
        request.setNumeroImmatriculation(immat);

        VerifOppositionResponse response =
                (VerifOppositionResponse) template.marshalSendAndReceive(request);

        System.out.println("Immatriculation : " + immat);
        System.out.println("Opposition      : " + response.isOpposition());
        System.out.println("Message         : " + response.getMessage());
    }
}
