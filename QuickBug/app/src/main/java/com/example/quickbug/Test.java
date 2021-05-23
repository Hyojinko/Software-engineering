package com.example.quickbug;

import java.io.FileNotFoundException;

public class Test {

    public static String testing(int hour, int minute, String src, String dest) throws FileNotFoundException {
        Dijkstra d = new Dijkstra(12);

        // a - IT융합대학
        // b - 글로벌센터
        // c - 예술대학/공과대학
        // d - 대학원/바이오나노대학
        // e - 바이오나노연구원
        // f - 비전타워,법대,공대2
        // g - 산악협력관
        // h - 가천관
        // i - 교육대학원
        // j - 중도
        // k - 학생회관
        // l - 기숙사

        // 인접한 두 꼭지점 사이의 가중치 주입
        d.input("a", "b", 60);
        d.input("a", "e", 60);
        d.input("b", "c", 70);
        d.input("c", "d", 180);
        d.input("c", "h", 180);
        d.input("d", "i", 60);
        d.input("i", "h", 120);
        d.input("i", "j", 120);
        d.input("h", "g", 120);
        d.input("h", "e", 240);
        d.input("g", "f", 180);
        d.input("f", "e", 120);
        d.input("j", "k", 120);
        d.input("k", "l", 300);

        d.input_m("a", "i", 240);
        d.input_m("i", "k", 120);
        d.input_m("k", "l", 240);
        d.input_m("l", "c", 480);
        d.input_m("c", "a", 120);

        d.setTime(hour, minute);
        System.out.println(src);
        System.out.println(dest);
        src = node(src);
        dest = node(dest);
        System.out.println(src);
        System.out.println(dest);

        // 시작점 a에서부터의 최단거리 및 최단경로 출력
        return d.algorithm(src, dest);
    }

    public static String node(String vertex) {
        String result = "";

        switch (vertex) {
            case "IT융합대학":
            case "a":
                result = "a";
                break;
            case "글로벌센터":
            case "b":
                result = "b";
                break;
            case "예술대학":
            case "공과대학":
            case "c":
                result = "c";
                break;
            case "대학원":
            case "바이오나노대학":
            case "d":
                result = "d";
                break;
            case "바이오나노연구원":
            case "e":
                result = "e";
                break;
            case "비전타워":
            case "법과대학":
            case "공과대학2":
            case "f":
                result = "f";
                break;
            case "산학협력관":
            case "g":
                result = "g";
                break;
            case "가천관":
            case "h":
                result = "h";
                break;
            case "교육대학원":
            case "i":
                result = "i";
                break;
            case "중앙도서관":
            case "j":
                result = "j";
                break;
            case "학생회관":
            case "k":
                result = "k";
                break;
            case "기숙사":
            case "l":
                result = "l";
                break;
        }

        return result;
    }
}
