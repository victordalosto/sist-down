package dalosto.dnit.sistdown.action;

import java.util.List;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.service.RequestHTTP;

@Component
public class Teste {

    @Autowired
    List<Acao> listAcao;

    @Autowired
    RequestHTTP requestHTTP;


    public void run() {
        System.out.println(listAcao);
        System.out.println(requestHTTP);
    }
    
}
