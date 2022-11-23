package sistdown.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *  Classe <b>FrasesPedrao</b> contendo métodos GAG. <p>
 *  A unica funcionalidade dessa classe e apresentar na tela algumas das frases do nosso colega de trabalho, Pedrao. 
 */
public class FrasesPedrao {

    public static List<String> listaFrasesPedrao = new ArrayList<>();

    static {
        listaFrasesPedrao.add("Eu hein sartei de banda.");
        listaFrasesPedrao.add("Ta vendo claudiana sobrou pra mim.");
        listaFrasesPedrao.add("Tem que ser pelo menos uma balzaquiana.");
        listaFrasesPedrao.add("hummm!!!!");
        listaFrasesPedrao.add("Um é pouco dois é bom mas três vezes vocês tomam vergonha na cara e comprem o cafezim de vocês.");
        listaFrasesPedrao.add("ishheee.");
        listaFrasesPedrao.add("Cafezin né se não o bicho pega.");
        listaFrasesPedrao.add("Minha mãe não deixa!");
        listaFrasesPedrao.add("Cês fizeram eu errar.");
        listaFrasesPedrao.add("To trabalhando aqui.");
        listaFrasesPedrao.add("tututu tututu tututu.");
        listaFrasesPedrao.add("SPAM.");
        listaFrasesPedrao.add("De poeta e louco todo mundo tem um pouco.");
        listaFrasesPedrao.add("Pintei meu cabelo só para dar um ar intelectual.");
        listaFrasesPedrao.add("A tabom. Agora é só eu quem faz isso neh...");
        listaFrasesPedrao.add("Oi Tia jane. Desculpa não posso falar agora.");
        listaFrasesPedrao.add("Eu sou o poeta e os artistas são vocês.");
        listaFrasesPedrao.add("Qualquer dia desses vou fazer um cafezin pro ces.");
        listaFrasesPedrao.add("aaaaaaaaaaaaa.");
        listaFrasesPedrao.add("Idade está no coração das pessoas.");
        listaFrasesPedrao.add("Vixi foi um lápis.");
        listaFrasesPedrao.add("Sou viciado 'em queijo.");
        listaFrasesPedrao.add("Eu não sou desse planeta não sei onde deixei minha nave.");
        listaFrasesPedrao.add("Eu gosto muito de trabalhar gente.");
        listaFrasesPedrao.add("Vocês estão passando preguiça p mim.");
        listaFrasesPedrao.add("Só um pititico de café que eu tomo.");
        listaFrasesPedrao.add("Lá todo marmore é carrara todo tapete é persa e todo lustre é da boemia.");
        listaFrasesPedrao.add("Grande hotel Barrera de Araxá é o maior hotel da america latina.");
        listaFrasesPedrao.add("15 min de banho de lama.. depois voce entra numa banheira mais 15 min com agua sulfurosa.... depois toma uma ducha com agua sulfurosa... depois vai pro colchão. quinze quinze quinze.");
        listaFrasesPedrao.add("As vezes a melhor resposta é o silencio.");
        listaFrasesPedrao.add("E promoção? ou é para mocinha?");
        listaFrasesPedrao.add("Meu advogado ta lá em cima.");
        listaFrasesPedrao.add("O amor é uma faca FATAL de dois gumes. O amor é doloroso mas mais doloroso ainda é não amar.");
        listaFrasesPedrao.add("Minha diabete é emocional depois vou contar pro cês um caso sério da caixa economica.");
        listaFrasesPedrao.add("O saúde está no solado dos pés.");
        listaFrasesPedrao.add("A idade está no coração das pessoas.");
        listaFrasesPedrao.add("Tem gente que tem vício de pinga o meu é café.");
        listaFrasesPedrao.add("Toda vez que vou visitar minha mãe eu levo uma pacote de cigarro pra ela.");
        listaFrasesPedrao.add("Brasília é uma coisa de louco.");
        listaFrasesPedrao.add("Sabe qual é a maior tristeza de uma arvore? É ver que o cabo do machado que a corta é feita de madeira.");
        listaFrasesPedrao.add("Ta vendo como são as coisas.");
        listaFrasesPedrao.add("Tem muitozano que faço isso.");
        listaFrasesPedrao.add("Uai tá tudo aceso!");
        listaFrasesPedrao.add("Cada um tem que agir por instinto.");
        listaFrasesPedrao.add("Mãe é coisa sagrada tem que aproveitar em quanto tem.");
        listaFrasesPedrao.add("Cês tão bom de serviço.");
        listaFrasesPedrao.add("Agora cê falou certinho.");
        listaFrasesPedrao.add("Qualquer dia desses vou fazer um cafezinho pra vocês.");
        listaFrasesPedrao.add("Vou até tomar um cafezinho.");
        listaFrasesPedrao.add("hoje andei pouquinho caminhei só uns 40 min... depois fui la no Dona de Casa.");
        listaFrasesPedrao.add("Essa blusa aqui é por causa do papai noel.");
        listaFrasesPedrao.add("Já viram que os prédios aqui em brasilia são tudo baixinho? uma maravilha!!!");
        listaFrasesPedrao.add("Sem comentários.");
        listaFrasesPedrao.add("Com comentarios: Uma folha nada mais é que um simples papel até que alguem escreva algo nela. Assim como um coração nada mais é que um simples vazio até que alguém o preencha.");
        listaFrasesPedrao.add("Voces ja viram o coração que tem na arvore aqui no DNIT? Coisa mais linda.");
        listaFrasesPedrao.add("Ziraldo tem 3 irmãos. Um é 100 por cento negro o outro é 100 por cento branco e o outro o ziraldo é indiano.");
        listaFrasesPedrao.add("BREKA TREM. Oh Victor olha aqui não breka.");
        listaFrasesPedrao.add("Se tiver a Jordana de Biquini até eu vou trabalhar com oces.");
        listaFrasesPedrao.add("Quando eu to muito na dúvida assim eu coloco CBUQ.");
        listaFrasesPedrao.add("Se a mulher tiver até uns 32 anos ta valendo.");
        listaFrasesPedrao.add("Esse restauranteziho aqui é muito ruim. É ruim de estacionar da não. Só tó aqui por causa de voces.");
        listaFrasesPedrao.add("O gado daqui é mais bonito.");
        listaFrasesPedrao.add("Minha mãe falava pra mim: Pedro você é a Lata do Fabio Jr.");
        listaFrasesPedrao.add("Meu signo é leão. UAAAAAAAAAA.");
        listaFrasesPedrao.add("Eu já vim almoçar aqui com a Claudiana. É encostadinho ali com o shopping?");
        listaFrasesPedrao.add("5 cidades de Minas: Beraba, Berlandia,bia, Belzonte, e a Bosta de araguari.");
        listaFrasesPedrao.add("Preciso fazer esse trecho? Olha aqui? Nunca vi um trem desses.");
        listaFrasesPedrao.add("Aaaaah. Isso é complicado.");
        listaFrasesPedrao.add("Isso não me pega mais não.");
        listaFrasesPedrao.add("Hein?");
        listaFrasesPedrao.add("Deu um tombo no telhado?");
        listaFrasesPedrao.add("Ces não querem ir na Dona Cecília comer um trenzim não?");
        listaFrasesPedrao.add("A gente podia marcar de ir na roda gigante pra gente gritar la de cima..... Aaaaaaaa.");
        listaFrasesPedrao.add("Temos que andar de lancha dar uma volta pelo lago.. vamos juntar e organizar isso aí.");
        listaFrasesPedrao.add("Ziralzi gostava muito de ir no pães e vinho ali no Sudoeste.");
        listaFrasesPedrao.add("O Ronaldo meu barbeiro sempre falava... Nunca vi alguém com tanto cabelo.");
        listaFrasesPedrao.add("Tenho 100% de certeza.");
        listaFrasesPedrao.add("Cês desculpa a vergonha que eu passei.");
        listaFrasesPedrao.add("Melhor que arroz doce em pagode.");
        listaFrasesPedrao.add("Isso ta mais pra jacaré que Tarzan.");
        listaFrasesPedrao.add("Águas passadas não movem moinhos.");
        listaFrasesPedrao.add("Vc sabem qual é a fruta mais limpa do mundo? A banana cê tira a casca e tá limpinha.");
        listaFrasesPedrao.add("Quando eu nasci não tinha talco mamãe passou açucar em mim por isso hjtenho diabete.");
        listaFrasesPedrao.add("Restaurante bom e aquele que a gnt se serve.");
        listaFrasesPedrao.add("Tudo tem seu tempo.");
        listaFrasesPedrao.add("A cada 10 anos a gente é uma nova pessoa.");
        listaFrasesPedrao.add("Ai você me apertou.");
        listaFrasesPedrao.add("Que que é isso.");
        listaFrasesPedrao.add("Eu não assito Globo.");
        listaFrasesPedrao.add("Quero ir na roda Gigante para fazer uhullll la em cima.");
        listaFrasesPedrao.add("Estão p consertando a caixa d'água do prédio.");
        listaFrasesPedrao.add("Qual é seu e-mail? vou te mandar um poema.");
        listaFrasesPedrao.add("Eu não troco água para vcs irem treinando.");
        listaFrasesPedrao.add("Emburrei.");
        listaFrasesPedrao.add("Fiquei Mudo.");
        listaFrasesPedrao.add("Poem lingua pra ele.");
        listaFrasesPedrao.add("Tinha um trecho de ontem aí terminei hoje!");
        listaFrasesPedrao.add("Queijo bom é da Serra de salitro minas gerais.");
        listaFrasesPedrao.add("Eu tenho muitos defeitos mas esse eu não tenho não chefe.");
        listaFrasesPedrao.add("Se Deus quiser no fim do ano eu vou virar imortal.");
        listaFrasesPedrao.add("Espera só eu lavar minha caneca.");
        listaFrasesPedrao.add("Gente é so eu que to com sono?");
        listaFrasesPedrao.add("Fui campeão mineiro de tiro com arma de fogo.");
        listaFrasesPedrao.add("Onde eu fui amarrar a minha egua.");
        listaFrasesPedrao.add("O Banqueiro é um agiota permitido pela LEI.");
        listaFrasesPedrao.add("Quem é essa marmota?");
        listaFrasesPedrao.add("AAAii meu Deus do ceu.");
        listaFrasesPedrao.add("O negocio vai ser pesado chefe.");
        listaFrasesPedrao.add("Vou levar vocês na torre qualquer dia.");
        listaFrasesPedrao.add("Café frio num da não.");
        listaFrasesPedrao.add("Deixa eu chorar um pouquinho.");
        listaFrasesPedrao.add("Cafezin pode deixar passar batido não.");
        listaFrasesPedrao.add("Isso não é mentira não é verdade");
        listaFrasesPedrao.add("Essa é de entortar o cano da garrucha.");
        listaFrasesPedrao.add("Je vous salue Marie pleine de grâce le Seigneur est avec toi Tu es bénie entre toutes les femmes et Jésus.");
        listaFrasesPedrao.add("É porque a turma ta querendo dar uma passada lá.");
        listaFrasesPedrao.add("Essa não é minha praia não.");
    }



    /**
     * @return Uma palavra aleatória do vocabulário do Pedrao.
     */
    public static String getRandomFrase() {
        int numeroSorteado = new Random().nextInt(listaFrasesPedrao.size());
        String frase = "Sabedoria " + numeroSorteado + " - " + listaFrasesPedrao.get(numeroSorteado);
        String fraseFormatada = frase.replaceAll(".{78}", "$0\n   ");
        return fraseFormatada;
    }

}
