package local.verifopposition.endpoint;

import local.verifopposition.entity.Opposition;
import local.verifopposition.repository.OppositionRepository;
import local.verifopposition.wsdl.VerifOppositionRequest;
import local.verifopposition.wsdl.VerifOppositionResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class VerifOppositionEndpoint {

    private static final String NAMESPACE_URI = "http://local.verifopposition";

    private final OppositionRepository oppositionRepository;

    public VerifOppositionEndpoint(OppositionRepository oppositionRepository) {
        this.oppositionRepository = oppositionRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "VerifOppositionRequest")
    @ResponsePayload
    public VerifOppositionResponse handleRequest(@RequestPayload VerifOppositionRequest request) {
        VerifOppositionResponse response = new VerifOppositionResponse();
        String numImmat = request.getNumeroImmatriculation() == null
                ? "" : request.getNumeroImmatriculation().trim();

        Optional<Opposition> oppositionOpt = oppositionRepository.findByNumeroImmatriculation(numImmat);

        if (oppositionOpt.isPresent()) {
            Opposition opposition = oppositionOpt.get();
            response.setOpposition(opposition.isEnOpposition());
            response.setMessage(opposition.getMessage());
        } else {
            response.setOpposition(false);
            response.setMessage("Véhicule non trouvé. Aucune opposition détectée.");
        }
        return response;
    }
}
