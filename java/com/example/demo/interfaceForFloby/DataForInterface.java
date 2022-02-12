package com.example.demo.interfaceForFloby;

public class DataForInterface {

    public static String[] settingsItems = {
            "name:2 weight:2.2 appointment:craft",      //Простой топор
            "name:2 weight:0.2 appointment:resources",      //Древесина
            "name:2 weight:0.1 appointment:resources",          //Металические осколки
            "",
            "name:2 weight:0.2 appointment:craft",        //Для чертежа карты
            "name:2 weight:0.01 appointment:resources",              //Перо
            "name:2 weight:0.2 appointment:resources",                       //Уголёк
            "name:2 weight:0.2 appointment:resources",      //Чернила
            "name:2 weight:2.1 appointment:craft",      //Факел
            "name:2 weight:2 appointment:resources",          //Шкура
            "name:2 weight:10 appointment:craft",           //Рюкзак
            "name:2 weight:0.5 appointment:resources",        //Верёвка
            "name:1 weight:3 appointment:sword damage:2",              //Поломаный меч
            "name:1 weight:0.5 appointment:cloak armor:0.5 frozen:0.5",                       //Порваный плащ
            "name:1 weight:1.7 appointment:jacket armor:0.5 frozen:0.5",      //Порваная куртка
            "name:1 weight:1 appointment:pants armor:1 frozen:1",          //Порваные брюки
            "name:1 weight:0.3 appointment:shoes armor:1 frozen:1",            //Порваная обувь
            "name:2 weight:0.1 appointment:poison skill:heal-10"
    };

    public static String[][] infoItemsForUpgrade = {
            {"ax","wood:2,metal:5","wood:5,metal:15,rope:1","wood:10,metal:25,rope:3","force:1,durability:6,weight:2","force:2,durability:12,weight:2","force:3,durability:24,weight:3"}, //0
            {},{},{},
            {"feather","pen:1,ember:1","pen:1,ink:1","pen:1,ember:2,ink:2","smear:10,weight:0","smear:25,weight:0","smear:100,weight:0"},//4
            {},{},{},
            {"torch","wood:1,skin:1,fire:1","wood:5,skin:2,rope:1,fire:1","wood:10,skin:3,metal:10,rope:5,fire:1","light:10,durability:25,weight:1","light:20,durability:100,weight:1","light:30,durability:500,weight:3"}, //8
            {},
            {"back","skin:2,rope:2","skin:5,rope:5","skin:10,rope:10,rope:5","capacity:20,weight:10","capacity:50,weight:12","capacity:100,weight:15"}, //10
            {}
    };

    public static String[][] infoSectionForUpgrade = {
            {"ax 53 82", "torch 153 122"},
            {"back 53 82"},
            {"feather 53 82"}
    };

    public static String translate(String s){
        return switch (s) {
            case "ax" -> "Топор";
            case "torch" -> "Факел";
            case "back" -> "Сумка";
            case "feather" -> "Чертёжные пренадлежности";
            case "wood" -> "Дерево";
            case "metal" -> "Металл";
            case "pen" -> "Перо";
            case "ember" -> "Уголёк";
            case "ink" -> "Чернила";
            case "skin" -> "Кожа";
            case "fire" -> "Искра";
            case "rope" -> "Верёвка";
            case "weight" -> "Вес";
            case "force" -> "Сила";
            case "durability" -> "Прочность";
            case "light" -> "Освещение";
            case "smear" -> "Мазки";
            case "capacity" -> "Вместимость";
            default -> "";
        };
    }

    public static String translateItems (String s){
        return switch (s) {
            case "ax" -> "0";
            case "wood" -> "1";
            case "metal" -> "2";
            case "fire" -> "3";
            case "feather" -> "4";
            case "pen" -> "5";
            case "ember" -> "6";
            case "ink" -> "7";
            case "torch" -> "8";
            case "skin" -> "9";
            case "back" -> "10";
            case "rope" -> "11";
            default -> "";
        };
    }

    public static int translateSectionForUpgrade(String s){
        return switch (s){
            case "ax","torch" -> 1;
            case "back" -> 2;
            case "feather" -> 3;
            default -> 0;
        };
    }

    public static String translateDescriptionItemFromCraft(String s,int i){
        switch (s){
            case "ax"-> {
                return switch (i){
                    case (1) -> """
                            Инструмент для
                            рубки деревьев""";
                    case (2) -> """
                            Крепкая ручка.
                            Должен протянуть
                            подольше""";
                    case (3) -> """
                            Стальная основа.
                            Это точто мечта
                            каждого дровосека""";
                    default -> "";
                };
            }
            case "torch"-> {
                return switch (i){
                    case (1) -> """
                            Палка с огоньком.
                            Бистро затухнет.
                            Нельзя выходить под метель""";
                    case (2) -> """
                            Намотки шкуры
                            продлят время роботы
                            факела""";
                    case (3) -> """
                            Железный ободок.
                            Даже метель не задует
                            этот факел""";
                    default -> "";
                };
            }
            case "back"-> {
                return switch (i){
                    case (1) -> """
                            Мешок со шкуры
                            и верёвок поможет
                            переносить больше
                            вещей""";
                    case (2) -> """
                            Укреплёный мешок
                            вмещает больше
                            предметов""";
                    case (3) -> """
                            Металические укрепления
                            стаким рюкзаком
                            не страшен переизбыток
                            веса""";
                    default -> "";
                };
            }
            case "feather"-> {
                return switch (i){
                    case (1) -> """
                            Пером и угольком
                            можно зарисовать
                            фрагменты исведай карты""";
                    case (2) -> """
                            С использованием чернил
                            зарисовки станут чётче""";
                    case (3) -> """
                            Смесь угля и чернил.
                            Хватит на много дольше""";
                    default -> "";
                };
            }
            default -> {
                return "";
            }
        }
    }

    public static String translateArticleItemFromCraft(String s,int i){
        switch (s){
            case "ax"-> {
                return switch (i){
                    case (1) -> """
                            Главное не уронить
                            лезвием на ногу""";
                    case (2) -> """
                            Обрубки могут
                            и в глаз попасть""";
                    case (3) -> """
                            Деревья трепещат
                            в ужасе""";
                    default -> "";
                };
            }
            case "torch"-> {
                return switch (i){
                    case (1) -> """
                            Осветят самые
                            самые тёмные места""";
                    case (2) -> """
                            Отгонит не только тьму,
                            но и приманит монстров""";
                    case (3) -> """
                            Ночь, как день,
                            когда в руке он""";
                    default -> "";
                };
            }
            case "back"-> {
                return switch (i){
                    case (1) -> """
                            Обноски слаживать
                            в обноски?""";
                    case (2) -> """
                            Он наверное и мой
                            вес выдержит""";
                    case (3) -> """
                            В него можно положить
                            50 таких рюкзаков""";
                    default -> "";
                };
            }
            case "feather"-> {
                return switch (i){
                    case (1) -> """
                            Можно случайно
                            и всю карту заляпать""";
                    case (2) -> """
                            Вот только бы чернила
                            не потекли""";
                    case (3) -> """
                            Может зарисовать
                            парочку портретов?""";
                    default -> "";
                };
            }
            default -> {
                return "";
            }
        }
    }

}
