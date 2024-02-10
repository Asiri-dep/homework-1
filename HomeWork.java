import java.io.IOException;
import java.util.Scanner;

void main() throws IOException, InterruptedException {

    final Scanner SCANNER = new Scanner(System.in);
    final String RED = "\033[41m";
    final String GREEN = "\033[42m";
    final String RESET = "\033[0m";
    String data = "";
    int id = 0;

    mainLoop:
    while (true) {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
        System.out.println("=================================================");
        System.out.println("                  \033[34; mStudent DB\033[0m");
        System.out.println("=================================================");

        System.out.println("(1). Add New Student");
        System.out.println("(2). Delete Student");
        System.out.println("(3). Search Student");
        System.out.println("(4). View All Student");
        System.out.println("(5). Exit");

        while (true) {
            System.out.print("Choose an Option : ");
            switch (SCANNER.nextInt()) {
                case 1 -> {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();

                    addNewLoop:
                    while (true) {
                        new ProcessBuilder("clear").inheritIO().start().waitFor();
                        System.out.println("=================================================");
                        System.out.println("                \033[34; mAdd New Student\033[0m");
                        System.out.println("=================================================");

                        nameLoop:
                        while (true) {
                            System.out.print("Enter Student Name : ");
                            SCANNER.skip("\n");
                            String name = SCANNER.nextLine();
                            if (name.isBlank()) {
                                System.out.println("Blank Input. Please Re-enter");
                                continue nameLoop;
                            } else {

                                String formattedId = String.format("S%03d", ++id);
                                data += formattedId + "-" + name + "/";

                                while (true) {
                                    System.out.print("Enter PF Mark : ");
                                    int pf_mark = SCANNER.nextInt();
                                    if (pf_mark < 0 || pf_mark > 100) {
                                        System.out.println("Invalid Marks");
                                        continue;
                                    } else {
                                        while (true) {
                                            System.out.print("Enter OS Mark : ");
                                            int os_mark = SCANNER.nextInt();
                                            if (os_mark < 0 || os_mark > 100) {
                                                System.out.println("Invalid Marks");
                                                continue;
                                            } else {
                                                data += pf_mark + "*" + os_mark + ",";
                                                while (true) {
                                                    System.out.print("Do you want add another student (y/n) : ");
                                                    switch (SCANNER.next()) {
                                                        case "y" -> {
                                                            continue addNewLoop;
                                                        }
                                                        case "n" -> {
                                                            continue mainLoop;
                                                        }
                                                        default -> {
                                                            System.out.println("Invalid Input");
                                                            continue;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                case 2 -> {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("=================================================");
                    System.out.println("                \033[34; mDelete Student\033[0m");
                    System.out.println("=================================================");
                    System.out.println("\n\n");

                    while (true) {
                        System.out.print("Enter Student ID to Delete (ID format like 'S001'): ");
                        String deleteSt = SCANNER.next();
                        if (data.contains(deleteSt)) {
                            int startIndex = data.indexOf(deleteSt);
                            String stDetail = data.substring(startIndex, data.indexOf(",", startIndex + 1) + 1);
                            data = data.replace(stDetail, "");
                            System.out.println("Student Delete Successfully");
                        } else {
                            System.out.println(deleteSt.isBlank() ? "Blank Input. Please Re-Enter" : "Student does not Exist");
                        }

                        System.out.print("Do you want delete another student (y/n) : ");

                        if (SCANNER.next().equalsIgnoreCase("n")) continue mainLoop;
                    }

                }
                case 3 -> {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("=================================================");
                    System.out.println("                \033[34; mSearch Student\033[0m");
                    System.out.println("=================================================");
                    System.out.println("\n");
                    while (true) {
                        System.out.print("Enter Student ID to Search (ID format like 'S001'): ");
                        String student = SCANNER.next();
                        if (data.contains(student)) {
                            int startIndex = data.indexOf(student);
                            String stDetail = data.substring(startIndex, data.indexOf(",", startIndex + 1) + 1);

                            System.out.println(STR."Student ID : \{stDetail.substring(0, stDetail.indexOf("-"))}");
                            System.out.println(STR."Student Name : \{stDetail.substring(stDetail.indexOf("-")+1, stDetail.indexOf("/"))}");
                            System.out.println(STR."PF Marks : \{stDetail.substring(stDetail.indexOf("/")+1, stDetail.indexOf("*"))}");
                            System.out.println(STR."OS Marks : \{stDetail.substring(stDetail.indexOf("*")+1, stDetail.indexOf(","))}");

                        } else {
                            System.out.println(student.isBlank() ? "Blank Input. Please Re-Enter" : "Student does not Exist");
                        }
                        System.out.print("Do you want search another student (y/n) : ");
                        if (SCANNER.next().equalsIgnoreCase("n")) continue mainLoop;

                    }
                }

                case 4 -> {

                    final String LINE = STR."+\{"-".repeat(4)}+\{"-".repeat(20)}+\{"-".repeat(10)}+\{"-".repeat(10)}+\{"-".repeat(13)}+\{"-".repeat(15)}+\{"-".repeat(8)}+";
                    final String ROW = STR."|%-4s|%-20s|%-10s|%-10s|%-13s|%-15s|%-8s\{RESET}|\n";
                    int commaIndex = -1;
                    int maximumMarks = 0;
                    int minimumMarks = 200;
                    int total;

                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("========================================================================================");
                    System.out.println("                                 \033[34; mView All Student\033[0m");
                    System.out.println("========================================================================================");
                    System.out.println("\n");

                    System.out.println(LINE);
                    System.out.printf(ROW, "ID","Name","PF Marks","OS Marks","Total Marks", "Average Marks","Status");
                    System.out.println(LINE);

                    detail:
                    while (true) {
                        int stCount = data.isBlank() ? 0 : data.length() - data.replace(",", "").length();
                        if(stCount == 0){
                            System.out.printf("|%-86s|\n","No Data Available");
                            System.out.println(LINE);
                        }

                        for (int i = 0; i < stCount; i++) {
                            String student = data.substring(++commaIndex, commaIndex = data.indexOf(",", commaIndex));
                            String pfMarks = student.substring(student.indexOf("/")+1 , student.indexOf("*"));
                            String osMarks = student.substring(student.indexOf("*")+1);
                            total = Integer.parseInt(pfMarks) + Integer.parseInt(osMarks);
                            maximumMarks = (total > maximumMarks) ? total : maximumMarks;
                            minimumMarks = (total < minimumMarks) ? total : minimumMarks;
                            if(i == stCount-1) commaIndex = -1;

                        }
                        for (int i = 0; i < stCount; i++) {
                            String student = data.substring(++commaIndex, commaIndex = data.indexOf(",", commaIndex));
                            String stId = student.substring(0, student.indexOf("-"));
                            String stName = student.substring(student.indexOf("-")+1 , student.indexOf("/"));
                            String pfMarks = student.substring(student.indexOf("/")+1 , student.indexOf("*"));
                            String osMarks = student.substring(student.indexOf("*")+1);
                            int stTotal = Integer.parseInt(pfMarks) + Integer.parseInt(osMarks);
                            double average = stTotal/2.;
                            char grade = (average >= 75) ? 'A':(average >= 65) ? 'B':(average >= 55) ? 'C':(average >= 45) ? 'S' :'F';

                            if(stTotal== maximumMarks)System.out.printf(GREEN+ROW+RESET, stId, stName, pfMarks,osMarks,stTotal,average,grade);
                            else if (stTotal== minimumMarks)System.out.printf(RED+ROW+RESET, stId, stName, pfMarks,osMarks,stTotal,average,grade);
                            else System.out.printf(ROW, stId, stName, pfMarks,osMarks,stTotal,average,grade);

                            System.out.println(LINE);
                        }

                        System.out.print("Press 1 to go back , press 2 to exit program : ");
                        switch (SCANNER.next()) {
                            case "1" -> {continue mainLoop;}
                            case "2"->System.exit(0);
                        }

                    }

                }

                case 5 -> System.exit(0);

                default -> System.out.println("\033[31mInvalid input. Please Enter a 1 to 5 Number\033[0m");


            }
        }
    }


}