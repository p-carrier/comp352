import java.util.Date;
import java.util.Random;
import java.util.Stack;

public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        CVR cvr17 = new CVR(100);
        CVR cvr10 = new CVR(100);
        cvr17.setKeyLength(17);
        cvr17.setThreshold(10);
        cvr10.setKeyLength(10);
        cvr10.setThreshold(10);

        String[] MODELS = {
                "Ferrari",
                "Ford",
                "Honda",
                "Pagani",
                "Acura",
                "Jaguar",
        };

        Accident[] ACCIDENTS = {
                new Accident(new Date(), "Accident1"),
                new Accident(new Date(), "Accident2"),
                new Accident(new Date(), "Accident3"),
                new Accident(new Date(), "Accident4"),
                new Accident(new Date(), "Accident5"),
        };

        String[] VINs10 = {
                "H8T6DYTSAR",
                "LVIQD7DO53",
                "X1PW13ZCC0",
                "URPSPIYMTQ",
                "UBBXFX6WOV",
                "540U6RMVOF",
                "68334HUQV5",
                "8JWJ3ZUV3Z",
                "89WZMCCSGS",
                "KX3QTDIQZ9",
                "MUIEUCXTPH",
                "6XCCT2O3GM",
                "09LLRH15OB",
                "7B6I4E200T",
                "ZD8LQ6MHG7",
                "D9ILYBWU88",
                "U65GZNH9VH",
                "4PKRCLDF9L",
                "0D2AI0X2Y2",
                "3A6MUKIRNG",
                "N3W63RZ11E",
                "5Y76VXLEFT",
                "CA7ZNTQBM1",
                "4FKALFMG1Z",
                "EBNT2DCX97",
                "R1BMKIA4UQ",
                "PRIQ1QY8A3",
                "ET0IEGDFGV",
                "0F20E8HT4G",
                "HWPXIPV33H",
        };

        String[] VINs17 = {
                "VBH5KH9I6EI3AZ4RC",
                "FYUSDEQWHTQUUGQAF",
                "3ZZ8AEEONWBTDI0SZ",
                "IEWO2UUBGU3BF0ET6",
                "W8NZZUPGUYJM2NDU2",
                "J0DOUQ6D11C3DTNGV",
                "B5H1R6EBURIFP9OUT",
                "Y8XSM79MV9Z5DHZJ6",
                "K853OEUAX86O5H18Z",
                "Y0XEXYYCXOQRIVFT0",
                "0Z4WUP9ZC6SCMUA1M",
                "AB2O4R2ZBT6FD6WNT",
                "DXVMBFW8IRHPFFVJH",
                "ZH42YGZSC23006IVT",
                "TCJPLQ35Y23FXDLYH",
                "0K29C3SPL4DLQ73OZ",
                "XOJZQWPMFHIV2AWC7",
                "L2ZZ6O3OQXW95SG28",
                "EWNMHVDIMAMF9Q24T",
                "JN23H6KXH8T85OAZP",
                "05J7ZDJ2TC5S5YWDO",
                "CI4S2L59TTWOTD6EF",
                "F9JRJBOEOG8JEHUWO",
                "60G2JV330R202NP59",
                "JFQWAU2SBOOISUEPO",
                "VZKJO643C1D3ZGGHE",
                "2U86FZTSSTMH08E2S",
                "3OLD2U3VDT92S36XG",
                "QFA9KK8M17MBK6PEZ",
                "WXU2S6SMRTV7MUBBX",
        };

        for (int i = 0; i < 9; i++) {
            Car car = new Car(MODELS[random.nextInt(6)]);
            for (int y = 0; y < random.nextInt(5); y++) {
                car.setAccidents(ACCIDENTS[y]);
            }
            cvr17.add(VINs17[i], car);
        }

        String[] cvr17Keys = cvr17.allKeys();

        for (String key : cvr17Keys) {
            System.out.println(key);
        }

        System.out.println(cvr17.getValue(cvr17Keys[3]));
        Stack<Accident> accidents = cvr17.prevAccids(cvr17Keys[3]);
        if (accidents.size() != 0)
            System.out.println(accidents.pop());
        if (accidents.size() > 1)
            System.out.println(accidents.pop());


        String nextKey = cvr17.nextKey(cvr17Keys[3]);
        System.out.print(cvr17Keys[4]);
        System.out.println(" should equal " + nextKey);
        String prevKey = cvr17.prevKey(cvr17Keys[3]);
        System.out.print(cvr17Keys[2]);
        System.out.println(" should equal " + prevKey);

        for (int i = 9; i < 12; i++) {
            Car car = new Car(MODELS[random.nextInt(6)]);
            for (int y = 0; y < random.nextInt(5); y++) {
                car.setAccidents(ACCIDENTS[y]);
            }
            cvr17.add(VINs17[i], car);
        }

        cvr17Keys = cvr17.allKeys();

        for (String key : cvr17Keys) {
            System.out.println(key);
        }

        cvr17.remove(cvr17Keys[10]);
        cvr17.remove(cvr17Keys[7]);
        cvr17.remove(cvr17Keys[4]);
        cvr17.remove(cvr17Keys[1]);

        cvr17Keys = cvr17.allKeys();

        for (String key : cvr17Keys) {
            System.out.println(key);
        }


        for (int i = 0; i < 9; i++) {
            Car car = new Car(MODELS[random.nextInt(6)]);
            for (int y = 0; y < random.nextInt(5); y++) {
                car.setAccidents(ACCIDENTS[y]);
            }
            cvr10.add(VINs10[i], car);
        }

        String[] cvr10Keys = cvr10.allKeys();

        for (String key : cvr10Keys) {
            System.out.println(key);
        }

        System.out.println(cvr10.getValue(cvr10Keys[3]));
        accidents = cvr10.prevAccids(cvr10Keys[3]);
        if (accidents.size() != 0)
            System.out.println(accidents.pop());
        if (accidents.size() > 1)
            System.out.println(accidents.pop());


        nextKey = cvr10.nextKey(cvr10Keys[3]);
        System.out.print(cvr10Keys[4]);
        System.out.println(" should equal " + nextKey);
        prevKey = cvr10.prevKey(cvr10Keys[3]);
        System.out.print(cvr10Keys[2]);
        System.out.println(" should equal " + prevKey);

        for (int i = 9; i < 12; i++) {
            Car car = new Car(MODELS[random.nextInt(6)]);
            for (int y = 0; y < random.nextInt(5); y++) {
                car.setAccidents(ACCIDENTS[y]);
            }
            cvr10.add(VINs10[i], car);
        }

        cvr10Keys = cvr10.allKeys();

        for (String key : cvr10Keys) {
            System.out.println(key);
        }


        cvr10.remove(cvr10Keys[10]);
        cvr10.remove(cvr10Keys[7]);
        cvr10.remove(cvr10Keys[4]);
        cvr10.remove(cvr10Keys[1]);

        cvr10.add(cvr10.generate(1)[0], new Car(MODELS[random.nextInt(6)]));

        cvr10Keys = cvr10.allKeys();

        for (String key : cvr10Keys) {
            System.out.println(key);
        }

    }
}
