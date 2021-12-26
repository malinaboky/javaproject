import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        DataBase.fillDataBase();
        DataBase.getGraph(DataBase.getDataHealth(), DataBase.getMiddleValueOfHealth());
        System.out.println("Средний показатель здоровья стран \"Western Europe\" или \"Sub-Saharan Africa\"- "+
                DataBase.getMiddleValueOfHealth());
        System.out.println("Страна с самыми средними показателями в \"Western Europe\" или \"Sub-Saharan Africa\"- "+
                DataBase.getMiddleCountry());
    }
}
