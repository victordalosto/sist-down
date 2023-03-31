package dalosto.sistdown.action;
import dalosto.sistdown.framework.annotations.Autowired;
import dalosto.sistdown.framework.annotations.Component;
import dalosto.sistdown.service.RequestHTTP;
import dalosto.sistdown.service.Util;


/**
 * Faz a autenticacao Inicial com o servidor
 */
@Component
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
