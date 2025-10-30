//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.util.*;

class Reservation {
    private int ticketNo;
    private String passengerName;
    private String seatNo;
    private String source;
    private String destination;

    public Reservation(int ticketNo, String passengerName, String seatNo, String source, String destination) {
        this.ticketNo = ticketNo;
        this.passengerName = passengerName;
        this.seatNo = seatNo;
        this.source = source;
        this.destination = destination;
    }

    public int getTicketNo() { return ticketNo; }
    public String getPassengerName() { return passengerName; }
    public String getSeatNo() { return seatNo; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return ticketNo + "," + passengerName + "," + seatNo + "," + source + "," + destination;
    }
}

public class PlaneReservationSystem {

    private static List<Reservation> reservations = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadReservations();
        int choice;
        do {
            System.out.println("\n====== PLANE RESERVATION SYSTEM ======");
            System.out.println("1. Book Ticket");
            System.out.println("2. View All Reservations");
            System.out.println("3. Search by Ticket Number");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addReservation();
                case 2 -> viewReservations();
                case 3 -> searchReservation();
                case 4 -> cancelReservation();
                case 5 -> saveReservations();
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }

    public static void addReservation() {
        System.out.print("Enter Ticket No: ");
        int ticketNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Seat No: ");
        String seatNo = sc.nextLine();

        System.out.print("Enter Source: ");
        String source = sc.nextLine();

        System.out.print("Enter Destination: ");
        String destination = sc.nextLine();

        reservations.add(new Reservation(ticketNo, name, seatNo, source, destination));
        saveReservations();
        System.out.println("✅ Ticket Booked Successfully!");
    }

    public static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No Reservations Found!");
            return;
        }
        System.out.println("\n===== All Reservations ======");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public static void searchReservation() {
        System.out.print("Enter Ticket No to Search: ");
        int ticketNo = sc.nextInt();
        boolean found = false;

        for (Reservation r : reservations) {
            if (r.getTicketNo() == ticketNo) {
                System.out.println("Reservation Found: " + r);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("❌ Ticket Not Found!");
        }
    }

    public static void cancelReservation() {
        System.out.print("Enter Ticket No to Cancel: ");
        int ticketNo = sc.nextInt();
        Reservation remove = null;

        for (Reservation r : reservations) {
            if (r.getTicketNo() == ticketNo) {
                remove = r;
                break;
            }
        }

        if (remove != null) {
            reservations.remove(remove);
            saveReservations();
            System.out.println("✅ Ticket Cancelled Successfully!");
        } else {
            System.out.println("❌ Ticket Not Found!");
        }
    }

    public static void saveReservations() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("reservations.txt"))) {
            for (Reservation r : reservations) {
                writer.println(r);
            }
        } catch (Exception e) {
            System.out.println("Error saving reservations!");
        }
    }

    public static void loadReservations() {
        try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                reservations.add(new Reservation(
                        Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]
                ));
            }
        } catch (Exception ignored) {}
    }
}
