package dalosto.sistdown.action;

import java.util.List;
import dalosto.sistdown.framework.annotations.Autowired;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.service.RequestHTTP;

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
