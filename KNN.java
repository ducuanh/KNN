import java.util.List;
public class Atrributes {
    public List<Double> AttributesValues;
    public String AttributesAnswerName;

    public Atrributes(List<Double> attributesValues, String attributesAnswerName) {
        AttributesValues = attributesValues;
        AttributesAnswerName = attributesAnswerName;
    }

    public List<Double> getAttributesValues() {
        return AttributesValues;
    }

    public String getAttributesAnswerName() {
        return AttributesAnswerName;
    }

    public void setAttributesValues(List<Double> attributesValues) {
        AttributesValues = attributesValues;
    }

    public void setAttributesAnswerName(String attributesAnswerName) {
        AttributesAnswerName = attributesAnswerName;
    }

    @Override
    public String toString() {
        return "Atrributes{" +
                "AttributesValues=" + AttributesValues +
                ", AttributesAnswerName='" + AttributesAnswerName + '\'' +
                '}';
    }
}
import java.util.Comparator;

public class TrainTestResults implements Comparable<TrainTestResults> {
    private Atrributes resultsClassName;
    private Double distance;

    public TrainTestResults(Atrributes resultsClassName, Double distance) {
        this.resultsClassName = resultsClassName;
        this.distance = distance;
    }



    public void setResultsClassName(Atrributes resultsClassName) {
        this.resultsClassName = resultsClassName;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Atrributes getResultsClassName() {
        return resultsClassName;
    }

    public Double getDistance() {
        return distance;
    }

    @Override
    public int compareTo(TrainTestResults o) {
        return this.distance < o.distance? -1:this.distance == o.distance? 0:1;
    }
}
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Atrributes> trainSetData;
        List<Atrributes> testSetData;
        boolean dlaPetli = true;
        Scanner scan = new Scanner(System.in);
        System.out.println("--------------------------------ALGORYTM KNN------------------------------------");
        System.out.println("podaj sciezke do pliku trainSet: ");
        String trainSet = scan.next();
        trainSetData = getData(trainSet);
        System.out.println("podaj sciezke do pliku testSet: ");
        String testSte = scan.next();
        testSetData = getData(testSte); // dla komunikatu zlej danych
        System.out.println("podaj liczbe k dla algorytmu: ");
        int k = scan.nextInt();
        System.out.println("podaj opcje ktora chcesz wybrac: " + "\n" + "1. chce sprawdzic dla  test set " + "\n" + "2. chce sprawdzic dla wlasnego wektora");
        int opcja = scan.nextInt();
        switch (opcja){
            case 1:
                KNN(trainSetData,getData(testSte),k,true);
                break;
            case 2:
                while(dlaPetli){
                    System.out.println("podaj 4 wartosci oddzielone znakiem srednik ; "+ "   " + "jezeli chcesz wyjsc daj exit");
                    String wybor = scan.next();
                    List<Atrributes> daneDlaWektora = new ArrayList<>();
                    if(wybor.equals("exit")){
                        break;
                    }
                    String [] chwilowe = wybor.split(";");
                    List<Double> valuesList = new ArrayList<>();
                    for (int i = 0; i < chwilowe.length-1; i++) {
                        valuesList.add(Double.parseDouble(chwilowe[i]));
                    }
                    daneDlaWektora.add(new Atrributes(valuesList,chwilowe[chwilowe.length-1]));
                    KNN(trainSetData,daneDlaWektora,k,false);
                }
        }
    }

    // wczytywanie danych do listy
    public static List<Atrributes> getData(String nazwaPliku) throws IOException {
        List<Atrributes> getData = new ArrayList<>();
        String line ="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(nazwaPliku));
            while((line = br.readLine())!= null){
                String [] k = line.split(";");
                List<Double> valuesList = new ArrayList<>();
                for (int i = 0; i < k.length-1; i++) {
                    valuesList.add(Double.parseDouble(k[i]));
                }
                getData.add(new Atrributes(valuesList,k[k.length-1]));
            }

        } catch (FileNotFoundException e) {
            System.out.println("Bledna sciezka do pliku!");
        }
        return getData;
    }
    //algorytm knn
    public static void KNN( List<Atrributes> trainSet,List<Atrributes> testSet, int k,boolean informacjeDlaTestu){
        double accuracy = 0;
        for (int i = 0; i < testSet.size() ; i++) {
            String odpowiedzZMetody = "";
            List<String> answerNames = new ArrayList<>();
            List<TrainTestResults> resultList = new ArrayList<>();
            for (Atrributes trainingSet:trainSet){
                resultList.add(new TrainTestResults(trainingSet,calculateDistance(testSet.get(i),trainingSet)));
            }
            Collections.sort(resultList);
            for(int x = 0; x < k; x++){
                //System.out.println(resultList.get(x).getResultsClassName()+ " .... " + resultList.get(x).getDistance());
                answerNames.add(resultList.get(x).getResultsClassName().getAttributesAnswerName());
            }

            odpowiedzZMetody = odpowiedz(answerNames);
            if(odpowiedzZMetody.equals(testSet.get(i).getAttributesAnswerName())){
                accuracy++;
            }
            System.out.println("odpowiedz od algorytmu to: " + odpowiedzZMetody);
            if (informacjeDlaTestu){
                System.out.println("poprawna odpowiedz to: "+ testSet.get(i).getAttributesAnswerName());
            }

        }
        if (informacjeDlaTestu){
            System.out.println("dokladnosc to: " + accuracy/testSet.size()*100);}

    }
    // obliczanie dystansu Euklidesem
    public static Double calculateDistance(Atrributes a, Atrributes b){
        double calculatedDistance = 0;
        for (int i = 0; i < a.AttributesValues.size() ; i++) {
            calculatedDistance += Math.sqrt(Math.pow(a.AttributesValues.get(i)-b.AttributesValues.get(i),2));
        }
        return calculatedDistance;
    }
    //uzyskanie najwiekszej ilosci z k
    public static  String  odpowiedz(List<String> answerNames){
        int a= 0;
        Map<String,Integer> count = new HashMap<>() ;
        String odpowiedz = "";
        for (String word : answerNames) {  // zliczanie ilosc elementow
                                
            if (! count.containsKey(word)) {
                count.put(word, 1 ) ;
            }
            else {
                int value = count.get(word) ;
                value++ ;
                count.put(word, value) ;
            }
        }
        List <String> mostCommons = new ArrayList<>() ; // przechowanie max elementow
        for ( Map.Entry<String,Integer> e : count.entrySet() ) {
            if (e.getValue() == Collections.max(count.values() )){
                // maksymalna wartosc z hasza
                mostCommons.add(e.getKey()) ;
            }
        }
        for (int i = 0; i < mostCommons.size(); i++) {
            odpowiedz = mostCommons.get(i);
        }
        return odpowiedz;
    }

}

