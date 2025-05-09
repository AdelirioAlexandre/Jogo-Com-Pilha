import java.util.*;

public class Principal{
     //Lista com os itens
     static List<String> itens = new ArrayList<>(Arrays.asList(
             "1","2","3","4","5","6",
             "12","11","10","9","8","7",
             "13","14","15","16","17","18",
             "24","23","22","21","20","19"
     ));

    //Pilhas
    static Stack<String> pilha1 = new Stack<>();
    static Stack<String> pilha2 = new Stack<>();
    static Stack<String> pilha3 = new Stack<>();
    static Stack<String> pilha4 = new Stack<>();
    static Stack<String> pilha5 = new Stack<>();

    public static void main(String[] args) {
        //Embarrass as Strings
        Collections.shuffle(itens);

        //Sem repetir
        preenchimento(pilha1);
        preenchimento(pilha2);
        preenchimento(pilha3);
        preenchimento(pilha4);
        preenchimento(pilha5);

        System.out.println(pilha1);
        System.out.println(pilha2);
        System.out.println(pilha3);
        System.out.println(pilha4);
        System.out.println(pilha5);
        //Menu do usuario
        usuario();
    }

    //Jogo-Usuario
    public static void usuario(){

    }

    //Sistema de preenchimento da pilha
    public static void preenchimento(Stack<String> pilha){
        pilha.clear();//Garante a pilha estar limpa
        for(int i = 0; i < 6 && !itens.isEmpty(); i++){
            pilha.push(itens.remove(0));
        }
    }
}