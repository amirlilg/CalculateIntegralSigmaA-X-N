import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Amirali on 11/27/2017.
 */



class Component {
    int model;//model : "0" -> ax^b
    int zarib;
    int tavan;
    public Component(int zarib, int tavan) {
        this.model = 0;
        this.tavan = tavan;
        this.zarib = zarib;
    }
}

class ArrayOfComponents{
    ArrayList<Component> main_list = new ArrayList<>();

    public void addToList(Component a) {
        main_list.add(a);
    }

    public void refreshList(){
        for(int j = 0; j < main_list.size(); j++) {
            Component a = main_list.get(j);
            for (int i = j+1; i < main_list.size(); i++) {
                if (main_list.get(i).tavan == a.tavan){
                    //Component b = main_list.get(i);
                    a.zarib = a.zarib + main_list.get(i).zarib;
                    main_list.remove(i);
                    i--;
                }
            }
        }
    }

    public ArrayOfComponents(String input){
        String[] list = input.split("\\+");
        ArrayList<String> splited_1 = new ArrayList<>();
        for (int i = 0 ; i < list.length ; i++){
            String[] splited_temp = list[i].split("\\-");
            for(int j = 0 ; j < splited_temp.length ; j++){
                splited_1.add(splited_temp[j]);
            }
        }
        for (int i = 0 ; i < splited_1.size() ; i++){
            splited_1.set(i,splited_1.get(i).replaceAll("\\s+",""));
            //System.out.println(splited_1.get(i));
        }
        ArrayList<Character> alamat_ha = new ArrayList<>();
        for(int i = 0 ; i < input.length() ; i++){
            if(input.charAt(i) == '+'){
                alamat_ha.add('+');
            }
            else if(input.charAt(i) == '-') {
                alamat_ha.add('-');
            }
        }
        // splited_1 and alamat_ha
        if(splited_1.size()==alamat_ha.size()+1){
            //System.out.println("HI");
            alamat_ha.add(0,'+');
        }

        // x 1  124x^342

        for(int i = 0 ; i < splited_1.size() ; i++){
            if (splited_1.get(i).contains("x")){
                if(splited_1.get(i).charAt(splited_1.get(i).length()-1) == 'x'){
                    String tavan_1 = splited_1.get(i);
                    tavan_1 = tavan_1 + "^1";
                    splited_1.set(i,tavan_1);
                }
            }
            else{
                splited_1.set(i,splited_1.get(i) + "x^0");
            }
        }
        //for(int i = 0 ; i < splited_1.size() ; i++){
        //    System.out.println(splited_1.get(i));
        //}
        for(int i = 0; i < splited_1.size() ; i++){
            String[] temp_zarib_and_tavan = splited_1.get(i).split("x\\^");
            if(temp_zarib_and_tavan[0].equals(""))
                temp_zarib_and_tavan[0] = "1";
            int alamat = -1;
            if(alamat_ha.get(i).equals('+')){
                alamat = 1;
            }
            this.addToList(new Component(alamat * Integer.parseInt(temp_zarib_and_tavan[0]),Integer.parseInt(temp_zarib_and_tavan[1])));
        }
        this.refreshList();
        for(int i = 0; i < this.main_list.size() ; i++){
            System.out.println("zarib: " + main_list.get(i).zarib + " tavan: " + main_list.get(i).tavan);
        }
    }

    public float get_meghdar(float x){
        float meghdar = 0f;
        for(int i = 0 ; i < main_list.size() ; i++) {
            meghdar += (float)(main_list.get(i).zarib) * Math.pow((float)x,main_list.get(i).tavan);
        }
        return meghdar;
    }

    public float get_rectangle_integral(float x_min, float x_max, float delta){
        float integral = 0;
        float current_x = x_min;
        while(current_x < x_max){
            integral += get_meghdar(current_x);
            current_x += delta;
        }
        return integral * delta;
    }
}
// ax^2 + bx^1 - cx^0

public class Integral {
    public static void main(String[] Args){
        //String a = "x^3 - x^2 + 3x - 5x^2";
        //ArrayOfComponents main = new ArrayOfComponents(a);
        //String[] temp_zarib_and_tavan = a.split("x\\^");
        //System.out.println(temp_zarib_and_tavan[0]);
        //System.out.println(temp_zarib_and_tavan[1]);
        Scanner scan = new Scanner(System.in);
        System.out.println("enter your equation (sigma (ax^b))");
        String input = scan.nextLine();
        ArrayOfComponents main = new ArrayOfComponents(input);
        float x_min,x_max;
        System.out.println("enter x_min:");
        x_min = scan.nextFloat();
        System.out.println("enter x_max:");
        x_max = scan.nextFloat();
        System.out.println("enter delta_x:");
        float delta_x = scan.nextFloat();
        System.out.println("integral is : " + main.get_rectangle_integral(x_min,x_max,delta_x));
    }
}
