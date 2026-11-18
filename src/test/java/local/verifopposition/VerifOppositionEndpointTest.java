package local.verifopposition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.util.Map;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

@SpringBootTest
class VerifOppositionEndpointTest {

    private static final Map<String, String> NS = Map.of("v", "http://local.verifopposition");

    @Autowired
    private ApplicationContext context;

    private MockWebServiceClient client;

    @BeforeEach
    void setUp() {
        client = MockWebServiceClient.createClient(context);
    }

    private StringSource requete(String immat) {
        return new StringSource(
                "<v:VerifOppositionRequest xmlns:v=\"http://local.verifopposition\">"
                        + "<v:numeroImmatriculation>" + immat + "</v:numeroImmatriculation>"
                        + "</v:VerifOppositionRequest>");
    }

    @Test
    void vehiculeEnOpposition() {
        client.sendRequest(withPayload(requete("1234-AB-5")))
                .andExpect(noFault())
                .andExpect(xpath("//v:opposition", NS).evaluatesTo("true"))
                .andExpect(xpath("//v:message", NS)
                        .evaluatesTo("Véhicule en opposition judiciaire. Vente impossible."));
    }

    @Test
    void vehiculeSansOpposition() {
        client.sendRequest(withPayload(requete("5678-CD-9")))
                .andExpect(noFault())
                .andExpect(xpath("//v:opposition", NS).evaluatesTo("false"))
                .andExpect(xpath("//v:message", NS).evaluatesTo("Aucune opposition détectée."));
    }

    @Test
    void vehiculeInconnu() {
        client.sendRequest(withPayload(requete("0000-ZZ-0")))
                .andExpect(noFault())
                .andExpect(xpath("//v:opposition", NS).evaluatesTo("false"))
                .andExpect(xpath("//v:message", NS)
                        .evaluatesTo("Véhicule non trouvé. Aucune opposition détectée."));
    }
}
