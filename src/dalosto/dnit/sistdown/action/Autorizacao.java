package dalosto.dnit.sistdown.action;
import dalosto.dnit.sistdown.framework.annotations.Autowired;
import dalosto.dnit.sistdown.framework.annotations.Component;
import dalosto.dnit.sistdown.framework.annotations.Order;
import dalosto.dnit.sistdown.service.RequestHTTP;
import dalosto.dnit.sistdown.service.Util;


/**
 * Faz a autenticacao Inicial com o servidor
 */
@Component
@Order(2)
public class Autorizacao implements Acao {

    @Autowired
    private RequestHTTP requestTokenService;


    public void executa() throws Exception {
        if (Util.verificaSeEhAPrimeiraVezRodandoOPrograma()) {
            String token = requestTokenService.obtemTokenValidacao();
            if (!isAuthtenticated(token)) {
                throw new RuntimeException(" Servidor não autenticado");
            }
        }
    }


    private boolean isAuthtenticated(String auth) {
        String pass = "This is a download manager used to solve some of the internal network problems at DNIT";
        if (auth.contains(pass))
            return true;
        return false;
    }

}
