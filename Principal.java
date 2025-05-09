import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Principal{
     //Lista com os itens
    static List<String> itens = Arrays.asList(
        "R","R","R","R","R","R",
        "G","G","G","G","G","G",
        "B","B","B","B","B","B",
        "E","E","E","E","E","E");

    //Pilhas
    static Stack<String> pilha1 = new Stack<>();
    static Stack<String> pilha2 = new Stack<>();
    static Stack<String> pilha3 = new Stack<>();
    static Stack<String> pilha4 = new Stack<>();

    public static void main(String[] args) {
        embaralhar();
        preenchimento(pilha1);
        embaralhar();
        preenchimento(pilha2);
        embaralhar();
        preenchimento(pilha3);
        embaralhar();
        preenchimento(pilha4);

        System.out.println();
        System.out.println("=================");
        System.out.println(pilha1);
        System.out.println(pilha2);
        System.out.println(pilha3);
        System.out.println(pilha4);
    }

    //Sistema para embaralhar a pilha
    public static void embaralhar(){
       Collections.shuffle(itens);
    }

    //Sistema de preenchimento da pilha
    public static void preenchimento(Stack<String> pilha){
        pilha.clear();//Garante a pilha estar limpa
        for(int i = 0; i < 5; i++){
            pilha.push(itens.get(i));
        };
    }
}