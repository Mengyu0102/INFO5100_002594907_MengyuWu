
import java.util.*;

public class OnlineStoreSystem {
    static String[] orderData = {
        "John,Laptop,1,899.99", "Sarah,Mouse,2,25.50", "Mike,Keyboard,1,75.00",
        "John,Monitor,1,299.99", "Sarah,Laptop,1,899.99", "Lisa,Mouse,3,25.50",
        "Mike,Headphones,1,150.00", "John,Mouse,1,25.50", "Lisa,Keyboard,2,75.00",
        "Sarah,Monitor,2,299.99", "Mike,Monitor,1,299.99", "Lisa,Headphones,1,150.00"
    };

    public static void main(String[] args) {
        System.out.println("=== ONLINE STORE ORDER PROCESSING SYSTEM ===\n");

        step1_ArrayList();
        step2_HashSet();
        step3_TreeSet();
        step4_HashMap();
        step5_Queue();
        step6_Stack();

        System.out.println("\nGoal: Learn when and why to use each data structure in real programming scenarios!");
    }

    static void step1_ArrayList() {
        System.out.println("STEP 1: Managing orders with ArrayList");
        ArrayList<String> orders = new ArrayList<>();
        for (String o : orderData) orders.add(o);

        System.out.println("Total orders: " + orders.size());
        System.out.println("First 3 orders:");
        for (int i = 0; i < Math.min(3, orders.size()); i++) {
            System.out.println("  " + orders.get(i));
        }
        System.out.println();
    }

    static void step2_HashSet() {
        System.out.println("STEP 2: Finding customers with HashSet");
        HashSet<String> customers = new HashSet<>();
        for (String o : orderData) {
            String[] parts = o.split(",");
            customers.add(parts[0]);
        }
        System.out.println("Unique customers: " + customers);
        System.out.println("Total customers: " + customers.size());
        System.out.println();
    }

    static void step3_TreeSet() {
        System.out.println("STEP 3: Sorting products with TreeSet");
        TreeSet<String> products = new TreeSet<>();
        for (String o : orderData) {
            String[] parts = o.split(",");
            products.add(parts[1]);
        }
        System.out.println("Sorted products: " + products);
        System.out.println("Total products: " + products.size());
        System.out.println();
    }

    static void step4_HashMap() {
        System.out.println("STEP 4: Calculating totals with HashMap");

        HashMap<String, Double> totalByCustomer = new HashMap<>();
        HashMap<String, Integer> qtyByProduct = new HashMap<>();

        for (String o : orderData) {
            String[] p = o.split(",");
            String customer = p[0];
            String product = p[1];
            int qty = Integer.parseInt(p[2]);
            double price = Double.parseDouble(p[3]);

            double amount = qty * price;
            totalByCustomer.put(customer, totalByCustomer.getOrDefault(customer, 0.0) + amount);
            qtyByProduct.put(product, qtyByProduct.getOrDefault(product, 0) + qty);
        }

        System.out.println("Total spent by customer:");
        for (Map.Entry<String, Double> e : totalByCustomer.entrySet()) {
            System.out.printf("  %s: $%.2f\n", e.getKey(), e.getValue());
        }

        System.out.println("\nTotal quantity sold per product:");
        for (Map.Entry<String, Integer> e : qtyByProduct.entrySet()) {
            System.out.printf("  %s: %d\n", e.getKey(), e.getValue());
        }
        System.out.println();
    }

    static void step5_Queue() {
        System.out.println("STEP 5: Processing big orders with Queue (orders >= $200)");
        Queue<String> bigOrders = new LinkedList<>();

        for (String o : orderData) {
            String[] p = o.split(",");
            int qty = Integer.parseInt(p[2]);
            double price = Double.parseDouble(p[3]);
            if (qty * price >= 200.0) {
                bigOrders.add(o);
            }
        }

        System.out.println("Processing queue (FIFO):");
        while (!bigOrders.isEmpty()) {
            String proc = bigOrders.poll();
            System.out.println("  Processing: " + proc);
        }
        System.out.println();
    }

    static void step6_Stack() {
        System.out.println("STEP 6: Handling returns with Stack (LIFO)");
        Stack<String> returns = new Stack<>();

        String[] sampleReturns = {
            "Lisa,Keyboard,2,75.00",
            "John,Mouse,1,25.50",
            "Mike,Monitor,1,299.99"
        };

        for (String r : sampleReturns) returns.push(r);

        System.out.println("Processing returns (LIFO):");
        while (!returns.isEmpty()) {
            String ret = returns.pop();
            System.out.println("  Processing return: " + ret);
        }
        System.out.println();
    }
}
