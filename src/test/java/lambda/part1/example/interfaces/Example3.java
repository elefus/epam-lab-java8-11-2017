package lambda.part1.example.interfaces;

interface First {

    int value = 100;

    int getValueFromFirst();

//    int getValue();

//    default int getValue() {
//        return value;
//    }

}

interface Second {

    int value = -100;

    int getValueFromSecond();

//    int getValue();

//    default int getValue() {
//        return value;
//    }
}