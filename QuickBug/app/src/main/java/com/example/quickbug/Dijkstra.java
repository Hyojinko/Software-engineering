package com.example.quickbug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Dijkstra {
    private int n; // 꼭지점 수를 변수로 선언
    private int[][] weight; // 2차원 배열 weight에 각 꼭지점의 가중치를 저장
    private int[][] weight_m;
    private String[] saveRoute;
    private String[] saveRoute_m;
    private String[] vertex = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l" };
    private int h;
    private int m;
    public String[] totalResult;

    public void setTime(int h, int m) {
        this.h = h;
        this.m = m;
    }

    public Dijkstra(int n) {
        super();
        this.n = n; // 생성자를 통해 꼭지점 수를 주입하고,
        weight = new int[n][n]; // 가중치를 저장할 배열 weight의 크기 지정
        weight_m = new int[n][n];
        saveRoute = new String[n];
        saveRoute_m = new String[n];
    }

    public int stringToInt(String s) {
        // 문자열을 int형으로 바꿔준다
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s))
                x = i;
        }
        return x;
    }

    public void input(String v1, String v2, int w) {
        // 먼저 문자열로 입력된 꼭지점 v1와 v2를 해당되는 숫자 인덱스 x1과 x2로 바꿔준다
        int x1 = stringToInt(v1);
        int x2 = stringToInt(v2);

        // x1에서 x2까지의 가중치를 주입
        // 이 때, x1에서 x2까지의 가중치와 x2에서 x1까지의 가중치는 같다. (중복될 뿐)
        // 가중치가 없어서 입력되지 않았다면 기본값이 0 입력
        weight[x1][x2] = w;
        weight[x2][x1] = w;
        weight_m[x1][x2] = w;
        weight_m[x2][x1] = w;
    }

    public void input_m(String v1, String v2, int w) {
        // 먼저 문자열로 입력된 꼭지점 v1와 v2를 해당되는 숫자 인덱스 x1과 x2로 바꿔준다
        int x1 = stringToInt(v1);
        int x2 = stringToInt(v2);

        // x1에서 x2까지의 가중치를 주입
        // 이 때, x1에서 x2까지의 가중치와 x2에서 x1까지의 가중치는 같다.(중복될 뿐)
        // 가중치가 없어서 입력되지 않았다면 기본값 0이 입력.
        weight_m[x1][x2] = w;
        weight_m[x2][x1] = 0;
    }

    public int[] dik(String a, String[] saveRoute, int[][] weight) {
        boolean[] visited = new boolean[n]; // 각 꼭지점의 방문 여부
        int distance[] = new int[n]; // 시작 꼭지점에서부터 각 꼭지점까지의 거리

        // 시작 꼭지점 a에서부터 각 꼭지점까지의 모든 거리 초기화
        for (int i = 0; i < n; i++) {
            // int형의 가장 큰 값 2147483647로 초기화한다.
            distance[i] = Integer.MAX_VALUE;
        }

        int x = stringToInt(a); // 문자열로 입력된 시작 꼭지점을 해당되는 숫자 인덱스 x로 바꾸고
        distance[x] = 0;// 시작 꼭지점 x의 거리를 0으로 초기화하고,
        visited[x] = true; // 방문 꼭지점이므로 true값 저장
        saveRoute[x] = vertex[x];// 시작 꼭지점의 경로는 자기 자신을 저장

        // 시작vertex에서 인접한 꼭지점까지의 거리를 갱신함.
        // 시작 꼭지점 x에서부터 꼭지점 i까지의 거리를 갱신한다.
        for (int i = 0; i < n; i++) {
            // 방문하지 않았고 x에서 i까지의 가중치가 존재한다면, 거리 i에 x에서 i까지의 가중치 저장
            // 즉 x에서 인접한 꼭지점까지의 거리를 갱신함.
            // (x와 인접하지 않은 꼭지점에는 Integer.MAX_VALUE가 계속 저장되어 있을 것이다.)
            // visited가 false 이고 weight 값이 있다면!
            if (!visited[i] && weight[x][i] != 0) {
                distance[i] = weight[x][i];
                saveRoute[i] = vertex[x]; // ★시작 꼭지점과 인접한 꼭지점의 경로에 시작 꼭지점을 저장
            }
        }

        for (int i = 0; i < n - 1; i++) {
            int minDistance = Integer.MAX_VALUE; // 최단거리 minDistance에 일단 가장 큰 정수로 저장하고,
            int minVertex = -1; // 그 거리값이 있는 인덱스 minIndex에 -1을 저장해둔다.

            // 시작vertex에서 인접한 꼭짓점들 중 최솟값 업데이트
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distance[j] != Integer.MAX_VALUE) {
                    if (distance[j] < minDistance) {
                        minDistance = distance[j];
                        minVertex = j;
                    }
                }
            }

            visited[minVertex] = true; // 위의 반복문을 통해 도출된 가장 가까운 꼭지점(최솟값) 에 방문 표시

            for (int j = 0; j < n; j++) {
                if (!visited[j] && weight[minVertex][j] != 0) {
                    // 지금 그 꼭지점이 가지고 있는 거리값이 minVertex와 가중치를 더한 값보다 크다면 최단거리 갱신
                    if (distance[j] > distance[minVertex] + weight[minVertex][j]) {
                        distance[j] = distance[minVertex] + weight[minVertex][j];
                        saveRoute[j] = vertex[minVertex];
                    }
                }
            }
        }
        return distance;
    }

    public String setroute(int dest, String[] saveRoute) {
        String route = "";
        int index = dest;
        while (true) {
            if (saveRoute[index] == vertex[index])
                break; // 시작 꼭지점일 경우 break
            route += " " + saveRoute[index];
            index = stringToInt(saveRoute[index]); // 결정적인 역할을 한 꼭지점을 int형으로 바꿔서 index에 저장
        }
        StringBuilder sb = new StringBuilder(route);
        String temp = sb.reverse() + vertex[dest];
        // String[] newroute=temp.split(" ");

        return temp;
    }

    public String showRoute(String x) {
        String[] a = x.split(" ");
        String b = "";

        for (int i = 0; i < a.length; i++) {
            if (i == a.length - 1) {
                b = b.concat(showName(a[i]));
            } else {
                b = b.concat(showName(a[i]) + " -> ");
            }
        }
        return b;
    }

    public String showName(String x) {
        String result = "";

        switch (x) {
            case "a":
                result = "IT융합대학";
                break;
            case "b":
                result = "글로벌센터";
                break;
            case "c":
                result = "예술대학/공과대학";
                break;
            case "d":
                result = "대학원/바이오나노대학";
                break;
            case "e":
                result = "바이오나노연구원";
                break;
            case "f":
                result = "비전타워/법과대학/공과대학2";
                break;
            case "g":
                result = "산학협력관";
                break;
            case "h":
                result = "가천관";
                break;
            case "i":
                result = "교육대학원";
                break;
            case "j":
                result = "중앙도서관";
                break;
            case "k":
                result = "학생회관";
                break;
            case "l":
                result = "기숙사";
                break;
        }
        return result;
    }

    public String show(int[] distance, String a, String b) {
        int x = stringToInt(a);
        int dest = stringToInt(b);
        String res = "";
        res = res.concat(showName(a) + "부터 " + showName(b) + "까지의 시간 : ");

        int min, sec, hour;
        sec = distance[dest];

        hour = sec / 3600;
        min = sec % 3600 / 60;
        sec = sec % 3600 % 60;

        if (hour > 0 || min == 0 && sec == 0)
            res = res.concat(hour + "시간");

        if (min > 0 || hour == 0 && sec == 0)
            res = res.concat(min + "분");

        if (sec > 0 || hour == 0 && min == 0)
            res = res.concat(sec + "초");

        return res;
    }

    public String resultTime(int sec) {
        int ts = sec + m * 60 + h * 3600;
        int th = ts / 3600;
        int tm = ts % 3600 / 60;
        ts = ts % 3600 % 60;
        String res = "";

        res = res.concat("도착시간: " + th + "시 " + tm + "분 " + ts + "초 ");

        return res;
    }

    public String algorithm(String a, String b) throws FileNotFoundException {
        StringBuilder sb= new StringBuilder();




        int mudang = 0;
        int x = stringToInt(a);
        int dest = stringToInt(b);
        int time1, time2;
        int[] distance = dik(a, saveRoute, weight);
        int[] distance_m = dik(a, saveRoute_m, weight_m);

        sb.append("[도보]\n");
        sb.append(showRoute(setroute(dest, saveRoute))+"\n");
        sb.append("---------------------------------------\n");
        time1 = distance[dest];
        sb.append(resultTime(distance[dest])+"\n");
        sb.append("\n");


        int index = -1;

        String route = setroute(dest, saveRoute_m); // 무당이 루트 구한다

        if (distance[dest] == distance_m[dest]) {
            sb.append("무당이 탈 필요 없음!!!\n");
            return sb.toString();
        }

        // 도보 루트랑 무당이 루트 가 다른경우 (무당이를 탄 경우)
        else {
            // 무당이를 탄 정류장을 구한다
            for (int i = 0; i < route.length(); i++) {

                String troute = route.substring(0, i + 1);

                if (troute.contains("a i")) {
                    index = 0;
                    break;
                } else if (troute.contains("i k")) {
                    index = 8;
                    break;
                } else if (troute.contains("k l")) {
                    index = 10;
                    break;
                } else if (troute.contains("l c")) {
                    index = 11;
                    break;
                } else if (troute.contains("c a")) {
                    index = 2;
                    break;
                }
            }

            // 구한 무당이 정류장 까지 걸어가는 시간: distance_m[index]
            // 경과한 시간
            int ts = distance_m[index] + m * 60 + h * 3600;
            int th = ts / 3600;
            int tm = ts % 3600 / 60;
            ts = ts % 3600 % 60;

            if (th < 8 || th > 16 || (th == 8 && tm < 30) || (th == 16 && tm > 10)) { // 8시 반 첫 차 16시 막차
                sb.append("무당이 운영 시간이 아닙니다\n");
                mudang = 1;
            }
            int wait = -1;

            switch (index) {
                case 11:
                case 0:
                    if (tm % 10 == 0)
                        wait = 0;
                    else
                        wait = (10 - tm % 10) * 60;
                    break;
                case 8:
                    if (tm % 10 == 4)
                        wait = 0;
                    else if (tm < 4)
                        wait = (4 - tm) * 60;
                    else
                        wait = (14 - tm % 10) * 60;
                    break;

                case 10:
                    if (tm % 10 == 6)
                        wait = 0;
                    else if (tm < 6)
                        wait = (6 - tm) * 60;
                    else
                        wait = (16 - tm % 10) * 60;
                    break;

                case 2:
                    if (tm % 10 == 8)
                        wait = 0;
                    else if (tm < 8)
                        wait = (8 - tm) * 60;
                    else
                        wait = (18 - tm % 10) * 60;
                    break;
            }

            distance_m[dest] += wait;
            time2 = distance_m[dest];
            if (mudang == 0) {
                sb.append("[무당이 타기]\n");
                wait = wait % 3600 / 60;
                if (wait > 0) {
                    sb.append(showName(vertex[index]) + "에서 " + wait + "분 기다린 후 무당이 타기\n\n");
                }
                else {
                    sb.append(showName(vertex[index]) + "에서 바로 무당이 타기\n\n");
                }
                sb.append(showRoute(setroute(dest, saveRoute_m))+"\n");
                sb.append(show(distance_m, a, b)+"\n");
                sb.append("---------------------------------------\n");
                sb.append(resultTime(distance_m[dest])+"\n");


                if (time1 >= time2) {
                    sb.append("[무당이]방식이 더 빠릅니다.\n");
                } else {
                    sb.append("[도보]방식이 더 빠릅니다.\n");
                }
            }
        }


        return sb.toString();
    }
}
